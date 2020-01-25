/* *****************************************************************************
 *  Name: Edward Cacho Casas
 *  Date: 23 nov 2019
 *  Description: Percolation solution
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96d;

    private final double[] percolates;
    private final double stddev;
    private final double mean;
    private final double trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        percolates = new double[trials];
        this.trials = trials;
        double sites = n*n;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                p.open(row, col);
                // TODO: remove this later
                // p.isOpen(row, col);
                // p.percolates();
                // p.numberOfOpenSites();
                // p.isFull(row, col);
            }
            percolates[i] = ((double) p.numberOfOpenSites()) / sites;
        }
        mean = StdStats.mean(percolates);
        stddev = StdStats.stddev(percolates);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (CONFIDENCE_95)*stddev/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (CONFIDENCE_95) * stddev / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Need two arguments");
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);


        // Stopwatch stopwatch = new Stopwatch();
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %f\n", stats.mean());
        StdOut.printf("stddev                  = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n",
                      stats.confidenceLo(), stats.confidenceHi());
        // StdOut.printf("Time: %f", stopwatch.elapsedTime());
    }
}
