package calculus.univariate.models.equations.log_equations;

import lombok.Builder;
import lombok.Getter;
import calculus.univariate.models.equations.Equation;
import calculus.univariate.models.equations.polynomial_equations.PolynomialEquation;
import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.logarithmic.LogFunction;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.numberUtils.Range;

import java.util.HashMap;
import java.util.List;

@Getter
@Builder
public class LogEquation implements Equation {

    private final LogFunction leftSide;
    private final Function rightSide;
    private HashMap<Range, Integer> solutions;

    @Override
    public void solve() {
        // TODO: Implement LogEquation solver for more cases

        // Case where left side is a log function of a polynomial and right side is a constant
        if (leftSide.getBody() instanceof PolynomialFunction && rightSide instanceof ConstantFunction) {
            final PolynomialEquation polynomialFunction = new PolynomialEquation(
                    new PolynomialFunction(((PolynomialFunction) leftSide.getBody()).getTerms(), leftSide.getFuncName(), leftSide.getVarName()),
                    new PolynomialFunction(List.of(PolynomialTerm.builder()
                            .coefficient(Math.pow(leftSide.getBase(), ((ConstantFunction) rightSide).getValue()))
                            .varName(leftSide.getVarName())
                            .exponent(0)
                            .build()), rightSide.getFuncName(), leftSide.getVarName()
                    )
            );
            polynomialFunction.solve();
            this.solutions = polynomialFunction.getSolutions();
            return;
        }

        throw new UnsupportedOperationException("LogEquation solver not implemented for this case");
    }
}
