package univariate.models.functions;

import univariate.models.functions.ConstantFunction;
import univariate.models.functions.Function;
import univariate.models.functions.FunctionType;
import univariate.models.functions.logarithmic.LogFunction;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialTerm;
import univariate.models.functions.trigonometric.TrigonometricFunction;
import univariate.models.functions.trigonometric.TrigonometricFunctionType;
import univariate.models.numberUtils.Range;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertEquals(Range.all(), sinFunc.getDomain().get(0));
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

    @Test
    public void secant_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction secFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.SECANT)
                .innerFunction(innerFunction)
                .build();

        assertEquals("f(x) = sec(x)", secFunc.printFunc());
        assertEquals("f", secFunc.getFuncName());
        assertEquals("x", secFunc.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, secFunc.getFuncType());
        assertEquals(TrigonometricFunctionType.SECANT, secFunc.getTrigonometricFunctionType());
        assertEquals(innerFunction, secFunc.getInnerFunction());

        assertEquals(1.0, secFunc.evaluate(0.0), 0.0);
        assertEquals(Math.sqrt(2), secFunc.evaluate(Math.PI / 4), 0.00001);
        assertEquals(-1.0, secFunc.evaluate(Math.PI), 0.00001);
        assertEquals(-Math.sqrt(2), secFunc.evaluate(3 * Math.PI / 4), 0.00001);
        assertEquals(1.0, secFunc.evaluate(2 * Math.PI), 0.00001);
    }

    @Test
    public void cosecant_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction cscFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSECANT)
                .innerFunction(innerFunction)
                .build();

        assertEquals("f(x) = csc(x)", cscFunc.printFunc());
        assertEquals("f", cscFunc.getFuncName());
        assertEquals("x", cscFunc.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, cscFunc.getFuncType());
        assertEquals(TrigonometricFunctionType.COSECANT, cscFunc.getTrigonometricFunctionType());
        assertEquals(innerFunction, cscFunc.getInnerFunction());

        assertEquals(Double.POSITIVE_INFINITY, cscFunc.evaluate(0.0), 0.0);
        assertEquals(Math.sqrt(2), cscFunc.evaluate(Math.PI / 4), 0.00001);
        assertEquals(1.0, cscFunc.evaluate(Math.PI / 2), 0.00001);
        assertEquals(Math.sqrt(2), cscFunc.evaluate(3 * Math.PI / 4), 0.00001);
//        assertEquals(Double.NEGATIVE_INFINITY, cscFunc.evaluate(Math.PI), 0.00001);
    }

    @Test
    public void cotangant_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .coefficient(1.0)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction cotFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COTANGENT)
                .innerFunction(innerFunction)
                .build();

        assertEquals("f(x) = cot(x)", cotFunc.printFunc());
        assertEquals("f", cotFunc.getFuncName());
        assertEquals("x", cotFunc.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, cotFunc.getFuncType());
        assertEquals(TrigonometricFunctionType.COTANGENT, cotFunc.getTrigonometricFunctionType());
        assertEquals(innerFunction, cotFunc.getInnerFunction());

        assertEquals(Double.POSITIVE_INFINITY, cotFunc.evaluate(0.0), 0.0);
        assertEquals(1.0, cotFunc.evaluate(Math.PI / 4), 0.00001);
        assertEquals(0.0, cotFunc.evaluate(Math.PI / 2), 0.00001);
        assertEquals(-1.0, cotFunc.evaluate(3 * Math.PI / 4), 0.00001);
//        assertEquals(Double.NEGATIVE_INFINITY, cotFunc.evaluate(Math.PI), 0.00001);
    }

    @Test
    public void simplify_tests() {
        final ConstantFunction innerFunction1 = ConstantFunction.builder()
                .funcName("i")
                .value(5.0)
                .build();
        final TrigonometricFunction trigFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                .innerFunction(innerFunction1)
                .build();

        assertEquals("f(x) = cos(5)", trigFunc.printFunc());

        final Function funcSimplified = trigFunc.simplify();
        assertEquals("f() = 0.28", funcSimplified.toString());

        final PolynomialFunction innerFunction2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "j", "x");

        final TrigonometricFunction trigFunc2 = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                .innerFunction(innerFunction2)
                .build();
        final Function funcSimplified2 = trigFunc2.simplify();

        assertEquals("f(x) = cos(2)", trigFunc2.printFunc());
    }

    @Test
    public void derivative_sine_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .varName("x")
                    .coefficient(1.0)
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

        final Function derivative = sinFunc.derivative();
        assertEquals("f'(x) = ( 1 )( 1 )( cos(x) )", derivative.printFunc());
    }

    @Test
    public void derivative_cosine_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .varName("x")
                    .coefficient(1.0)
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction cosFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                .innerFunction(innerFunction)
                .build();

        assertEquals("f(x) = cos(x)", cosFunc.printFunc());
        assertEquals("f", cosFunc.getFuncName());
        assertEquals("x", cosFunc.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, cosFunc.getFuncType());
        assertEquals(TrigonometricFunctionType.COSINE, cosFunc.getTrigonometricFunctionType());
        assertEquals(innerFunction, cosFunc.getInnerFunction());

        final Function derivative = cosFunc.derivative();
        assertEquals("f'(x) = ( 1 )( - 1 )( 1 )( sin(x) )", derivative.printFunc());
    }

    @Test
    public void deriviative_exceptions_tests() {
        final LogFunction innerFunction = LogFunction.builder()
                .varName("x")
                .funcName("i")
                .body(ConstantFunction.builder()
                        .funcName("ii")
                        .value(2.0)
                        .build())
                .base(10)
                .build();

        final TrigonometricFunction cosFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                .innerFunction(innerFunction)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> cosFunc.derivative());
    }

    @Test
    public void unimplemented_tests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .varName("x")
                    .coefficient(1.0)
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction cosFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                .innerFunction(innerFunction)
                .build();

        assertThrows(UnsupportedOperationException.class, cosFunc::integral);
        assertThrows(UnsupportedOperationException.class, () -> cosFunc.integral(0.0, 1.0));
        assertThrows(UnsupportedOperationException.class, () -> cosFunc.limit(0.0));
    }

    @Test
    public void copyTests() {
        final PolynomialFunction innerFunction = new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .varName("x")
                    .coefficient(1.0)
                    .exponent(1)
                    .build());
        }}, "i", "x");
        final TrigonometricFunction cosFunc = TrigonometricFunction.builder()
                .funcName("f")
                .varName("x")
                .trigonometricFunctionType(TrigonometricFunctionType.COSINE)
                .innerFunction(innerFunction)
                .build();

        final TrigonometricFunction copy = (TrigonometricFunction) cosFunc.deepCopy("g");
        assertEquals("g(x) = cos(x)", copy.printFunc());
        assertEquals("g", copy.getFuncName());
        assertEquals("x", copy.getVarName());
        assertEquals(FunctionType.TRIGONOMETRIC, copy.getFuncType());
        assertEquals(TrigonometricFunctionType.COSINE, copy.getTrigonometricFunctionType());
    }
}
