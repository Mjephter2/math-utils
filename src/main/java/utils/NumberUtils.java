package utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

/**
 * Utility class for number operations
 */
@UtilityClass
public class NumberUtils {

    private static final long GCD_PRECISION = 1000000;

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
        for (int precision = 1; precision <= GCD_PRECISION; precision *= 10) {
            final int int1 = (int) (a * precision);
            final int int2 = (int) (b * precision);
            final int intGcd = gcd(int1, int2);

            if ((a / intGcd) % 1 == 0.0 && (b / intGcd) % 1 == 0.0) {
                return (double) (intGcd / precision);
            }
        }
        return 1.0;
    }

    private static Integer gcd(final Integer a, final Integer b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }

    /**
     * Calculates the least common divisor of the given array of numbers
     * @param sortedNums -> numbers to find lcd of
     *                   -> must be sorted in ascending order
     *                   -> must not contain 0
     * @return the lcd
     */
    public static Double lcd(@NonNull final Double[] sortedNums) {
        for (double i = 2.0; i < sortedNums[0]; i++) {
            double finalI = i;
            if (Arrays.stream(sortedNums).allMatch(num -> num % finalI == 0)) {
                return i;
            }
        }

        return 1.0;
    }
}
