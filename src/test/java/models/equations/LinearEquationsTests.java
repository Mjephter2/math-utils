package models.equations;

import models.equations.polynomialEquations.LinearEquation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

public class LinearEquationsTests {

    @Test
    public void linear_equation_no_solution() {
        final PolynomialFunction leftSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(5.0)
                            .varName("x")
                            .exponent(1)
                            .build());
                }})
                .build();

        final PolynomialFunction rightSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(5.0)
                            .varName("x")
                            .exponent(1)
                            .build());
                }})
                .build();

        final LinearEquation linearEquation = new LinearEquation(leftSide, rightSide);

        assertNull(linearEquation.solve());
    }

    @Test
    public void linear_equation_success_no_reduce() {
        final PolynomialFunction leftSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(5.0)
                            .varName("x")
                            .exponent(1)
                            .build());
                }})
                .build();

        final PolynomialFunction rightSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(15.0)
                            .varName("x")
                            .exponent(0)
                            .build());
                }})
                .build();

        final LinearEquation linearEquation = new LinearEquation(leftSide, rightSide);

        final Double[] solution = linearEquation.solve();

        assertNotNull(solution);
        assertEquals(1, solution.length);
        assertEquals(3.0, solution[0], 0.0);
    }

    @Test
    public void linear_equation_success_reduce() {
        final PolynomialFunction leftSide = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(4.0)
                            .varName("x")
                            .exponent(1)
                            .build());
                    add(PolynomialTerm.builder()
                            .coefficient(-15.0)
                            .varName("x")
                            .exponent(0)
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
                            .exponent(1)
                            .build());
                }})
                .build();

        final LinearEquation linearEquation = new LinearEquation(leftSide, rightSide);

        assertEquals(leftSide, linearEquation.leftSide());
        assertEquals(rightSide, linearEquation.rightSide());

        final Double[] solution = linearEquation.solve();

        assertNotNull(solution);
        assertEquals(1, solution.length);
        assertEquals(3.0, solution[0], 0.0);
    }

    @Test
    public void linear_equation_exceptions() {
        final PolynomialFunction func1 = PolynomialFunction.builder()
                .funcName("f")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(-1.0)
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

        assertThrows(IllegalArgumentException.class, () -> new LinearEquation(null, func1));
        assertThrows(IllegalArgumentException.class, () -> new LinearEquation(func1, null));

        assertThrows(IllegalArgumentException.class, () -> new LinearEquation(func1, func2));
    }
}
