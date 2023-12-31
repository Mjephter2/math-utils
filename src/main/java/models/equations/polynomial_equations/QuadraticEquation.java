package models.equations.polynomial_equations;

import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class representing a quadratic equation.
 */
public class QuadraticEquation extends PolynomialEquation {

    public QuadraticEquation(final PolynomialFunction leftSide, final PolynomialFunction rightSide) {
        super(leftSide, rightSide);

        if (this.getDegree() > 2) {
            throw new IllegalArgumentException("Invalid degree(s) passed.");
        }
    }

    @Override
    public List<Range> solve() {
        PolynomialFunction leftSideCopy = this.getLeftSide().deepCopy(this.getLeftSide().getFuncName());
        PolynomialFunction rightSideCopy = this.getRightSide().deepCopy(this.getRightSide().getFuncName());

        final Optional<PolynomialTerm> deg2  = leftSideCopy.getTerms().stream().filter(term -> term.getExponent() == 2).findFirst();
        // In case the quadratic equation reduces to a linear equation
        if (deg2.isEmpty()) {
            return new LinearEquation(leftSideCopy, rightSideCopy).solve();
        }
        final double a = deg2.map(PolynomialTerm::getCoefficient).orElse(0.0);


        final Optional<PolynomialTerm> deg1 = leftSideCopy.getTerms().stream().filter(term -> term.getExponent() == 1).findFirst();
        final double b = deg1.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final Optional<PolynomialTerm> deg0 = leftSideCopy.getTerms().stream().filter(term -> term.getExponent() == 0).findFirst();
        final double c = deg0.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final double discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant > 0) {
            final double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            final double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return List.of(Range.singleton(x1), Range.singleton(x2));
        } else if (discriminant == 0) {
            final double x = -b / (2 * a);
            return List.of(Range.singleton(x));
        }

        return null;
    }

    public String toString() {
        return this.getLeftSide().printBody() + " = " + this.getRightSide().printBody();
    }
}
