package models.equations;

import lombok.Builder;
import lombok.Getter;
import models.equations.polynomial_equations.PolynomialEquation;
import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.functions.radicals.SquareRootFunction;
import models.numberUtils.Range;

import java.util.List;

@Builder
@Getter
public class SquareRootEquation implements Equation {

    private final SquareRootFunction leftSide;
    private final Function rightSide;

    @Override
    public List<Range> solve() {
        // TODO: Implement SquareRootEquation solver for various cases

        // 1 - Case where left side is a square root function of a polynomial and right side is a polynomial function
        if (leftSide.getBody() instanceof PolynomialFunction && rightSide instanceof PolynomialFunction) {
            final PolynomialFunction lhsSquared = new PolynomialFunction(((PolynomialFunction) leftSide.getBody()).getTerms(), leftSide.getFuncName(), leftSide.getVarName());
            final PolynomialFunction rhsSquared = new PolynomialFunction(((PolynomialFunction) rightSide).getTerms(), rightSide.getFuncName(), rightSide.getVarName()).power(2);
            return new PolynomialEquation(lhsSquared, rhsSquared).solve();
        } else if (
                leftSide.getBody() instanceof PolynomialFunction &&
                rightSide instanceof SquareRootFunction &&
                ((SquareRootFunction) rightSide).getBody() instanceof PolynomialFunction) {
            // 2 - Case where both left and right side are square root functions of a polynomial
            return new PolynomialEquation(
                    new PolynomialFunction(((PolynomialFunction) leftSide.getBody()).getTerms(), leftSide.getFuncName(), leftSide.getVarName()),
                    new PolynomialFunction(((PolynomialFunction) ((SquareRootFunction) rightSide).getBody()).getTerms(), rightSide.getFuncName(), rightSide.getVarName())
            ).solve();
        }
        throw new UnsupportedOperationException("Unimplemented solver for square root equations.");
    }
}
