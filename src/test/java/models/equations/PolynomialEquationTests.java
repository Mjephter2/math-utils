package models.equations;

import models.equations.polynomial_equations.PolynomialEquation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PolynomialEquationTests {

    @Test
    public void solve_quadratic_test() {
        final PolynomialFunction lhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(2)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(3)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");

        final PolynomialEquation equation = new PolynomialEquation(lhs, rhs);

        assertEquals("x + 1 = - x² + 3", equation.print());

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());
        assertEquals(2, equation.getDegree());

        final Double[] solution = equation.solve();
        assertEquals(2, solution.length);
        assertEquals(1.0, solution[0], 0.0);
        assertEquals(-2.0, solution[1], 0.0);
    }

    @Test
    public void solve_linear_test() {
        final PolynomialFunction lhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");

        final PolynomialEquation equation = new PolynomialEquation(lhs, rhs);

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());
        assertEquals(1, equation.getDegree());

        final Double[] solution = equation.solve();
        assertEquals(1, solution.length);
        assertEquals(-2.0, solution[0], 0.0);
    }

    @Test
    public void exceptions_higher_degree_test() {
        final PolynomialFunction lhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(3)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");

        final PolynomialEquation equation = new PolynomialEquation(lhs, rhs);

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());
        assertEquals(3, equation.getDegree());

        assertThrows(UnsupportedOperationException.class, () -> equation.solve());
    }

    @Test
    public void exceptions_null_lhs_test() {
        final PolynomialFunction rhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");

        assertThrows(IllegalArgumentException.class, () ->new PolynomialEquation(null, rhs));
    }

    @Test
    public void exceptions_null_rhs_test() {
        final PolynomialFunction lhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");

        assertThrows(IllegalArgumentException.class, () -> new PolynomialEquation(lhs, null));
    }
}
