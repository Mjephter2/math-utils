package calculus.univariate.models.inequalities;

import lombok.Builder;
import lombok.Getter;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.numberUtils.Range;

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
            final PolynomialInequality polynomialInequality = new PolynomialInequality(type, (PolynomialFunction) leftSide, (PolynomialFunction) rightSide);
            polynomialInequality.solve();
            this.solution = polynomialInequality.getSolution();
            return;
        }
        throw new UnsupportedOperationException("Unimplemented solver for provided inputs: " + this.printInequality());
    }
}
