package models.utils;

import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.jupiter.api.Test;
import utils.DerivativeUtils;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        final Function derivative = DerivativeUtils.productRule(f1, f2);

        assertEquals("(f1 * f2)'(x) = ( 1 )( 6x² )( 2x² ) + ( 1 )( 2x³ )( 4x )", derivative.printFunc());
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

        assertThrows(IllegalArgumentException.class, () -> DerivativeUtils.productRule(f1, f2));
        assertThrows(IllegalArgumentException.class, () -> DerivativeUtils.quotientRule(f1, f2));
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
        final Function derivative = DerivativeUtils.quotientRule(f1, f2);

        assertEquals("(f1 / f2)'(x) = ( 1 )( ( ( 1 )( 6x² )( 2x² ) + ( 1 )( 2x³ )( 4x ) ) / ( ( 1 )( 2x² )( 2x² ) ) )", derivative.printFunc());
    }
}
