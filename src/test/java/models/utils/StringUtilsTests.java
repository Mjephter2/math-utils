package models.utils;

import org.junit.Test;
import utils.StringUtils;

import static junit.framework.TestCase.assertEquals;

public class StringUtilsTests {

    @Test
    public void trim_leading_plus_tests() {
        final String str0 = "";
        final String str1 = "+ 3x²";
        final String str2 = "3x²";
        final String str3 = "3x² + 2x + 1";
        final String str4 = "+ 3x² + 2x + 1";
        final String str5 = "+ 3x² + 2x + 1 +";
        final String str6 = "+ 3x² + 2x + 1 + +";
        final String str7 = "+ 3x² + 2x + 1 + + +";

        assertEquals("", StringUtils.trimTrailingLeadingPlus(str0));
        assertEquals("3x²", StringUtils.trimTrailingLeadingPlus(str1));
        assertEquals("3x²", StringUtils.trimTrailingLeadingPlus(str2));
        assertEquals("3x² + 2x + 1", StringUtils.trimTrailingLeadingPlus(str3));
        assertEquals("3x² + 2x + 1", StringUtils.trimTrailingLeadingPlus(str4));
        assertEquals("3x² + 2x + 1", StringUtils.trimTrailingLeadingPlus(str5));
        assertEquals("3x² + 2x + 1", StringUtils.trimTrailingLeadingPlus(str6));
        assertEquals("3x² + 2x + 1", StringUtils.trimTrailingLeadingPlus(str7));
    }
}
