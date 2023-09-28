package com.mhorak.dsa.sort;

/**
 * A common interface for sorting individual elements in an array.
 */
public interface IndividualSorting {

    /**
     * Sorts a one-dimensional array of Double elements.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array of Double elements.
     */
    Double[] sortIndividual(Double[] arrayOfNumbers);

    /**
     * Sorts a two-dimensional array of Double elements.
     *
     * @param arrayOfNumbers The array of Double elements to be sorted.
     * @return The sorted array of Double elements.
     */
    Double[][] sortIndividual(Double[][] arrayOfNumbers);
}
