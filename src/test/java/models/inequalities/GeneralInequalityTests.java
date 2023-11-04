package models.inequalities;

import com.google.common.collect.Range;
import models.functions.ConstantFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.trigonometric.TrigonometricFunction;
import models.functions.trigonometric.TrigonometricFunctionType;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

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
        List<Range<Double>> solution = generalInequality.getSolution();
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

        generalInequality.solve();
        assertNull(generalInequality.getSolution());
    }
}
