/**
 * File Name: BubbleSort.java
 * Date: January 26, 2018
 * Author: Matt Huffman
 * Course:
 * Assignment:
 * Purpose: This is the sort class for the bubble sort algorithm.
 * Created Using: IntelliJ IDEA
 */

public class BubbleSort implements SortInterface{

    private int count;
    private long time;

    public BubbleSort() {
        count = 0;
        time = 0;
    }

    // This method begins the recursive sort using the method required from the interface.
    public void recursiveTime(int[] array) throws UnsortedException {
        count = 0;
        long startTime = System.nanoTime();
        recursiveSort(array);
        time = System.nanoTime() - startTime;
    }

    public void recursiveSort(int[] array) {
        count++;
        boolean swap = false;
        for(int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i+1]) {
                int temp = array[i];
                array[i] = array[i+1];
                array[i+1] = temp;
                swap = true;
            }
        }
        if (swap) recursiveSort(array);
    }

    // Iterative bubble sort algorithm. Source: GeeksForGeeks website, code contributed by Rajat Mishra at:
    // https://www.geeksforgeeks.org/bubble-sort/
    public void iterativeSort(int[] array) throws UnsortedException {

        long startTime = System.nanoTime();
        for (int i = 0; i < (array.length-1); i++) {
            for (int j = 0; j < (array.length-i-1); j++) {
                if (array[j] > array[j+1]) {
                    count++; // Tracks the number of times a swap is made.
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        time = System.nanoTime() - startTime;
    }

    public int getCount() {
        return count;
    }

    public long getTime() {
        return time;
    }
}