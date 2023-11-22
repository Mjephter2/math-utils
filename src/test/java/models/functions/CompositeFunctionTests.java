package models.functions;

import models.functions.combinations.CompositeFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

public class CompositeFunctionTests {

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

         System.out.println(expectedReducedFunction.printFunc());
     }
}
