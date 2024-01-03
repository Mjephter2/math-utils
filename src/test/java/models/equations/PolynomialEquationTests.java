package models.equations;

import models.equations.polynomial_equations.PolynomialEquation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        assertEquals("x² + x - 2 = 0.0", equation.print());

        assertEquals(lhs, equation.getLeftSide());
        assertEquals(rhs, equation.getRightSide());
        assertEquals(2, equation.getDegree());

        equation.solve();
        final HashMap<Range, Integer> solution = equation.getSolutions();
        assertEquals(2, solution.size());
//        final Set<Range> solutionRanges = solution.keySet();
//        assertTrue(solutionRanges.contains(Range.singleton(-2.0)));
//        assertTrue(solutionRanges.contains(Range.singleton(1.0)));
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

        equation.solve();
        final HashMap<Range, Integer> solution = equation.getSolutions();
        final Set<Range> solutionRanges = solution.keySet();

        assertEquals(1, solution.size());
        assertTrue(solutionRanges.stream().anyMatch(range -> range.equals(Range.singleton(-2.0))));
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
