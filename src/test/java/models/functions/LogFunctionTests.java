package models.functions;


import com.google.common.collect.Range;
import models.functions.logarithmic.LogFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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

        assertEquals(Range.open(0.0, Double.POSITIVE_INFINITY), logFunction.getRange());

        assertEquals(2.0, logFunction.evaluate(5.0), 0.0);
        assertEquals(2.0, logFunction.limit(5.0), 0.0);
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

        System.out.println(simplified2);
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
        assertThrows(UnsupportedOperationException.class, logFunction::getDomain);
        assertThrows(UnsupportedOperationException.class, logFunction::integral);
        assertThrows(UnsupportedOperationException.class, () -> logFunction.integral(1.0, 2.0));
    }
}
