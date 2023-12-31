package models.equations;

import models.equations.log_equations.LogEquation;
import models.equations.log_equations.NaturalLogEquation;
import models.functions.ConstantFunction;
import models.functions.logarithmic.NaturalLogFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NaturalLogEquationTests {

    @Test
    public void solve_tests() {
        final LogEquation equation = new NaturalLogEquation(
                new NaturalLogFunction("f", "x",
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
                ),
                ConstantFunction.builder()
                    .funcName("C")
                    .value(1)
                    .build()
        );

        final List<Range> solution = equation.solve();

        assertEquals(solution.size(), 1);
        assertEquals(solution.get(0).getLowerBound(), (Double)Math.E);
        assertEquals(solution.get(0).getUpperBound(), (Double)Math.E);
    }
}
