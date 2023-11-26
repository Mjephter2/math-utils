package models.functions;

import models.functions.combinations.CompositeFunction;
import models.functions.combinations.ExponentialFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExponentialFunctionTests {

    @Test
    public void builder_tests() {
        final ExponentialFunction expFunc = exponentialFuncSample().get(0); // f(x) = 2^(2x^2 + x)

        assertEquals(1.0, expFunc.evaluate(0.0), 0);
        assertEquals(8.0, expFunc.evaluate(1.0), 0);

        assertEquals(FunctionType.EXPONENTIAL, expFunc.getFuncType());
        assertEquals(0.0, expFunc.getDomain().lowerEndpoint(), 0);
        assertEquals(2, expFunc.getBase(), 0);
        assertEquals("f", expFunc.getFuncName());
        assertEquals("x", expFunc.getVarName());
    }

    @Test
    public void exceptions_test() {
        final ExponentialFunction func = ExponentialFunction.builder().build();

        assertThrows(UnsupportedOperationException.class, func::getRange);
        assertThrows(UnsupportedOperationException.class, func::integral);
        assertThrows(UnsupportedOperationException.class, () -> func.integral(0, 1));
    }

    @Test
    public void derivative_test() {
        final ExponentialFunction func = exponentialFuncSample().get(0); // f(x) = 2^(2x^2 + x)

        final CompositeFunction derivative =  (CompositeFunction) func.derivative();

        assertEquals(FunctionType.OTHER, derivative.getFuncType());
        assertEquals("f'", derivative.getFuncName());
        assertEquals("x", derivative.getVarName());

        assertEquals("f'(x) = ( 0.7 )( 4x + 1 )( 2.0^(2x² + x) )", derivative.toString());
    }

    @Test
    public void limit_test() {
        final ExponentialFunction func = exponentialFuncSample().get(1); // f(x) = 3^(2x^2 + x)

        assertEquals(1.0, func.limit(0.0), 0);
        assertEquals(27.0, func.limit(1.0), 0);

        assertEquals(new PolynomialFunction(new LinkedList<>() {{
            add(PolynomialTerm.builder()
                    .varName("x")
                    .coefficient(1)
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .varName("x")
                    .coefficient(2)
                    .exponent(2)
                    .build());
        }}, "f", "x").toString(), func.getExponent().toString());
    }

    private List<ExponentialFunction> exponentialFuncSample() {
        return List.of(
                ExponentialFunction.builder()
                        .base(2)
                        .funcName("f")
                        .varName("x")
                        .exponent(new PolynomialFunction(new LinkedList<>() {{
                            add(PolynomialTerm.builder()
                                    .varName("x")
                                    .coefficient(1)
                                    .exponent(1)
                                    .build());
                            add(PolynomialTerm.builder()
                                    .varName("x")
                                    .coefficient(2)
                                    .exponent(2)
                                    .build());
                        }}, "f", "x"))
                        .build(),
                ExponentialFunction.builder()
                        .base(3.0)
                        .funcName("f")
                        .varName("x")
                        .exponent(new PolynomialFunction(new LinkedList<>() {{
                            add(PolynomialTerm.builder()
                                    .varName("x")
                                    .coefficient(1)
                                    .exponent(1)
                                    .build());
                            add(PolynomialTerm.builder()
                                    .varName("x")
                                    .coefficient(2)
                                    .exponent(2)
                                    .build());
                        }}, "f", "x"))
                        .build(),
                ExponentialFunction.builder()
                        .base(2)
                        .funcName("f")
                        .varName("x")
                        .exponent(ConstantFunction.builder()
                                .funcName("exp")
                                .value(5)
                                .build())
                        .build());
    }

    @Test
    public void simplify_test() {
        final ExponentialFunction func1 = exponentialFuncSample().get(0); // f(x) = 2^(2x^2 + x)
        final ExponentialFunction func2 = exponentialFuncSample().get(2); // f(x) = 2^(5)

        assertEquals("f(x) = 2.0^(2x² + x)", func1.simplify().printFunc());
        assertEquals("f(null) = 32", func2.simplify().printFunc());
    }

    @Test
    public void equals_tests() {
        final ExponentialFunction func1 = exponentialFuncSample().get(0); // f(x) = 2^(2x^2 + x)
        final ExponentialFunction func2 = exponentialFuncSample().get(1); // f(x) = 3^(2x^2 + x)
        final ExponentialFunction func3 = ExponentialFunction.builder()
                .base(2.0)
                .funcName("f")
                .varName("x")
                .exponent(new PolynomialFunction(new LinkedList<>() {{
                    add(PolynomialTerm.builder()
                            .varName("x")
                            .coefficient(1)
                            .exponent(1)
                            .build());
                    add(PolynomialTerm.builder()
                            .varName("x")
                            .coefficient(2)
                            .exponent(2)
                            .build());
                }}, "f", "x"))
                .build();
        final ExponentialFunction func4 = func1;

        assertEquals(func1.evaluate(2.0), func3.evaluate(2.0), 0);
        assertEquals(func1, func3);
        assertEquals(func1, func4);
        assertNotEquals(func1, func2);
        assertNotEquals(func1, ConstantFunction.builder().value(2).build());
    }
}
