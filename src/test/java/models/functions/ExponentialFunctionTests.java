package models.functions;

import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ExponentialFunctionTests {

    @Test
    public void builder_tests() {
        final ExponentialFunction expFunc = ExponentialFunction.builder()
                .base(2)
                .funcName("f")
                .varName("x")
                .exponent(PolynomialFunction.builder()
                        .terms(new LinkedList<>() {{
                            add(PolynomialTerm.builder()
                                    .varName("x")
                                    .coefficient(1)
                                    .exponent(1)
                                    .build());
                        }})
                        .funcName("f")
                        .varName("x")
                        .build())
                .build();

        assertEquals(1.0, expFunc.evaluate(0.0), 0);
        assertEquals(2.0, expFunc.evaluate(1.0), 0);

        assertEquals(FunctionType.EXPONENTIAL, expFunc.getFuncType());
        assertEquals(0.0, expFunc.getDomain().lowerEndpoint(), 0);
        assertEquals(2, expFunc.getBase(), 0);
        assertEquals("f", expFunc.getFuncName());
        assertEquals("x", expFunc.getVarName());

        final PolynomialFunction exponent = (PolynomialFunction) expFunc.getExponent();
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
        final ExponentialFunction func = ExponentialFunction.builder()
                .base(2)
                .funcName("f")
                .varName("x")
                .exponent(PolynomialFunction.builder()
                        .terms(new LinkedList<>() {{
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
                        }})
                        .funcName("f")
                        .varName("x")
                        .build())
                .build();

        final CompositeFunction derivative =  (CompositeFunction) func.derivative();

        assertEquals(FunctionType.OTHER, derivative.getFuncType());
        assertEquals("f", derivative.getFuncName());
        assertEquals("x", derivative.getVarName());

        assertEquals("f(x) = ( 0.7 )( 4x + 1 )( 2.0^(2x² + x) )", derivative.toString());
    }
}
