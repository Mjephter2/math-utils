package models.functions;

import models.functions.combinations.RationalFunction;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RationalFunctionTests {

    @Test
    public void builder_test() {
        final Function numerator = ConstantFunction.builder()
                .funcName("numerator")
                .value(1.0)
                .build();
        final Function denominator = ConstantFunction.builder()
                .funcName("denominator")
                .value(2.0)
                .build();

        final RationalFunction rationalFunction = new RationalFunction(numerator, denominator, "f", "x");
        assertEquals(rationalFunction.getFuncName(), "f");
        assertEquals(rationalFunction.getVarName(), "x");
        assertEquals(rationalFunction.getNumerator(), numerator);
        assertEquals(rationalFunction.getDenominator(), denominator);
        assertEquals(rationalFunction.getFuncType(), FunctionType.RATIONAL);
        assertEquals(rationalFunction.getDomain(), denominator.getDomain().intersection(numerator.getDomain()));

        assertEquals(rationalFunction.evaluate(1.0), 0.5, 0.0);
        assertEquals(rationalFunction.evaluate(2.0), 0.5, 0.0);

        assertEquals( "f(x) = ( 1 ) / ( 2 )",rationalFunction.toString());
    }

    @Test
    public void deep_copy_test() {
        final RationalFunction ratFunc = RationalFunction.builder()
                .funcName("f")
                .varName("x")
                .numerator(new PolynomialFunction(new LinkedList<>() {{
                    add(new PolynomialTerm(1, "x", 1));
                }}, "numerator", "x"))
                .denominator(new PolynomialFunction(new LinkedList<>() {{
                    add(new PolynomialTerm(1, "x", 1));
                    add(new PolynomialTerm(1, "x", 0));
                }}, "denominator", "x"))
                .build();

        assertEquals(1, ((PolynomialFunction) ratFunc.getNumerator()).getTerms().size());
        assertEquals(2, ((PolynomialFunction) ratFunc.getDenominator()).getTerms().size());


        final RationalFunction copy = (RationalFunction) ratFunc.deepCopy(ratFunc.getFuncName());

        assertEquals(ratFunc.getFuncName(), copy.getFuncName());
        assertEquals(ratFunc.getVarName(), copy.getVarName());
        assertEquals(ratFunc.getNumerator().toString(), copy.getNumerator().toString());
        assertEquals(ratFunc.getDenominator().toString(), copy.getDenominator().toString());
        assertEquals(ratFunc.getFuncType(), copy.getFuncType());
        assertEquals(ratFunc.getDomain(), copy.getDomain());
        assertEquals(ratFunc.evaluate(1.0), copy.evaluate(1.0), 0.0);
        assertEquals(ratFunc.evaluate(2.0), copy.evaluate(2.0), 0.0);
    }

    @Test
    public void exception_test() {
        final RationalFunction rat = RationalFunction.builder().build();

        assertThrows(UnsupportedOperationException.class, rat::getRange);
        assertThrows(UnsupportedOperationException.class, rat::integral);
        assertThrows(UnsupportedOperationException.class, () -> rat.integral(1, 2));
        assertThrows(UnsupportedOperationException.class, () -> rat.limit(1));
    }

    @Test
    public void derivative_tests() {
        final RationalFunction ratFunc = RationalFunction.builder()
                .funcName("R")
                .varName("x")
                .numerator(new PolynomialFunction(new LinkedList<>() {{
                    add(new PolynomialTerm(1, "x", 1));
                }}, "f", "x"))
                .denominator(new PolynomialFunction(new LinkedList<>() {{
                    add(new PolynomialTerm(1, "x", 1));
                    add(new PolynomialTerm(1, "x", 0));
                }}, "g", "x"))
                .build();

        final Function derivative = ratFunc.derivative();

        assertEquals("(f / g)'(x) = ( ( ( 1 )( x + 1 ) + ( x )( 1 ) ) / ( ( x + 1 )( x + 1 ) ) )", derivative.printFunc());
    }

}
