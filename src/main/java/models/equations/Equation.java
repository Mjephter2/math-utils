package models.equations;

import models.functions.Function;

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
     * @return the solution of the equation.
     */
    Double[] solve();
}
