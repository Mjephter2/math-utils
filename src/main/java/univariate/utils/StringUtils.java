package univariate.utils;

import lombok.experimental.UtilityClass;

/**
 * This class contains utility methods for String manipulation.
 */
@UtilityClass
public final class StringUtils {

    /**
     * Trims the trailing and leading plus sign from the given StringBuilder
     * @param str : StringBuilder to trim
     * @return the trimmed StringBuilder
     */
    public static String trimTrailingLeadingPlus(final String str) {
        if (str.length() == 0) {
            return "";
        }

        StringBuilder strBldr = new StringBuilder(str.trim());

        boolean done = false;

        while (!done) {
            if (strBldr.charAt(0) == '+') {
                strBldr.deleteCharAt(0);
                strBldr = new StringBuilder(strBldr.toString().trim());
            } else {
                done = true;
            }
        }

        done = false;
        while (!done) {
            if (strBldr.charAt(strBldr.length() - 1) == '+') {
                strBldr.deleteCharAt(strBldr.length() - 1);
                strBldr = new StringBuilder(strBldr.toString().trim());
            } else {
                done = true;
            }
        }

        return strBldr.toString();
    }
}
