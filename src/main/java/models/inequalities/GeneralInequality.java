package models.inequalities;

import lombok.Builder;
import lombok.Getter;
import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.numberUtils.Range;

import java.util.List;

@Getter
@Builder
public class GeneralInequality implements Inequality {

    private Function leftSide;
    private Function rightSide;
    private InequalityType type;
    private List<Range> solution;

    @Override
    public void solve() {
        // If both sides are polynomials, solve as a polynomial inequality
        if (leftSide instanceof PolynomialFunction && rightSide instanceof PolynomialFunction) {
            final PolynomialInequality polynomialInequality = PolynomialInequality.builder()
                    .leftSide((PolynomialFunction) leftSide)
                    .rightSide((PolynomialFunction) rightSide)
                    .type(type)
                    .build();
            polynomialInequality.solve();
            this.solution = polynomialInequality.getSolution();
            return;
        }
        throw new UnsupportedOperationException("Unimplemented solver for provided inputs: " + this.printInequality());
    }
}
