package multivariate.models;

import multivariate.models.functions.Function;
import multivariate.models.functions.polynomials.PolynomialFunction;
import multivariate.models.functions.polynomials.PolynomialTerm;
import univariate.models.Variable;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class PolynomialFunctionTests {

    @Test
    public void testPartialEvaluation() {
        // P((x, y, z) = x^10.0 * y^1 + 1x^1 * z^2
        final PolynomialFunction polynomialFunction = new PolynomialFunction(
                "P",
                List.of(new Variable("x"), new Variable("y"), new Variable("z")),
                false,
                List.of(
                        new PolynomialTerm(
                                Map.of(new Variable("x"), 10.0, new Variable("y"), 1.0),
                                1.0
                        ),
                        new PolynomialTerm(
                                Map.of(new Variable("x"), 1.0, new Variable("z"), 2.0),
                                1.0
                        )
                )
        );

        System.out.println(polynomialFunction);

        // evaluate the function at z=10
        final Function evaluatedFunction = polynomialFunction.partialEvaluate(Map.of(new Variable("z"), 10.0));
        System.out.println(evaluatedFunction);
    }
}
