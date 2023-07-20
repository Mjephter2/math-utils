package models.equations;

import models.functions.Function;

/**
 * Interface representing an equation.
 */
public interface Equation {

    /**
     * @return the left side of the equation.
     */
    Function leftSide();

    /**
     * @return the right side of the equation.
     */
    Function rightSide();

    /**
     * @return the solution of the equation.
     */
    Double solve();
}
