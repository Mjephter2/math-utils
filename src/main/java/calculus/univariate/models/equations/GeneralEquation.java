package calculus.univariate.models.equations;

import lombok.Builder;
import lombok.Getter;
import calculus.univariate.models.equations.polynomial_equations.PolynomialEquation;
import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.numberUtils.Range;

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
        // Case where left side is equal to the right side: any value can be a solution
        if (this.leftSide.equals(this.rightSide)) {
            final HashMap<Range, Integer> solutions = new HashMap<>() {{
                put(Range.all(), -1);
            }};
            this.solutions = solutions;
            return;
        }

        if (this.leftSide instanceof PolynomialFunction && this.rightSide instanceof PolynomialFunction) {
            final PolynomialEquation equation = new PolynomialEquation((PolynomialFunction) this.leftSide, (PolynomialFunction) this.rightSide);
            this.solutions =  equation.getSolutions();
            return;
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
            return;
        } else if (this.leftSide instanceof ConstantFunction && this.rightSide instanceof PolynomialFunction) {
            this.solutions =  GeneralEquation.builder()
                    .leftSide(this.rightSide)
                    .rightSide(this.leftSide)
                    .build()
                    .getSolutions();
            return;
        }
        // TODO: Implement GeneralEquation for other types of equations
        throw new UnsupportedOperationException("GeneralEquation does not support this type of equation.");
    }

    @Override
    public String print() {
        return Equation.super.print();
    }
}
