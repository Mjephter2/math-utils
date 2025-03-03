package calculus.univariate.models.equations.log_equations;

import lombok.Getter;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.logarithmic.NaturalLogFunction;

/**
 * Represents an equation with a natural log function on the left side and a function on the right side
 */
@Getter
public class NaturalLogEquation extends LogEquation {

    public NaturalLogEquation(final NaturalLogFunction leftSide, final Function rightSide) {
        super(leftSide, rightSide, null);
    }
}
