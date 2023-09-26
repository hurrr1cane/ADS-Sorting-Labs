package com.mhorak.dsa.sort;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements the Shell Sort algorithm for sorting an array of comparable elements.
 *
 * @param <T> The type of elements to be sorted, which must implement the Comparable interface.
 */
public class ShellSort<T extends Comparable<T>> extends Sort<T> {

    /**
     * Constructs a ShellSort instance with the given array of numbers.
     *
     * @param arrayOfNumbers The array of numbers to be sorted.
     */
    public ShellSort(T[] arrayOfNumbers) {
        this.countOfNumbers = arrayOfNumbers.length;
        this.arrayOfNumbers = arrayOfNumbers;

        startOfSorting = LocalTime.now();
        endOfSorting = LocalTime.now();
    }

    /**
     * Sorts the array and returns the sorted array.
     *
     * @return The sorted array.
     */
    @Override
    public T[] sort() {

        startOfSorting = LocalTime.now();

        int step = arrayOfNumbers.length / 2;

        while (step > 0) {
            for (int j = step; j < arrayOfNumbers.length; j++) {
                T temp = arrayOfNumbers[j];
                int current = j - step;
                while (current >= 0 && temp.compareTo(arrayOfNumbers[current]) < 0) {
                    arrayOfNumbers[current + step] = arrayOfNumbers[current];
                    current -= step;
                }
                arrayOfNumbers[current + step] = temp;
            }

            step /= 2;
        }

        endOfSorting = LocalTime.now();

        return arrayOfNumbers;
    }

    /**
     * Sorts the array with intermediate steps and returns a list of intermediate sorting states.
     *
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    @Override
    public ArrayList<T[]> sortWithSteps() {
        ArrayList<T[]> steps = new ArrayList<>();

        steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));

        startOfSorting = LocalTime.now();

        int step = arrayOfNumbers.length / 2;

        while (step > 0) {
            for (int j = step; j < arrayOfNumbers.length; j++) {
                T temp = arrayOfNumbers[j];
                int current = j - step;
                while (current >= 0 && temp.compareTo(arrayOfNumbers[current]) < 0) {
                    arrayOfNumbers[current + step] = arrayOfNumbers[current];
                    current -= step;
                }
                arrayOfNumbers[current + step] = temp;

                steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));
            }
            step /= 2;
        }

        endOfSorting = LocalTime.now();

        return steps;
    }

}
