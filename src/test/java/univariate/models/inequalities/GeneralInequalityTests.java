package univariate.models.inequalities;

import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.functions.trigonometric.TrigonometricFunction;
import calculus.univariate.models.functions.trigonometric.TrigonometricFunctionType;
import calculus.univariate.models.inequalities.GeneralInequality;
import calculus.univariate.models.inequalities.InequalityType;
import calculus.univariate.models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeneralInequalityTests {

    @Test
    public void solve_polynomial_inequality_test() {
        final GeneralInequality generalInequality = GeneralInequality.builder()
                .leftSide(new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(6.0)
                            .varName("x")
                            .exponent(1)
                            .build());
                }}, "f", "x"))
                .rightSide(new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(6.0)
                            .varName("x")
                            .exponent(0)
                            .build());
                }}, "f", "x"))
                .type(InequalityType.LESS_THAN)
                .build();

        generalInequality.solve();
        List<Range> solution = generalInequality.getSolution();
        assertEquals(1, solution.size());
        assertEquals(Range.lessThan(1.0), solution.get(0));
    }

    @Test
    public void exceptionsTests() {
        final GeneralInequality generalInequality = GeneralInequality.builder()
                .leftSide(TrigonometricFunction.builder()
                        .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                        .innerFunction(ConstantFunction.builder().build())
                        .build())
                .rightSide(new PolynomialFunction(new LinkedList<>(), "f", "x"))
                .type(InequalityType.LESS_THAN)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> generalInequality.solve());
    }
}
