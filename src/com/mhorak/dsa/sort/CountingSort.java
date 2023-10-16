package com.mhorak.dsa.sort;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class CountingSort extends Sort{
    /**
     * Sorts a one-dimensional array of Double elements.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array of Double elements.
     */
    @Override
    public Double[] sortIndividual(Double[] arrayOfNumbers) {
        return new Double[0];
    }

    /**
     * Sorts a two-dimensional array of Double elements using Counting Sort and returns the sorted array.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array of Double elements.
     */
    @Override
    public Double[][] sortIndividual(Double[][] arrayOfNumbers) {
        startOfSorting = LocalTime.now();

        // Get the dimensions of the array
        int rows = arrayOfNumbers.length;
        int cols = arrayOfNumbers[0].length;

        // Transpose the array for sorting based on the first column
        Double[][] transposedArray = new Double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedArray[j][i] = arrayOfNumbers[i][j];
            }
        }

        // Calculate the minimum and maximum values in the transposed array
        int min = (int) (transposedArray[0][0] * 10);
        int max = (int) (transposedArray[0][0] * 10);
        for (int i = 0; i < transposedArray.length; i++) {
            int value = (int) (transposedArray[i][0] * 10);
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
        }

        int length = max - min;
        int[] frequencies = new int[length + 1];

        // Calculate the frequencies of values in the first column
        for (int i = 0; i < transposedArray.length; i++) {
            frequencies[((int) (transposedArray[i][0] * 10)) - min]++;
        }

        int currentSum = frequencies[0];
        for (int i = 1; i < frequencies.length; i++) {
            currentSum += frequencies[i];
            frequencies[i] = currentSum;
        }

        for (int i = frequencies.length - 1; i > 0; i--) {
            frequencies[i] = frequencies[i - 1];
        }
        frequencies[0] = 0;

        // Create a sorted array based on the frequencies
        Double[][] sorted = new Double[transposedArray.length][transposedArray[0].length];
        for (int i = 0; i < transposedArray.length; i++) {
            int index = frequencies[((int) (transposedArray[i][0] * 10)) - min];
            sorted[index] = transposedArray[i];
            frequencies[((int) (transposedArray[i][0] * 10)) - min]++;
        }

        // Transpose the sorted array back to its original form
        Double[][] finalArray = new Double[sorted[0].length][sorted.length];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                finalArray[j][i] = sorted[i][j];
            }
        }

        endOfSorting = LocalTime.now();

        return finalArray;
    }


    /**
     * Sorts the array of Integer elements using Counting Sort and returns the sorted array.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return The sorted array of Integer elements.
     */
    @Override
    public Integer[] sortLab(Integer[] arrayOfNumbers) {
        startOfSorting = LocalTime.now();

        // Find the minimum and maximum values in the array
        Integer min = arrayOfNumbers[0], max = arrayOfNumbers[0];
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[i] < min) {
                min = arrayOfNumbers[i];
            }
            if (arrayOfNumbers[i] > max) {
                max = arrayOfNumbers[i];
            }
        }

        // Calculate the range of values in the array
        int length = max - min;

        // Initialize an array to store the frequencies of each value
        int[] frequencies = new int[length + 1];

        // Calculate the frequencies of each value
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            frequencies[arrayOfNumbers[i] - min]++;
        }

        // Calculate the cumulative frequencies
        int currentSum = frequencies[0];
        for (int i = 1; i < frequencies.length; i++) {
            currentSum += frequencies[i];
            frequencies[i] = currentSum;
        }

        // Adjust frequencies to determine the final positions of values in the sorted array
        for (int i = frequencies.length - 1; i > 0; i--) {
            frequencies[i] = frequencies[i - 1];
        }
        frequencies[0] = 0;

        // Create the sorted array
        Integer[] sorted = new Integer[arrayOfNumbers.length];

        // Populate the sorted array by placing values in their final positions
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            sorted[frequencies[arrayOfNumbers[i] - min]] = arrayOfNumbers[i];
            frequencies[arrayOfNumbers[i] - min]++;
        }

        endOfSorting = LocalTime.now();

        return sorted;
    }

    /**
     * Sorts the array of Integer elements using Counting Sort with intermediate steps and returns a list of
     * intermediate sorting states.
     *
     * @param arrayOfNumbers The array of Integer elements to be sorted.
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    @Override
    public ArrayList<Integer[]> sortLabWithSteps(Integer[] arrayOfNumbers) {
        ArrayList<Integer[]> steps = new ArrayList<>();

        // Add the initial state of the array to the steps
        steps.add(Arrays.copyOf(arrayOfNumbers, arrayOfNumbers.length));

        startOfSorting = LocalTime.now();

        // Find the minimum and maximum values in the array
        Integer min = arrayOfNumbers[0], max = arrayOfNumbers[0];
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[i] < min) {
                min = arrayOfNumbers[i];
            }
            if (arrayOfNumbers[i] > max) {
                max = arrayOfNumbers[i];
            }
        }

        // Calculate the range of values in the array
        int length = max - min;

        // Initialize an array to store the frequencies of each value
        int[] frequencies = new int[length + 1];

        // Calculate the frequencies of each value
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            frequencies[arrayOfNumbers[i] - min]++;
        }

        // Convert the frequencies array to Integer array and add to steps
        Integer[] integerArray = new Integer[frequencies.length];
        for (int i = 0; i < frequencies.length; i++) {
            integerArray[i] = frequencies[i];
        }
        steps.add(Arrays.copyOf(integerArray, integerArray.length));

        // Calculate the cumulative frequencies
        int currentSum = frequencies[0];
        for (int i = 1; i < frequencies.length; i++) {
            currentSum += frequencies[i];
            frequencies[i] = currentSum;
        }

        // Update the Integer array and add to steps
        for (int i = 0; i < frequencies.length; i++) {
            integerArray[i] = frequencies[i];
        }
        steps.add(Arrays.copyOf(integerArray, integerArray.length));

        // Adjust frequencies to determine the final positions of values in the sorted array
        for (int i = frequencies.length - 1; i > 0; i--) {
            frequencies[i] = frequencies[i - 1];
        }
        frequencies[0] = 0;

        // Update the Integer array and add to steps
        for (int i = 0; i < frequencies.length; i++) {
            integerArray[i] = frequencies[i];
        }
        steps.add(Arrays.copyOf(integerArray, integerArray.length));

        // Create the sorted array
        Integer[] sorted = new Integer[arrayOfNumbers.length];

        // Populate the sorted array by placing values in their final positions
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            sorted[frequencies[arrayOfNumbers[i] - min]] = arrayOfNumbers[i];
            frequencies[arrayOfNumbers[i] - min]++;
        }

        // Add the final sorted state to steps
        steps.add(Arrays.copyOf(sorted, sorted.length));

        endOfSorting = LocalTime.now();

        return steps;
    }

}
