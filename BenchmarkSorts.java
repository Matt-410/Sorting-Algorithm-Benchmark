/**
 * File Name: BenchmarkSorts.java
 * Date: January 26, 2018
 * Author: Matt Huffman
 * Course:
 * Assignment:
 * Purpose: This is the class that creates the data sets, runs the sorts, runs the benchmark, calculates the
 * benchmark data and displays the data.
 * Created Using: IntelliJ IDEA
 */

import java.io.*;
import java.util.Random;

class BenchmarkSorts {

    private int[] setSizes;
    // This specifies how many times to create random data and sort on each set size.
    private final int runsPerDataSetSize = 50;
    // Stores the raw count and time data for each individual sort of every array.
    private long[][][] recursiveData;
    private long[][][] iterativeData;
    // Stores the calculated data of mean and coefficients of variation of count and time for each set size.
    private double[][] recursiveProcessedData;
    private double[][] iterativeProcessedData;
    private MergeSort sort;
    private File file;


    // Constructor
    BenchmarkSorts(int[] sizes) {
        this.setSizes = sizes;
        sort = new MergeSort();
    }

    // Method to run the sorts.
    void runSorts() {

        // Create the file to store the verification printout.
        file = new File("VerifySort.txt");
        recursiveData = new long[setSizes.length][2][runsPerDataSetSize];
        iterativeData = new long[setSizes.length][2][runsPerDataSetSize];

        // Check to see if file exists and delete so the file does not get appended to on each run of this program.
        if (file.delete()) {
            System.out.println("Deleted previous VerifySort.txt file.\n");
        } else {
            System.out.println("VerifySort.txt file created.\n");
        }

        // Outer loop for each data set size.
        for (int i = 0; i < setSizes.length; i++) {
            // Inner loop to run a set number of sorts of each size.
            for (int j = 0; j < runsPerDataSetSize; j++) {
                int[] dataSet = generateArray(setSizes[i]);
                int[] recursiveSet = new int[dataSet.length];
                int[] iterativeSet = new int[dataSet.length];
                System.arraycopy(dataSet, 0, recursiveSet, 0, dataSet.length);
                System.arraycopy(dataSet, 0, iterativeSet, 0, dataSet.length);

                try {
                    sort.recursiveSort(recursiveSet);
                    recursiveData[i][0][j] = sort.getCount();
                    recursiveData[i][1][j] = sort.getTime();
                    sort.iterativeSort(iterativeSet);
                    iterativeData[i][0][j] = sort.getCount();
                    iterativeData[i][1][j] = sort.getTime();
                } catch (UnsortedException e) {
                    System.out.println("Array Not Sorted: " + e.getMessage());
                }
                // Will print out the set that half the total sets to a file for verification the sort works.
                if (j == runsPerDataSetSize/2) {
                    printArray(dataSet, recursiveSet, iterativeSet);
                }
            }
        }
    } // End runSorts method.

    // Method to generate the array with random numbers within a set range specified by size parameter.
    private int[] generateArray(int size) {

        int[] dataSet = new int[size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            dataSet[i] = rand.nextInt(size);
        }
        return dataSet;
    } // End generateArray method.

