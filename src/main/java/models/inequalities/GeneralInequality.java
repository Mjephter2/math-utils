package models.inequalities;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;
import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;

import java.util.List;

@Getter
@Builder
public class GeneralInequality implements Inequality {

    private Function leftSide;
    private Function rightSide;
    private InequalityType type;

    @Override
    public List<Range<Double>> solve() {
        // If both sides are polynomials, solve as a polynomial inequality
        if (leftSide instanceof PolynomialFunction && rightSide instanceof PolynomialFunction) {
            return PolynomialInequality.builder()
                    .leftSide((PolynomialFunction) leftSide)
                    .rightSide((PolynomialFunction) rightSide)
                    .type(type)
                    .build()
                    .solve();
        }
        System.out.println("Unimplemented solver for provided inputs: " + this.printInequality());
        return null;
    }
}
