package models.functions;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static utils.SuperscriptUtil.convertToSuperscript;

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

    private static final DecimalFormat df = new DecimalFormat("#.#");

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

    public PolynomialTerm(final double coefficient, final String varName, final int exponent) {
        this.coefficient = coefficient;
        this.varName = varName;
        this.exponent = exponent;
    }

    /**
     * Create a Term from a String representation.
     * @param termString -> String representation of the Term
     * @param variable -> variable name
     * @return the Term
     */
    public static PolynomialTerm from(final String termString, final String variable) {
        if (termString == null || termString.isEmpty()) {
            throw new IllegalArgumentException("Term string representation cannot be null or empty!");
        }

        final Matcher matcher = validate(termString, variable);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid term string representation!");
        }

        String sign = matcher.group(1) == null ? "" : matcher.group(1);
        String coefficientString = matcher.group(2);

        if (coefficientString == null) {
            coefficientString = matcher.group(1) == null ? "1" : matcher.group(1) +  "1";
        } else {
            coefficientString = sign + coefficientString;
        }

        final double coefficient = Double.parseDouble(coefficientString.replace(" ", ""));
        final int exponent = matcher.group(4) != null ? Integer.parseInt(matcher.group(4).substring(1)) : (termString.contains(variable) ? 1 : 0);
        return PolynomialTerm.builder()
                .coefficient(coefficient)
                .varName(variable)
                .exponent(exponent)
                .build();
    }

    /**
     * Validate a potential Term String representation.
     * @param strRep -> String representation of the Term
     * @param varName -> variable name
     * @return a regex Matcher object
     */
    private static Matcher validate(final String strRep, final String varName) {
        final String pattern = "(\\+ |- )?([0-9]*[.][0-9]+|[0-9]+)?(" + varName + "(\\^\\d+)?)?";

        Pattern r = Pattern.compile(pattern);
        return r.matcher(strRep);
    }

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
    public String toString(final boolean isFirstTerm) {
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        if (coefficient == 0.0) {
            return "0.0";
        }

        final String coefString;
        if ((coefficient == 1.0 || coefficient == -1.0) && exponent != 0) {
            coefString = "";
        } else {
            coefString = df.format(Math.abs(coefficient));
        }

        String sign;
        if (isFirstTerm && coefficient >= 0.0) {
            sign = "";
        } else {
            sign = coefficient < 0.0 ? "-" : "+";
        }

        if (exponent == 0) {
            return sign.isEmpty() ?  coefString : sign + " " + coefString;
        }
        String strRep = sign + " " + (exponent == 1 ? coefString + varName : coefString + varName + convertToSuperscript(exponent));
        return strRep.trim();
    }

    /**
     * @return the negation of the current Term.
     */
    public PolynomialTerm negate() {
        return PolynomialTerm.builder()
                .coefficient(-coefficient)
                .varName(varName)
                .exponent(exponent)
                .build();
    }

    /**
     * Checks if the current Term is equal to another one.
     * @param o -> the other Term
     * @return true if the two Terms are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolynomialTerm that = (PolynomialTerm) o;
        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, varName, exponent);
    }

    /**
     * @return the derivative of the current Term.
     */
    public PolynomialTerm derivative() {
        if (coefficient == 0.0 || exponent == 0) {
            return PolynomialTerm.builder()
                    .coefficient(0.0)
                    .varName(varName)
                    .exponent(0)
                    .build();
        }
        return PolynomialTerm.builder()
                .coefficient(coefficient * exponent)
                .varName(varName)
                .exponent(exponent - 1)
                .build();
    }

    /**
     * @return the integral of the current Term.
     */
    public PolynomialTerm integral() {
        if (coefficient == 0.0 || exponent == 0) {
            return PolynomialTerm.builder()
                    .coefficient(0.0)
                    .varName(varName)
                    .exponent(0)
                    .build();
        }
        return new PolynomialTerm(coefficient / (exponent + 1), varName, exponent + 1);
    }
}
