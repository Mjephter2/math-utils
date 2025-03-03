package calculus.univariate.models.equations.polynomial_equations;

import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.numberUtils.Range;

import java.util.HashMap;
import java.util.Optional;

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
    public void solve() {
        final Optional<PolynomialTerm> deg2  = this.getLeftSide().getTerms().stream().filter(term -> term.getExponent() == 2).findFirst();
        // In case the quadratic equation reduces to a linear equation
        if (deg2.isEmpty()) {
            final LinearEquation linearEquation = new LinearEquation(this.getLeftSide(), this.getRightSide());
            linearEquation.solve();
            this.solutions = linearEquation.getSolutions();
        }
        final double a = deg2.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final Optional<PolynomialTerm> deg1 = this.getLeftSide().getTerms().stream().filter(term -> term.getExponent() == 1).findFirst();
        final double b = deg1.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final Optional<PolynomialTerm> deg0 = this.getLeftSide().getTerms().stream().filter(term -> term.getExponent() == 0).findFirst();
        final double c = deg0.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final double discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant > 0) {
            final double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            final double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            this.solutions = new HashMap<>() {{
                put(Range.singleton(x1), 1);
                put(Range.singleton(x2), 1);
            }};
        } else if (discriminant == 0) {
            final double x = -b / (2 * a);
            this.solutions = new HashMap<>() {{
                put(Range.singleton(x), 2);
            }};
        }
    }

    public String toString() {
        return this.getLeftSide().printBody() + " = " + this.getRightSide().printBody();
    }
}
