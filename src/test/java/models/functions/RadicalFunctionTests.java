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
}
