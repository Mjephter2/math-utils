package models.functions;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static utils.StringUtils.trimTrailingLeadingPlus;

/**
 * This class implements a multi Term polynomial expression in one variable (for now)
 * of the form a_0 + a_1*x^1 + a_2*x^2 + ... + a_n*x^n
 * where a_i is a double value
 */
@Getter
@Setter
@Builder
public class PolynomialFunction implements Function {

    /**
     * A LinkedList of Terms representing the polynomial expression.
     */
    @NonNull
    private final List<PolynomialTerm> terms;

    /**
     * String representing the name of the polynomial function.
     * i.e. the 'f' in 'f(x)' or the 'h' in 'h(x)'
     */
    @NonNull
    private final String funcName;

    /**
     * independent variable name
     */
    @NonNull
    private final String varName;

    /**
     * Creates a polynomial function from a list of terms.
     * @param pTerms -> list of terms
     * @param funcName -> name of the polynomial function
     * @param varName -> name of the independent variable
     */
    public PolynomialFunction(final @NonNull List<PolynomialTerm> pTerms, final @NonNull String funcName, final @NonNull String varName) {
        validate(pTerms, varName);

        this.terms = new LinkedList<>();
        pTerms.forEach(this::addTerm);

        this.funcName = funcName;
        this.varName = varName;

        this.removeZeroTerms();
        this.terms.sort(PolynomialTerm.TERM_COMPARATOR);
    }

    /**
     * Validates that all terms have the same variable name
     * @param pTerms -> list of terms
     * @param varName -> name of the independent variable
     */
    private void validate(final List<PolynomialTerm> pTerms, final String varName) {
        for (PolynomialTerm term: pTerms) {
            if (!term.getVarName().equals(varName)) {
                throw new IllegalArgumentException("All terms must have the same variable name!");
            }
        }
    }

    /**
     * Create a PolynomialFunction from a String representation.
     * @param polynomialString -> String representation of the PolynomialFunction
     * @param funcName -> name of the polynomial function
     * @param varName -> name of the independent variable
     * @return the PolynomialFunction
     */
    public static PolynomialFunction from(final String polynomialString,final String funcName, final String varName)  {
        final String[] terms = polynomialString.split("[+-]");
        int latestIndex = 0;
        final PolynomialFunction func = PolynomialFunction.builder()
                .funcName(funcName)
                .varName(varName)
                .terms(new LinkedList<>())
                .build();
        for (String part: terms) {
            final String cleaned = part.trim();
            int index = polynomialString.indexOf(part, latestIndex);
            latestIndex = index;
            final String sign = index == 0 ? "+" : polynomialString.substring(latestIndex - 1, latestIndex);
            if (cleaned.length() > 0) {
                func.addTerm(PolynomialTerm.from(sign + " " + cleaned, varName));
            }
        }
        return func;
    }

    /**
     * Removes zero terms from the polynomial
     */
    private void removeZeroTerms() {
        final List<PolynomialTerm> zeroTerms = new ArrayList<>();
        for (PolynomialTerm term : this.terms) {
            if (term.getCoefficient() == 0) {
                zeroTerms.add(term);
            }
        }
        this.terms.removeAll(zeroTerms);
    }

    /**
     * Adds a Term to current polynomial
     * @param t : Term to add to current polynomial
     */
    public void addTerm(final PolynomialTerm t) {
        PolynomialTerm match = degExists(t);
        if(match != null) {
            double newCoefficient = match.getCoefficient() + t.getCoefficient();
            match.setCoefficient(newCoefficient);
        } else {
            PolynomialTerm newTerm = PolynomialTerm.builder()
                    .coefficient(t.getCoefficient())
                    .varName(t.getVarName())
                    .exponent(t.getExponent())
                    .build();
            this.terms.add(newTerm);
            this.terms.sort(PolynomialTerm.TERM_COMPARATOR);
        }
        this.removeZeroTerms();
    }

    /**
     * Adds a Polynomial to current Polynomial
     */
    public void add(final PolynomialFunction other) {
        if (!this.varName.equals(other.varName)) {
            throw new IllegalArgumentException("This operation is not implemented for terms with different variable names!");
        }
        for(PolynomialTerm term : other.terms) {
            this.addTerm(term);
        }
    }

