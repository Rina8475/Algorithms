/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufTop;
    private boolean[] isOpen;
    private int n, openCnt;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufTop = new WeightedQuickUnionUF(n * n + 1);
        isOpen = new boolean[n * n + 2];
        isOpen[0] = true;
        isOpen[isOpen.length - 1] = true;
        openCnt = 0;
    }

    /* map a coordinate to index */
    private int index(int row, int col) {
        return (row - 1) * n + col;
    }

    private boolean isOutOfRange(int row, int col) {
        return (row <= 0) || (row > n) || (col <= 0) || (col > n);
    }

    private void merge(int drow, int dcol, int srow, int scol) {
        if (isOutOfRange(drow, dcol) || !isOpen(drow, dcol))
            return;
        uf.union(index(drow, dcol), index(srow, scol));
        ufTop.union(index(drow, dcol), index(srow, scol));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOutOfRange(row, col))
            throw new IllegalArgumentException();
        if (isOpen(row, col))
            return;
        isOpen[index(row, col)] = true;        /* open this site */
        openCnt += 1;
        /* merge with other site */
        merge(row - 1, col, row, col);
        merge(row + 1, col, row, col);
        merge(row, col - 1, row, col);
        merge(row, col + 1, row, col);
        if (row == 1) {
            uf.union(0, index(row, col));
            ufTop.union(0, index(row, col));
        }
        if (row == n)
            uf.union(isOpen.length - 1, index(row, col));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isOutOfRange(row, col))
            throw new IllegalArgumentException();
        return isOpen[index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOutOfRange(row, col))
            throw new IllegalArgumentException();
        return ufTop.find(0) == ufTop.find(index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCnt;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(isOpen.length - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p;
        int a, b, n;

        n = StdIn.readInt();
        p = new Percolation(n);
        while (!StdIn.isEmpty()) {
            a = StdIn.readInt();
            b = StdIn.readInt();
            p.open(a, b);
        }
        StdOut.println(p.percolates());
        StdOut.println(p.numberOfOpenSites());
    }
}
