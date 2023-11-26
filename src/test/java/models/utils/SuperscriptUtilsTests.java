package models.utils;

import org.junit.jupiter.api.Test;
import utils.SuperscriptUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuperscriptUtilsTests {

    @Test
    public void convertToSuperscriptTest() {
        assertEquals("¹²³", SuperscriptUtil.convertToSuperscript(123));
        assertEquals("¹²³⁴⁵⁶⁷⁸⁹", SuperscriptUtil.convertToSuperscript(123456789));
    }
}
