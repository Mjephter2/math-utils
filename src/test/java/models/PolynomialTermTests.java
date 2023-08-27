package models;

import models.functions.PolynomialTerm;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class PolynomialTermTests {

    @Test
    public void parse_string_tests() {
        final String termString1 = "3.0x^2";
        final String variable1 = "x";
        final PolynomialTerm term1 = PolynomialTerm.from(termString1, variable1);
        assertEquals("x", term1.getVarName());
        assertEquals(3.0, term1.getCoefficient());
        assertEquals(2, term1.getExponent());
        assertEquals("+ 3x²", term1.toString(false));

        final String termString2 = "- x^2";
        final String variable2 = "x";
        final PolynomialTerm term2 = PolynomialTerm.from(termString2, variable2);
        assertEquals("x", term2.getVarName());
        assertEquals(-1.0, term2.getCoefficient());
        assertEquals(2, term2.getExponent());
        assertEquals("- x²", term2.toString(false));

        final String termString3 = "+ x^2";
        final String variable3 = "x";
        final PolynomialTerm term3 = PolynomialTerm.from(termString3, variable3);
        assertEquals("x", term3.getVarName());
        assertEquals(+1.0, term3.getCoefficient());
        assertEquals(2, term3.getExponent());
        assertEquals("+ x²", term3.toString(false));
    }

    @Test
    public void parse_string_fails() {
        final String variableX = "x";
        final String variableY = "y";

        final String termString1 = "3.0x^2";
        assertThrows(IllegalArgumentException.class, () -> PolynomialTerm.from(termString1, variableY));

        final String termString2 = "3.x^2";
        assertThrows(IllegalArgumentException.class, () -> PolynomialTerm.from(termString2, variableX));

        final String termString3 = "3.0x^";
        assertThrows(IllegalArgumentException.class, () -> PolynomialTerm.from(termString3, variableX));

        assertThrows(IllegalArgumentException.class, () -> PolynomialTerm.from("", variableX));

        final String termString5 = "3.0x^2";
        assertThrows(IllegalArgumentException.class, () -> PolynomialTerm.from(termString5, null));
    }

    @Test
    public void negate_tests() {
        final PolynomialTerm term1 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(3.0)
                .exponent(2)
                .build();
        final PolynomialTerm term2 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(-3.0)
                .exponent(2)
                .build();
        assertEquals(term2.getCoefficient(), term1.negate().getCoefficient());
        assertEquals(term2.getExponent(), term1.negate().getExponent());
    }

    @Test
    public void equals_tests() {
        PolynomialTerm term1 = this.sampleTerms()[0]; // 3x^2

        PolynomialTerm term2 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(3.0)
                .exponent(2)
                .build();

        Assert.assertTrue(term1.equals(term2));
    }

    @Test
    public void terms_multiply_succeeds() {
        PolynomialTerm term1 = this.sampleTerms()[0];
        term1.multiplyBy(this.sampleTerms()[1]);
        assertEquals("x", term1.getVarName());
        assertEquals(21.0, term1.getCoefficient());
        assertEquals(5, term1.getExponent());
    }

    @Test
    public void terms_multiply_fails() {
        final PolynomialTerm term1 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(3.0)
                .exponent(2)
                .build();
        final PolynomialTerm term2 = PolynomialTerm.builder()
                .varName("y")
                .coefficient(7.0)
                .exponent(3)
                .build();

        assertThrows(IllegalArgumentException.class, () -> term1.multiplyBy(term2));
    }

    @Test
    public void term_evaluate_succeeds() {
        PolynomialTerm term1 = this.sampleTerms()[0];
        PolynomialTerm term2 = this.sampleTerms()[1];

        assertEquals(3.0, term1.evaluate(1.0));
        assertEquals(56.0, term2.evaluate(2.0));
    }

    @Test
    public void term_derivative_tests() {
        final PolynomialTerm term1 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(3.0)
                .exponent(2)
                .build();
        final PolynomialTerm derivativeTerm1 = term1.derivative();
        assertEquals(6.0, derivativeTerm1.getCoefficient());
        assertEquals(1, derivativeTerm1.getExponent());

        final PolynomialTerm term2 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(0.0)
                .exponent(1)
                .build();
        final PolynomialTerm derivativeTerm2 = term2.derivative();
        assertEquals(0.0, derivativeTerm2.getCoefficient());
        assertEquals(0, derivativeTerm2.getExponent());
    }

    @Test
    public void term_integral() {
        final PolynomialTerm term1 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(3.0)
                .exponent(2)
                .build();
        final PolynomialTerm integralTerm1 = term1.integral();
        assertEquals(1.0, integralTerm1.getCoefficient());
        assertEquals(3, integralTerm1.getExponent());

        final PolynomialTerm term2 = PolynomialTerm.builder()
                .varName("x")
                .coefficient(0.0)
                .exponent(1)
                .build();
        final PolynomialTerm integralTerm2 = term2.integral();
        assertEquals(0.0, integralTerm2.getCoefficient());
        assertEquals(0, integralTerm2.getExponent());
    }

    @Test
    public void term_toString_tests() {
        PolynomialTerm term1 = this.sampleTerms()[0];
        PolynomialTerm term2 = this.sampleTerms()[1];
        PolynomialTerm term3 = this.sampleTerms()[2];
        PolynomialTerm term4 = this.sampleTerms()[3];
        PolynomialTerm term5 = this.sampleTerms()[4];
        PolynomialTerm term6 = this.sampleTerms()[5];
        PolynomialTerm term7 = this.sampleTerms()[6];

        assertEquals("+ 3x²", term1.toString(false));
        assertEquals("7x³", term2.toString(true));
        assertEquals("+ 7x", term3.toString(false));
        assertEquals("0.0", term4.toString(false));
        assertEquals("+ 7", term5.toString(false));
        assertEquals("- 7", term6.toString(false));
        assertEquals("x", term7.toString(true));
    }

    @Test
    public void term_compare_test() {
        PolynomialTerm term1 = this.sampleTerms()[0];
        PolynomialTerm term2 = this.sampleTerms()[1];

        assertTrue(PolynomialTerm.TERM_COMPARATOR.compare(term2, term1) < 0);
    }

    private PolynomialTerm[] sampleTerms() {
        return new PolynomialTerm[]{
            PolynomialTerm.builder()
                .varName("x")
                .coefficient(3.0)
                .exponent(2)
            .build(),
            PolynomialTerm.builder()
                .varName("x")
                .coefficient(7.0)
                .exponent(3)
            .build(),
            PolynomialTerm.builder()
                .varName("x")
                .coefficient(7.0)
                .exponent(1)
            .build(),
            PolynomialTerm.builder()
                .varName("x")
                .coefficient(0.0)
                .exponent(1)
            .build(),
            PolynomialTerm.builder()
                .varName("y")
                .coefficient(7.0)
                .exponent(0).build(),
            PolynomialTerm.builder()
                .varName("x")
                .coefficient(-7.0)
                .exponent(0)
            .build(),
            PolynomialTerm.builder()
                .varName("x")
                .coefficient(1.0)
                .exponent(1)
                .build()
        };
    }
}
