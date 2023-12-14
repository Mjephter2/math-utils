package models.utils;

import models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void atMostTests() {
        final Range range1 = Range.atMost(0.0);

        assertEquals(0.0, range1.getUpperBound());
        assertTrue(range1.isIncludeUpperBound());
        assertEquals(Double.NEGATIVE_INFINITY, range1.getLowerBound());
        assertTrue(range1.isIncludeUpperBound());

        assertTrue(range1.includes(0.0));
        assertFalse(range1.includes(1.1));
    }

    @Test
    public void lessThanTests() {
        final Range range1 = Range.lessThan(0.0);

        assertEquals(0.0, range1.getUpperBound());
        assertFalse(range1.isIncludeUpperBound());
        assertEquals(Double.NEGATIVE_INFINITY, range1.getLowerBound());
        assertFalse(range1.isIncludeUpperBound());

        assertFalse(range1.includes(0.0));
        assertFalse(range1.includes(1.0));
        assertTrue(range1.includes(-1.0));
    }

    @Test
    public void greaterThanTests() {
        final Range range1 = Range.greaterThan(0.0);

        assertEquals(0.0, range1.getLowerBound());
        assertFalse(range1.isIncludeLowerBound());
        assertEquals(Double.POSITIVE_INFINITY, range1.getUpperBound());
        assertFalse(range1.isIncludeUpperBound());

        assertFalse(range1.includes(0.0));
        assertTrue(range1.includes(1.0));
        assertFalse(range1.includes(-1.1));
    }

    @Test
    public void allTests() {
        final Range range1 = Range.all();

        assertEquals(Double.NEGATIVE_INFINITY, range1.getLowerBound());
        assertFalse(range1.isIncludeLowerBound());
        assertEquals(Double.POSITIVE_INFINITY, range1.getUpperBound());
        assertFalse(range1.isIncludeUpperBound());

        assertTrue(range1.includes(-1.1));
        assertTrue(range1.includes(0.0));
        assertTrue(range1.includes(1.0));
        assertTrue(range1.includes(Double.NEGATIVE_INFINITY));
        assertTrue(range1.includes(Double.POSITIVE_INFINITY));

        assertTrue(range1.includes(new Random().nextDouble() * 1000000000));
    }

    @Test
    public void exceptionsTests() {
        assertThrows(IllegalArgumentException.class, () -> new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, true));
    }
}
