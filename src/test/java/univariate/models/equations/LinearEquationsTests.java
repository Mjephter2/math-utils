package univariate.models.equations;

import univariate.models.equations.polynomial_equations.LinearEquation;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialTerm;
import univariate.models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        linearEquation.solve();

        assertEquals(linearEquation.getSolutions().size(), 1);
        assertTrue(linearEquation.getSolutions().keySet().stream().anyMatch(range -> range.equals(Range.all())));
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

        linearEquation.solve();
        final HashMap<Range, Integer> solutions = linearEquation.getSolutions();

        final List<Double> expected = List.of(3.0);

        assertNotNull(solutions);
        assertEquals(solutions.size(), 1.0);
        assertTrue(expected.contains(((Range) solutions.keySet().toArray()[0]).getLowerBound()));
        assertTrue(expected.contains(((Range) solutions.keySet().toArray()[0]).getLowerBound()));
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

        linearEquation.solve();
        final HashMap<Range, Integer> solutions = linearEquation.getSolutions();

        assertNotNull(solutions);
        assertEquals(1, solutions.size());
        final List<Double> expected = List.of(3.0);

        assertTrue(expected.contains(((Range) solutions.keySet().toArray()[0]).getLowerBound()));
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

        linearEquation.solve();
        assertNull(linearEquation.getSolutions());
    }
}
