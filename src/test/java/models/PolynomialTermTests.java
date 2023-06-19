package models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class PolynomialTermTests {

    @Test
    public void terms_multiply_succeeds() {
        PolynomialTerm term1 = this.sampleTerms()[0];
        term1.multiplyBy(this.sampleTerms()[1]);
        assertEquals("x", term1.getVarName());
        assertEquals(21.0, term1.getCoefficient());
        assertEquals((int) 5, term1.getExponent());
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
    public void term_toString_tests() {
        PolynomialTerm term1 = this.sampleTerms()[0];
        PolynomialTerm term2 = this.sampleTerms()[1];
        PolynomialTerm term3 = this.sampleTerms()[2];
        PolynomialTerm term4 = this.sampleTerms()[3];
        PolynomialTerm term5 = this.sampleTerms()[4];
        PolynomialTerm term6 = this.sampleTerms()[5];

        assertEquals("+ 3.0x^2", term1.toString());
        assertEquals("+ 7.0x^3", term2.toString());
        assertEquals("+ 7.0x", term3.toString());
        assertEquals("0.0", term4.toString());
        assertEquals("+ 7.0", term5.toString());
        assertEquals("- 7.0", term6.toString());
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
            .build()
        };
    }
}
