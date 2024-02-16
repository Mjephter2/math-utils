package models.functions.polynomials;

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

/** This class implements a single term / variate polynomial in the form c * x ^ p.
 * c is the coefficient and p is the exponent of the independent variable x
 */
@Getter
@Setter
@Builder
public class PolynomialTerm {

    // Static members

    /**
     * A comparator to arrange terms in a polynomial by degrees in descending order.
     **/
    public static final Comparator<PolynomialTerm> TERM_COMPARATOR =
            (PolynomialTerm t1, PolynomialTerm t2) -> (t2.exponent - t1.exponent);

    /**
     * Default DecimalFormat of form #.####.
     */
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");

    // Instance members

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
     * Creates a PolynomialTerm with provided coefficient, variable name and exponent.
     * @param coefficient -> coefficient of polynomial term
     * @param varName -> variable name
     * @param exponent -> exponent/degree of polynomial term
     */
    public PolynomialTerm(final double coefficient, final String varName, final int exponent) {
        // exponent must be positive
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent of PolynomialTerm must be positive! Value provided: " + exponent);
        }
        this.coefficient = coefficient;
        this.varName = varName;
        this.exponent = exponent;
    }

    /**
     * Creates a PolynomialTerm with provided coefficient.
     * Defaults variable name to "x" and exponent to 1
     * @param coefficient -> coefficient of polynomial term
     * @return the new Term
     */
    public static PolynomialTerm withCoefficient(final double coefficient) {
        return new PolynomialTerm(coefficient, "x", 1);
    }

    /**
     * Creates a PolynomialTerm with default values.
     * Coefficient = 1
     * variableName = "x"
     * exponent = 1
     * @return the new Term
     */
    public static PolynomialTerm withDefaults() {
        return new PolynomialTerm(1, "x", 1);
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
        this.coefficient = this.coefficient * other.coefficient;
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
        this.DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_EVEN);

        if (this.coefficient == 0.0) {
            return "0.0";
        }

        final String coefString;
        if ((this.coefficient == 1.0 || this.coefficient == -1.0) && this.exponent != 0) {
            coefString = "";
        } else {
            coefString = this.DECIMAL_FORMAT.format(Math.abs(this.coefficient));
        }

        String sign;
        if (isFirstTerm && this.coefficient >= 0.0) {
            sign = "";
        } else {
            sign = this.coefficient < 0.0 ? "-" : "+";
        }

        if (this.exponent == 0) {
            return sign.isEmpty() ?  coefString : sign + " " + coefString;
        }
        String strRep = sign + " " + (this.exponent == 1 ? coefString + this.varName : coefString + this.varName + convertToSuperscript(this.exponent));
        return strRep.trim();
    }

    /**
     * @return the negation of the current Term.
     */
    public PolynomialTerm negate() {
        return PolynomialTerm.builder()
                .coefficient(-this.coefficient)
                .varName(new String(this.varName))
                .exponent(this.exponent)
                .build();
    }

    /**
     * Checks if the current Term is equal to another one.
     * @param other -> the other Term
     * @return true if the two Terms are equal, false otherwise
     */
    public boolean equals(final PolynomialTerm other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.coefficient, this.varName, this.exponent);
    }

    /**
     * @return the derivative of the current Term.
     */
    public PolynomialTerm derivative() {
        if (this.coefficient == 0.0 || this.exponent == 0) {
            return PolynomialTerm.builder()
                    .coefficient(0.0)
                    .varName(new String(this.varName))
                    .exponent(0)
                    .build();
        }
        return PolynomialTerm.builder()
                .coefficient(this.coefficient * this.exponent)
                .varName(new String(this.varName))
                .exponent(this.exponent - 1)
                .build();
    }

    /**
     * @return the integral of the current Term.
     */
    public PolynomialTerm integral() {
        if (this.coefficient == 0.0 || this.exponent == 0) {
            return PolynomialTerm.builder()
                    .coefficient(0.0)
                    .varName(new String(this.varName))
                    .exponent(0)
                    .build();
        }
        return new PolynomialTerm(this.coefficient / (this.exponent + 1), this.varName, this.exponent + 1);
    }

    /**
     * @return a deep copy of a Term.
     */
    public PolynomialTerm deepCopy() {
        return PolynomialTerm.builder()
                .coefficient(this.coefficient)
                .varName(new String(this.varName))
                .exponent(this.exponent)
                .build();
    }
}
