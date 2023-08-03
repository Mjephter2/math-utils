package models.inequalities;

import com.google.common.collect.Range;

import models.functions.Function;


/**
 * Class representing an inequality
 */
public class LinearInequality {

    private InequalityType type;
    private Function leftSide;
    private Function rightSide;

    public LinearInequality(final InequalityType type, final Function leftSide, final Function rightSide) {
        // TODO: Validate function types as linear
        this.type = type;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    /**
     * Solves the inequality
     * @return the range of values that satisfy the inequality
     */
    public Range<Double> solve() {
        // TODO: Implement
        return null;
    }
}
