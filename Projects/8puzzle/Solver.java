/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2023/12/2
 *  Description:    Implement Solver Class
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    private boolean isSolveable;
    private SearchNode goalNode;
    private ArrayList<Board> solution;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        SearchNode prev;
        boolean isTwin;
        int depth;
        int distance;

        public SearchNode(Board board, SearchNode prev, boolean isTwin, int depth) {
            this.board = board;
            this.prev = prev;
            this.isTwin = isTwin;
            this.depth = depth;
            this.distance = board.manhattan();
        }

        public int compareTo(SearchNode that) {
            if (this.distance + this.depth == that.distance + that.depth)
                return Integer.compare(this.distance, that.distance);
            return Integer.compare(this.distance + this.depth,
                                   that.distance + that.depth);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        SearchNode node;
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, null, false, 0));
        pq.insert(new SearchNode(initial.twin(), null, true, 0));

        while (true) {
            node = pq.delMin();
            if (node.board.isGoal())
                break;
            for (Board b : node.board.neighbors())
                if (node.prev == null || !b.equals(node.prev.board))
                    pq.insert(new SearchNode(b, node, node.isTwin, node.depth + 1));
        }
        isSolveable = !node.isTwin;
        goalNode = isSolveable ? node : null;
        solution = solve();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolveable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolveable ? goalNode.depth : -1;
    }

    private ArrayList<Board> solve() {
        if (!isSolveable)
            return null;
        ArrayList<Board> result = new ArrayList<Board>();
        SearchNode node = goalNode;
        while (node != null) {
            result.add(node.board);
            node = node.prev;
        }
        Collections.reverse(result);
        return result;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return isSolveable ? new ArrayList<Board>(solution) : null;
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] test = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 },
                };
        Board board = new Board(test);
        Solver solver = new Solver(board);
        assert solver.isSolvable();
        assert solver.moves() == 4;
        for (Board b : solver.solution())
            System.out.println(b);
    }
}
