package com.mhorak.dsa.sort;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class implements the Shell Sort algorithm for sorting an array of comparable elements.
 */
public class ShellSort extends Sort {

    /**
     * Constructs a ShellSort instance with the given array of numbers.
     */
    public ShellSort() {

        startOfSorting = LocalTime.now();
        endOfSorting = LocalTime.now();
    }

    /**
     * Sorts the array in-place using the Shell Sort algorithm and returns the sorted array.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return The sorted array.
     */
    @Override
    public Integer[] sortLab(Integer[] arrayOfNumbers) {

        startOfSorting = LocalTime.now();

        int step = arrayOfNumbers.length / 2;

        while (step > 0) {
            for (int j = step; j < arrayOfNumbers.length; j++) {
                Integer temp = arrayOfNumbers[j];
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
     * Sorts the array of Double elements using the Shell Sort algorithm.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return An empty array (not implemented for Double[]).
     */
    @Override
    public Double[] sortIndividual(Double[] arrayOfNumbers) {
        return new Double[0];
    }

    /**
     * Sorts a two-dimensional array of Double elements by the first elements of columns using the Shell Sort algorithm.
     *
     * @param arrayOfNumbers The two-dimensional array of Double elements to be sorted.
     * @return A sorted two-dimensional array of Doubles.
     */
    public Double[][] sortIndividual(Double[][] arrayOfNumbers) {
        startOfSorting = LocalTime.now();
        int rows = arrayOfNumbers.length;
        int cols = arrayOfNumbers[0].length;

        // Transpose the array
        Double[][] transposedArray = new Double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedArray[j][i] = arrayOfNumbers[i][j];
            }
        }

        int step = transposedArray.length / 2;
        // Sort each row (which is equivalent to sorting by columns in the original array)
        while (step > 0) {
            for (int j = step; j < transposedArray.length; j++) {
                Double[] temp = transposedArray[j];
                int current = j - step;
                while (current >= 0 && temp[0] < transposedArray[current][0]) {
                    transposedArray[current + step] = transposedArray[current];
                    current -= step;
                }
                transposedArray[current + step] = temp;
            }

            step /= 2;
        }

        // Transpose the sorted array back to its original form
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                arrayOfNumbers[j][i] = transposedArray[i][j];
            }
        }

        endOfSorting = LocalTime.now();

        return arrayOfNumbers;
    }



    /**
     * Sorts a two-dimensional array of Double elements using the Shell Sort algorithm.
     * Returns a list of intermediate sorting states.
     *
     * @param arrayOfNumbers The two-dimensional array of Double elements to be sorted.
     * @return An ArrayList of two-dimensional arrays representing intermediate sorting states.
     */
    public ArrayList<Double[][]> sortIndividualWithSteps(Double[][] arrayOfNumbers) {
        ArrayList<Double[][]> steps = new ArrayList<>();

        steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));

        startOfSorting = LocalTime.now();

        int step = arrayOfNumbers.length / 2;

        while (step > 0) {
            for (int j = step; j < arrayOfNumbers.length; j++) {
                Double[] temp = arrayOfNumbers[j];
                int current = j - step;
                while (current >= 0 && temp[0] > arrayOfNumbers[current][0]) {
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

    /**
     * Sorts the array of Integer elements using the Shell Sort algorithm with intermediate steps.
     * Returns a list of intermediate sorting states.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    @Override
    public ArrayList<Integer[]> sortLabWithSteps(Integer[] arrayOfNumbers) {
        ArrayList<Integer[]> steps = new ArrayList<>();

        steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));

        startOfSorting = LocalTime.now();

        int step = arrayOfNumbers.length / 2;

        while (step > 0) {
            for (int j = step; j < arrayOfNumbers.length; j++) {
                Integer temp = arrayOfNumbers[j];
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
