/* *****************************************************************************
 *  Name:           Rina
 *  Date:           2023/11/30
 *  Description:    Implement Board Class
 **************************************************************************** */

import java.util.ArrayList;

public class Board {
    private int[][] squares;
    private int dimension;
    private int hammingDistance = 0;
    private int manhattanDistance = 0;
    private int zeroI;
    private int zeroJ;

    /* create a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     * Assume 2 <= dimension < 128 */
    public Board(int[][] tiles) {
        dimension = tiles.length;
        squares = new int[dimension][dimension];

        for (int i = 0; i < dimension; i += 1) {
            for (int j = 0; j < dimension; j += 1) {
                squares[i][j] = tiles[i][j];    // copy the array
                if (tiles[i][j] == 0) {         // store the coordinate of elem ZERO
                    zeroI = i;
                    zeroJ = j;
                    continue;
                }
                if (squares[i][j] != index2goalVal(i, j))       // compute hamming distance
                    hammingDistance += 1;
                manhattanDistance += Math.abs(i - goalVal2indexI(squares[i][j])) + Math.abs(
                        j - goalVal2indexJ(squares[i][j]));     // compute manhattan distance
            }
        }
    }

    /* toString - string representation of this board */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format(" %d", squares[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /* index2goalVal - 获取 goal[i][j] 的值 */
    private int index2goalVal(int i, int j) {
        return (i * dimension + j + 1) % (dimension * dimension);
    }

    /* goalVal2indexX - 根据值 val 来获取其在目标棋盘中的 I 坐标 */
    private int goalVal2indexI(int val) {
        return ((val + dimension * dimension - 1) % (dimension * dimension)) / dimension;
    }

    /* goalVal2indexJ - 根据值 val 来获取其在目标棋盘中的 J 坐标 */
    private int goalVal2indexJ(int val) {
        return ((val + dimension * dimension - 1) % (dimension * dimension)) % dimension;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    /* hamming - number of tiles out of place */
    public int hamming() {
        return hammingDistance;
    }

    /* manhattan - sum of Manhattan distances between tiles and goal */
    public int manhattan() {
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hammingDistance == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (this == y)
            return true;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (this.dimension != that.dimension)
            return false;
        for (int i = 0; i < dimension; i += 1)
            for (int j = 0; j < dimension; j += 1)
                if (this.squares[i][j] != that.squares[i][j])
                    return false;
        return true;
    }

    /* exchangeElem - 交换 squares[i][j] 和 squares[m][n]的值 生成一个 Board 对象 */
    private Board exchElem(int i, int j, int m, int n) {
        int[][] clone = new int[dimension][dimension];

        for (int k = 0; k < dimension; k += 1)
            for (int p = 0; p < dimension; p += 1)
                clone[k][p] = squares[k][p];
        clone[i][j] = squares[m][n];
        clone[m][n] = squares[i][j];
        return new Board(clone);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> result = new ArrayList<Board>();

        if (zeroI - 1 >= 0)             // up
            result.add(exchElem(zeroI, zeroJ, zeroI - 1, zeroJ));
        if (zeroJ - 1 >= 0)             // left
            result.add(exchElem(zeroI, zeroJ, zeroI, zeroJ - 1));
        if (zeroI + 1 < dimension)      // down
            result.add(exchElem(zeroI, zeroJ, zeroI + 1, zeroJ));
        if (zeroJ + 1 < dimension)      // right
            result.add(exchElem(zeroI, zeroJ, zeroI, zeroJ + 1));
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (zeroI == 0 && zeroJ == 0)               // (0, 0) is not a titles
            return exchElem(0, dimension - 1, dimension - 1, dimension - 1);
        if (zeroI == 0 && zeroJ == dimension - 1)   // (0, dimension-1) is not a title
            return exchElem(0, 0, dimension - 1, dimension - 1);
        return exchElem(0, 0, 0, dimension - 1);
    }

    /* unit testing (not graded) */
    public static void main(String[] args) {
        Board board, copy;
        String boardStr;
        // test1
        int[][] test1 = {
                { 0, 1 },
                { 2, 3 }
        };
        board = new Board(test1);
        boardStr = "2\n 0 1\n 2 3\n";
        assert board.dimension() == 2;
        assert boardStr.equals(board.toString());
        assert board.hamming() == 3;
        assert board.manhattan() == 4;
        assert !board.isGoal();
        copy = new Board(test1);
        assert board.equals(copy);
        for (Board b : board.neighbors()) {
            System.out.println(b.toString());
        }
        System.out.println(board.twin().toString());

        // test2
        int[][] test2 = {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 },
                };
        board = new Board(test2);
        boardStr = "3\n 0 1 3\n 4 2 5\n 7 8 6\n";
        assert board.dimension() == 3;
        assert boardStr.equals(board.toString());
        assert board.hamming() == 4;
        assert board.manhattan() == 4;
        assert !board.isGoal();
        copy = new Board(test1);
        assert !board.equals(copy);
        for (Board b : board.neighbors()) {
            System.out.println(b.toString());
        }
        System.out.println(board.twin().toString());
    }
}
