package com.mhorak.dsa.sort;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that implements the Selection Sort algorithm for sorting an array of comparable elements.
 *
 * @param <T> The type of elements in the array, which must implement the Comparable interface.
 */
public class SelectionSort<T extends Comparable<T>> extends Sort<T> {

    /**
     * Constructs a SelectionSort instance with the given array of numbers.
     *
     * @param arrayOfNumbers The array of numbers to be sorted.
     */
    public SelectionSort(T[] arrayOfNumbers) {
        this.countOfNumbers = arrayOfNumbers.length;
        this.arrayOfNumbers = arrayOfNumbers;

        startOfSorting = LocalTime.now();
        endOfSorting = LocalTime.now();
    }

    /**
     * Sorts the array with steps and returns a list of intermediate sorting states.
     *
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    @Override
    public ArrayList<T[]> sortWithSteps() {
        ArrayList<T[]> steps = new ArrayList<>();

        startOfSorting = LocalTime.now();

        for (int i = 0; i < countOfNumbers; i++) {
            int indexOfLowest = findMin(i);
            swap(i, indexOfLowest);

            steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));
        }

        endOfSorting = LocalTime.now();

        return steps;
    }

    /**
     * Sorts the array in-place and returns the sorted array.
     *
     * @return The sorted array.
     */
    @Override
    public T[] sort() {
        startOfSorting = LocalTime.now();

        for (int i = 0; i < countOfNumbers; i++) {
            int indexOfLowest = findMin(i);
            swap(i, indexOfLowest);
        }

        endOfSorting = LocalTime.now();

        return arrayOfNumbers;
    }

    /**
     * Swaps two elements in the array.
     *
     * @param index1 The index of the first element to be swapped.
     * @param index2 The index of the second element to be swapped.
     */
    private void swap(int index1, int index2) {
        T temp = arrayOfNumbers[index1];

        arrayOfNumbers[index1] = arrayOfNumbers[index2];
        arrayOfNumbers[index2] = temp;
    }

    /**
     * Finds the index of the minimum element in the array starting from a given index.
     *
     * @param from The starting index for finding the minimum element.
     * @return The index of the minimum element.
     */
    private int findMin(int from) {
        T lowest = arrayOfNumbers[from];
        int indexOfLowest = from;

        for (int i = from + 1; i < countOfNumbers; i++) {
            if (arrayOfNumbers[i].compareTo(lowest) < 0) {
                lowest = arrayOfNumbers[i];
                indexOfLowest = i;
            }
        }

        return indexOfLowest;
    }
}