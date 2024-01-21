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
import java.util.List;

@Getter
@Builder
public class GeneralEquation implements Equation {

    final Function leftSide;
    final Function rightSide;
    final HashMap<Range, Integer> solutions;

    @Override
    public Function getLeftSide() {
        return this.leftSide;
    }

    @Override
    public Function getRightSide() {
        return this.rightSide;
    }

    @Override
    public HashMap<Range, Integer> getSolutions() {
        if (this.leftSide instanceof PolynomialFunction && this.rightSide instanceof PolynomialFunction) {
            final PolynomialEquation equation = new PolynomialEquation((PolynomialFunction) this.leftSide, (PolynomialFunction) this.rightSide);
            return equation.getSolutions();
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
            return equation.getSolutions();
        } else if (this.leftSide instanceof ConstantFunction && this.rightSide instanceof PolynomialFunction) {
            return GeneralEquation.builder()
                    .leftSide(this.rightSide)
                    .rightSide(this.leftSide)
                    .build()
                    .getSolutions();
        }
        // TODO: Implement GeneralEquation for other types of equations
        throw new UnsupportedOperationException("GeneralEquation does not support this type of equation.");
    }

    @Override
    public void solve() {
        // TODO: Implement solve for GeneralEquation
    }

    @Override
    public String print() {
        return Equation.super.print();
    }
}
