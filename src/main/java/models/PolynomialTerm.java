package models;

import java.util.Comparator;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** This class implements a single term / variate polynomial
 * in the form c * x ^ p
 * where c is the coefficient and p is the exponent of the independent variable x,
 */
@Getter
@Setter
@Builder
public class PolynomialTerm {
    /**
     * A comparator to arrange terms in a polynomial by degrees in descending order.
     **/
    public static final Comparator<PolynomialTerm> TERM_COMPARATOR = (PolynomialTerm polynomialTerm1, PolynomialTerm polynomialTerm2)
            ->
            (polynomialTerm2.exponent - polynomialTerm1.exponent);

    /**
     * Create a Term from a String representation.
     * @param termString -> String representation of the Term
     * @param variable -> variable name
     * @return the Term
     */
    public static PolynomialTerm from(final String termString, final String variable) {
        final String[] termParts = termString.split("\\^");
        final String[] coefficientAndVariable = termParts[0].split(variable);
        final double coefficient = Double.parseDouble(coefficientAndVariable[0].trim().replace(" ", ""));
        final int exponent = termParts.length > 1 ? Integer.parseInt(termParts[1].trim()) : (termParts[0].contains(variable) ? 1 : 0);
        return PolynomialTerm.builder()
                .coefficient(coefficient)
                .varName(variable)
                .exponent(exponent)
                .build();
    }

    /**
     * coefficient of polynomial term.
     */
    private double coefficient;

    /**
     * variable name.
     */
    private final String varName;

    /**
     * exponent/degree of polynomial term.
     */
    private int exponent;

    /**
     * Multiply the current Term by another one.
     * @param other Term to multiply by
     */
    public void multiplyBy(final PolynomialTerm other) {
        if (!this.varName.contains(other.varName)) {
            throw new IllegalArgumentException("This operation is not implemented for terms with different variable names!");
        }
        this.coefficient = coefficient * other.coefficient;
        this.exponent = this.exponent + other.exponent;
    }

    /**
     * Evaluate Term with the given input value.
     * @param input -> value to substitute variable by
     * @return the computed value
     */
    public double evaluate(final double input) {
        return this.coefficient * Math.pow(input, this.exponent);
    }

    /**
     * @return a String representation of the Term.
     */
    public String toString() {
        if (coefficient == 0.0) {
            return "0.0";
        }

        final String sign = coefficient < 0 ? "-" : "+";
        if (exponent == 0) {
            return sign + " " + Math.abs(coefficient);
        }
        return sign + " " + (exponent == 1 ? Math.abs(coefficient) + varName : Math.abs(coefficient) + varName + "^" + exponent);
    }
}
