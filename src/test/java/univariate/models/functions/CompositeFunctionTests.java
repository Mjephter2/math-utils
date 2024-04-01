package univariate.models.functions;

import univariate.models.functions.Function;
import univariate.models.functions.combinations.CompositeFunction;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialTerm;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositeFunctionTests {

    @Test
    public void constructor_tests() {
        final CompositeFunction compositeFunction = new CompositeFunction(
                "f",
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>(),
                new LinkedList<>()
        );

        assertEquals(0, compositeFunction.getPolynomialFactors().size());
        assertEquals(0, compositeFunction.getExponentialFunctions().size());
        assertEquals(0, compositeFunction.getTrigonometricFunctions().size());
        assertEquals(0, compositeFunction.getRationalFunctions().size());
        assertEquals(0, compositeFunction.getRadicalFactors().size());
    }

     @Test
     public void testReduce() {
         final PolynomialFunction factor1 = new PolynomialFunction(new LinkedList<>(){{
             add(new PolynomialTerm(1.0, "x", 1));
         }}, "f", "x");
         final PolynomialFunction factor2 = new PolynomialFunction(new LinkedList<>(){{
             add(new PolynomialTerm(1.0, "x", 1));
             add(new PolynomialTerm(1.0, "x", 0));
         }}, "g", "x");
         final PolynomialFunction factor3 = new PolynomialFunction(new LinkedList<>(){{
             add(new PolynomialTerm(1.0, "x", 1));
             add(new PolynomialTerm(-1.0, "x", 0));
         }}, "h", "x");

         final LinkedList<Function> factors = new LinkedList<>(){{
             add(factor1);
             add(factor2);
             add(factor3);
         }};

         final CompositeFunction compositeFunction = new CompositeFunction("C", factors);

         assertEquals(3, compositeFunction.getPolynomialFactors().size());

         final CompositeFunction expectedReducedFunction = compositeFunction.reduce();
     }
}
