package models.equations.log_equations;

import lombok.Builder;
import lombok.Getter;
import models.equations.Equation;
import models.equations.polynomial_equations.PolynomialEquation;
import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.logarithmic.LogFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;

import java.util.List;

@Getter
@Builder
public class LogEquation implements Equation {

    private final LogFunction leftSide;
    private final Function rightSide;

    @Override
    public Function leftSide() {
        return this.leftSide;
    }

    @Override
    public Function rightSide() {
        return this.rightSide;
    }

    @Override
    public Double[] solve() {
        // TODO: Implement LogEquation solver for more cases

        // Case where left side is a log function of a polynomial and right side is a constant
        if (leftSide.getBody() instanceof PolynomialFunction && rightSide instanceof ConstantFunction) {
            return new PolynomialEquation(
                    PolynomialFunction.builder()
                            .funcName(leftSide.getFuncName())
                            .varName(leftSide.getVarName())
                            .terms(((PolynomialFunction) leftSide.getBody()).getTerms())
                            .build(),
                    PolynomialFunction.builder()
                            .funcName(rightSide.getFuncName())
                            .varName(rightSide.getVarName())
                            .terms(List.of(PolynomialTerm.builder()
                                    .coefficient(Math.pow(leftSide.getBase(), ((ConstantFunction) rightSide).getValue()))
                                            .varName(rightSide.getVarName())
                                            .exponent(1)
                                    .build()))
                            .build())
                    .solve();
        }

        return new Double[0];
    }
}
