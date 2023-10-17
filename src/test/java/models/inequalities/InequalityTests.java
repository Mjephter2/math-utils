package models.inequalities;

import com.google.common.collect.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class InequalityTests {

    @Test
    public void solve_less_than_test() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(6.0)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(10.0)
                    .varName("x")
                    .exponent(0)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality = LinearInequality.builder()
                .leftSide(leftSide)
                .rightSide(rightSide)
                .type(InequalityType.LESS_THAN)
                .build();

        assertEquals("6x + 2 < x + 12", inequality.printInequality());
        assertEquals(InequalityType.LESS_THAN, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        List<Range<Double>> ranges = inequality.solve();
        assertEquals(1, ranges.size());
        assertEquals(Range.lessThan(2.0), ranges.get(0));
    }

    @Test
    public void solve_greater_than_test() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-6.0)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-2.0)
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
            add(PolynomialTerm.builder()
                    .coefficient(-10.0)
                    .varName("x")
                    .exponent(0)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-2.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality = LinearInequality.builder()
                .leftSide(leftSide)
                .rightSide(rightSide)
                .type(InequalityType.LESS_THAN)
                .build();

        assertEquals(InequalityType.LESS_THAN, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        List<Range<Double>> ranges = inequality.solve();
        assertEquals(1, ranges.size());
        assertEquals(Range.greaterThan(2.0), ranges.get(0));
    }

    @Test
    public void solve_less_than_equal_test() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(6.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(6.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "g", "x");
        final Inequality inequality = LinearInequality.builder()
                .leftSide(leftSide)
                .rightSide(rightSide)
                .type(InequalityType.LESS_THAN_OR_EQUAL_TO)
                .build();

        assertEquals(InequalityType.LESS_THAN_OR_EQUAL_TO, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        List<Range<Double>> ranges = inequality.solve();
        assertEquals(1, ranges.size());
        assertEquals(Range.all(), ranges.get(0));
    }

    @Test
    public void inequality_infinite_solutions() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(), "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(), "g", "x");
        final Inequality inequality1 = LinearInequality.builder()
                .leftSide(leftSide)
                .rightSide(rightSide)
                .type(InequalityType.LESS_THAN_OR_EQUAL_TO)
                .build();
        final Inequality inequality2 = LinearInequality.builder()
                .leftSide(leftSide)
                .rightSide(rightSide)
                .type(InequalityType.GREATER_THAN_OR_EQUAL_TO)
                .build();

        assertEquals(Range.all(), inequality1.solve().get(0));
        assertEquals(Range.all(), inequality2.solve().get(0));
    }

    @Test
    public void exceptions_tests() {
        assertThrows(IllegalArgumentException.class, () -> new LinearInequality(InequalityType.LESS_THAN,
                new PolynomialFunction(new LinkedList<>(), "f", "x"), null));

        assertThrows(IllegalArgumentException.class, () -> new LinearInequality(InequalityType.LESS_THAN,
                null, new PolynomialFunction(new LinkedList<>(), "g", "x")));

        assertThrows(IllegalArgumentException.class, () -> new LinearInequality(InequalityType.LESS_THAN,
                new PolynomialFunction(new LinkedList<>(), "f", "x"),
                new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1.0)
                            .varName("x")
                            .exponent(2)
                            .build());
        }}, "g", "x")));
    }
}
