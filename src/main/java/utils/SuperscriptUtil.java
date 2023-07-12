package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting digits to superscript characters
 */
public final class SuperscriptUtil {

    /**
     * Map of digits to superscript characters
     */
    public static Map<Integer, SuperscriptEnum> digitToSuperscript = new HashMap<>(){
        {
            put(0, SuperscriptEnum.ZERO);
            put(1, SuperscriptEnum.ONE);
            put(2, SuperscriptEnum.TWO);
            put(3, SuperscriptEnum.THREE);
            put(4, SuperscriptEnum.FOUR);
            put(5, SuperscriptEnum.FIVE);
            put(6, SuperscriptEnum.SIX);
            put(7, SuperscriptEnum.SEVEN);
            put(8, SuperscriptEnum.EIGHT);
            put(9, SuperscriptEnum.NINE);

        }
    };

    /**
     * Converts the given number to a superscript string
     * @param number -> number to convert
     * @return the superscript string
     */
    public static String convertToSuperscript(final int number) {
        final StringBuilder sb = new StringBuilder();
        final char[] digits = String.valueOf(number).toCharArray();
        for (final char digit : digits) {
            sb.append(digitToSuperscript.get(Character.getNumericValue(digit)).getSuperscript());
        }
        return sb.toString();
    }
}
