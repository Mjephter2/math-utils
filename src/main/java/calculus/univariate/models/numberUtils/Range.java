package calculus.univariate.models.numberUtils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a range of numbers
 */
@Getter
public class Range implements Comparable {

    /**
     * The lower bound of the range
     */
    private final Double lowerBound;
    /**
     * Whether the lower bound is included in the range
     */
    private final boolean includeLowerBound;

    /**
     * The upper bound of the range
     */
    private final Double upperBound;
    /**
     * Whether the upper bound is included in the range
     */
    private final boolean includeUpperBound;

    /**
     * Constructs a range of numbers
     * @param lowerBound - the lower bound of the range
     * @param upperBound - the upper bound of the range
     * @param includeLowerBound - whether the lower bound is included in the range
     * @param includeUpperBound - whether the upper bound is included in the range
     */
    public Range(final Double lowerBound, final Double upperBound, final boolean includeLowerBound, final boolean includeUpperBound) {
        if (validatedBounds(lowerBound, upperBound, includeLowerBound, includeUpperBound)) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.includeLowerBound = includeLowerBound;
            this.includeUpperBound = includeUpperBound;
        } else {
            throw new IllegalArgumentException("Invalid bounds values provided");
        }
    }

    private boolean validatedBounds(final Double lowerBound, final Double uppperBound, final boolean includeLowerBound, final boolean includeUpperBound) {
        return (!includeLowerBound || !lowerBound.isInfinite()) && (!includeUpperBound || !uppperBound.isInfinite());
    }

    /**
     * Returns a range of all numbers equal or greater than a specified lower bound
     * @param lowerBound - the lower bound of the range
     * @return a range of all numbers equal or greater than the lower bound
     */
    public static Range atLeast(final double lowerBound) {
        return new Range(lowerBound, Double.POSITIVE_INFINITY, true, false);
    }

    /**
     * Returns a range of all numbers strictly greater than a specified lower bound
     * @param lowerBound - the lower bound of the range
     * @return a range of all numbers strictly greater than the lower bound
     */
    public static Range greaterThan(final double lowerBound) {
        return new Range(lowerBound, Double.POSITIVE_INFINITY, false, false);
    }

    /**
     * Returns a range of all numbers less than or equal to a specified upper bound
     * @param upperBound - the upper bound of the range
     * @return a range of all numbers less than or equal to the upper bound
     */
    public static Range atMost(final double upperBound) {
        return new Range(Double.NEGATIVE_INFINITY, upperBound, false, true);
    }

    /**
     * Returns a range of all numbers strictly less than a specified upper bound
     * @param upperBound - the upper bound of the range
     * @return a range of all numbers strictly less than the upper bound
     */
    public static Range lessThan(final double upperBound) {
        return new Range(Double.NEGATIVE_INFINITY, upperBound, false, false);
    }

    /**
     * Returns a range of all numbers between two bounds
     * @param lowerBound - the lower bound of the range
     * @param upperBound - the upper bound of the range
     * @return a range of all numbers between the lower and upper bounds
     */
    public static Range open(final double lowerBound, final double upperBound) {
        return new Range(lowerBound, upperBound, false, false);
    }

    /**
     * Returns a range of all numbers between two bounds, including the bounds
     * @param lowerBound - the lower bound of the range
     * @param upperBound - the upper bound of the range
     * @return a range of all numbers between the lower and upper bounds
     */
    public static Range closed(final double lowerBound, final double upperBound) {
        return new Range(lowerBound, upperBound, true, true);
    }

    /**
     * Returns a range of all numbers between two bounds, including the upper bound
     * @param lowerBound - the lower bound of the range
     * @param upperBound - the upper bound of the range
     * @return a range of all numbers between the lower and upper bounds, including the upper bound
     */
    public static Range openClosed(final double lowerBound, final double upperBound) {
        return new Range(lowerBound, upperBound, false, true);
    }

    /**
     * Returns a range of all numbers between two bounds, including the lower bound
     * @param lowerBound - the lower bound of the range
     * @param upperBound - the upper bound of the range
     * @return a range of all numbers between the lower and upper bounds, including the lower bound
     */
    public static Range closedOpen(final double lowerBound, final double upperBound) {
        return new Range(lowerBound, upperBound, true, false);
    }

    /**
     * Returns a range of all Double values
     */
    public static Range all() {
        return new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, false);
    }

    /**
     * Excludes a single value from a range.
     * @param exceptedValue - the value to exclude
     * @param range - the range to exclude the value from
     * @return a list of two ranges that excludes the value
     */
    public static List<Range> rangeExcept(final Double exceptedValue, final Range range) {
        if (exceptedValue.isInfinite() || exceptedValue.isNaN()) {
            throw new IllegalArgumentException("Value to except cannot be infinite or invalid");
        } else if (range.includes(exceptedValue)) {
            return List.of(
                    new Range(range.getLowerBound(), exceptedValue, range.isIncludeLowerBound(), false),
                    new Range(exceptedValue, range.getUpperBound(), false, range.isIncludeUpperBound())
            );
        } else {
            return List.of(range);
        }
    }

    /**
     * Returns a range that excludes a single value
     * @param exceptedValue - the value to exclude
     * @return a list of two ranges that excludes the value
     */
    public static List<Range> allExcept(final Double exceptedValue) {
        return List.of(
                new Range(Double.NEGATIVE_INFINITY, exceptedValue, false, false),
                new Range(exceptedValue, Double.POSITIVE_INFINITY, false, false)
        );
    }

    /**
     * Returns a range that excludes a range
     * @param exceptedRange - the range to exclude
     * @return a list of two ranges that excludes the range
     */
    public static List<Range> allExcept(final Range exceptedRange) {
        return List.of(
                new Range(Double.NEGATIVE_INFINITY, exceptedRange.getLowerBound(), false, !exceptedRange.isIncludeUpperBound()),
                new Range(exceptedRange.getUpperBound(), Double.POSITIVE_INFINITY, !exceptedRange.isIncludeUpperBound(), false)
        );
    }

    /**
     * Returns a range that only consists of a single value
     * @param value - the value to include
     * @return a range that includes the value
     */
    public static Range singleton(final Double value) {
        if (value.isInfinite() || value.isNaN()) {
            throw new IllegalArgumentException("Value to except cannot be infinite or invalid");
        }
        return new Range(value, value, true, true);
    }

    /**
     * Returns a range that includes all positive numbers
     * @param includeZero - whether to include zero in the range
     * @return a range that includes all positive numbers except maybe zero
     */
    public static Range allPositive(final boolean includeZero) {
        return new Range(0.0, Double.POSITIVE_INFINITY, includeZero, false);
    }

    /**
     * Returns a range that includes all negative numbers
     * @param includeZero - whether to include zero in the range
     * @return a range that includes all negative numbers except maybe zero
     */
    public static Range allNegative(final boolean includeZero) {
        return new Range(Double.NEGATIVE_INFINITY, 0.0, false, includeZero);
    }

    /**
     * Returns the absolute value of a range
     */
    public static Range abs(final Range range) {
        if (range.getLowerBound() >= 0) {
            return range;
        } else if (range.getUpperBound() <= 0) {
            return new Range(-range.getUpperBound(), -range.getLowerBound(), range.isIncludeUpperBound(), range.includeLowerBound);
        } else {
            // TODO: Unit test to check if this is correct
            return new Range(0.0, Math.max(-range.getLowerBound(), range.getUpperBound()), true, range.includeUpperBound);
        }
    }

    /**
     * Returns a range that includes all numbers between the provided bounds
     * @param sortedBounds - the bounds of the range
     * @return a list of ranges that includes all numbers between the bounds
     */
    public static List<Range> fromBounds(final List<Double> sortedBounds) {
        // Check if sortedBounds is sorted
        for (int i = 0; i < sortedBounds.size() - 1; i += 1) {
            if (sortedBounds.get(i) >= sortedBounds.get(i + 1)) {
                throw new IllegalArgumentException("Bounds must be sorted and unique!");
            }
        }

        final List<Range> ranges = new java.util.ArrayList<>();
        for (int i = 0; i < sortedBounds.size() - 1; i += 1) {
            ranges.add(new Range(sortedBounds.get(i), sortedBounds.get(i + 1), !sortedBounds.get(i).isInfinite(), !sortedBounds.get(i).isInfinite() && (i == sortedBounds.size() - 2)));
        }
        return ranges;
    }

    /**
     * @return a shortened list of all the values in this range
     * @param precision - difference between two consecutive values
     */
    public List<Double> toList(final double precision) {
        final List<Double> list = new ArrayList<>();
        if (includeLowerBound) {
            list.add(lowerBound);
        }

        for (double i = lowerBound + precision; i < upperBound; i += precision) {
            list.add(i);
        }

        if (includeUpperBound) {
            list.add(upperBound);
        }

        return list;
    }

    /**
     * Returns whether this range includes a value
     * @param value - the value to check
     * @return true when the value is included in this range
     *       false otherwise
     */
    public boolean includes(final Double value) {
        if (value.equals(Double.POSITIVE_INFINITY)) {
            return this.upperBound.equals(Double.POSITIVE_INFINITY);
        } else if (value.equals(Double.NEGATIVE_INFINITY)) {
            return this.lowerBound.equals(Double.NEGATIVE_INFINITY);
        }

        return (this.includeLowerBound ? (value >= this.lowerBound) : (value > this.lowerBound)) &&
                (this.includeUpperBound ? (value <= this.upperBound) : (value < this.upperBound));
    }

    /**
     * Returns whether this range includes all the values in another range
     * @param other - the other range to check values against
     * @return true when all  values of @other are included in this range
     *        false otherwise
     */
    public boolean includes(final Range other) {
        return this.includes(other.lowerBound) && this.includes(other.upperBound);
    }

    /**
     * Returns the overlap of this range with another range
     * @param other - the other range to check for overlap
     * @return the common range between this range and the other range
     */
    public Range intersection(final Range other) {
        if (this.includes(other)) {
            return other;
        } else if (other.includes(this)) {
            return this;
        } else if (this.includes(other.lowerBound)) {
            final Range intersection =  new Range(other.lowerBound, this.upperBound, other.includeLowerBound, this.includeUpperBound);
            if (intersection.lowerBound.equals(intersection.upperBound) && (!intersection.includeLowerBound || !intersection.includeUpperBound)) {
                return null;
            } else {
                return intersection;
            }
        } else if (this.includes(other.upperBound)) {
            final Range intersection = new Range(this.lowerBound, other.upperBound, this.includeLowerBound, other.includeUpperBound);
            if (intersection.lowerBound.equals(intersection.upperBound) && (!intersection.includeLowerBound || !intersection.includeUpperBound)) {
                return null;
            } else {
                return intersection;
            }
        } else {
            return null;
        }
    }

    /**
     * Returns whether this range overlaps with another range
     * @param other - the other range to check for overlap
     * @return true when the ranges overlap
     *       false otherwise
     */
    public boolean overlaps(final Range other) {
        if ((this.upperBound.equals(other.lowerBound) && (other.includeLowerBound || this.isIncludeUpperBound()))
                ||
                (this.lowerBound.equals(other.upperBound) && (this.includeLowerBound || other.isIncludeUpperBound()))
        ) {
            return true;
        }
        return this.includes(other.lowerBound) || this.includes(other.upperBound);
    }

    /**
     * Returns a string representation of this range
     * @return a string of the form `Range::[lowerBound --> upperBound]`
     */
    @Override
    public String toString() {
        return "Range::" + (includeLowerBound ? "[" : "(") +
                printBound(this.lowerBound) + " --> " + printBound(this.upperBound) +
                (this.includeUpperBound ? "]" : ")");
    }

    private String printBound(final Double value) {
        if (value.equals(Double.POSITIVE_INFINITY)) {
            return "∞";
        } else if (value.equals(Double.NEGATIVE_INFINITY)) {
            return "-∞";
        }

        return value.toString();
    }

    @Override
    public int compareTo(final Object o) {
        if (this == o) return 0;
        if (!(o instanceof Range other)) {
            throw new IllegalArgumentException("Cannot compare Range to different cla " + o.getClass().getName());
        }

        if (!this.lowerBound.equals(other.lowerBound)) {
            return this.lowerBound < other.lowerBound ? -1 : 1;
        } else if (this.includeLowerBound != other.includeLowerBound) {
            return this.includeLowerBound ? -1 : 1;
        } else if (!this.upperBound.equals(other.upperBound)) {
            return this.upperBound < other.upperBound ? -1 : 1;
        } else if (this.includeUpperBound != other.includeUpperBound) {
            return this.includeUpperBound ? -1 : 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(final Object other) {
        return  other instanceof Range && this.compareTo(other) == 0;
    }

    /**
     * Merge two ranges
     * If the ranges overlap, then merge them into one range
     * If the ranges do not overlap, then return a list of the two ranges
     * @param other - the other range to merge with this range
     * @return a list of the merged ranges
     **/
    public List<Range> merge(final Range other) {
        if (this.overlaps(other)) {
            final Double newLowerBound = this.lowerBound.compareTo(other.lowerBound) < 0 ? this.lowerBound : other.lowerBound;
            final Double newUpperBound = this.upperBound.compareTo(other.upperBound) > 0 ? this.upperBound : other.upperBound;
            final boolean newIncludeLowerBound = this.lowerBound.compareTo(other.lowerBound) < 0 ? this.includeLowerBound : other.includeLowerBound;
            final boolean newIncludeUpperBound = this.upperBound.compareTo(other.upperBound) > 0 ? this.includeUpperBound : other.includeUpperBound;

            return List.of(new Range(newLowerBound, newUpperBound, newIncludeLowerBound, newIncludeUpperBound));
        } else {
            return List.of(this.deepCopy(), other.deepCopy());
        }
    }

    /**
     * Returns a deep copy of this range
     */
    public Range deepCopy() {
        return new Range(this.lowerBound, this.upperBound, this.includeLowerBound, this.includeUpperBound);
    }
}
