package models.equations;

import models.equations.log_equations.LogEquation;
import models.equations.log_equations.NaturalLogEquation;
import models.functions.ConstantFunction;
import models.functions.logarithmic.NaturalLogFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class NaturalLogEquationTests {

    @Test
    public void solve_tests() {
        final LogEquation equation = NaturalLogEquation.builder()
                .leftSide(new NaturalLogFunction("f", "x",
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
                ))
                .rightSide(ConstantFunction.builder()
                        .funcName("C")
                        .value(1)
                        .build())
                .build();

        Double[] solution = equation.solve();

        assertEquals(solution.length, 1);
        assertEquals(solution[0], (Double)Math.E);
    }
}
