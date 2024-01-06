package models.equations;

import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.radicals.SquareRootFunction;
import models.functions.trigonometric.TrigonometricFunction;
import models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        equation.solve();
        final HashMap<Range, Integer> solutions = equation.getSolutions();

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());

        assertEquals(1, solutions.size());
        System.out.println(solutions.keySet().toArray()[0]);
        assertEquals(1.0, ((Range) solutions.keySet().toArray()[0]).getLowerBound());
        assertEquals(1.0, ((Range) solutions.keySet().toArray()[0]).getUpperBound());
    }

    /**
     * Case where both left and right side are square root functions of a polynomial
     */
    @Test
    public void case2_tests() {
        final SquareRootFunction lhs = new SquareRootFunction("g", "x",
                new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(2)
                            .build());
                }}, "f", "x"));
        final SquareRootFunction rhs = new SquareRootFunction("h", "x",
                new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(1)
                            .build());
                }}, "f", "x"));

        final SquareRootEquation equation = SquareRootEquation.builder()
                .leftSide(lhs)
                .rightSide(rhs)
                .build();

        equation.solve();
        final HashMap<Range, Integer> solutions = equation.getSolutions();

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());

        assertEquals(2, solutions.size());

        assertEquals(1.0,  ((Range) solutions.keySet().toArray()[0]).getLowerBound());
        assertEquals(1.0,  ((Range) solutions.keySet().toArray()[0]).getUpperBound());

        assertEquals(0.0, ((Range) solutions.keySet().toArray()[1]).getLowerBound());
        assertEquals(0.0, ((Range) solutions.keySet().toArray()[1]).getUpperBound());
    }

    @Test
    public void exceptions_tests() {
        final SquareRootFunction lhs = new SquareRootFunction("g", "x",
                new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(2)
                            .build());
                }}, "f", "x"));
        final TrigonometricFunction trigFunc = TrigonometricFunction.builder().build();

        assertThrows(UnsupportedOperationException.class, () -> SquareRootEquation.builder()
                .leftSide(lhs)
                .rightSide(trigFunc)
                .build()
                .solve()
        );
    }
}
