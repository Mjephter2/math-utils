package univariate.models.equations;

import calculus.univariate.models.equations.log_equations.LogEquation;
import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.FunctionType;
import calculus.univariate.models.functions.logarithmic.LogFunction;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.functions.radicals.RadicalFunction;
import calculus.univariate.models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogEquationTests {

    @Test
    public void solve_tests() {
        final LogFunction leftSide = LogFunction.builder()
                .base(10)
                .body(
                        new PolynomialFunction(List.of(
                                PolynomialTerm.builder()
                                        .coefficient(1)
                                        .varName("x")
                                        .exponent(1)
                                        .build()
                        ),
                                "f",
                                "x"
                        )
                )
                .funcName("g")
                .varName("x")
                .build();
        final LogEquation logEquation = LogEquation.builder()
                .leftSide(leftSide)
                .rightSide(ConstantFunction.builder()
                        .funcName("C")
                        .value(2)
                        .build())
                .build();

        assertEquals(logEquation.getLeftSide(), leftSide);
        assertEquals(logEquation.getLeftSide().getFuncType(), FunctionType.LOGARITHMIC);
        assertEquals(logEquation.getLeftSide().getBase(), 10, 0);
        assertEquals(logEquation.getLeftSide().getBody().getFuncType(), FunctionType.POLYNOMIAL);
        assertEquals(((PolynomialFunction) logEquation.getLeftSide().getBody()).getTerms().size(), 1);
        assertEquals(((PolynomialFunction) logEquation.getLeftSide().getBody()).getTerms().get(0).getCoefficient(), 1, 0);
        assertEquals(((PolynomialFunction) logEquation.getLeftSide().getBody()).getTerms().get(0).getVarName(), "x");
        assertEquals(((PolynomialFunction) logEquation.getLeftSide().getBody()).getTerms().get(0).getExponent(), 1, 0);
        assertEquals(logEquation.getLeftSide().getFuncName(), "g");
        assertEquals(logEquation.getLeftSide().getVarName(), "x");

        assertEquals(logEquation.getRightSide().getFuncType(), FunctionType.CONSTANT);
        assertEquals(((ConstantFunction)logEquation.getRightSide()).getValue(), 2, 0);
        assertEquals(logEquation.getRightSide().getFuncName(), "C");

        logEquation.solve();
        HashMap<Range, Integer> solutions = logEquation.getSolutions();
        assertEquals(solutions.size(), 1);
    }

    @Test
    public void exception_tests() {
        final LogEquation logEquation = LogEquation.builder()
                .leftSide(LogFunction.builder()
                        .base(10)
                        .body(
                                RadicalFunction.builder().build()
                        )
                        .funcName("g")
                        .varName("x")
                        .build())
                .rightSide(ConstantFunction.builder()
                        .funcName("C")
                        .value(2)
                        .build())
                .build();

        assertEquals(logEquation.getLeftSide().getFuncType(), FunctionType.LOGARITHMIC);
        assertEquals(((LogFunction)logEquation.getLeftSide()).getBody().getFuncType(), FunctionType.RADICAL);
        assertEquals(logEquation.getRightSide().getFuncType(), FunctionType.CONSTANT);

        assertThrows(UnsupportedOperationException.class, logEquation::solve);
    }
}
