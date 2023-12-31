package models.equations;

import models.equations.polynomial_equations.LinearEquation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LinearEquationsTests {

    @Test
    public void linear_equation_solution_zero() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(5.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");

        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(5.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");

        final LinearEquation linearEquation = new LinearEquation(leftSide, rightSide);

        assertEquals(linearEquation.solve().size(), 1);
        assertEquals(Range.all(), linearEquation.solve().get(0));
    }

    @Test
    public void linear_equation_success_no_reduce() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(5.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");

        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(15.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");

        final LinearEquation linearEquation = new LinearEquation(leftSide, rightSide);

        final List<Range> solution = linearEquation.solve();

        assertNotNull(solution);
        assertEquals(solution.size(), 1.0);
        assertEquals(3.0, solution.get(0).getLowerBound());
        assertEquals(3.0, solution.get(0).getUpperBound());
    }

    @Test
    public void linear_equation_success_reduce() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
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
        }}, "f", "x");

        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");

        final LinearEquation linearEquation = new LinearEquation(leftSide, rightSide);

        assertEquals(leftSide, linearEquation.getLeftSide());
        assertEquals(rightSide, linearEquation.getRightSide());

        final List<Range> solution = linearEquation.solve();

        assertNotNull(solution);
        assertEquals(1, solution.size());
        assertEquals(3.0, solution.get(0).getLowerBound());
        assertEquals(3.0, solution.get(0).getUpperBound());
    }

    @Test
    public void linear_equation_exceptions() {
        final PolynomialFunction func1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
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

        assertThrows(IllegalArgumentException.class, () -> new LinearEquation(null, func1));
        assertThrows(IllegalArgumentException.class, () -> new LinearEquation(func1, null));

        assertThrows(IllegalArgumentException.class, () -> new LinearEquation(func1, func2));
    }

    @Test
    public void linear_equation_no_solution() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(0.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");

        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(5.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");

        final LinearEquation linearEquation = new LinearEquation(leftSide, rightSide);

        assertNull(linearEquation.solve());
    }
}
