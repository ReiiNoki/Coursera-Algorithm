package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufForFull; // handle backwash
    private final int number; // input number n to create a n * n uf table
    private final int ufSize;
    private boolean[] isOpen;
    private final int bottom; // index of virtual bottom site
    private final int top; // index of virtual top site
    private int numOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a number bigger than 0");
        }
        number = n;
        ufSize = number * number + 2;
        uf = new WeightedQuickUnionUF(ufSize);
        ufForFull = new WeightedQuickUnionUF(ufSize - 1);
        isOpen = new boolean[ufSize];
        isOpen[0] = false;
        bottom = ufSize - 1;
        top = 0;
        numOpenSites = 0;
    }

    private int transferToIndex(int row, int col) {
        cornerCheck(row, col);
        return (row - 1) * number + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = transferToIndex(row, col);
        if (isOpen[index]) {
            return;
        }
        numOpenSites++;
        isOpen[index] = true;
        if (row - 1 > 0 && isOpen(row-1, col)) { // up
            uf.union(index, transferToIndex(row - 1, col));
            ufForFull.union(index, transferToIndex(row - 1, col));
        }
        if (row < number && isOpen(row + 1, col)) { // down
            uf.union(index, transferToIndex(row + 1, col));
            ufForFull.union(index, transferToIndex(row + 1, col));
        }
        if (col - 1 > 0 && isOpen(row, col - 1)) { // left
            uf.union(index, transferToIndex(row, col - 1));
            ufForFull.union(index, transferToIndex(row, col - 1));
        }
        if (col < number && isOpen(row, col + 1)) { // right
            uf.union(index, transferToIndex(row, col + 1));
            ufForFull.union(index, transferToIndex(row, col + 1));
        }
        if (row == 1) {
            uf.union(index, 0);
            ufForFull.union(index, 0);
        }
        if (row == number) {
            uf.union(index, ufSize - 1);
        }
    }

    private void cornerCheck(int row, int col) {
        if (row > number || row <= 0) {
            throw new IllegalArgumentException("row index" + row + "is out of range");
        }
        if (col > number || col <= 0) {
            throw new IllegalArgumentException("column index" + col + "is out of range");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = transferToIndex(row, col);
        return isOpen[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = transferToIndex(row, col);
        return ufForFull.find(top) == ufForFull.find(index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // test client (optional)
//    public static void main(String[] args) {
//
//    }
}
