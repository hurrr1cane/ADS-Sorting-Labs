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
     * Checks if an array is sorted in the specified order (ascending or descending).
     *
     * @param arrayOfNumbers The array to check for sorting.
     * @param byAscending    A boolean flag indicating whether to check for ascending order (true) or descending order (false).
     * @return True if the array is sorted in the specified order, otherwise false.
     */
    public static boolean isArraySorted(Object[] arrayOfNumbers, boolean byAscending) {
        if (byAscending) {
            // Check for ascending order
            for (int i = 1; i < arrayOfNumbers.length; i++) {
                if (arrayOfNumbers[0] instanceof Double[]) {
                    Double[][] newArray = (Double[][]) arrayOfNumbers;
                    // Check for ascending order within a row of the two-dimensional array
                    if (newArray[0][i - 1].compareTo(newArray[0][i]) > 0) {
                        return false;
                    }
                } else {
                    Comparable[] newArray = (Comparable[]) arrayOfNumbers;
                    // Check for ascending order within a one-dimensional array
                    if (newArray[i - 1].compareTo(newArray[i]) > 0) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            // Check for descending order
            for (int i = 1; i < arrayOfNumbers.length; i++) {
                if (arrayOfNumbers[0] instanceof Double[]) {
                    Double[][] newArray = (Double[][]) arrayOfNumbers;
                    // Check for descending order within a row of the two-dimensional array
                    if (newArray[0][i - 1].compareTo(newArray[0][i]) < 0) {
                        return false;
                    }
                } else {
                    Comparable[] newArray = (Comparable[]) arrayOfNumbers;
                    // Check for descending order within a one-dimensional array
                    if (newArray[i - 1].compareTo(newArray[i]) < 0) {
                        return false;
                    }
                }
            }
            return true;
        }
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
        int min = useHugeNumbers ? Integer.MIN_VALUE : -50;
        int max = useHugeNumbers ? Integer.MAX_VALUE : 50; // Use a smaller range for huge numbers

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
     * Mutates an array of Double values by applying specific functions based on the lab variant.
     *
     * @param arrayOfNumbers The array of Double values to mutate.
     * @param lab            The lab variant, which determines the mutation function to apply.
     */
    public static void mutateArray(Double[] arrayOfNumbers, int lab) {
        for (int i = 0; i < arrayOfNumbers.length; i++) {
            if (i % 2 == 0 && lab == 1) {
                // Apply the function for lab variant 1 to even-indexed elements
                applyFunctionLab1(arrayOfNumbers, i);
            }
            if (arrayOfNumbers[i] < 0 && lab == 4) {
                // Apply the function for lab variant 4 to elements less than 0
                applyFunctionLab2(arrayOfNumbers, i);
            }
        }
    }

    /**
     * Applies a square root function to an element at the given index and stores the result.
     *
     * @param arrayOfNumbers The array of Double values to mutate.
     * @param i              The index of the element to be mutated.
     */
    private static void applyFunctionLab1(Double[] arrayOfNumbers, int i) {
        arrayOfNumbers[i] = Math.sqrt(Math.abs(arrayOfNumbers[i] - 10.));
    }

    /**
     * Applies a sine function to an element at the given index and stores the result.
     *
     * @param arrayOfNumbers The array of Double values to mutate.
     * @param i              The index of the element to be mutated.
     */
    private static void applyFunctionLab2(Double[] arrayOfNumbers, int i) {
        arrayOfNumbers[i] = Math.sin(arrayOfNumbers[i]);
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

        // If there is no mode (maxFrequency is 1), remove first element
        if (maxFrequency == 1) {
            Double[] resultArray = new Double[inputArray.length - 1];
            System.arraycopy(inputArray, 1, resultArray, 0, resultArray.length);
            return resultArray;
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