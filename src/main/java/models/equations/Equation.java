package models.equations;

import models.functions.Function;
import models.numberUtils.Range;

import java.util.List;

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
    List<Range> solve();

    default String print() {
        return this.getLeftSide().printBody() + " = " + this.getRightSide().printBody();
    }
}
