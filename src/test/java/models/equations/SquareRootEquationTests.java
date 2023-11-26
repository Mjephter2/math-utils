package models.equations;

import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.radicals.SquareRootFunction;
import models.functions.trigonometric.TrigonometricFunction;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        final Double[] solution = equation.solve();

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());

        assertEquals(2, solution.length);

        assertEquals(1.0, solution[0], 0.0);
        assertEquals(0.0, solution[1], 0.0);

        System.out.println(solution[0]);
        System.out.println(solution[1]);
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