    /**
     * Subtracts a Polynomial from current Polynomial
     */
    public void subtract(final PolynomialFunction other) {
        if (!this.varName.equals(other.varName)) {
            throw new IllegalArgumentException("This operation is not implemented for terms with different variable names!");
        }
        for (PolynomialTerm term : other.terms) {
            this.addTerm(term.negate());
        }
    }

    /**
     * Returns the negation of the current Polynomial
     */
    public PolynomialFunction negate() {
        final PolynomialFunction negated = PolynomialFunction.builder()
                .varName(this.varName)
                .funcName(this.funcName)
                .terms(new LinkedList<>())
                .build();
        for (PolynomialTerm term : this.terms) {
            negated.addTerm(term.negate());
        }
        return negated;
    }

    /**
     * Checks if a Term with given exponent exists in current Polynomial
     * if there is such a Term, return it
     * else return null
     * @param t : Term whose degree we are looking for in current Polynomial
     */
    public PolynomialTerm degExists(final PolynomialTerm t) {
        for(PolynomialTerm term : this.terms) {
            if (term.getExponent() == t.getExponent()) {
                return term;
            }
        }
        return null;
    }

    /**
     * Multiply the current Polynomial by the given Term
     * @param other : Term to multiply the current polynomial by
     */
    public void multiplyByTerm(final PolynomialTerm other) {
        for(PolynomialTerm term : this.terms) {
            term.multiplyBy(other);
        }
    }

    /**
     * Multiply the current Polynomial by the given Polynomial
     * @param other  : Polynomial to multiply the current Polynomial by
     * @return the resulting Polynomial (product)
     */
    public PolynomialFunction multiplyBy(final PolynomialFunction other) {
        final PolynomialFunction result = PolynomialFunction.builder()
                .varName(this.varName)
                .funcName(this.funcName)
                .terms(new LinkedList<>())
                .build();
        for(PolynomialTerm t : other.terms) {
            PolynomialFunction copy = PolynomialFunction.builder()
                    .varName(this.varName)
                    .funcName(this.funcName)
                    .terms(new LinkedList<>())
                    .build();
            for(PolynomialTerm term : this.terms) {
                copy.terms.add(new PolynomialTerm(term.getCoefficient(),this.varName, term.getExponent()));
            }
            copy.multiplyByTerm(t);
            result.add(copy);
        }
        return result;
    }

    /**
     * Compose the current Polynomial with the given Polynomial
     * @param other : Polynomial to compose the current Polynomial with
     * @return the resulting Polynomial (composition)
     */
    public PolynomialFunction composeWith(final PolynomialFunction other) {
        final List<PolynomialFunction> compositionParts = new ArrayList<>();
        for (PolynomialTerm term : this.terms) {
            final PolynomialFunction part = other.power(term.getExponent());
            part.multiplyByTerm(PolynomialTerm.builder()
                            .varName(term.getVarName())
                            .coefficient(term.getCoefficient())
                            .exponent(0)
                    .build());
            compositionParts.add(part);
        }

        final PolynomialFunction result = PolynomialFunction.builder()
                .varName(this.varName)
                .funcName(this.funcName)
                .terms(new LinkedList<>())
                .build();
        for (PolynomialFunction func : compositionParts) {
            result.add(func);
        }
        return result;
    }

    /**
     * Evaluate Polynomial with the given input value
     * @param input -> value to substitute x by
     * return the evaluated number
     */
    private Double evaluateFunc(final double input) {
        double value = 0.0;
        for(PolynomialTerm term : this.terms) {
            value += term.evaluate(input);
        }
        return value;
    }

