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

    @Test
    public void includesRangeTests() {
        final Range range1 = Range.allPositive(true);

        assertTrue(range1.includes(Range.singleton(0.0)));
        assertTrue(range1.includes(Range.singleton(1.0)));
        assertTrue(range1.includes(new Range(0.0, 1.0, true, true)));
    }

    @Test
    public void intersectionTests() {
        final Range range1 = Range.allPositive(true);
        final Range range2 = Range.allNegative(true);
        assertEquals(new Range(0.0, 0.0, true, true), range1.intersection(range2));

        final Range range3 = Range.atLeast(5);
        final Range range4 = Range.atMost(10);
        assertEquals(new Range(5.0, 10.0, true, true), range3.intersection(range4));

        final Range range5 = Range.greaterThan(5);
        final Range range6 = Range.lessThan(10);
        assertEquals(new Range(5.0, 10.0, false, false), range5.intersection(range6));

        final Range range7 = Range.greaterThan(5);
        final Range range8 = Range.greaterThan(2);
        assertEquals(new Range(5.0, Double.POSITIVE_INFINITY, false, false), range7.intersection(range8));
        assertEquals(new Range(5.0, Double.POSITIVE_INFINITY, false, false), range8.intersection(range7));

        final Range range9 = Range.atLeast(5);
        final Range range10 = Range.greaterThan(5);
        assertEquals(new Range(5.0, Double.POSITIVE_INFINITY, false, false), range9.intersection(range10));
    }

    @Test
    public void mergeTests() {
        final Range range1 = Range.allPositive(true); // [0, +inf)
        final Range range2 = Range.allNegative(true); // (-inf, 0]
        Range expectedMerge = new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, false); // (-inf, +inf)
        List<Range> mergedRanges = range1.merge(range2); // Expectation: (-inf, 0] U [0, +inf) = (-inf, +inf)
        assertEquals(1, mergedRanges.size());
        assertEquals(expectedMerge, range1.merge(range2).get(0));

        final Range range3 = Range.allPositive(false);
        final Range range4 = Range.allNegative(true);
        mergedRanges = range3.merge(range4);
        assertEquals(1, mergedRanges.size());
        assertEquals(expectedMerge, mergedRanges.get(0));

        final Range range5 = Range.allPositive(true);
        final Range range6 = Range.allNegative(false);
        mergedRanges = range5.merge(range6);
        assertEquals(1, mergedRanges.size());
        assertEquals(expectedMerge, mergedRanges.get(0));

        final Range range7 = Range.allPositive(false);
        final Range range8 = Range.allNegative(false);
        mergedRanges = range7.merge(range8);
        assertEquals(2, mergedRanges.size());
        assertEquals(range7, mergedRanges.get(0));
        assertEquals(range8, mergedRanges.get(1));

        final Range range9 = Range.atLeast(10.0);
        final Range range10 = Range.atMost(5.0);
        mergedRanges = range9.merge(range10);
        assertEquals(2, mergedRanges.size());
        assertEquals(range9, mergedRanges.get(0));
        assertEquals(range10, mergedRanges.get(1));
    }
}
