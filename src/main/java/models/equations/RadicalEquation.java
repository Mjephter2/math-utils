package models.equations;

import lombok.Builder;
import models.functions.Function;
import models.functions.PolynomialFunction;
import models.functions.SquareRootFunction;

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
        // TODO: Implement solver for RadicalEquation
        final PolynomialFunction lhsSquared = PolynomialFunction.builder()
                .funcName(leftSide.getFuncName())
                .varName(leftSide.getVarName())
                .terms(((PolynomialFunction) leftSide.getFunction()).getTerms())
                .build();
        final PolynomialFunction rhsSquared = PolynomialFunction.builder()
                .funcName(rightSide.getFuncName())
                .varName(rightSide.getVarName())
                .terms(rightSide.getTerms())
                .build()
                .power(2);
        return new Double[0];
    }
}