    /**
     * Raise the current Polynomial to the given power.
     * @return the resulting Polynomial (power)
     */
    public PolynomialFunction power(final int p) {
        if (p < 0) {
            throw new IllegalArgumentException("This operation is not implemented for negative powers!");
        }
        if (p == 0) {
            return PolynomialFunction.builder()
                    .varName(this.varName)
                    .funcName(this.funcName)
                    .terms(new LinkedList<>(){{
                        add(new PolynomialTerm(1.0, varName, 0));
                    }})
                    .build();
        }
        PolynomialFunction result = PolynomialFunction.builder()
                .varName(this.varName)
                .funcName(this.funcName)
                .terms(new LinkedList<>())
                .build();
        result.add(this);
        for (int i = 1; i < p; i++) {
            result = result.multiplyBy(this);
        }
        return result;
    }

    /**
     * Checks if the current Polynomial is a zero function
     * @return true if the Polynomial is a zero function, false otherwise
     */
    public boolean isZeroFunction() {
        return this.terms.stream().allMatch(term -> term.getCoefficient() == 0.0);
    }

    @Override
    public Range<Double> getDomain() {
        return Range.all();
    }

    @Override
    public Range<Double> getRange() {
        return Range.all();
    }

    /**
     * Evaluates the current Polynomial with the given input values
     * @param values : input values to evaluate the Polynomial with
     * @return the evaluated number
     */
    @Override
    public double evaluate(Double... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("This operation is not implemented for zero input values!");
        }
        if (values.length > 1) {
            throw new IllegalArgumentException("This operation is not implemented for more than one input value!");
        }
        return this.evaluateFunc(values[0]);
    }

    /**
     * Computes the derivative of the current Polynomial
     * @return the derivative of the Polynomial
     */
    @Override
    public Function derivative() {
        return new PolynomialFunction(new LinkedList<>(){{
            for (PolynomialTerm term : terms) {
                add(term.derivative());
            }
        }}, this.funcName + "'", this.varName);
    }

    /**
     * Computes the derivative of the current Polynomial
     * @return the derivative of the Polynomial
     */
    @Override
    public Function integral() {
        return new PolynomialFunction(
                new LinkedList<>() {{
                    for (PolynomialTerm term : terms) {
                        add(term.integral());
                    }
                }},
                "∫" + this.funcName,
                this.varName);
    }

    /**
     * Computes the integral of the current Polynomial in the given bounds
     * @param lowerBound : lower bound of the integral
     * @param upperBound : upper bound of the integral
     * @return the value of the integral
     */
    @Override
    public double integral(double lowerBound, double upperBound) {
        final Function integral = this.integral();
        return Math.round((integral.evaluate(upperBound) - integral.evaluate(lowerBound)) * 100) / 100.0;
    }

    @Override
    public double limit(double value) {
        return this.evaluate(value);
    }

    public PolynomialFunction deepCopy() {
        return PolynomialFunction.builder()
                .varName(this.varName)
                .funcName(this.funcName)
                .terms(this.terms.stream().map(PolynomialTerm::deepCopy).collect(Collectors.toList()))
                .build();
    }

    /*
     * returns a String representation of the polynomial expression
     */
    public String toString(final boolean isIntegral) {
        if (isZeroFunction() || this.terms.isEmpty()) {
            return "0.0";
        }

        String repPreffix = "";
        if (isIntegral) {
            repPreffix = this.funcName +  "(" + this.varName + ")d" + this.varName + " = ";
        } else {
            repPreffix = this.funcName + "(" + this.varName + ") = ";
        }

        if (this.terms.size() == 1) {
            return repPreffix + this.terms.get(0).toString(true) + (isIntegral ? " + C" : "");
        }

        final StringBuilder rep = new StringBuilder();

        for (PolynomialTerm term : this.terms) {
            rep.append(term.toString(false)).append(" ");
        }

        return (repPreffix + trimTrailingLeadingPlus(rep.toString())).trim() + (isIntegral ? " + C" : "");
    }

    public String printBody() {
        if (isZeroFunction() || this.terms.isEmpty()) {
            return "0.0";
        }

        if (this.terms.size() == 1) {
            return this.terms.get(0).toString(true);
        }

        final StringBuilder rep = new StringBuilder();

        for (PolynomialTerm term : this.terms) {
            rep.append(term.toString(false)).append(" ");
        }

        return (trimTrailingLeadingPlus(rep.toString())).trim();
    }
}
