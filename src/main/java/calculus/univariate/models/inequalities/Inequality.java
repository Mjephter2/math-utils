package calculus.univariate.models.inequalities;

import calculus.univariate.models.functions.Function;
import calculus.univariate.models.numberUtils.Range;

import java.util.List;

/**
 * Represents an inequality
 */
public interface Inequality {

    Function getLeftSide();

    Function getRightSide();

    InequalityType getType();

    List<Range> getSolution();

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
