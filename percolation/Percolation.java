/* *****************************************************************************
 *  Name: Edward Cacho Casas
 *  Date: 23 nov 2019
 *  Description: Percolation solution
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid; // false = blocked, true = open
    private final int size;
    private final int virtualTopRoot;
    private final int virtualBottomRoot;
    private int openSites;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        int len = n*n+2;
        uf = new WeightedQuickUnionUF(len);
        virtualTopRoot = len-2;
        virtualBottomRoot = len-1;
        grid = new boolean[n][n];
        size = n;
        openSites = 0;
        makeVirtualRootConnections();
    }

    private void makeVirtualRootConnections() {
        for (int i = 0; i < size; i++) {
            int last = size*size-1 -i;
            uf.union(i, virtualTopRoot);
            uf.union(last, virtualBottomRoot);
        }
    }

    private void validateInput(int row, int col) {
        row = row-1;
        col = col-1;
        if (row < 0 || col < 0 ||
                row >= grid.length || col >= grid.length)
        {
            String msg = String.format("row = %d, col = %d", row, col);
            throw new IllegalArgumentException(msg);
        }
    }

    private int getIndexUF(int row, int col) {
        return grid.length * row + col;
    }

    private void union(int row, int col, int row2, int col2) {
        int p = getIndexUF(row, col);
        int q = getIndexUF(row2, col2);

        uf.union(p, q);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col);

        int x = row-1;
        int y = col-1;

        if (grid[x][y]) {
            return;
        }

        grid[x][y] = true;
        openSites++;

        // verify neighbors

        // top
        if (row > 1 && isOpen(row-1, col)) {
            union(x-1, y, x, y);
        }
        // left
        if (col > 1 && isOpen(row, col-1)) {
            union(x, y-1, x, y);
        }

        // right
        if (col < size && isOpen(row, col+1)) {
            union(x, y+1, x, y);
        }

        // bottom
        if (row < size  && isOpen(row+1, col)) {
            union(x+1, y, x, y);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateInput(row, col);
        row = row-1;
        col = col-1;
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);

        if (!isOpen(row, col)) {
           return false;
        }

        int p = getIndexUF(row-1, col-1);
        return uf.connected(p, virtualTopRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(virtualTopRoot, virtualBottomRoot);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            p.open(row, col);
            p.isOpen(row, col);
            p.percolates();
            p.numberOfOpenSites();
            p.isFull(row, col);
        }
        StdOut.printf("Open sites: %d\n", p.numberOfOpenSites());
        StdOut.printf("Percolate: %s",
                      String.valueOf(p.percolates()));
    }
}