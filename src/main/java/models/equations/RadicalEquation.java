package models.equations;

import lombok.Builder;
import models.equations.polynomialEquations.PolynomialEquation;
import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.functions.radicals.SquareRootFunction;

@Builder
public class RadicalEquation implements Equation {

    private final SquareRootFunction leftSide;
    private final PolynomialFunction rightSide;

    @Override
    public Function leftSide() {
        return leftSide;
    }

    @Override
    public Function rightSide() {
        return rightSide;
    }

    @Override
    public Double[] solve() {
        // TODO: Implement RadicalEquation solver for various cases

        // Case where left side is a square root function and right side is a polynomial function
        final PolynomialFunction lhsSquared = PolynomialFunction.builder()
                .funcName(leftSide.getFuncName())
                .varName(leftSide.getVarName())
                .terms(((PolynomialFunction) leftSide.getBody()).getTerms())
                .build();
        final PolynomialFunction rhsSquared = PolynomialFunction.builder()
                .funcName(rightSide.getFuncName())
                .varName(rightSide.getVarName())
                .terms(rightSide.getTerms())
                .build()
                .power(2);
        return new PolynomialEquation(lhsSquared, rhsSquared).solve();
    }
}
