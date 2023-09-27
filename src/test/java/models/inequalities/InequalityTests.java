package models.inequalities;

import com.google.common.collect.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InequalityTests {

    @Test
    public void createInequality() {
        final Inequality inequality = LinearInequality.builder()
                .leftSide(new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(5.0)
                            .varName("x")
                            .exponent(1)
                            .build());
                }}, "f", "x"))
                .rightSide(new PolynomialFunction(new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(10.0)
                            .varName("x")
                            .exponent(0)
                            .build());
                }}, "g", "x"))
                .type(InequalityType.LESS_THAN)
                .build();
        List<Range<Double>> ranges = inequality.solve();
        assertEquals(1, ranges.size());
        assertEquals(Range.lessThan(2.0), ranges.get(0));
    }
}
