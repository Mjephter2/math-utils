package models.equations;

import models.equations.log_equations.LogEquation;
import models.functions.ConstantFunction;
import models.functions.FunctionType;
import models.functions.logarithmic.LogFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LogEquationTests {

    @Test
    public void solve_tests() {
        final LogEquation logEquation = LogEquation.builder()
                .leftSide(LogFunction.builder()
                        .base(10)
                        .body(
                                new PolynomialFunction(List.of(
                                        PolynomialTerm.builder()
                                                .coefficient(1)
                                                .varName("x")
                                                .exponent(1)
                                                .build()
                                ),
                                "f",
                                "x"
                                )
                        )
                        .funcName("g")
                        .varName("x")
                        .build())
                .rightSide(ConstantFunction.builder()
                        .funcName("C")
                        .value(2)
                        .build())
                .build();

        assertEquals(logEquation.leftSide().getFuncType(), FunctionType.LOGARITHMIC);
        assertEquals(logEquation.rightSide().getFuncType(), FunctionType.CONSTANT);
        System.out.println(logEquation.solve()[0]);

    }
}
