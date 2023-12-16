package models.utils;

import models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.List;
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

    @Test
    public void exceptTests() {
        final Range exceptedRange = new Range(1.0, 10.0, true, true);
        final List<Range> rangeConcat = Range.allExcept(exceptedRange);

        final Range range1 = rangeConcat.get(0);
        final Range range2 = rangeConcat.get(1);

        assertEquals(new Range(Double.NEGATIVE_INFINITY, 1.0, false, false), range1);
        assertEquals(new Range(10.0, Double.POSITIVE_INFINITY, false, false), range2);
    }

    @Test
    public void exceptTestsSingleValue() {
        final double exceptedValue = 10.0;
        final List<Range> rangeConcat = Range.allExcept(exceptedValue);

        final Range range1 = rangeConcat.get(0);
        final Range range2 = rangeConcat.get(1);

        assertEquals(new Range(Double.NEGATIVE_INFINITY, 10.0, false, false), range1);
        assertEquals(new Range(10.0, Double.POSITIVE_INFINITY, false, false), range2);
    }

    @Test
    public void singletonTests() {
        final Range range1 = Range.singleton(10.0);

        assertEquals(10.0, range1.getLowerBound());
        assertEquals(10.0, range1.getUpperBound());

        assertTrue(range1.isIncludeLowerBound());
        assertTrue(range1.isIncludeUpperBound());

        assertThrows(IllegalArgumentException.class, () -> Range.singleton(Double.POSITIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> Range.singleton(Double.NEGATIVE_INFINITY));
        assertThrows(IllegalArgumentException.class, () -> Range.singleton(Double.valueOf("a")));
    }

    @Test
    public void allPositiveTests() {
        final Range allPositiveWithZero = Range.allPositive(true);
        assertEquals(new Range(0.0, Double.POSITIVE_INFINITY, true, false), allPositiveWithZero);

        final Range allPositiveNoZero = Range.allPositive(false);
        assertEquals(new Range(0.0, Double.POSITIVE_INFINITY, false, false), allPositiveNoZero);
    }

    @Test
    public void allNegativeTests() {
        final Range allNegativeWithZero = Range.allNegative(true);
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 0.0, false, true), allNegativeWithZero);

        final Range allNegativeNoZero = Range.allNegative(false);
        assertEquals(new Range(Double.NEGATIVE_INFINITY, 0.0, false, false), allNegativeNoZero);
    }
}
