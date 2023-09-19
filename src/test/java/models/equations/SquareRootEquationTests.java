package models.equations;

import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.radicals.SquareRootFunction;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class SquareRootEquationTests {

    /**
     * Case where left side is a square root function of a polynomial and right side is a polynomial function
     */
    @Test
    public void case1_tests() {
        final SquareRootFunction lhs = new SquareRootFunction("g", "x",
                new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(2)
                            .build());
                }}, "f", "x"));
        final PolynomialFunction rhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-2.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "h", "x");

        final SquareRootEquation equation = SquareRootEquation.builder()
                .leftSide(lhs)
                .rightSide(rhs)
                .build();

        final Double[] solution = equation.solve();

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());

        assertEquals(1, solution.length);
        assertEquals(1.0, solution[0], 0.0);
    }
}
