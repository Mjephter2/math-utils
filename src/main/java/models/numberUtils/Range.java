package models.numberUtils;

import lombok.Getter;

import java.util.List;

/**
 * A class representing a range of numbers
 */
@Getter
public class Range implements Comparable {

    /**
     * The lower bound of the range
     */
    private Double lowerBound;
    /**
     * Whether the lower bound is included in the range
     */
    private boolean includeLowerBound;

    /**
     * The upper bound of the range
     */
    private Double upperBound;
    /**
     * Whether the upper bound is included in the range
     */
    private boolean includeUpperBound;

    /**
     * Constructs a range of numbers
     * @param lowerBound - the lower bound of the range
     * @param uppperBound - the upper bound of the range
     * @param includeLowerBound - whether the lower bound is included in the range
     * @param includeUpperBound - whether the upper bound is included in the range
     */
    public Range(final Double lowerBound, final Double uppperBound, final boolean includeLowerBound, final boolean includeUpperBound) {
        if (validatedBounds(lowerBound, uppperBound, includeLowerBound, includeUpperBound)) {
            this.lowerBound = lowerBound;
            this.upperBound = uppperBound;
            this.includeLowerBound = includeLowerBound;
            this.includeUpperBound = includeUpperBound;
        } else {
            throw new IllegalArgumentException("Invalid bounds values provided");
        }
    }

    private boolean validatedBounds(final Double lowerBound, final Double uppperBound, final boolean includeLowerBound, final boolean includeUpperBound) {
        if ((includeLowerBound && lowerBound.isInfinite()) || (includeUpperBound && uppperBound.isInfinite())) {
            return false;
        }
        return true;
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
     * Returns a range of all Double values
     */
    public static Range all() {
        return new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, false);
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
     * @param other
     * @return the common range between this range and the other range
     */
    public Range intersection(final Range other) {
        if (this.includes(other)) {
            return other;
        } else if (other.includes(this)) {
            return this;
        } else if (this.includes(other.lowerBound)) {
            return new Range(other.lowerBound, this.upperBound, other.includeLowerBound, this.includeUpperBound);
        } else if (this.includes(other.upperBound)) {
            return new Range(this.lowerBound, other.upperBound, this.includeLowerBound, other.includeUpperBound);
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
        final StringBuilder rep = new StringBuilder("Range::").append(includeLowerBound ? "[" : "(");
        rep.append(printBound(this.lowerBound)).append(" --> ").append(printBound(this.upperBound));
        rep.append(this.includeUpperBound ? "]" : ")");

        return rep.toString();
    }

    private String printBound(final Double value) {
        if (value.equals(Double.POSITIVE_INFINITY)) {
            return "∞";
        } else if (value.equals(Double.NEGATIVE_INFINITY)) {
            return "-∞";
        }

        return value.toString();
    }

    /**
     * Returns whether this range is equal to another range
     * @param o the object to be compared.
     * @return true when the ranges have the same bounds and include flags
     */
    @Override
    public int compareTo(final Object o) {
        if (o instanceof Range) {
            final Range other = (Range) o;
            if (this.lowerBound.equals(other.lowerBound)) {
                if (this.upperBound.equals(other.upperBound)) {
                    return this.includeLowerBound == other.includeLowerBound && this.includeUpperBound == other.includeUpperBound ? 0 : -1;
                } else {
                    return this.upperBound.compareTo(other.upperBound);
                }
            } else {
                return this.lowerBound.compareTo(other.lowerBound);
            }
        }

        throw new IllegalArgumentException("Cannot compare Range to non-Range object");
    }

    @Override
    public boolean equals(final Object other) {
        return  this.compareTo(other) == 0;
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
