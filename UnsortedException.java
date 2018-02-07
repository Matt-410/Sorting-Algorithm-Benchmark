/**
 * File Name: UnsortedException.java
 * Date: January 26, 2018
 * Author: Matt Huffman
 * Course:
 * Assignment:
 * Purpose: This is the custom exception UnsortedException to be thrown when the data fails to run the sort methods
 * properly.
 * Created Using: IntelliJ IDEA
 */

class UnsortedException extends Exception {

    public UnsortedException(String message) {
        super(message);
    }
}