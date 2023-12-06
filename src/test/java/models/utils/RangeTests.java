package models.utils;

import models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RangeTests {

    @Test
    public void atLeastTests() {
        final Range range1 = Range.atLeast(0.0);

        assertEquals(0.0, range1.getLowerBound());
        assertTrue(range1.isIncludeLowerBound());
        assertEquals(Double.POSITIVE_INFINITY, range1.getUpperBound());
        assertFalse(range1.isIncludeUpperBound());

        assertTrue(range1.includes(0.0));
        assertFalse(range1.includes(-1.1));
    }
}
