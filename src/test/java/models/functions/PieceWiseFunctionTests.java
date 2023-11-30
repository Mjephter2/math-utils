package models.functions;

import com.google.common.collect.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.specials.PieceWiseFunction;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieceWiseFunctionTests {

    @Test
    public void createTests() {
        final PieceWiseFunction func = PieceWiseFunction.builder()
                .funcName("f")
                .varName("x")
                .functionsToRangesMap(this.funcToRangeSample())
                .build();

        assertTrue(
                func.printFunc().equals("f(x) = 2  for (-∞..0.0], 1  for (0.0..+∞)") ||
                        func.printFunc().equals("f(x) = 1  for (0.0..+∞), 2  for (-∞..0.0]")
        );
    }

    @Test
    public void exceptionTests() {
        final PieceWiseFunction func = PieceWiseFunction.builder()
                .funcName("f")
                .varName("x")
                .functionsToRangesMap(this.funcToRangeSample())
                .build();

        assertThrows(UnsupportedOperationException.class, () -> func.simplify());
        assertThrows(UnsupportedOperationException.class, () -> func.integral());
        assertThrows(UnsupportedOperationException.class, () -> func.integral(1, 2));
        assertThrows(UnsupportedOperationException.class, () -> func.limit(2));
    }

    private Map<Function, Range<Double>> funcToRangeSample() {
        final PolynomialFunction p1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .coefficient(2)
                    .build());
        }}, "p1", "x");

        final Range<Double> p1Range = Range.atMost(0.0);

        final PolynomialFunction p2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .coefficient(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .coefficient(0.0)
                    .build());
        }}, "p2", "x");
        final Range<Double> p2Range = Range.greaterThan(0.0);

        return Map.of(p1, p1Range, p2, p2Range);
    }
}
