package models.inequalities;

import com.google.common.collect.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        final Inequality inequality = new LinearInequality(InequalityType.LESS_THAN, leftSide, rightSide);

        assertEquals("6x + 2 < x + 12", inequality.printInequality());
        assertEquals(InequalityType.LESS_THAN, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        inequality.solve();
        List<Range<Double>> ranges = inequality.getSolution();
        assertEquals(1, ranges.size());
        assertEquals(Range.lessThan(2.0), ranges.get(0));
    }

    @Test
    public void solve_less_than_or_equals_test() {
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
        final Inequality inequality = new LinearInequality(InequalityType.LESS_THAN_OR_EQUAL_TO, leftSide, rightSide);

        assertEquals("6x + 2 <= x + 12", inequality.printInequality());
        assertEquals(InequalityType.LESS_THAN_OR_EQUAL_TO, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        inequality.solve();
        List<Range<Double>> ranges = inequality.getSolution();
        assertEquals(1, ranges.size());
        assertEquals(Range.atMost(2.0), ranges.get(0));
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
        final Inequality inequality = new LinearInequality(InequalityType.LESS_THAN, leftSide, rightSide);

        assertEquals(InequalityType.LESS_THAN, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        inequality.solve();
        List<Range<Double>> ranges = inequality.getSolution();
        assertEquals(1, ranges.size());
        assertEquals(Range.greaterThan(2.0), ranges.get(0));
    }

    @Test
    public void solve_greater_than_or_equals_test() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(6.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(18.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality = new LinearInequality(InequalityType.GREATER_THAN_OR_EQUAL_TO, leftSide, rightSide);

        assertEquals(InequalityType.GREATER_THAN_OR_EQUAL_TO, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        inequality.solve();
        List<Range<Double>> ranges = inequality.getSolution();
        assertEquals(1, ranges.size());
        assertEquals(Range.atLeast(3.0), ranges.get(0));
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
        final Inequality inequality = new LinearInequality(InequalityType.LESS_THAN_OR_EQUAL_TO, leftSide, rightSide);

        assertEquals(InequalityType.LESS_THAN_OR_EQUAL_TO, inequality.getType());
        assertEquals(inequality.getLeftSide(), leftSide);
        assertEquals(inequality.getRightSide(), rightSide);

        inequality.solve();
        List<Range<Double>> ranges = inequality.getSolution();
        assertEquals(1, ranges.size());
        assertEquals(Range.all(), ranges.get(0));
    }

    @Test
    public void inequality_infinite_solutions() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(), "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(), "g", "x");
        final Inequality inequality1 = new LinearInequality(InequalityType.LESS_THAN_OR_EQUAL_TO, leftSide, rightSide);
        final Inequality inequality2 = new LinearInequality(InequalityType.GREATER_THAN_OR_EQUAL_TO, leftSide, rightSide);

        inequality1.solve();
        inequality2.solve();

        assertEquals(Range.all(), inequality1.getSolution().get(0));
        assertEquals(Range.all(), inequality2.getSolution().get(0));
    }

    @Test
    public void inequality_no_solutions() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(), "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(), "g", "x");
        final Inequality inequality1 = new LinearInequality(InequalityType.LESS_THAN, leftSide, rightSide);
        final Inequality inequality2 = new LinearInequality(InequalityType.GREATER_THAN, leftSide, rightSide);

        inequality1.solve();
        inequality2.solve();

        assertEquals(Collections.emptyList(), inequality1.getSolution());
        assertEquals(Collections.emptyList(), inequality2.getSolution());
    }

    @Test
    public void inequality_case3() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(), "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality1 = new LinearInequality(InequalityType.LESS_THAN, leftSide, rightSide);
        final Inequality inequality2 = new LinearInequality(InequalityType.LESS_THAN_OR_EQUAL_TO, leftSide, rightSide);

        inequality1.solve();
        inequality2.solve();

        assertEquals(Collections.emptyList(), inequality1.getSolution());
        assertEquals(Collections.emptyList(), inequality2.getSolution());
    }

    @Test
    public void inequality_case4() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(), "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality1 = new LinearInequality(InequalityType.GREATER_THAN, leftSide, rightSide);
        final Inequality inequality2 = new LinearInequality(InequalityType.GREATER_THAN_OR_EQUAL_TO, leftSide, rightSide);

        inequality1.solve();
        inequality2.solve();

        assertEquals(Collections.singletonList(Range.all()), inequality1.getSolution());
        assertEquals(Collections.singletonList(Range.all()), inequality2.getSolution());
    }

    @Test
    public void inequality_case5() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(), "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality1 = new LinearInequality(InequalityType.LESS_THAN, leftSide, rightSide);
        final Inequality inequality2 = new LinearInequality(InequalityType.LESS_THAN_OR_EQUAL_TO, leftSide, rightSide);

        inequality1.solve();
        inequality2.solve();

        assertEquals(Collections.singletonList(Range.all()), inequality1.getSolution());
        assertEquals(Collections.singletonList(Range.all()), inequality2.getSolution());
    }

    @Test
    public void inequality_equals_to_case5() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality1 = new LinearInequality(InequalityType.EQUAL_TO, leftSide, rightSide);

        assertThrows(IllegalArgumentException.class, inequality1::solve);
    }

    @Test
    public void inequality_case6() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(), "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");
        final Inequality inequality1 = new LinearInequality(InequalityType.GREATER_THAN, leftSide, rightSide);
        final Inequality inequality2 = new LinearInequality(InequalityType.GREATER_THAN_OR_EQUAL_TO, leftSide, rightSide);

        inequality1.solve();
        inequality2.solve();

        assertEquals(Collections.emptyList(), inequality1.getSolution());
        assertEquals(Collections.emptyList(), inequality2.getSolution());
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
