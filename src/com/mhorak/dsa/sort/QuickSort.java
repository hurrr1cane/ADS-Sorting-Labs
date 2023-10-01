package com.mhorak.dsa.sort;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class implements the Quick Sort algorithm for sorting arrays of comparable elements.
 */
public class QuickSort extends Sort {
    /**
     * Sorts a one-dimensional array of Double elements.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array of Double elements.
     */
    @Override
    public Double[] sortIndividual(Double[] arrayOfNumbers) {
        startOfSorting = LocalTime.now();

        quickSort(arrayOfNumbers, 0, arrayOfNumbers.length - 1);

        endOfSorting = LocalTime.now();
        return arrayOfNumbers;
    }

    /**
     * Sorts a two-dimensional array of Double elements.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array of Double elements.
     */
    @Override
    public Double[][] sortIndividual(Double[][] arrayOfNumbers) {
        return new Double[0][];
    }

    /**
     * Sorts a one-dimensional array of Integer elements using the Quick Sort algorithm.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return The sorted array of Integer elements.
     */
    @Override
    public Integer[] sortLab(Integer[] arrayOfNumbers) {
        startOfSorting = LocalTime.now();

        quickSort(arrayOfNumbers, 0, arrayOfNumbers.length - 1);

        endOfSorting = LocalTime.now();

        return arrayOfNumbers;
    }

    /**
     * Performs the Quick Sort algorithm on an array of comparable elements.
     *
     * @param arrayOfNumbers The array to be sorted.
     * @param first          The index of the first element to be considered.
     * @param last           The index of the last element to be considered.
     */
    private void quickSort(Comparable[] arrayOfNumbers, int first, int last) {
        // Check if there is more than one element to sort
        if (first < last) {
            // Partition the array and get the pivot index
            int i = sortThePart(arrayOfNumbers, first, last);

            // Recursively sort the subarrays before and after the pivot
            quickSort(arrayOfNumbers, first, i - 1);
            quickSort(arrayOfNumbers, i + 1, last);
        }
    }

    /**
     * Partitions a portion of the array for the Quick Sort algorithm.
     *
     * @param arrayOfNumbers The array to be partitioned.
     * @param first          The index of the first element to be considered.
     * @param last           The index of the last element to be considered.
     * @return The pivot index after partitioning.
     */
    private int sortThePart(Comparable[] arrayOfNumbers, int first, int last) {
        // Choose a random pivot index within the specified range
        int pivotIndex = new Random().nextInt(first, last + 1);

        // Swap the pivot element with the last element in the range
        swap(arrayOfNumbers, pivotIndex, last);

        int i = first - 1;
        int j = first;

        // Partition the elements into two subarrays
        while (j < last) {
            // Compare each element with the pivot (last element)
            if (arrayOfNumbers[j].compareTo(arrayOfNumbers[last]) < 0) {
                i++;
                // Swap elements if the current element is less than the pivot
                swap(arrayOfNumbers, i, j);
            }
            j++;
        }

        i++;
        // Swap the pivot element to its correct position
        swap(arrayOfNumbers, i, last);
        return i;
    }

    /**
     * Performs the Quick Sort algorithm with intermediate steps on a one-dimensional array of Integer elements.
     *
     * @param arrayOfNumbers The array to be sorted.
     * @param first          The index of the first element to be considered.
     * @param last           The index of the last element to be considered.
     * @param steps          An ArrayList to store intermediate sorting states.
     */
    private void quickSortWithSteps(Integer[] arrayOfNumbers, int first, int last, ArrayList<Integer[]> steps) {

        if (first < last) {
            // Partition the array and get the pivot index
            int i = sortThePart(arrayOfNumbers, first, last);

            // Store the current state of the array in the steps ArrayList
            steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));

            // Recursively sort the subarrays before and after the pivot
            quickSortWithSteps(arrayOfNumbers, first, i - 1, steps);
            quickSortWithSteps(arrayOfNumbers, i + 1, last, steps);
        }
    }

    /**
     * Swaps two elements in an array.
     *
     * @param arrayOfNumbers The array in which elements are swapped.
     * @param first          The index of the first element to be swapped.
     * @param second         The index of the second element to be swapped.
     */
    private void swap(Comparable[] arrayOfNumbers, int first, int second) {
        Comparable temp = arrayOfNumbers[first];
        arrayOfNumbers[first] = arrayOfNumbers[second];
        arrayOfNumbers[second] = temp;
    }

    /**
     * Sorts a one-dimensional array of Integer elements with intermediate steps and returns a list of intermediate sorting states.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    @Override
    public ArrayList<Integer[]> sortLabWithSteps(Integer[] arrayOfNumbers) {
        ArrayList<Integer[]> steps = new ArrayList<>();

        startOfSorting = LocalTime.now();

        quickSortWithSteps(arrayOfNumbers, 0, arrayOfNumbers.length - 1, steps);

        endOfSorting = LocalTime.now();
        return steps;
    }
}
