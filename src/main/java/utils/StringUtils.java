package utils;

public final class StringUtils {

    public static String trimLeadingTrailingPlus(String str) {
        if (str.startsWith("+")) {
            str =  str.substring(1);
        }
        if (str.endsWith("+")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
}
