package models.functions;

import models.functions.specials.AbsoluteValueFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbsoluteValueTests {

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

        assertEquals("f(x) = | 5.0 |", f.printFunc());
    }
}
