package models.functions;

import org.junit.Test;

import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

public class CompositeFunctionTests {

    // @Test
    // public void testReduce() {
    //     final PolynomialFunction factor1 = PolynomialFunction.builder()
    //             .funcName("f")
    //             .varName("x")
    //             .terms(new LinkedList<>(){{
    //                 add(new PolynomialTerm(1.0, "x", 1));
    //             }})
    //             .build();
    //     final PolynomialFunction factor2 = PolynomialFunction.builder()
    //             .funcName("g")
    //             .varName("x")
    //             .terms(new LinkedList<>(){{
    //                 add(new PolynomialTerm(1.0, "x", 1));
    //                 add(new PolynomialTerm(1.0, "x", 0));
    //             }})
    //             .build();
    //     final PolynomialFunction factor3 = PolynomialFunction.builder()
    //             .funcName("h")
    //             .varName("x")
    //             .terms(new LinkedList<>(){{
    //                 add(new PolynomialTerm(1.0, "x", 1));
    //                 add(new PolynomialTerm(-1.0, "x", 0));
    //             }})
    //             .build();

    //     final LinkedList<PolynomialFunction> factors = new LinkedList<>(){{
    //         add(factor1);
    //         add(factor2);
    //         add(factor3);
    //     }};

    //     final CompositeFunction compositeFunction = new CompositeFunction(factors);

    //     assertEquals(factors, compositeFunction.factors);

    //     final PolynomialFunction expectedReducedFunction = compositeFunction.reduce();

    //     System.out.println(expectedReducedFunction.toString(false));
    // }
}
