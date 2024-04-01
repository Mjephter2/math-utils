package univariate.models.equations;

import lombok.Getter;
import univariate.models.equations.log_equations.LogEquation;
import univariate.models.equations.log_equations.NaturalLogEquation;
import univariate.models.functions.ConstantFunction;
import univariate.models.functions.logarithmic.NaturalLogFunction;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialTerm;
import univariate.models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Getter
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

        equation.solve();

        final HashMap<Range, Integer> solutions = equation.getSolutions();
        final List<Double> expected = List.of(Math.E);

        assertEquals(solutions.size(), 1);

        assertTrue(expected.contains(((Range) solutions.keySet().toArray()[0]).getLowerBound()));
        assertTrue(expected.contains(((Range) solutions.keySet().toArray()[0]).getUpperBound()));
    }
}
