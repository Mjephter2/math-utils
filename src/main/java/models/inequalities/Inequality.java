package models.inequalities;

import com.google.common.collect.Range;
import models.functions.Function;

import java.util.List;

/**
 * Represents an inequality
 */
public interface Inequality {

    Function getLeftSide();

    Function getRightSide();

    InequalityType getType();

    List<Range<Double>> getSolution();

    /**
     * Solves the inequality
     * @return the range of values that satisfy the inequality
     */
    void solve();

    /**
     * Prints the inequality
     * @return the inequality as a string
     */
    default String printInequality() {
        return this.getLeftSide().printBody() + " " + this.getType().getSymbol() + " " + this.getRightSide().printBody();
    }
}
