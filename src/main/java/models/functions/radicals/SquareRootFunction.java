package models.functions.radicals;

import com.google.common.collect.Range;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import models.functions.Function;
import models.functions.FunctionType;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.inequalities.InequalityType;
import models.inequalities.LinearInequality;

import java.util.Collections;

/**
 * Class implementing a Square Root function.
 */
@Getter
public class SquareRootFunction extends RadicalFunction {

    private static final String SQUARE_ROOT = "√";

    SquareRootFunction(final String funcName, final String varName, final Function body) {
        super(funcName, varName, 2, body);
    }

    @Override
    public String printBody() {
        return SQUARE_ROOT + "(" + this.getBody().printBody() + ")";
    }
}
