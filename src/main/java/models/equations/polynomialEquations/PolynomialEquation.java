package models.equations.polynomialEquations;

import lombok.Getter;
import models.equations.Equation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;

import java.util.stream.Stream;

/**
 * Class representing a polynomial equation.
 */
@Getter
public class PolynomialEquation implements Equation {

    private final PolynomialFunction leftSide;
    private final PolynomialFunction rightSide;
    private final int degree;

    public PolynomialEquation(final PolynomialFunction left, final PolynomialFunction right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }
        this.leftSide = left;
        this.rightSide = right;
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
    public PolynomialFunction leftSide() {
        return this.leftSide;
    }

    @Override
    public PolynomialFunction rightSide() {
        return this.rightSide;
    }

    @Override
    public Double[] solve() {
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
