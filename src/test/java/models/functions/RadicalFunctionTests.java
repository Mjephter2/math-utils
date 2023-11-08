package models.functions;

import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.radicals.RadicalFunction;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class RadicalFunctionTests {

    @Test
    public void builderTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc)
                .build();

        assertEquals("f(x) = ³√(2x²)", func.printFunc());

        assertEquals("f", func.getFuncName());
        assertEquals("x", func.getVarName());
        assertEquals(3, func.getRootIndex());
        assertEquals(innerFunc, func.getBody());
        assertEquals(FunctionType.RADICAL, func.getFuncType());
    }

    @Test
    public void evaluateTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc)
                .build();

        assertEquals(1.259, func.evaluate(1.0), 0.001);
        assertEquals(2.0, func.evaluate(2.0), 0.0);
        assertEquals(3.174, func.evaluate(4.0), 0.001);
    }

    @Test
    public void simplifyTest() {
        final ConstantFunction innerFunc = new ConstantFunction("innerFunc", 8.0);

        final RadicalFunction func = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc)
                .build();

        assertEquals("f(x) = ³√(8)", func.printFunc());
        assertEquals("f() = 2", func.simplify().toString());
    }
}
