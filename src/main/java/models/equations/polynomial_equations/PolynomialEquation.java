package models.equations.polynomial_equations;

import lombok.Getter;
import models.equations.Equation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;

import java.util.List;
import java.util.stream.Stream;

/**
 * Class representing a polynomial equation.
 */
@Getter
public class PolynomialEquation implements Equation {

    private final PolynomialFunction leftSide;
    private final PolynomialFunction rightSide;
    private int degree;

    /**
     * Constructs a PolynomialEquation with provided left and right side expressions.
     * Automatically reduces the right side to zero.
     * @param left - left side of the equation
     * @param right - right side of the equation
     */
    public PolynomialEquation(final PolynomialFunction left, final PolynomialFunction right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }
        this.leftSide = left;
        this.rightSide = right;

        this.reduce();
    }

    /**
     * Reduce the RHS to zero
     */
    protected void reduce() {
        while (!this.rightSide.getTerms().isEmpty()) {
            PolynomialTerm term = this.rightSide.getTerms().get(0);
            PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
            this.leftSide.addTerm(subtractedTerm);
            this.rightSide.addTerm(subtractedTerm);
        }
        this.degree = computeDegree();
    }

    /**
     * Computes the degree of the polynomial equation.
     */
    private int computeDegree() {
        return Stream.of(this.leftSide.getTerms(), this.rightSide.getTerms())
                .map(terms -> terms.stream()
                        .mapToInt(PolynomialTerm::getExponent)
                        .max()
                        .orElse(0))
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public List<Range> solve() {
        // TODO: Implement solver for more polynomial equations degrees
        if (this.degree == 1) {
            return new LinearEquation(this.leftSide, this.rightSide).solve();
        } else if (this.degree == 2) {
            return new QuadraticEquation(this.leftSide, this.rightSide).solve();
        } else {
            throw new UnsupportedOperationException("Unimplemented solver for polynomial equations of degree " + this.degree + ".");
        }
    }
}
