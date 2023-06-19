package models;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import java.util.LinkedList;
import java.util.List;

/*
 * This class implements a multi Term polynomial expression in one variable (for now)
 * of the form a_0 + a_1*x^1 + a_2*x^2 + ... + a_n*x^n
 * where a_i is a double value
 */
@Getter
@Setter
@Builder
public class PolynomialFunction {

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

    /*
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
     * Evaluate Polynomial with the given input value
     * @param input -> value to substitute x by
     * return the evaluated number
     */
    public Double evaluate(final double input) {
        double value = 0.0;
        for(PolynomialTerm term : this.terms) {
            value += term.evaluate(input);
        }
        return value;
    }

    public boolean isZeroFunction() {
        for (PolynomialTerm term : terms) {
            if (term.getCoefficient() != 0.0) return false;
        }
        return true;
    }

    /*
     * returns a String representation of the polynomial expression
     */
    public String toString() {
        if (isZeroFunction() || this.terms.isEmpty()) return "0.0";
        StringBuilder rep = new StringBuilder();
        int i;
        rep.append(this.funcName).append("(").append(this.varName).append(") = ");
        for (i = 0; i < terms.size() - 1; i++) {
            if(terms.get(i).getCoefficient() == 0) {
                continue;
            }
            rep.append(terms.get(i).toString()).append(" + ");
        }
        if (terms.get(i).getCoefficient() != 0) {
            rep.append(terms.get(i).toString());
        } else {
            rep.replace(rep.length() - 3, rep.length(), "");
        }
        String result = rep.toString();
        result = result.replace("+ -", "-").replace("+ +", "+");
        return result;
    }
}
