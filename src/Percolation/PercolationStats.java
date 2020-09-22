package Percolation;/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholdList;
    private final int trials;
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stdDev;
    private double confidenceLo;
    private double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials < 1) {
            throw new IllegalArgumentException("input number should both positive");
        }
        this.thresholdList = new double[trials];
        this.trials = trials;
        int counter = 0;
        while (trials != counter) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row,col)) {
                    percolation.open(row, col);
                }
            }
            thresholdList[counter] = (double) percolation.numberOfOpenSites() / (double) (n * n);
            percolation = new Percolation(n);
            counter++;
        }
        this.mean = StdStats.mean(thresholdList);
        this.stdDev = StdStats.stddev(thresholdList);

        confidenceHi = this.mean + CONFIDENCE_95 * this.stdDev / Math.sqrt(trials);
        confidenceLo = this.mean - CONFIDENCE_95 * this.stdDev / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

// test client (see below)
    public static void main(String[] args) {
//        int n = Integer.;
//        int trials = 20;
//        PercolationStats ps = new PercolationStats(n, trials);
//        StdOut.println("mean 				   = " + ps.mean());
//        StdOut.println("stddev 				   = " + ps.stddev());
//        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}
