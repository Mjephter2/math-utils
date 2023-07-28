package models.equations;

import models.functions.PolynomialFunction;
import models.functions.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class QuadraticEquationsTests {

    @Test
    public void quadratic_equation_success() {
        final PolynomialFunction leftSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(2)
                            .build());
                }})
                .build();

        final PolynomialFunction rightSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(0)
                            .build());
                }})
                .build();

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
        final PolynomialFunction func1 = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(2.0)
                            .varName("x")
                            .exponent(2)
                            .build());
                }})
                .build();

        final PolynomialFunction func2 = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
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
                }})
                .build();

        final QuadraticEquation quadraticEquation = new QuadraticEquation(func1, func2);

        final Double[] solution = quadraticEquation.solve();

        assertEquals(2, solution.length);
        assertEquals(1.0, solution[0], 0.0);
        assertEquals(-1.0, solution[1], 0.0);
    }

    @Test
    public void quadratic_equation_no_solution() {
        final PolynomialFunction leftSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(2)
                            .build());
                }})
                .build();

        final PolynomialFunction rightSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(-1.0)
                            .varName("x")
                            .exponent(0)
                            .build());
                }})
                .build();

        final QuadraticEquation quadraticEquation = new QuadraticEquation(leftSide, rightSide);

        assertNull(quadraticEquation.solve());
    }

    @Test
    public void quadratic_equation_null_argument() {
        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(null, PolynomialFunction.builder()
                .terms(new LinkedList<>())
                .funcName("f")
                .varName("x")
                .build()));
        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(PolynomialFunction.builder()
                .terms(new LinkedList<>())
                .funcName("f")
                .varName("x")
                .build(), null));
    }

    @Test
    public void linear_equation_discriminant_zero() {
        final PolynomialFunction func1 = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
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
                }})
                .build();

        final PolynomialFunction func2 = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(-1.0)
                            .varName("x")
                            .exponent(0)
                            .build());
                }})
                .build();

        final QuadraticEquation quadraticEquation = new QuadraticEquation(func1, func2);

        final Double[] solution = quadraticEquation.solve();

        assertEquals(1, solution.length);
        assertEquals(-1.0, solution[0], 0.0);
    }

    @Test
    public void linear_equation_exceptions() {
        final PolynomialFunction func1 = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
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
                }})
                .build();

        final PolynomialFunction func2 = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(-1.0)
                            .varName("x")
                            .exponent(2)
                            .build());
                }})
                .build();

        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(null, func1));
        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(func1, null));

        assertThrows(IllegalArgumentException.class, () -> new QuadraticEquation(func1, func2));
    }
}
