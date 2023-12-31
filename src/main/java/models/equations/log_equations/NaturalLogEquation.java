package models.equations.log_equations;

import models.functions.Function;
import models.functions.logarithmic.NaturalLogFunction;

/**
 * Represents an equation with a natural log function on the left side and a function on the right side
 */
public class NaturalLogEquation extends LogEquation {

    public NaturalLogEquation(final NaturalLogFunction leftSide, final Function rightSide) {
        super(leftSide, rightSide);
    }
}
