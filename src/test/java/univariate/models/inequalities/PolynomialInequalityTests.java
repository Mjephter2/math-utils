package univariate.models.inequalities;

import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.inequalities.InequalityType;
import calculus.univariate.models.inequalities.PolynomialInequality;
import calculus.univariate.models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolynomialInequalityTests {

    @Test
    public void linearInequalityTest() {
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

        final PolynomialInequality inequality = new PolynomialInequality(InequalityType.GREATER_THAN, leftSide, rightSide);

        assertEquals(InequalityType.GREATER_THAN, inequality.getType());
        assertEquals(leftSide, inequality.getLeftSide());
        assertEquals(rightSide, inequality.getRightSide());

        inequality.solve();
        List<Range> solution = inequality.getSolution();

        assertEquals(1, solution.size());
    }

    @Test
    public void polynomialInequalityTest() {
        final PolynomialFunction leftSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(5)
                    .build());
        }}, "f", "x");
        final PolynomialFunction rightSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "g", "x");

        final PolynomialInequality inequality1 = new PolynomialInequality(InequalityType.GREATER_THAN, leftSide, rightSide);
        assertEquals(InequalityType.GREATER_THAN, inequality1.getType());
        assertEquals(leftSide, inequality1.getLeftSide());
        assertEquals(rightSide, inequality1.getRightSide());
        assertEquals(5, inequality1.getDegree());

        inequality1.solve();
        List<Range> solution = inequality1.getSolution();

        assertEquals(2, solution.size());
        assertTrue(solution.contains(new Range(1.0, Double.POSITIVE_INFINITY, false, false)));
        assertTrue(solution.contains(new Range(-1.0, 0.0, false, false)));

        final PolynomialInequality inequality2 = new PolynomialInequality(InequalityType.LESS_THAN, leftSide, rightSide);
        assertEquals(InequalityType.LESS_THAN, inequality2.getType());
        assertEquals(leftSide, inequality2.getLeftSide());
        assertEquals(rightSide, inequality2.getRightSide());

        inequality2.solve();
        List<Range> solution2 = inequality2.getSolution();

        assertEquals(2, solution2.size());
        assertTrue(solution2.contains(new Range(Double.NEGATIVE_INFINITY, -1.0, false, false)));
        assertTrue(solution2.contains(new Range(0.0, 1.0, false, false)));

        assertThrows(IllegalArgumentException.class, () -> new PolynomialInequality(InequalityType.LESS_THAN, null, rightSide));
        assertThrows(IllegalArgumentException.class, () -> new PolynomialInequality(InequalityType.LESS_THAN, leftSide, null));
    }

    @Test
    public void exceptionsTest() {
        final PolynomialFunction nonNullSide = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f", "x");

        assertThrows(IllegalArgumentException.class, () -> new PolynomialInequality(InequalityType.GREATER_THAN, nonNullSide, null));
        assertThrows(IllegalArgumentException.class, () -> new PolynomialInequality(InequalityType.GREATER_THAN, null, nonNullSide));
    }
}
