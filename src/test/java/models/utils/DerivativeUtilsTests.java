package models.utils;

import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.Test;
import utils.derivativeUtils;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DerivativeUtilsTests {

    @Test
    public void productRuleTests() {
        final PolynomialFunction f1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(3)
                    .build());
        }}, "f1", "x");
        final PolynomialFunction f2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f2", "x");

        // Compute derivative of f1 * f2
        final Function derivative = derivativeUtils.productRule(f1, f2);

        assertEquals("(f1 * f2)'(x) = ( 6x² )( 2x² ) + ( 2x³ )( 4x )", derivative.printFunc());
    }

    @Test
    public void exceptionsTests() {
        final PolynomialFunction f1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(3)
                    .build());
        }}, "f1", "x");
        final PolynomialFunction f2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("y")
                    .exponent(2)
                    .build());
        }}, "f2", "y");

        assertThrows(IllegalArgumentException.class, () -> derivativeUtils.productRule(f1, f2));
        assertThrows(IllegalArgumentException.class, () -> derivativeUtils.quotientRule(f1, f2));
    }

    @Test
    public void quotientRuleTests() {
        final PolynomialFunction f1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(3)
                    .build());
        }}, "f1", "x");
        final PolynomialFunction f2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f2", "x");

        // Compute derivative of f1 / f2
        final Function derivative = derivativeUtils.quotientRule(f1, f2);

        assertEquals("(f1 / f2)'(x) = ( ( ( 6x² )( 2x² ) + ( 2x³ )( 4x ) ) / ( ( 2x² )( 2x² ) ) )", derivative.printFunc());
    }
}
