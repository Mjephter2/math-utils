package models.functions;

import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.specials.AbsoluteValueFunction;
import models.numberUtils.Range;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbsoluteValueFunctionTests {

    @Test
    public void createAbsoluteValueFunction() {
        final AbsoluteValueFunction f = AbsoluteValueFunction.builder()
                .funcName("f")
                .varName("x")
                .innerFunction(ConstantFunction.builder()
                        .funcName("g")
                        .value(5.0)
                        .build())
                .build();

        assertEquals("f(x) = | 5 |", f.printFunc());
        assertEquals(ConstantFunction.builder()
                .funcName("g")
                .value(5.0)
                .build(), f.getInnerFunction());
        assertEquals("f", f.getFuncName());
        assertEquals("x", f.getVarName());
        assertEquals(FunctionType.ABSOLUTE_VALUE, f.getFuncType());
        assertEquals(Range.all(), f.getDomain().get(0));

        assertEquals(5.0, f.evaluate(0.0));
    }

    @Test
    public void getRangeTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(3)
                    .build());
        }}, "i", "x");

        final AbsoluteValueFunction f = AbsoluteValueFunction.builder()
                .funcName("f")
                .varName("x")
                .innerFunction(innerFunc)
                .build();

        assertEquals(Range.atLeast(0.0), f.getRange().get(0));
    }

    @Test
    public void exceptionTests() {
        final AbsoluteValueFunction f = AbsoluteValueFunction.builder()
                .funcName("f")
                .varName("x")
                .innerFunction(ConstantFunction.builder()
                        .funcName("g")
                        .value(5.0)
                        .build())
                .build();

        assertThrows(UnsupportedOperationException.class, () -> f.integral());
        assertThrows(UnsupportedOperationException.class, () -> f.integral(1.0, 2.0));
        assertThrows(UnsupportedOperationException.class, () -> f.limit(1.0));
    }

    @Test
    public void deepCopyTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2.0)
                    .varName("x")
                    .exponent(3)
                    .build());
        }}, "i", "x");


        final AbsoluteValueFunction f = AbsoluteValueFunction.builder()
                .funcName("f")
                .varName("x")
                .innerFunction(innerFunc)
                .build();
        final AbsoluteValueFunction fCopy = (AbsoluteValueFunction) f.deepCopy("g");

        assertEquals("f(x) = | 2x³ |", f.printFunc());
        assertEquals("g(x) = | 2x³ |", fCopy.printFunc());

    }
}
