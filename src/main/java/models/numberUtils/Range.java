package models.numberUtils;

import lombok.Getter;

import java.util.List;

/**
 *
 */
@Getter
public class Range implements Comparable {

    private Double lowerBound;
    private boolean includeLowerBound;

    private Double upperBound;
    private boolean includeUpperBound;

    // made public for testability
    // this constructor should technically be private
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

    public static Range atLeast(final double lowerBound) {
        return new Range(lowerBound, Double.POSITIVE_INFINITY, true, false);
    }

    public static Range greaterThan(final double lowerBound) {
        return new Range(lowerBound, Double.POSITIVE_INFINITY, false, false);
    }

    public static Range atMost(final double upperBound) {
        return new Range(Double.NEGATIVE_INFINITY, upperBound, false, true);
    }

    public static Range lessThan(final double upperBound) {
        return new Range(Double.NEGATIVE_INFINITY, upperBound, false, false);
    }

    public static Range all() {
        return new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, false);
    }

    public static Range allPositive(final boolean includeZero) {
        return new Range(0.0, Double.POSITIVE_INFINITY, includeZero, false);
    }

    public static Range allNegative(final boolean includeZero) {
        return new Range(Double.NEGATIVE_INFINITY, 0.0, false, includeZero);
    }

    public boolean includes(final Double value) {
        if (value.equals(Double.POSITIVE_INFINITY)) {
            return this.upperBound.equals(Double.POSITIVE_INFINITY);
        } else if (value.equals(Double.NEGATIVE_INFINITY)) {
            return this.lowerBound.equals(Double.NEGATIVE_INFINITY);
        }

        return (this.includeLowerBound ? value >= this.lowerBound : value > this.lowerBound) &&
                (this.includeUpperBound ? value <= this.upperBound : value < this.upperBound);
    }

    public boolean includes(final Range other) {
        return this.includes(other.lowerBound) && this.includes(other.upperBound);
    }

    public boolean overlaps(final Range other) {
        return this.includes(other.lowerBound) || this.includes(other.upperBound);
    }

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

    @Override
    public int compareTo(final Object o) {
        if (o instanceof Range) {
            final Range other = (Range) o;
            if (this.lowerBound.equals(other.lowerBound)) {
                if (this.upperBound.equals(other.upperBound)) {
                    return this.lowerBound == other.lowerBound && this.upperBound == other.upperBound ? 0 : -1;
                } else {
                    return this.upperBound.compareTo(other.upperBound);
                }
            } else {
                return this.lowerBound.compareTo(other.lowerBound);
            }
        }

        throw new IllegalArgumentException("Cannot compare Range to non-Range object");
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

    // Deep copy this range
    public Range deepCopy() {
        return new Range(this.lowerBound, this.upperBound, this.includeLowerBound, this.includeUpperBound);
    }
}
