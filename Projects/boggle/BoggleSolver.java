/* *****************************************************************************
 *  Name:           rina
 *  Date:           2024/6/2
 *  Description:    In this class, we assume all the parameters are not null by
 *                  default.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {
    private final static int MIN_STR_LEN = 3;

    private TSET wordSet;

    private boolean[][] marked;

    /**
     * Initializes the data structure using the given array of strings as the dictionary.
     *
     * @param dictionary Assume each word in the dictionary contains only the
     *                   uppercase letters A through Z.
     */
    public BoggleSolver(String[] dictionary) {
        wordSet = new TSET();
        for (String word : dictionary) {
            wordSet.add(word);
        }
    }

    /**
     * @param board the input board, assume the board.col() > 0 and board.row() > 0
     * @return the set of all valid words in the given Boggle board, as an Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        // finds all the string from the board
        // 1. start from every dice
        // 2. step to the neighbour dice, and using a marked array to record the trace,
        //      using a string to record the characters which has been read from board.
        // 2.1 when the next visited dice is out of boundary or has been marked, stop
        //      visiting
        // 2.2 the marked array should be refreshed after a trace is over
        // 3. when the length of string greater than 3, add this string to a set
        int row = board.rows();
        int col = board.cols();
        marked = new boolean[row][col];
        Set<String> result = new HashSet<String>();
        for (int i = 0; i < row; i += 1) {
            for (int j = 0; j < col; j += 1) {
                dfs(board, i, j, "", result);
            }
        }
        return result;
    }

    private void dfs(BoggleBoard board, int row, int col, String s, Set<String> set) {
        marked[row][col] = true;
        char c = board.getLetter(row, col);
        s += c == 'Q' ? "QU" : c;
        if (s.length() >= MIN_STR_LEN) {
            if (wordSet.contains(s)) {
                set.add(s);
            }
            else if (wordSet.get(s) == null) {
                marked[row][col] = false;
                return;
            }
        }
        for (int i = row - 1; i <= row + 1; i += 1) {
            for (int j = col - 1; j <= col + 1; j += 1) {
                if (i == row && j == col) {
                    continue;
                }
                if (canVisit(board, i, j)) {
                    dfs(board, i, j, s, set);
                }
            }
        }
        marked[row][col] = false;
    }

    /* where i corresponds to row, j corresponds to col */
    private boolean canVisit(BoggleBoard board, int i, int j) {
        return i >= 0 && i < board.rows() && j >= 0 && j < board.cols() && !marked[i][j];
    }

    /**
     * calculates the score of the given word.
     *
     * @param word the given word. Assume the word contains only the uppercase
     *             letters A through Z.
     * @return Returns the score of the given word if it is in the dictionary,
     * zero otherwise.
     */
    public int scoreOf(String word) {
        if (wordSet.contains(word)) {
            return scorceOf(word.length());
        }
        return 0;
    }

    private int scorceOf(int len) {
        if (len == 3 || len == 4) {
            return 1;
        }
        else if (len == 5) {
            return 2;
        }
        else if (len == 6) {
            return 3;
        }
        else if (len == 7) {
            return 5;
        }
        else if (len >= 8) {
            return 11;
        }
        assert false : "This line should never be reached";
        return 0;
    }

    public static void main(String[] args) {
        In in = new In("dictionary-algs4.txt");
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        // BoggleBoard board = new BoggleBoard(args[1]);
        BoggleBoard board = new BoggleBoard(50, 50);
        StdOut.println(board.toString());
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

