package univariate.models.equations;

import univariate.models.functions.Function;

/**
 * Interface representing an equation.
 */
public interface Equation {

    /**
     * @return the left side of the equation.
     */
    Function getLeftSide();

    /**
     * @return the right side of the equation.
     */
    Function getRightSide();

    /**
     * Solves the equation.
     */
    void solve();

    default String print() {
        return this.getLeftSide().printBody() + " = " + this.getRightSide().printBody();
    }
}
