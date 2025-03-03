package univariate.models.functions;

import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.FunctionType;
import calculus.univariate.models.functions.combinations.RationalFunction;
import calculus.univariate.models.functions.logarithmic.LogFunction;
import calculus.univariate.models.functions.logarithmic.NaturalLogFunction;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.numberUtils.Range;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogFunctionTests {

    @Test
    public void evaluateTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(4)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "inner", "x");
        final LogFunction logFunction = LogFunction.builder()
                .funcName("f")
                .varName("x")
                .body(innerFunc)
                .base(10)
                .build();

        assertEquals(FunctionType.LOGARITHMIC, logFunction.getFuncType());
        assertEquals("f", logFunction.getFuncName());
        assertEquals("x", logFunction.getVarName());
        assertEquals(10.0, logFunction.getBase(), 0.0);
        assertEquals(innerFunc, logFunction.getBody());
        assertEquals("f(x) = log_10.0(4x²)", logFunction.printFunc());

        assertEquals(Range.all(), logFunction.getRange().get(0));

        assertEquals(2.0, logFunction.evaluate(5.0), 0.0);
        assertEquals(2.0, logFunction.limit(5.0), 0.0);
    }

    @Test
    public void domainTests() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "inner", "x");
        final LogFunction logFunction = LogFunction.builder()
                .funcName("f")
                .varName("x")
                .body(innerFunc)
                .base(10)
                .build();
        assertEquals(Range.lessThan(0.0), logFunction.getDomain().get(0));
        assertEquals(Range.greaterThan(0.0), logFunction.getDomain().get(1));
    }

    @Test
    public void simplifyTest() {
        final ConstantFunction innerFunc = ConstantFunction.builder()
                .value(10)
                .build();
        final LogFunction logFunction = LogFunction.builder()
                .funcName("f")
                .varName("x")
                .body(innerFunc)
                .base(10)
                .build();

        assertEquals("f(x) = log_10.0(10)", logFunction.printFunc());
        final Function simplified = logFunction.simplify();
        assertEquals("f(null) = 1", simplified.printFunc());

        final PolynomialFunction innerFunc2 = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(4)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "inner", "x");
        final LogFunction logFunction2 = LogFunction.builder()
                .funcName("f")
                .varName("x")
                .body(innerFunc2)
                .base(10)
                .build();
        final Function simplified2 = logFunction2.simplify();
    }

    @Test
    public void derivativeTests() {
        final PolynomialFunction body = new PolynomialFunction(
                new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(2)
                            .varName("x")
                            .exponent(2)
                            .build());
                    add(PolynomialTerm.builder()
                            .coefficient(1)
                            .varName("x")
                            .exponent(0)
                            .build());
                }}, "body", "x"
        );

        final LogFunction func = LogFunction.builder()
                .base(Math.E)
                .body(body)
                .funcName("func")
                .varName("x")
                .build();

        final Function derivative = func.derivative();

        assertTrue(derivative instanceof RationalFunction);
        assertEquals("func'(x) = ( 4x ) / ( ( 1 )( 2x² + 1 ) )", derivative.printFunc());
    }

    @Test
    public void exceptionsTest() {
        final ConstantFunction innerFunc = ConstantFunction.builder()
                .value(10)
                .build();
        final LogFunction logFunction = LogFunction.builder()
                .funcName("f")
                .varName("x")
                .body(innerFunc)
                .base(10)
                .build();

        assertThrows(UnsupportedOperationException.class, logFunction::derivative);
        assertThrows(UnsupportedOperationException.class, () -> logFunction.integral());
        assertThrows(UnsupportedOperationException.class, () -> logFunction.integral(1.0, 2.0));
    }

    @Test
    public void copyTest() {
        final ConstantFunction innerFunc = ConstantFunction.builder()
                .value(10)
                .build();
        final LogFunction logFunction = LogFunction.builder()
                .funcName("f")
                .varName("x")
                .body(innerFunc)
                .base(10)
                .build();
        final Function copy = logFunction.deepCopy("g");
        assertEquals("g(x) = log_10.0(10)", copy.printFunc());
        assertEquals(logFunction, copy);

        assertNotEquals(logFunction, new Object());
    }

    @Test
    public void naturalLogTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "inner", "x");
        final NaturalLogFunction logFunction = new NaturalLogFunction("f", "x", innerFunc);

        assertEquals("f(x) = ln(x²)", logFunction.printFunc());

        assertEquals(2.0, logFunction.evaluate(Math.E), 0.0);
    }
}
