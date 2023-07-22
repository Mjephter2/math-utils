package utils;

import lombok.NonNull;

/**
 * Utility class for number operations
 */
public class NumberUtils {

    /**
     * Rounds the given value to the given number of decimal places
     * @param value -> value to round
     * @param places -> number of decimal places
     * @return the rounded value
     */
    public static double round(final double value, final int places) {
        final double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    /**
     * Returns the greatest common divisor of the given array of numbers
     * @param sortedNums -> numbers to find gcd of
     * @return the gcd
     */
    public static Double gcd(@NonNull final Double[] sortedNums) {

        Double result = sortedNums[0];

        for (Double num : sortedNums) {
            result = gcd(result, num);

            if (result == 1.0) {
                return 1.0;
            }
        }

        return result;
    }

    /**
     * Returns the greatest common divisor of the given numbers
     * @param a -> first number
     * @param b -> second number
     * @return the gcd
     */
    private static Double gcd(final Double a, final Double b) {
        if (a == 0.0) {
            return b;
        }
        return gcd(b % a, a);
    }
}
