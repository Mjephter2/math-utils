package models.functions.polynomials;

import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.FunctionType;
import models.numberUtils.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;

import static models.functions.polynomials.PolynomialTerm.TERM_COMPARATOR;
import static utils.StringUtils.trimTrailingLeadingPlus;

/**
 * This class implements a multi Term polynomial expression in one variable (for now)
 * of the form a_0 + a_1*x^1 + a_2*x^2 + ... + a_n*x^n
 * where a_i is a double value
 */
@Getter
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
     * independent variable name.
     */
    @NonNull
    private final String varName;

    /**
     * degree of the polynomial expression.
     */
    private int degree;

    /**
     * type of the polynomial function
     */
    private PolynomialFunctionType type;

    /**
     * boolean representing whether the polynomial is an indefinite integral or not.
     */
    private boolean isIndefiniteIntegral = false;

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

        this.degree = this.terms.stream().mapToInt(PolynomialTerm::getExponent).max().orElse(0);
        if (degree > 3) {
            this.type = PolynomialFunctionType.HIGHER_DEGREES;
        } else if (degree == 3) {
            this.type = PolynomialFunctionType.CUBIC;
        } else if (degree == 2) {
            this.type = PolynomialFunctionType.QUADRATIC;
        } else {
            this.type = PolynomialFunctionType.LINEAR;
        }

        this.funcName = funcName;
        this.varName = varName;

        this.removeZeroTerms();
        this.terms.sort(TERM_COMPARATOR);
    }

    /**
     * Creates a polynomial function from a list of terms.
     * @param pTerms -> list of terms
     * @param funcName -> name of the polynomial function
     * @param varName -> name of the independent variable
     * @param isIndefiniteIntegral -> boolean representing whether the polynomial is an indefinite integral or not
     */
    public PolynomialFunction(final @NonNull List<PolynomialTerm> pTerms, final @NonNull String funcName, final @NonNull String varName, final boolean isIndefiniteIntegral) {
        validate(pTerms, varName);

        this.terms = new LinkedList<>();
        pTerms.forEach(this::addTerm);

        this.degree = this.terms.stream().mapToInt(PolynomialTerm::getExponent).max().orElse(0);

        this.funcName = funcName;
        this.isIndefiniteIntegral = isIndefiniteIntegral;
        this.varName = varName;

        this.removeZeroTerms();
        this.terms.sort(TERM_COMPARATOR);
    }

    /**
     * Validates that all terms have the same variable name.
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
    public static PolynomialFunction from(final String polynomialString, final String funcName, final String varName)  {
        final String[] terms = polynomialString.split("[+-]");
        int latestIndex = 0;
        final PolynomialFunction func = new PolynomialFunction(new LinkedList<>(), funcName, varName, false);
        for (String part: terms) {
            final String cleaned = part.trim();
            int index = polynomialString.indexOf(part, latestIndex);
            latestIndex = index;
            final String sign = index == 0 ? "+" : polynomialString.substring(latestIndex - 1, latestIndex);
            if (!cleaned.isEmpty()) {
                func.addTerm(PolynomialTerm.from(sign + " " + cleaned, varName));
            }
        }
        func.degree = func.terms.stream().mapToInt(PolynomialTerm::getExponent).max().orElse(0);
        return func;
    }

    /**
     * Removes zero terms from the polynomial.
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
     * Adds a Term to current polynomial.
     * @param t : Term to add to current polynomial
     */
    public void addTerm(final PolynomialTerm t) {
        PolynomialTerm match = degExists(t);
        if (match != null) {
            double newCoefficient = match.getCoefficient() + t.getCoefficient();
            match.setCoefficient(newCoefficient);
        } else {
            PolynomialTerm newTerm = PolynomialTerm.builder()
                    .coefficient(t.getCoefficient())
                    .varName(t.getVarName())
                    .exponent(t.getExponent())
                    .build();
            this.terms.add(newTerm);
            this.terms.sort(TERM_COMPARATOR);
        }
        this.removeZeroTerms();
        this.degree = this.terms.stream().mapToInt(PolynomialTerm::getExponent).max().orElse(0);
    }

    /**
     * Adds a Polynomial to current Polynomial.
     * @param other : Polynomial to add to current Polynomial
     */
    public void add(final PolynomialFunction other) {
        if (!this.varName.equals(other.varName)) {
            throw new IllegalArgumentException("This operation is not implemented for terms with different variable names!");
        }
        for (PolynomialTerm term : other.terms) {
            this.addTerm(term);
        }
        this.degree = this.terms.stream().mapToInt(PolynomialTerm::getExponent).max().orElse(0);
    }

    /**
     * Subtracts a Polynomial from current Polynomial.
     * @param other : Polynomial to subtract from current Polynomial
     */
    public void subtract(final PolynomialFunction other) {
        if (!this.varName.equals(other.varName)) {
            throw new IllegalArgumentException("This operation is not implemented for terms with different variable names!");
        }
        for (PolynomialTerm term : other.terms) {
            this.addTerm(term.negate());
        }
        this.degree = this.terms.stream().mapToInt(PolynomialTerm::getExponent).max().orElse(0);
    }

    /**
     * Returns the negation of the current Polynomial.
     * @return the negation of the current Polynomial
     */
    public PolynomialFunction negate() {
        final PolynomialFunction negated = new PolynomialFunction(new LinkedList<>(), this.funcName, this.varName, false);
        for (PolynomialTerm term : this.terms) {
            negated.addTerm(term.negate());
        }
        return negated;
    }

    /**
     * Checks if a Term with given exponent exists in current Polynomial.
     * if there is such a Term, return it
     * else return null
     * @param t : Term whose degree we are looking for in current Polynomial
     * @return the Term with the given exponent if it exists, null otherwise
     */
    public PolynomialTerm degExists(final PolynomialTerm t) {
        for (PolynomialTerm term : this.terms) {
            if (term.getExponent() == t.getExponent()) {
                return term;
            }
        }
        return null;
    }

    /**
     * Multiply the current Polynomial by the given Term.
     * @param other : Term to multiply the current polynomial by
     */
    public void multiplyByTerm(final PolynomialTerm other) {
        for (PolynomialTerm term : this.terms) {
            term.multiplyBy(other);
        }
        this.degree = this.terms.stream().mapToInt(PolynomialTerm::getExponent).max().orElse(0);
    }

    /**
     * Multiply the current Polynomial by the given Polynomial.
     * @param other  : Polynomial to multiply the current Polynomial by
     * @return the resulting Polynomial (product)
     */
    public PolynomialFunction multiplyBy(final PolynomialFunction other) {
        final PolynomialFunction result = new PolynomialFunction(new LinkedList<>(), this.funcName, this.varName, false);
        for (PolynomialTerm t : other.terms) {
            PolynomialFunction copy = new PolynomialFunction(new LinkedList<>(), this.funcName, this.varName, false);
            for (PolynomialTerm term : this.terms) {
                copy.terms.add(new PolynomialTerm(term.getCoefficient(), this.varName, term.getExponent()));
            }
            copy.multiplyByTerm(t);
            result.add(copy);
        }
        return result;
    }

    /**
     * Divide the current Polynomial by the given Polynomial.
     * aka Polynomial long division
     * @param divider
     * @return
     */
    public HashMap<PolynomialFunction, PolynomialFunction> divideBy(final PolynomialFunction divider) {
        final HashMap<PolynomialFunction, PolynomialFunction> result = new HashMap<>();
        final PolynomialFunction quotient = new PolynomialFunction(new LinkedList<>(), this.funcName, this.varName, false);
        final PolynomialFunction remainder = new PolynomialFunction(new LinkedList<>(), this.funcName, this.varName, false);
        for (PolynomialTerm term : this.terms) {
            remainder.addTerm(term);
        }
        while (remainder.degree >= divider.degree) {
            final PolynomialTerm term = PolynomialTerm.builder()
                    .coefficient(remainder.terms.get(0).getCoefficient() / divider.terms.get(0).getCoefficient())
                    .varName(this.varName)
                    .exponent(remainder.terms.get(0).getExponent() - divider.terms.get(0).getExponent())
                    .build();
            quotient.addTerm(term);
            final PolynomialFunction dividerCopy = divider.deepCopy("dividerCopy");
            dividerCopy.multiplyByTerm(term);
            remainder.subtract(dividerCopy);
        }
        result.put(quotient, remainder);
        return result;
    }

    /**
     * Compose the current Polynomial with the given Polynomial.
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

        final PolynomialFunction result = new PolynomialFunction(new LinkedList<>(), this.funcName, this.varName, false);
        for (PolynomialFunction func : compositionParts) {
            result.add(func);
        }
        return result;
    }

    /**
     * Evaluate Polynomial with the given input value.
     * @param input -> value to substitute x by
     * @return the evaluated number
     */
    private Double evaluateFunc(final double input) {
        double value = 0.0;
        for (PolynomialTerm term : this.terms) {
            value += term.evaluate(input);
        }
        return value;
    }

    /**
     * Raise the current Polynomial to the given power.
     * @param p : power to raise the current Polynomial to
     * @return the resulting Polynomial (power)
     */
    public PolynomialFunction power(final int p) {
        if (p < 0) {
            throw new IllegalArgumentException("This operation is not implemented for negative powers!");
        }
        if (p == 0) {
            return new PolynomialFunction(new LinkedList<>() { {
                add(new PolynomialTerm(1.0, varName, 0));
            } }, this.funcName, this.varName, false);
        }
        PolynomialFunction result = new PolynomialFunction(new LinkedList<>(), this.funcName, this.varName, false);
        result.add(this);
        for (int i = 1; i < p; i++) {
            result = result.multiplyBy(this);
        }
        return result;
    }

    /**
     * Checks if the current Polynomial is a zero function.
     * @return true if the Polynomial is a zero function, false otherwise
     */
    public boolean isZeroFunction() {
        return this.terms.stream().allMatch(term -> term.getCoefficient() == 0.0);
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.POLYNOMIAL;
    }

    @Override
    public List<Range> getDomain() {
        return List.of(Range.all());
    }

    @Override
    public List<Range> getRange() {
        return List.of(Range.all());
    }

    /**
     * Evaluates the current Polynomial with the given input values.
     * @param values : input values to evaluate the Polynomial with
     * @return the evaluated number
     */
    @Override
    public double evaluate(final Double... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("This operation is not implemented for zero input values!");
        }
        if (values.length > 1) {
            throw new IllegalArgumentException("This operation is not implemented for more than one input value!");
        }
        return this.evaluateFunc(values[0]);
    }

    @Override
    public Function simplify() {
        if (this.terms.size() == 1 && this.terms.get(0).getExponent() == 0) {
            return ConstantFunction.builder()
                    .funcName(this.funcName)
                    .value(this.terms.get(0).getCoefficient())
                    .build();
        }
        return this;
    }

    /**
     * Computes the derivative of the current Polynomial.
     * @return the derivative of the Polynomial
     */
    @Override
    public Function derivative() {
        return new PolynomialFunction(new LinkedList<>() { {
            for (PolynomialTerm term : terms) {
                add(term.derivative());
            }
        } }, this.funcName + "'", this.varName, false);
    }

    /**
     * Computes the derivative of the current Polynomial.
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
                this.varName,
                true);
    }

    /**
     * Computes the integral of the current Polynomial in the given bounds.
     * @param lowerBound : lower bound of the integral
     * @param upperBound : upper bound of the integral
     * @return the value of the integral
     */
    @Override
    public double integral(final double lowerBound, final double upperBound) {
        final Function integral = this.integral();
        return Math.round((integral.evaluate(upperBound) - integral.evaluate(lowerBound)) * 100) / 100.0;
    }

    @Override
    public double limit(final double value) {
        return this.evaluate(value);
    }

    @Override
    public PolynomialFunction deepCopy(final String newFuncName) {
        return new PolynomialFunction(this.terms.stream().map(PolynomialTerm::deepCopy).collect(Collectors.toList()), newFuncName, this.varName, false);
    }

    /**
     * Constructs a String representation of the current Polynomial, depending on whether it is an indefinite integral or not.
     * @param isIndefiniteIntegral : boolean representing whether the polynomial is an indefinite integral or not
     * @return the String representation of the Polynomial
     */
    public String toString(final boolean isIndefiniteIntegral) {
        if (isZeroFunction() || this.terms.isEmpty()) {
            return this.funcName + "(" + this.varName + ") = 0.0";
        }

        String repPrefix;
        if (isIndefiniteIntegral) {
            repPrefix = this.funcName +  "(" + this.varName + ")d" + this.varName + " = ";
        } else {
            repPrefix = this.funcName + "(" + this.varName + ") = ";
        }

        if (this.terms.size() == 1) {
            return repPrefix + this.terms.get(0).toString(true) + (isIndefiniteIntegral ? " + C" : "");
        }

        final StringBuilder rep = new StringBuilder();

        for (PolynomialTerm term : this.terms) {
            rep.append(term.toString(false)).append(" ");
        }

        return (repPrefix + trimTrailingLeadingPlus(rep.toString())).trim() + (isIndefiniteIntegral ? " + C" : "");
    }

    /**
     * Constructs a String representation of the current Polynomial, assuming it is not an indefinite integral.
     * @return the String representation of the Polynomial
     */
    public String toString() {
        return this.printFunc();
    }

    @Override
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

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof PolynomialFunction) {
            final PolynomialFunction otherPolynomialFunction = (PolynomialFunction) other;
            for (int i = 0; i < this.terms.size(); i++) {
                if (!this.terms.get(i).equals(otherPolynomialFunction.terms.get(i))) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.varName,
                this.degree,
                this.isIndefiniteIntegral,
                this.terms.stream()
                        .map(term -> Integer.toString(term.hashCode())).reduce("", String::concat));
    }

    /**
     * Apply Descartes's rule of signs to the current Polynomial.
     * @return the maximum number of positive and negative roots
     */
    public int[] runDescartesRuleOfSign() {
        int numberOfPositiveRoots = 0;
        int numberOfNegativeRoots = 0;

        for (int i = 0; i < this.terms.size() - 1; i++) {
            if (this.terms.get(i).getCoefficient() * this.terms.get(i + 1).getCoefficient() < 0.0) {
                numberOfPositiveRoots++;
            }
        }

        // Compute terms for f(-x)
        final List<PolynomialTerm> negatedTerms = this.terms.stream().map(term -> PolynomialTerm.builder()
                .coefficient(term.getCoefficient() * Math.pow(-1, term.getExponent()))
                .varName(term.getVarName())
                .exponent(term.getExponent())
                .build()
                .negate()).collect(Collectors.toList());
        for (int i = 0; i < negatedTerms.size() - 1; i++) {
            if (negatedTerms.get(i).getCoefficient() * negatedTerms.get(i + 1).getCoefficient() < 0.0) {
                numberOfNegativeRoots++;
            }
        }

        return new int[]{numberOfPositiveRoots, numberOfNegativeRoots};
    }
}
