package models.functions;

import com.google.common.collect.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.trigonometric.TrigonometricFunction;
import models.functions.trigonometric.TrigonometricFunctionType;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class TrigonometricFunctionTests {

    @Test
    public void sine_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction sinFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.SINE)
                .innerFunction(innerFunction)
                .build();

        assertEquals("f(x) = sin(x)", sinFunc.printFunc());
        assertEquals("f", sinFunc.getFuncName());
        assertEquals("x", sinFunc.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, sinFunc.getFuncType());
        assertEquals(TrigonometricFunctionType.SINE, sinFunc.getTrigonometricFunctionType());
        assertEquals(innerFunction, sinFunc.getInnerFunction());

        assertEquals(Range.all(), sinFunc.getDomain());
        assertThrows(UnsupportedOperationException.class, sinFunc::getRange);

        assertEquals(0.0, sinFunc.evaluate(0.0), 0.0);
        assertEquals(1.0, sinFunc.evaluate(Math.PI / 2), 0.0);
        assertEquals(0.0, sinFunc.evaluate(Math.PI), 0.00001);
        assertEquals(-1.0, sinFunc.evaluate(3 * Math.PI / 2), 0.0);
        assertEquals(0.0, sinFunc.evaluate(2 * Math.PI), 0.00001);
    }

    @Test
    public void cosine_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction sinFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                .innerFunction(innerFunction)
                .build();

        assertEquals("f(x) = cos(x)", sinFunc.printFunc());
        assertEquals("f", sinFunc.getFuncName());
        assertEquals("x", sinFunc.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, sinFunc.getFuncType());
        assertEquals(TrigonometricFunctionType.COSINE, sinFunc.getTrigonometricFunctionType());
        assertEquals(innerFunction, sinFunc.getInnerFunction());

        assertEquals(1.0, sinFunc.evaluate(0.0), 0.0);
        assertEquals(0.0, sinFunc.evaluate(Math.PI / 2), 0.00001);
        assertEquals(-1.0, sinFunc.evaluate(Math.PI), 0.0);
        assertEquals(0.0, sinFunc.evaluate(3 * Math.PI / 2), 0.00001);
        assertEquals(1.0, sinFunc.evaluate(2 * Math.PI), 0.0);
    }

    @Test
    public void tangent_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction tanFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.TANGENT)
                .innerFunction(innerFunction)
                .build();

        assertEquals("f(x) = tan(x)", tanFunc.printFunc());
        assertEquals("f", tanFunc.getFuncName());
        assertEquals("x", tanFunc.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, tanFunc.getFuncType());
        assertEquals(TrigonometricFunctionType.TANGENT, tanFunc.getTrigonometricFunctionType());
        assertEquals(innerFunction, tanFunc.getInnerFunction());

        assertEquals(0.0, tanFunc.evaluate(0.0), 0.0);
        assertEquals(1.0, tanFunc.evaluate(Math.PI / 4), 0.00001);
        assertEquals(0.0, tanFunc.evaluate(Math.PI), 0.00001);
        assertEquals(-1.0, tanFunc.evaluate(3 * Math.PI / 4), 0.00001);
        assertEquals(0.0, tanFunc.evaluate(Math.PI), 0.00001);
    }
}
