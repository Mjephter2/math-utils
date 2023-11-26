package models.utils;

import utils.NumberUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberUtilsTests {

    @Test
    public void round_tests() {
        final double originalValue = 1.23456789;

        assert NumberUtils.round(originalValue, 0) == 1.0;
        assert NumberUtils.round(originalValue, 1) == 1.2;
        assert NumberUtils.round(originalValue, 2) == 1.23;
        assert NumberUtils.round(originalValue, 3) == 1.235;
        assert NumberUtils.round(originalValue, 4) == 1.2346;
        assert NumberUtils.round(originalValue, 5) == 1.23457;
        assert NumberUtils.round(originalValue, 6) == 1.234568;
    }

    @Test
    public void gcd_tests() {
        final Double[] sortedNums = { 20.0, 40.0, 100.0 };

        assert NumberUtils.gcd(sortedNums) == 20.0;

        final Double[] sortedNums2 = { 2.0, 3.0, 4.0, 5.0 };
        assertEquals(1.0, NumberUtils.gcd(sortedNums2), 0.0);
    }

    @Test
    public void lcd_tests() {
        final Double[] sortedNums1 = { 20.0, 40.0, 100.0 };
        final Double[] sortedNums2 = { 2.0, 3.0, 4.0, 5.0 };

        assertEquals(2.0, NumberUtils.lcd(sortedNums1), 0.0);
        assertEquals(1.0, NumberUtils.lcd(sortedNums2), 0.0);
    }
}
