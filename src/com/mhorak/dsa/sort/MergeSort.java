package com.mhorak.dsa.sort;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class MergeSort extends Sort {
    /**
     * Sorts a one-dimensional array of Double elements.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array of Double elements.
     */
    @Override
    public Double[] sortIndividual(Double[] arrayOfNumbers) {
        startOfSorting = LocalTime.now();
        mergeSort(arrayOfNumbers, false);
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
     * Sorts the array and returns the sorted array.
     *
     * @param arrayOfNumbers The array to be sorted.
     * @return The sorted array.
     */
    @Override
    public Integer[] sortLab(Integer[] arrayOfNumbers) {
        startOfSorting = LocalTime.now();
        mergeSort(arrayOfNumbers, true);
        endOfSorting = LocalTime.now();
        return arrayOfNumbers;
    }

    /**
     * Sorts the array with intermediate steps and returns a list of intermediate sorting states.
     *
     * @param arrayOfNumbers The array to be sorted.
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    @Override
    public ArrayList<Integer[]> sortLabWithSteps(Integer[] arrayOfNumbers) {
        ArrayList<Integer[]> steps = new ArrayList<>();

        startOfSorting = LocalTime.now();

        steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));

        mergeSortWithSteps(arrayOfNumbers, steps);

        endOfSorting = LocalTime.now();

        return steps;
    }

    /**
     * Recursively performs Merge Sort on an array of Integers while recording intermediate sorting steps.
     *
     * @param array The array of Integers to be sorted.
     * @param steps An ArrayList to store intermediate sorting states.
     */
    private void mergeSortWithSteps(Integer[] array, ArrayList<Integer[]> steps) {
        // Base case: if the array has only one element, it's considered sorted.
        if (array.length == 1) {
            return;
        } else {
            // Divide the array into two halves.
            int midIndex = array.length / 2;
            Integer[] left = new Integer[midIndex];
            Integer[] right = new Integer[array.length - midIndex];

            // Copy elements into the left and right subarrays.
            System.arraycopy(array, 0, left, 0, left.length);
            if (array.length - midIndex >= 0)
                System.arraycopy(array, midIndex, right, 0, array.length - midIndex);

            // Recursively sort the left and right subarrays.
            mergeSortWithSteps(left, steps);
            mergeSortWithSteps(right, steps);

            // Merge the sorted subarrays and store the current state in the steps ArrayList.
            merge(left, right, array, true);
            steps.add(Arrays.copyOf(array, array.length));
        }
    }

    /**
     * Recursively performs Merge Sort on an array of Comparables.
     *
     * @param array    The array of Comparables to be sorted.
     * @param byGrowth A flag indicating whether to sort in ascending or descending order.
     */
    private void mergeSort(Comparable[] array, boolean byGrowth) {
        // Base case: if the array has only one element, it's considered sorted.
        if (array.length == 1) {
            return;
        } else {
            // Divide the array into two halves.
            int midIndex = array.length / 2;
            Comparable[] left = new Comparable[midIndex];
            Comparable[] right = new Comparable[array.length - midIndex];

            // Copy elements into the left and right subarrays.
            System.arraycopy(array, 0, left, 0, left.length);
            if (array.length - midIndex >= 0)
                System.arraycopy(array, midIndex, right, 0, array.length - midIndex);

            // Recursively sort the left and right subarrays.
            mergeSort(left, byGrowth);
            mergeSort(right, byGrowth);

            // Merge the sorted subarrays.
            merge(left, right, array, byGrowth);
        }
    }

    /**
     * Merges two sorted subarrays into a single sorted array.
     *
     * @param left     The left subarray.
     * @param right    The right subarray.
     * @param array    The array to store the merged result.
     * @param byGrowth A flag indicating whether to merge in ascending or descending order.
     */
    private void merge(Comparable[] left, Comparable[] right, Comparable[] array, boolean byGrowth) {
        int leftInd = 0, rightInd = 0;

        for (int i = 0; i < array.length; i++) {
            if (leftInd == left.length) {
                array[i] = right[rightInd];
                rightInd++;
                continue;
            }
            if (rightInd == right.length) {
                array[i] = left[leftInd];
                leftInd++;
                continue;
            }
            if (byGrowth) {
                if (left[leftInd].compareTo(right[rightInd]) < 0) {
                    array[i] = left[leftInd];
                    leftInd++;
                } else {
                    array[i] = right[rightInd];
                    rightInd++;
                }
            } else {
                if (left[leftInd].compareTo(right[rightInd]) > 0) {
                    array[i] = left[leftInd];
                    leftInd++;
                } else {
                    array[i] = right[rightInd];
                    rightInd++;
                }
            }
        }
    }

}
