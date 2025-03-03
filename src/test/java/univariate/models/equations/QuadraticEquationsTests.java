package univariate.models.equations;

import calculus.univariate.models.equations.polynomial_equations.QuadraticEquation;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuadraticEquationsTests {

    @Test
    public void quadratic_equation_success() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
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
        }}, "f", "x");

        final QuadraticEquation quadraticEquation = new QuadraticEquation(leftSide, rightSide);

        assertEquals(leftSide, quadraticEquation.getLeftSide());
        assertEquals(rightSide, quadraticEquation.getRightSide());

        quadraticEquation.solve();
        final HashMap<Range, Integer> solutions = quadraticEquation.getSolutions();

        assertEquals(2, solutions.size());
//        assertEquals(1.0, solution.get(0).getLowerBound(), 0.0);
//        assertEquals(1.0, solution.get(0).getUpperBound(), 0.0);
//        assertEquals(-1.0, solution.get(1).getLowerBound(), 0.0);
//        assertEquals(-1.0, solution.get(1).getUpperBound(), 0.0);
    }

    @Test
    public void quadratic_equation_success_reduce() {
        final PolynomialFunction func1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f", "x");

        final PolynomialFunction func2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f", "x");

        final QuadraticEquation quadraticEquation = new QuadraticEquation(func1, func2);

        quadraticEquation.solve();
        final HashMap<Range, Integer> solutions = quadraticEquation.getSolutions();

        assertEquals(2, solutions.size());
//        assertEquals(1.0, solution.get(0).getLowerBound());
//        assertEquals(1.0, solution.get(0).getUpperBound());
//        assertEquals(-1.0, solution.get(1).getLowerBound());
//        assertEquals(-1.0, solution.get(1).getUpperBound());
    }

    @Test
    public void quadratic_equation_no_solution() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f", "x");

        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");

        final QuadraticEquation quadraticEquation = new QuadraticEquation(leftSide, rightSide);
        quadraticEquation.solve();
        assertTrue(quadraticEquation.getSolutions().isEmpty());
    }

    @Test
    public void quadratic_equation_null_argument() {
        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(null, new PolynomialFunction(new LinkedList<>(), "f", "x")));

        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(new PolynomialFunction(new LinkedList<>(), "f", "x"), null));
    }

    @Test
    public void linear_equation_discriminant_zero() {
        final PolynomialFunction func1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(2)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");

        final PolynomialFunction func2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");

        final QuadraticEquation quadraticEquation = new QuadraticEquation(func1, func2);

        quadraticEquation.solve();
        final HashMap<Range, Integer> solutions = quadraticEquation.getSolutions();

        assertEquals(1, solutions.size());
//        assertEquals(-1.0, solution.get(0).getLowerBound());
//        assertEquals(-1.0, solution.get(0).getUpperBound());
    }

    @Test
    public void linear_equation_exceptions() {
        final PolynomialFunction func1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(3)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");

        final PolynomialFunction func2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f", "x");

        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(null, func1));
        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(func1, null));

        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(func1, func2));
    }
}
