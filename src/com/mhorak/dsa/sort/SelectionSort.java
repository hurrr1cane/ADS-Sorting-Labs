package com.mhorak.dsa.sort;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that implements the Selection Sort algorithm for sorting an array of comparable elements.
 */
public class SelectionSort extends Sort {

    /**
     * Constructs a SelectionSort instance with the given array of numbers.
     */
    public SelectionSort() {
        startOfSorting = LocalTime.now();
        endOfSorting = LocalTime.now();
    }

    /**
     * Sorts the array with steps and returns a list of intermediate sorting states.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    @Override
    public ArrayList<Integer[]> sortLabWithSteps(Integer[] arrayOfNumbers) {
        ArrayList<Integer[]> steps = new ArrayList<>();

        startOfSorting = LocalTime.now();

        for (int i = 0; i < arrayOfNumbers.length; i++) {
            int indexOfLowest = findMin(i, arrayOfNumbers);
            swap(i, indexOfLowest, arrayOfNumbers);

            steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));
        }

        endOfSorting = LocalTime.now();

        return steps;
    }

    /**
     * Sorts the array with steps and returns a list of intermediate sorting states.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    public ArrayList<Double[]> sortIndividualWithSteps(Double[] arrayOfNumbers) {
        ArrayList<Double[]> steps = new ArrayList<>();

        startOfSorting = LocalTime.now();

        for (int i = 0; i < arrayOfNumbers.length; i++) {
            int indexOfLowest = findMin(i, arrayOfNumbers);
            swap(i, indexOfLowest, arrayOfNumbers);

            steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));
        }

        endOfSorting = LocalTime.now();

        return steps;
    }

    /**
     * Sorts the array in-place and returns the sorted array.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return The sorted array.
     */
    @Override
    public Integer[] sortLab(Integer[] arrayOfNumbers) {
        startOfSorting = LocalTime.now();

        for (int i = 0; i < arrayOfNumbers.length; i++) {
            int indexOfLowest = findMin(i, arrayOfNumbers);
            swap(i, indexOfLowest, arrayOfNumbers);
        }

        endOfSorting = LocalTime.now();

        return arrayOfNumbers;
    }

    /**
     * Sorts the array in-place and returns the sorted array.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array.
     */
    public Double[] sortIndividual(Double[] arrayOfNumbers) {
        startOfSorting = LocalTime.now();

        for (int i = 0; i < arrayOfNumbers.length; i++) {
            int indexOfLowest = findMin(i, arrayOfNumbers);
            swap(i, indexOfLowest, arrayOfNumbers);
        }

        endOfSorting = LocalTime.now();

        return arrayOfNumbers;
    }

    /**
     * This method is not implemented for Double[][] arrays.
     *
     * @param arrayOfNumbers The two-dimensional array of Double elements.
     * @return An empty two-dimensional array (not implemented).
     */
    @Override
    public Double[][] sortIndividual(Double[][] arrayOfNumbers) {
        return new Double[0][];
    }

    /**
     * Swaps two elements in the array.
     *
     * @param index1 The index of the first element to be swapped.
     * @param index2 The index of the second element to be swapped.
     */
    private void swap(int index1, int index2, Comparable[] arrayOfNumbers) {
        Comparable temp = arrayOfNumbers[index1];

        arrayOfNumbers[index1] = arrayOfNumbers[index2];
        arrayOfNumbers[index2] = temp;
    }

    /**
     * Finds the index of the minimum element in the array starting from a given index.
     *
     * @param from The starting index for finding the minimum element.
     * @return The index of the minimum element.
     */
    private int findMin(int from, Comparable[] arrayOfNumbers) {
        Comparable lowest = arrayOfNumbers[from];
        int indexOfLowest = from;

        for (int i = from + 1; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[i].compareTo(lowest) < 0) {
                lowest = arrayOfNumbers[i];
                indexOfLowest = i;
            }
        }

        return indexOfLowest;
    }
}