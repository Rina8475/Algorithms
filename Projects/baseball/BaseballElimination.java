/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2024/5/21
 *  Description:    implements the BaseballElimination class, using the max flow
 *                  algorithms.
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {
    private int n;                /* the number of teams */
    private Map<String, Integer> team2Idx;      /* map the name of team to the index */
    private Map<Integer, String> idx2Team;      /* map the index to the name of team */
    private int[] wins;
    private int[] loss;
    private int[] left;
    private int[] dleft;        /* record the number of games left in this division */
    private int[][] against;

    private boolean[] isEliminated;
    private Set<String>[] certificates;

    /**
     * create a baseball division from given filename in format specified below
     *
     * @param filename the name of file, the input format can be seen in website
     *                 <p>
     *                 Assume the amount of team >= 1 and that the input files
     *                 are in the specified format and internally consistent
     * @throws IllegalArgumentException when <code>filename</code> is null
     */
    public BaseballElimination(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("the name of input file cannot be null");
        }
        readData(filename);
        isEliminated = new boolean[n];
        certificates = (Set<String>[]) new Set[n];

        trivialElimination();
        nontrivalElimination();
    }

    private void readData(String filename) {
        In in = new In(filename);
        String amount = in.readLine();      /* read the amount of the teams */
        n = Integer.parseInt(amount);

        team2Idx = new HashMap<>();
        idx2Team = new HashMap<>();
        wins = new int[n];
        loss = new int[n];
        left = new int[n];
        dleft = new int[n];
        against = new int[n][n];
        for (int i = 0; i < n; i += 1) {
            String line = in.readLine().trim();
            String[] tokens = line.split(" +");
            // TODO System.out.println(Arrays.toString(tokens));
            team2Idx.put(tokens[0], i);
            idx2Team.put(i, tokens[0]);
            wins[i] = Integer.parseInt(tokens[1]);
            loss[i] = Integer.parseInt(tokens[2]);
            left[i] = Integer.parseInt(tokens[3]);
            for (int j = 0; j < n; j += 1) {
                against[i][j] = Integer.parseInt(tokens[4 + j]);
                dleft[i] += against[i][j];
            }
        }
    }

    private void trivialElimination() {
        Set<String> buffer = new HashSet<>();
        for (int i = 0; i < n; i += 1) {
            for (int j = 0; j < n; j += 1) {
                isEliminated[i] = (wins[i] + left[i] < wins[j]) || isEliminated[i];
                if (wins[i] + left[i] < wins[j]) {
                    buffer.add(idx2Team.get(j));
                    System.out.println(idx2Team.get(i) + " is eliminated by " + idx2Team.get(j));
                }
            }
            if (isEliminated[i]) {
                certificates[i] = buffer;
                buffer = new HashSet<>();
            }
        }
    }

    private void nontrivalElimination() {
        double leftGames = 0.0;
        for (int i = 0; i < n; i += 1) {
            leftGames += dleft[i];
        }
        leftGames /= 2;

        for (int i = 0; i < n; i += 1) {
            if (isEliminated[i]) {
                continue;
            }
            FlowNetwork network = initFlowNetwork(i);
            FordFulkerson maxFlow = new FordFulkerson(network, n, network.V() - 1);
            isEliminated[i] = (maxFlow.value() < leftGames - dleft[i]);
            if (isEliminated[i]) {    /* elimination */
                certificates[i] = generateCertificate(maxFlow);
                System.out.println(idx2Team.get(i) + " is eliminated by " + maxFlow.value());
                // TODO System.out.println(network.toString());
            }
        }
    }

    /**
     * @param v the vertice dont take part into the generation of graph; [0, n)
     * @return a <code>FlowNetWork</code> which constructed by the following format
     * n is s, N is t, where N = 2 + (n * (n - 1)) / 2
     * [0, n)       the team nodes,         n nodes, the rest one node is <code>v</code>
     * (n, N)       the left games nodes    C{n-1}{2} = ((n-1) * (n-2)) / 2 nodes
     * The graph generated is not a connected graph, for simplicity, there rest a node for v
     */
    private FlowNetwork initFlowNetwork(int v) {
        int N = 2 + (n * (n - 1)) / 2;
        int idx = n + 1;        /* the idx is used to index the left games nodes */
        FlowNetwork graph = new FlowNetwork(N + 1);
        for (int i = 0; i < n; i += 1) {
            if (i == v) {
                continue;
            }
            /* set the edge with respect of the left games nodes */
            for (int j = i + 1; j < n; j += 1) {
                if (j == v) {
                    continue;
                }
                graph.addEdge(new FlowEdge(n, idx, against[i][j]));
                graph.addEdge(new FlowEdge(idx, i, Double.POSITIVE_INFINITY));
                graph.addEdge(new FlowEdge(idx, j, Double.POSITIVE_INFINITY));
                idx += 1;
            }
            /* set the edge with respect of the team nodes */
            graph.addEdge(new FlowEdge(i, N, wins[v] + left[v] - wins[i]));
        }
        assert idx == N : "The value of idx should be" + N + " : " + idx;
        return graph;
    }

    /**
     * generates the certificate with respect of the network
     *
     * @param flow a Max Flow object
     * @return return the certificate
     */
    private Set<String> generateCertificate(FordFulkerson flow) {
        /* checks all the team node, if the node is in the cut of s, then add this
         * team into certificate.
         * P.S. We have stayed a team node which correspond to the queried team */
        Set<String> certificate = new HashSet<>();
        for (int i = 0; i < n; i += 1) {
            if (flow.inCut(i)) {
                certificate.add(idx2Team.get(i));
            }
        }
        return certificate;
    }

    /** @return the number of teams */
    public int numberOfTeams() {
        return n;
    }

    /** @return the name of all teams */
    public Iterable<String> teams() {
        return idx2Team.values();
    }

    private void validateTeamName(String team) {
        if (team == null) {
            throw new IllegalArgumentException("The name of team must not be NULL");
        }
        if (!team2Idx.containsKey(team)) {
            throw new IllegalArgumentException("The team : " + team + " is not contained.");
        }
    }

    /**
     * @param team the name of the team
     * @return the number of wins for given <code>team</code>
     * @throws IllegalArgumentException when <code>team</code> is null or the team is not contained
     */
    public int wins(String team) {
        validateTeamName(team);
        return wins[team2Idx.get(team)];
    }

    /**
     * @param team the name of the team
     * @return the number of losses for given team
     * @throws IllegalArgumentException when <code>team</code> is null or the team is not contained
     */
    public int losses(String team) {
        validateTeamName(team);
        return loss[team2Idx.get(team)];
    }

    /**
     * @param team the name of the team
     * @return the number of remaining games for given team
     * @throws IllegalArgumentException when <code>team</code> is null or the team is not contained
     */
    public int remaining(String team) {
        validateTeamName(team);
        return left[team2Idx.get(team)];
    }


    /**
     * @param team1 the name of the team
     * @param team2 the name of the team
     * @return number of remaining games between team1 and team2
     * @throws IllegalArgumentException when <code>team1</code> or
     *                                  <code>team</code> is null or either of them are not
     *                                  contained
     */
    public int against(String team1, String team2) {
        validateTeamName(team1);
        validateTeamName(team2);
        return against[team2Idx.get(team1)][team2Idx.get(team2)];
    }

    /**
     * @param team the name of the team
     * @return if the given team is eliminated, return <code>true</code>;
     * else return <code>false</code>
     * @throws IllegalArgumentException when <code>team</code> is null or the team is not contained
     */
    public boolean isEliminated(String team) {
        validateTeamName(team);
        return isEliminated[team2Idx.get(team)];
    }

    /**
     * @param team the name of the team
     * @return subset R of teams that eliminates given team; null if not
     * eliminated
     * @throws IllegalArgumentException when <code>team</code> is null or the team is not contained
     */
    public Iterable<String> certificateOfElimination(String team) {
        validateTeamName(team);
        return certificates[team2Idx.get(team)];
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
