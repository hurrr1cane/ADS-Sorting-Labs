package com.mhorak.dsa.tools;

import java.util.Random;

/**
 * A utility class for various operations on arrays.
 *
 * @param <T> The type of elements in the array, which must implement the Comparable interface.
 */
public class Tools<T extends Comparable<T>> {

    /**
     * Checks if an array is sorted in ascending order.
     *
     * @param arrayOfNumbers The array to check for sorting.
     * @return True if the array is sorted in ascending order, otherwise false.
     */
    public boolean isArraySorted(T[] arrayOfNumbers) {
        for (int i = 1; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[i - 1].compareTo(arrayOfNumbers[i]) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Initializes an array of numbers with random values.
     *
     * @param arrayOfNumbers   The array to initialize.
     * @param useHugeNumbers   Indicates whether to use a range for huge numbers.
     *                         If true, the range includes Integer.MIN_VALUE to Integer.MAX_VALUE;
     *                         otherwise, the range is from 0 to 100.
     */
    public static <T extends Number> void initializeArray(T[] arrayOfNumbers, boolean useHugeNumbers) {
        Random rand = new Random();
        int min = useHugeNumbers ? Integer.MIN_VALUE : 0;
        int max = useHugeNumbers ? Integer.MAX_VALUE : 100; // Use a smaller range for huge numbers

        for (int i = 0; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[i] instanceof Integer) {
                arrayOfNumbers[i] = (T) Integer.valueOf(rand.nextInt(min, max));
            } else if (arrayOfNumbers[i] instanceof Double) {
                arrayOfNumbers[i] = (T) Double.valueOf(rand.nextDouble(min, max));
            }
        }
    }

    /**
     * Mutates an array of Double values by applying a square root function to even-indexed elements.
     *
     * @param arrayOfNumbers The array of Double values to mutate.
     */
    public static void mutateArray(Double[] arrayOfNumbers) {
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            if (i % 2 == 0) {
                applyFunction(arrayOfNumbers, i);
            }
        }
    }

    // Private helper method to apply a square root function to an element at the given index.
    private static void applyFunction(Double[] arrayOfNumbers, int i) {
        arrayOfNumbers[i] = Math.sqrt(Math.abs(arrayOfNumbers[i] - 10.));
    }
}