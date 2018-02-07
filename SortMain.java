/**
 * File Name: SortMain.java
 * Date: January 26, 2018
 * Author: Matt Huffman
 * Course:
 * Assignment:
 * Purpose: This is the main class that contains the main method.
 * Created Using: IntelliJ IDEA
 */

import java.util.ArrayList;
import java.util.List;


public class SortMain {

    // Specify the size of warmup.
    private final static int warmupSize = 10000000;
    private static List<String> warmupList = new ArrayList<>(warmupSize);

    // Static block to fill an array with 10000000 integers to warm up the JVM before running the benchmark.
    static {
        long startTime = System.nanoTime();
        for (int i = 0; i < warmupSize; i++) {
            warmupList.add(Integer.toString(i));
        }
        double endTime = System.nanoTime() - startTime;
        System.out.println("Warm Up Time = " + endTime/1000000000 + " seconds." );
    }


    // Main method to run the program.
    public static void main(String[] args) {

        // Each element of the arrays below are the size of the data set to be sorted. Add more elements for more set
        // sizes and change the value of each element to change the size of the data set.

        // Extra sizes arrays below used for testing.

        //int[] sizes = {10, 20, 50, 75};
        int[] sizes = {10, 20, 50, 75, 125, 250, 500, 1000, 2000, 3000};
        //int[] sizes = {50, 150, 500, 1500, 5000, 15000, 50000, 150000, 500000, 1500000};
        //int[] sizes = {50, 500, 2500, 5000, 10000, 50000, 100000, 500000, 1000000, 5000000};

        BenchmarkSorts bs = new BenchmarkSorts(sizes);
        bs.runSorts();
        bs.calculateData();
        bs.displayReport();
    } // End main method.
}