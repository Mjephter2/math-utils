package models.equations;

import lombok.Builder;
import lombok.Getter;
import models.equations.polynomial_equations.PolynomialEquation;
import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;

import java.util.HashMap;
import java.util.LinkedList;

@Getter
@Builder
public class GeneralEquation implements Equation {

    private final Function leftSide;
    final Function rightSide;
    private HashMap<Range, Integer> solutions;

    @Override
    public Function getLeftSide() {
        return this.leftSide;
    }

    @Override
    public Function getRightSide() {
        return this.rightSide;
    }

    @Override
    public void solve() {
        if (this.leftSide instanceof PolynomialFunction && this.rightSide instanceof PolynomialFunction) {
            final PolynomialEquation equation = new PolynomialEquation((PolynomialFunction) this.leftSide, (PolynomialFunction) this.rightSide);
            this.solutions =  equation.getSolutions();
        } else if (this.leftSide instanceof PolynomialFunction && this.rightSide instanceof ConstantFunction) {
            final PolynomialTerm constantTerm = PolynomialTerm.builder()
                    .coefficient(((ConstantFunction) this.rightSide).getValue())
                    .exponent(0)
                    .varName(this.rightSide.getVarName())
                    .build();
            final PolynomialFunction constFunc = new PolynomialFunction(new LinkedList<>() {{
                add(constantTerm);
            }}, "temp", this.rightSide.getVarName());
            final PolynomialEquation equation = new PolynomialEquation((PolynomialFunction) this.leftSide, constFunc);
            this.solutions = equation.getSolutions();
        } else if (this.leftSide instanceof ConstantFunction && this.rightSide instanceof PolynomialFunction) {
            this.solutions =  GeneralEquation.builder()
                    .leftSide(this.rightSide)
                    .rightSide(this.leftSide)
                    .build()
                    .getSolutions();
        }
        // TODO: Implement GeneralEquation for other types of equations
        throw new UnsupportedOperationException("GeneralEquation does not support this type of equation.");
    }

    @Override
    public String print() {
        return Equation.super.print();
    }
}
