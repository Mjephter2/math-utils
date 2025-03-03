package calculus.univariate.models.numberUtils;

public class GenericRange<T extends Comparable> implements Comparable{

    /**
     * The lower bound of the range
     */
    private final T lowerBound;
    /**
     * Whether the lower bound is included in the range
     */
    private final boolean includeLowerBound;

    /**
     * The upper bound of the range
     */
    private final T upperBound;
    /**
     * Whether the upper bound is included in the range
     */
    private final boolean includeUpperBound;

    public GenericRange(T lowerBound, boolean includeLowerBound, T upperBound, boolean includeUpperBound) {
        if (lowerBound == null || upperBound == null) {
            throw new IllegalArgumentException("Lower and Upper bounds cannot be null");
        }
        if (lowerBound.compareTo(upperBound) > 0) {
            throw new IllegalArgumentException("Lower bound cannot be greater than upper bound");
        }
        this.lowerBound = lowerBound;
        this.includeLowerBound = includeLowerBound;
        this.upperBound = upperBound;
        this.includeUpperBound = includeUpperBound;
    }

    @Override
    public int compareTo(Object other) {
        if (this == other) return 0;
        if (!(other instanceof GenericRange otherRange)) {
            throw new IllegalArgumentException("Cannot compare Range to different class " + other.getClass().getName());
        } else {
            if (this.lowerBound.compareTo(otherRange) == 0) {
                return (this.lowerBound.compareTo(otherRange.lowerBound) < 0) ? -1 : 1;
            } else if (this.includeLowerBound != otherRange.includeLowerBound) {
                return this.includeLowerBound ? -1 : 1;
            } else if (!this.upperBound.equals(otherRange.upperBound)) {
                return (this.upperBound.compareTo(otherRange.upperBound) < 0) ? -1 : 1;
            } else if (this.includeUpperBound != otherRange.includeUpperBound) {
                return this.includeUpperBound ? -1 : 1;
            } else {
                return 0;
            }
        }
    }
}
