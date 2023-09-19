/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    /* get x[0] - 0 time, x[1] - 1 time, ... x[n] - n times.
     * xAvg = sum(x) / x.length
     * s is sample standard deviation
     * and TRIALS = x.length */
    private static final double CONFIDENCE = 1.96;
    private double[] x;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        x = new double[trials];
        for (int i = 0; i < trials; i += 1) {
            x[i] = (double) doTrial(n) / Math.pow(n, 2);
        }
        mean = StdStats.mean(x);
        stddev = StdStats.stddev(x);
    }

    /* 执行一次 percolation 模拟，然后返回所开启的格子数 */
    private int doTrial(int n) {
        Percolation p = new Percolation(n);
        int a, b;

        // TODO 建立一个循环 循环条件为 p not percolated
        // TODO 随机选择一个 bolcked 的格子然后开启
        // TODO 循环结束后返回 p.numberOfOpenSites()
        while (!p.percolates()) {
            a = StdRandom.uniformInt(1, n + 1);
            b = StdRandom.uniformInt(1, n + 1);
            p.open(a, b);
        }
        return p.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((CONFIDENCE * stddev()) / Math.sqrt(x.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((CONFIDENCE * stddev()) / Math.sqrt(x.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]),
                                                   Integer.parseInt(args[1]));
        StdOut.printf("%-23s = %f\n", "mean", ps.mean());
        StdOut.printf("%-23s = %f\n", "stddev", ps.stddev());
        StdOut.printf("%-23s = [%f, %f]", "95% confidence interval", ps.confidenceLo(),
                      ps.confidenceHi());
    }
}
