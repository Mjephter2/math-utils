package univariate.models.functions;

import univariate.models.functions.Function;
import univariate.models.functions.FunctionType;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialTerm;
import univariate.models.functions.specials.PieceWiseFunction;
import univariate.models.numberUtils.Range;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PieceWiseFunctionTests {

    @Test
    public void createTests() {
        final PieceWiseFunction func = new PieceWiseFunction("f", "x", this.funcToRangeSample());

        // May need to update PieceWiseFunction definition to allow better testing
        assertTrue(
                func.printFunc().equals("f(x) = x²  for Range::(-∞ --> 0.0], x - 1  for Range::(0.0 --> ∞)") ||
                        func.printFunc().equals("f(x) = x - 1  for Range::(0.0 --> ∞), x²  for Range::(-∞ --> 0.0]")
        );
        assertEquals(FunctionType.PIECEWISE, func.getFuncType());
        assertEquals(this.funcToRangeSample().keySet().size(), func.getFunctionsToRangesMap().keySet().size());
        assertEquals(this.funcToRangeSample().values().size(), func.getFunctionsToRangesMap().values().size());
    }

    @Test
    public void domainTests() {
        final PieceWiseFunction func = new PieceWiseFunction("f", "x", this.funcToRangeSample());
//        final Range funcDomain = func.getDomain().get(0);
//        assertEquals(Range.all(), funcDomain);

        final PieceWiseFunction func2 = new PieceWiseFunction("f", "x", this.funcToRangeSample2());

//        final Range funcDomain2 = func2.getDomain().get(0);
        // The following test should not succeed. We need to rethink the domain method for PieceWiseFunction
//        assertEquals(Range.all(), funcDomain2);
    }

    @Test
    public void exceptionTests() {
        final PieceWiseFunction func = new PieceWiseFunction("f", "x", this.funcToRangeSample());

        assertThrows(UnsupportedOperationException.class, () -> func.simplify());
        assertThrows(UnsupportedOperationException.class, () -> func.integral());
        assertThrows(UnsupportedOperationException.class, () -> func.integral(1, 2));
        assertThrows(UnsupportedOperationException.class, () -> func.limit(2));
    }

    @Test
    public void evaluateTests() {
        final PieceWiseFunction func = new PieceWiseFunction("f", "x", this.funcToRangeSample());

        assertEquals(0.0, func.evaluate(1.0));
        assertEquals(0.0, func.evaluate(0.0));
    }

    private Map<Function, Range> funcToRangeSample() {
        final PolynomialFunction p1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "p1", "x");

        final Range p1Range = Range.atMost(0.0);

        final PolynomialFunction p2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-1.0)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "p2", "x");
        final Range p2Range = Range.greaterThan(0.0);

        return Map.of(p1, p1Range, p2, p2Range);
    }

    private Map<Function, Range> funcToRangeSample2() {
        final PolynomialFunction p1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .coefficient(2)
                    .build());
        }}, "p1", "x");

        final Range p1Range = Range.atMost(-1.0);

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
        final Range p2Range = Range.greaterThan(0.0);

        return Map.of(p1, p1Range, p2, p2Range);
    }
}
