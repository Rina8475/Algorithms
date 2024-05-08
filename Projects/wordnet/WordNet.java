/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2024/4/24
 *  Description:    Implement the class WordNet
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class WordNet {
    private Digraph graph;
    private final RedBlackBST<String, List<Integer>> bst;   // map s -> v
    private final LinkedList<String> keys;            // map v -> s
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) {
            throw new IllegalArgumentException();
        }
        bst = new RedBlackBST<String, List<Integer>>();
        keys = new LinkedList<String>();
        // read from file and construct BST, List
        initBstKeys(synsets);
        // read from file and construct graph
        initGraph(hypernyms);
        // if the graph is a rooted DAG
        if (!isGraphValid()) {
            throw new IllegalArgumentException();
        }
        // construct sap
        sap = new SAP(graph);
    }

    private void initBstKeys(String synsets) {
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");      // idx, nouns, definition
            int idx = Integer.parseInt(tokens[0]);
            keys.add(idx, tokens[1]);
            String[] nouns = tokens[1].split(" ");
            for (String noun : nouns) {
                List<Integer> lst;
                if (bst.contains(noun)) {
                    lst = bst.get(noun);
                }
                else {
                    lst = new LinkedList<Integer>();
                    bst.put(noun, lst);
                }
                lst.add(idx);
            }
        }
        in.close();
    }

    private void initGraph(String hypernyms) {
        graph = new Digraph(keys.size());
        In in = new In(hypernyms);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int start = Integer.parseInt(tokens[0]);
            for (int i = 1; i < tokens.length; i += 1) {
                int end = Integer.parseInt(tokens[i]);
                graph.addEdge(start, end);
            }
        }
        in.close();
    }

    private boolean isGraphValid() {
        DirectedCycle directedCycle = new DirectedCycle(graph);
        if (directedCycle.hasCycle()) {
            return false;
        }
        int vertex = graph.V();
        int count = 0;
        for (int i = 0; i < vertex; i += 1) {
            if (graph.outdegree(i) == 0) {
                count += 1;
            }
        }
        return count == 1;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return bst.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return bst.contains(word);
    }

    private void validateNoun(String noun) {
        if (noun == null) {
            throw new IllegalArgumentException();
        }
        if (!isNoun(noun)) {
            throw new IllegalArgumentException();
        }
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        // convert name to idx
        return sap.length(bst.get(nounA), bst.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);
        int ance = sap.ancestor(bst.get(nounA), bst.get(nounB));
        return keys.get(ance);
    }

    public String toString() {
        String s = graph.toString();
        s += keys.toString();
        return s;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet net = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println(net);
        System.out.println(net.sap("A-list", "A-team"));
        System.out.println(net.distance("A-list", "A-team"));
    }
}
