package com.mhorak.dsa.sort;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * An abstract class representing a sorting algorithm for sorting an array of comparable elements.
 */
public abstract class Sort implements IndividualSorting{
    protected LocalTime startOfSorting;
    protected LocalTime endOfSorting;

    /**
     * Gets the duration of the sorting process.
     *
     * @return The duration of the sorting process as a Duration object.
     */
    public Duration getTimeOfProcessing() {
        return Duration.between(startOfSorting, endOfSorting);
    }

    /**
     * Sorts the array and returns the sorted array.
     *
     * @return The sorted array.
     */
    public abstract Integer[] sortLab(Integer[] arrayOfNumbers);


    /**
     * Sorts the array with intermediate steps and returns a list of intermediate sorting states.
     *
     * @return An ArrayList of arrays representing intermediate sorting states.
     */
    public abstract ArrayList<Integer[]> sortLabWithSteps(Integer[] arrayOfNumbers);
}