    // Method to print the contents of the original unsorted array and the recursive and iterative sorted arrays to a
    // file for verification that the sort works.
    private void printArray(int unsorted[], int recSorted[], int iterSorted[]) {
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            pw.println();
            pw.println("Showing the contents of the unsorted, recursive sorted, and iterative sorted arrays, for data" +
                    " set size: " + unsorted.length);
            pw.println();
            pw.printf("%12s%12s%12s%n", "Unsorted", "Recursive", "Iterative");
            pw.printf("%12s%12s%12s%n", "-----------", "-----------", "-----------");
            for (int i = 0; i < unsorted.length; i++) {
                pw.printf("%12s%12s%12s%n", unsorted[i], recSorted[i], iterSorted[i]);
            }
            pw.println();
            pw.close();
        } catch (IOException er) {
            System.out.println("File Not Found: " + er.getMessage());
        }
    }// End printArray method.

    // Method to calculate and store the mean and coefficient of variation data.
    void calculateData() {

        // Stores the sums.
        long a, b, c, d;
        // Stores the mean data.
        double s, t, u, v;
        // Used to calculate and store the standard deviation.
        double w, x, y, z;
        // Used to convert time to milliseconds.
        double xx, yy;
        recursiveProcessedData = new double[setSizes.length][4];
        iterativeProcessedData = new double[setSizes.length][4];

        // Get total sum for mean.
        for (int i = 0; i < setSizes.length; i++) {
            a = b = c = d = 0;
            w = x = y = z = 0;
            for (int j = 0; j < runsPerDataSetSize; j++) {
                a += recursiveData[i][0][j];
                b += recursiveData[i][1][j];
                c += iterativeData[i][0][j];
                d += iterativeData[i][1][j];
            }
            // Calculate and store the mean for recursive and iterative count and time.
            s = (double) a / runsPerDataSetSize;
            t = (double) b / runsPerDataSetSize;
            xx = t / 1000000;
            u = (double) c / runsPerDataSetSize;
            v = (double) d / runsPerDataSetSize;
            yy = v / 1000000;
            recursiveProcessedData[i][0] = s;
            recursiveProcessedData[i][2] = xx;
            iterativeProcessedData[i][0] = u;
            iterativeProcessedData[i][2] = yy;

            // Calculate the standard deviation - sums of (data - mean)^2.
            for (int k = 0; k < runsPerDataSetSize; k++) {
                w += ((recursiveData[i][0][k] - s) * (recursiveData[i][0][k] - s));
                x += ((recursiveData[i][1][k] - t) * (recursiveData[i][1][k] - t));
                y += ((iterativeData[i][0][k] - u) * (iterativeData[i][0][k] - u));
                z += ((iterativeData[i][1][k] - v) * (iterativeData[i][1][k] - v));
            }

            // Finish the calculation to get the standard deviation, Sqrt(sum / number of items in set - 1)
            w = Math.sqrt(w / (runsPerDataSetSize - 1));
            x = Math.sqrt(x / (runsPerDataSetSize - 1));
            y = Math.sqrt(y / (runsPerDataSetSize - 1));
            z = Math.sqrt(z / (runsPerDataSetSize - 1));

            // Calculate and store the coefficient of variation for recursive and iterative count and time.
            // (Standard Deviation / Mean)
            recursiveProcessedData[i][1] = w / s;
            recursiveProcessedData[i][3] = x / t;
            iterativeProcessedData[i][1] = y / u;
            iterativeProcessedData[i][3] = z / v;
        }
    } // End calculateData method.

    // Method to display the mean and COV of count and time, formatted into a table for readability. The formatting
    // values used allow for data set sizes much larger as well as the mean values to be much larger before
    // formatting gets misaligned.
    void displayReport() {
        System.out.println("--------------------------------------------------------------------------------" +
                "----------------------------------");
        System.out.printf("|%-10s|%-49s|||%-49s|%n", "Data Set", "                    Recursive",
                "                    Iterative");
        System.out.printf("|%-10s|%-49s|||%-49s|%n", "Size N", "", "");
        System.out.println("--------------------------------------------------------------------------------" +
                "----------------------------------");
        System.out.printf("|%-10s|%-15s|%-8s|%-15s|%-8s|||%-15s|%-8s|%-15s|%-8s|%n", "", "Mean", "Count", "Mean Time",
                "Time", "Mean", "Count", "Mean Time", "Time");
        System.out.printf("|%-10s|%-15s|%-8s|%-15s|%-8s|||%-15s|%-8s|%-15s|%-8s|%n", "", "Count", "COV", "" +
                "(milliseconds)", "COV", "Count", "COV", "(milliseconds)", "COV");
        System.out.println("--------------------------------------------------------------------------------" +
                "----------------------------------");
        for (int i = 0; i < setSizes.length; i++) {
            System.out.printf("|%-10s|%-15.2f|%-8.5f|%-15.4f|%-8.5f|||%-15.2f|%-8.5f|%-15.4f|%-8.5f|%n",
                    setSizes[i], recursiveProcessedData[i][0], recursiveProcessedData[i][1],
                    recursiveProcessedData[i][2], recursiveProcessedData[i][3], iterativeProcessedData[i][0],
                    iterativeProcessedData[i][1], iterativeProcessedData[i][2], iterativeProcessedData[i][3]);
        }
        System.out.println("--------------------------------------------------------------------------------" +
                "----------------------------------");
    } // End displayReport method.
}