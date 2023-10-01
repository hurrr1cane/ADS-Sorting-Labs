package com.mhorak.dsa.tools;

import java.text.DecimalFormat;
import java.util.*;

/**
 * A utility class for various operations on arrays.
 */
public class Tools {

    public static final String filePath = "Output.txt";

    public static final DecimalFormat decimalFormat = new DecimalFormat("0.00"); // Pattern for two digits after the decimal point

    /**
     * Checks if an array is sorted in ascending order.
     *
     * @param arrayOfNumbers The array to check for sorting.
     * @return True if the array is sorted in ascending order, otherwise false.
     */
    public static boolean isArraySorted(Object[] arrayOfNumbers) {
        for (int i = 1; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[0] instanceof Double[]) {
                Double[][] newArray = (Double[][]) arrayOfNumbers;
                if (newArray[i - 1][0].compareTo(newArray[i][0]) > 0) {
                    return false;
                }
            }
            else {
                Comparable[] newArray = (Comparable[]) arrayOfNumbers;
                if (newArray[i - 1].compareTo(newArray[i]) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Initializes an array of numbers with random values.
     *
     * @param arrayOfNumbers The array to initialize.
     * @param useHugeNumbers Indicates whether to use a range for huge numbers.
     *                       If true, the range includes Integer.MIN_VALUE to Integer.MAX_VALUE;
     *                       otherwise, the range is from 0 to 100.
     */
    public static void initializeArray(Object[] arrayOfNumbers, boolean useHugeNumbers) {
        Random rand = new Random();
        int min = useHugeNumbers ? Integer.MIN_VALUE : 0;
        int max = useHugeNumbers ? Integer.MAX_VALUE : 100; // Use a smaller range for huge numbers

        for (int i = 0; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[i] instanceof Integer) {
                arrayOfNumbers[i] = rand.nextInt(min, max);
            } else if (arrayOfNumbers[i] instanceof Double) {
                arrayOfNumbers[i] = rand.nextDouble(min, max);
            } else if (arrayOfNumbers[i] instanceof Double[]) {
                initializeArray((Double[]) arrayOfNumbers[i], useHugeNumbers);
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

    /**
     * Removes elements that belong to the mode (most frequently occurring whole parts)
     * from a Double array and returns a modified array without those elements.
     *
     * @param inputArray The input array of Double values.
     * @return A Double array with mode elements removed.
     */
    public static Double[] removeMode(Double[] inputArray) {
        // Create a HashMap to store the frequency of each whole part
        Map<Long, Integer> frequencyMap = new HashMap<>();

        // Find the maximum frequency (mode)
        int maxFrequency = 0;
        for (double num : inputArray) {
            long wholePart = (long) num; // Get the whole part of the double
            frequencyMap.put(wholePart, frequencyMap.getOrDefault(wholePart, 0) + 1);
            maxFrequency = Math.max(maxFrequency, frequencyMap.get(wholePart));
        }

        // If there is no mode (maxFrequency is 1), return the same array
        if (maxFrequency == 1) {
            return inputArray;
        }

        // Create a list to store elements that are not part of the mode
        List<Double> result = new ArrayList<>();
        for (double num : inputArray) {
            long wholePart = (long) num;
            if (frequencyMap.get(wholePart) != maxFrequency) {
                result.add(num);
            }
        }

        // Convert the list to an array
        Double[] resultArray = new Double[result.size()];
        for (int i = 0; i < result.size(); i++) {
            resultArray[i] = result.get(i);
        }

        return resultArray;
    }

}