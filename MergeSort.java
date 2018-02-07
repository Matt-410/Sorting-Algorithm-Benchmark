/**
 * File Name: MergeSort.java
 * Date: January 27, 2018
 * Author: Matt Huffman
 * Course: CMSC 451
 * Assignment: Project 1
 * Purpose: This is the sort class for the merge sort.
 * Source: The 2 recursive methods, iterative method, and merge method were found from
 *    http://kosbie.net/cmu/summer-08/15-100/handouts/IterativeMergeSort.java produced by David Kosbie.
 *    The recursive merge sort was further taken from Sedgewick and Wayne, Introduction To Programming In Java at
 *    http://www.cs.princeton.edu/introcs/42sort/MergeSort.java.html
 * Created Using: IntelliJ IDEA
 */

class MergeSort implements SortInterface {

    private int count;
    private long time;

    MergeSort() {
        count = 0;
        time = 0;
    }

    // This method begins the recursive sort using the method required from the interface.
    public void recursiveSort(int[] a) throws UnsortedException {
        count = 0;
        int n = a.length;
        int[] aux = new int[n];
        long startTime = System.nanoTime();
        recursiveSort(a, aux, 0, n);
        time = System.nanoTime() - startTime;
    } // End recursiveSort method.

    // This method divides the array into halves sorts each recursively and then merges them together. Count is counting
    // each time recursion occurs.
    private void recursiveSort(int[] array, int[] aux, int lo, int hi) {
        // base case
        if (hi - lo <= 1) return;
        // sort each half, recursively
        int mid = lo + (hi - lo) / 2;
        recursiveSort(array, aux, lo, mid);
        recursiveSort(array, aux, mid, hi);
        // merge back together
        merge(array, aux, lo, mid, hi);
    } // End recursiveSort method.

    // This method performs the iterative merge sort. Count is counting each time the array is broken down and merged.
    public void iterativeSort(int[] array) throws UnsortedException {
        count = 0;
        long startTime = System.nanoTime();
        int[] aux = new int[array.length];
        for (int blockSize = 1; blockSize < array.length; blockSize *= 2) {
            for (int start = 0; start < array.length; start += 2 * blockSize) {
                merge(array, aux, start, start + blockSize, start + 2 * blockSize);
            }
        }
        time = System.nanoTime() - startTime;
    } // End iterativeSort method.

    // This is the merge method used by both the iterative and recursive methods. No count is needed to compare
    // recursive vs iterative because they both use this same method.
    private void merge(int[] array, int[] aux, int lo, int mid, int hi) {
        // DK: add two tests to first verify "mid" and "hi" are in range
        if (mid >= array.length) {
            return;
        }
        if (hi > array.length) {
            hi = array.length;
        }
        int i = lo, j = mid;
        for (int k = lo; k < hi; k++) {
            if (i == mid) {
                aux[k] = array[j++];
            } else {
                if (j == hi) {
                    aux[k] = array[i++];
                } else {
                    count++;
                    if (array[j] < array[i]) {
                        aux[k] = array[j++];
                    } else {
                        aux[k] = array[i++];
                    }
                }
            }
        }
        // Copy back
        System.arraycopy(aux, lo, array, lo, hi - lo);
    } // End iterativeSort method.

    // Getter for count.
    public int getCount() {
        return count;
    } // End getCount method.

    // Getter for time.
    public long getTime() {
        return time;
    } // End getTime method.
}