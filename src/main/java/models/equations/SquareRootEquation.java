package models.equations;

import lombok.Builder;
import lombok.Getter;
import models.equations.polynomial_equations.PolynomialEquation;
import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.functions.radicals.SquareRootFunction;
import models.numberUtils.Range;

import java.util.HashMap;

@Builder
@Getter
public class SquareRootEquation implements Equation {

    private final SquareRootFunction leftSide;
    private final Function rightSide;
    private HashMap<Range, Integer> solutions;

    @Override
    public void solve() {
        // TODO: Implement SquareRootEquation solver for various cases

        // 1 - Case where left side is a square root function of a polynomial and right side is a polynomial function
        if (leftSide.getBody() instanceof PolynomialFunction && rightSide instanceof PolynomialFunction) {
            final PolynomialFunction lhsSquared = new PolynomialFunction(((PolynomialFunction) leftSide.getBody()).getTerms(), leftSide.getFuncName(), leftSide.getVarName());
            final PolynomialFunction rhsSquared = new PolynomialFunction(((PolynomialFunction) rightSide).getTerms(), rightSide.getFuncName(), rightSide.getVarName()).power(2);
            final PolynomialEquation polynomialEquation = new PolynomialEquation(lhsSquared, rhsSquared);
            polynomialEquation.solve();
            this.solutions = polynomialEquation.getSolutions();
        } else if (
                leftSide.getBody() instanceof PolynomialFunction &&
                rightSide instanceof SquareRootFunction &&
                ((SquareRootFunction) rightSide).getBody() instanceof PolynomialFunction) {
            // 2 - Case where both left and right side are square root functions of a polynomial
            final PolynomialEquation polynomialEquation = new PolynomialEquation(
                    new PolynomialFunction(((PolynomialFunction) leftSide.getBody()).getTerms(), leftSide.getFuncName(), leftSide.getVarName()),
                    new PolynomialFunction(((PolynomialFunction) ((SquareRootFunction) rightSide).getBody()).getTerms(), rightSide.getFuncName(), rightSide.getVarName())
            );
            polynomialEquation.solve();
            this.solutions = polynomialEquation.getSolutions();
        }
        throw new UnsupportedOperationException("Unimplemented solver for square root equations.");
    }
}
