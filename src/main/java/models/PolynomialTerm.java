package models;

import java.util.Comparator;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/** This class implements a single term / variate polynomial
 * in the form c * x ^ p
 * where c is the coefficient and p is the exponent of the independent variable x,
 */
@Getter
@Builder
public class PolynomialTerm {
    /**
     * A comparator to arrange terms in a polynomial by degrees in descending order.
     **/
    public static final Comparator<PolynomialTerm> TERM_COMPARATOR = (PolynomialTerm polynomialTerm1, PolynomialTerm polynomialTerm2)
            ->
            (polynomialTerm2.exponent - polynomialTerm1.exponent);

    /**
     * coefficient of polynomial term.
     */
    @NonNull
    private final Double coefficient;

    /**
     * variable name.
     */
    @NonNull
    private final String varName;

    /**
     * exponent/degree of polynomial term.
     */
    @NonNull
    private final Integer exponent;

    /**
     * Multiply a Term by another one.
     * @param term1 first operand
     * @param term2 second operand
     * @return the resulting term
     */
    public static PolynomialTerm multiply(final PolynomialTerm term1, final PolynomialTerm term2) {
        if (!term1.varName.contains(term2.varName)) {
            throw new IllegalArgumentException("This operation is not implemented for terms with different variable names!");
        }
        final Double newCoefficient = term1.coefficient * term2.coefficient;
        final Integer newExponent = term1.exponent + term2.exponent;
        return PolynomialTerm.builder()
                .varName(term1.varName)
                .coefficient(newCoefficient)
                .exponent(newExponent)
                .build();
    }

    /**
     * Evaluate Term with the given input value.
     * @param input -> value to substitute variable by
     * @return the computed value
     */
    public static Double evaluate(final PolynomialTerm term, final Double input) {
        return term.coefficient * Math.pow(input, term.exponent);
    }

    /**
     * @return a String representation of the Term.
     */
    public String toString() {
        if (coefficient == 0.0) {
            return "0.0";
        }
        if (exponent == 0) {
            return String.valueOf(coefficient);
        }
        return (exponent == 1 ? "(" + coefficient + ")" + "x" : "(" + coefficient + ")" + "x^" + exponent);
    }
}
