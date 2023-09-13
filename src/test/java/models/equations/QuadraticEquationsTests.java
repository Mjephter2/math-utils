package models.equations;

import models.equations.polynomial_equations.QuadraticEquation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

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

        assertEquals(leftSide, quadraticEquation.leftSide());
        assertEquals(rightSide, quadraticEquation.rightSide());

        final Double[] solution = quadraticEquation.solve();

        assertEquals(2, solution.length);
        assertEquals(1.0, solution[0], 0.0);
        assertEquals(-1.0, solution[1], 0.0);
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

        final Double[] solution = quadraticEquation.solve();

        assertEquals(2, solution.length);
        assertEquals(1.0, solution[0], 0.0);
        assertEquals(-1.0, solution[1], 0.0);
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

        assertNull(quadraticEquation.solve());
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

        final Double[] solution = quadraticEquation.solve();

        assertEquals(1, solution.length);
        assertEquals(-1.0, solution[0], 0.0);
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
