/**
 * File Name: SortInterface.java
 * Date: January 26, 2018
 * Author: Matt Huffman
 * Course: CMSC 451
 * Assignment: Project 1
 * Purpose: This is the interface for the sort class.
 * Created Using: IntelliJ IDEA
 */

interface SortInterface {

    void recursiveSort(int[] array) throws UnsortedException;
    void iterativeSort(int[] array) throws UnsortedException;
    int getCount();
    long getTime();
}