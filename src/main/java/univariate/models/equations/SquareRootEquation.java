package univariate.models.equations;

import lombok.Builder;
import lombok.Getter;
import univariate.models.equations.polynomial_equations.PolynomialEquation;
import univariate.models.functions.ConstantFunction;
import univariate.models.functions.Function;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialTerm;
import univariate.models.functions.radicals.SquareRootFunction;
import univariate.models.numberUtils.Range;

import java.util.HashMap;
import java.util.List;

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
            return;
        }
        // Case 3 -  where left side is a square root function of a constant and right side is a PolynomialFunction
        else if (leftSide.getBody() instanceof ConstantFunction && rightSide instanceof PolynomialFunction) {
            final PolynomialFunction lhsSquared = new PolynomialFunction(List.of(PolynomialTerm.builder()
                    .coefficient(((ConstantFunction) leftSide.getBody()).getValue())
                    .varName(leftSide.getVarName())
                    .exponent(0)
                    .build()), leftSide.getFuncName(), leftSide.getVarName());
            final PolynomialFunction rhsSquared = new PolynomialFunction(((PolynomialFunction) rightSide).getTerms(), rightSide.getFuncName(), rightSide.getVarName()).power(2);
            final PolynomialEquation polynomialEquation = new PolynomialEquation(rhsSquared, lhsSquared);
            polynomialEquation.solve();
            this.solutions = polynomialEquation.getSolutions();
            return;
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
            return;
        }
        throw new UnsupportedOperationException("Unimplemented solver for square root equations.");
    }
}
