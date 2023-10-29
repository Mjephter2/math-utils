package models.inequalities;

import com.google.common.collect.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class PolynomialInequalityTests {

    @Test
    public void linearInequalityTest() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");

        final PolynomialInequality inequality = PolynomialInequality.builder()
                .leftSide(leftSide)
                .rightSide(rightSide)
                .type(InequalityType.GREATER_THAN)
                .build();

        assertEquals(InequalityType.GREATER_THAN, inequality.getType());
        assertEquals(leftSide, inequality.getLeftSide());
        assertEquals(rightSide, inequality.getRightSide());

        List<Range<Double>> solution = inequality.solve();

        assertEquals(1, solution.size());
    }

    @Test
    public void exceptionsTest() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");

        final PolynomialInequality inequality = PolynomialInequality.builder()
                .leftSide(leftSide)
                .rightSide(rightSide)
                .type(InequalityType.GREATER_THAN)
                .build();

        assertThrows(UnsupportedOperationException.class, inequality::solve);
    }
}
