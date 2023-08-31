package models.equations.polynomialEquations;

import models.equations.Equation;
import models.functions.Function;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class representing a quadratic equation.
 */
public class QuadraticEquation extends PolynomialEquation {

    public QuadraticEquation(final PolynomialFunction leftSide, final PolynomialFunction rightSide) {
        super(leftSide, rightSide);

        int leftSideDegree = 0;
        if (!leftSide.getTerms().isEmpty()) {
            leftSideDegree = Collections.max(leftSide.getTerms()
                    .stream()
                    .map(PolynomialTerm::getExponent)
                    .collect(Collectors.toList()));
        }

        int rightSideDegree = 0;
        if (!rightSide.getTerms().isEmpty()) {
            rightSideDegree = Collections.max(rightSide.getTerms()
                    .stream()
                    .map(PolynomialTerm::getExponent)
                    .collect(Collectors.toList()));
        }

        if (leftSideDegree > 2 || rightSideDegree > 2) {
            throw new IllegalArgumentException("Invalid degree(s) passed.");
        }
    }

    @Override
    public Double[] solve() {
        PolynomialFunction leftSideCopy = this.getLeftSide().deepCopy();
        PolynomialFunction rightSideCopy = this.getRightSide().deepCopy();
        QuadraticEquation equationCopy = new QuadraticEquation(leftSideCopy, rightSideCopy);

        System.out.println("Starting to solve quadratic equation: " + equationCopy);
        System.out.println("Zeroing right side...");

        while (!rightSideCopy.getTerms().isEmpty()) {
            System.out.println("Current equation: " + equationCopy);
            PolynomialTerm term = rightSideCopy.getTerms().get(0);
            PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
            leftSideCopy.addTerm(subtractedTerm);
            rightSideCopy.addTerm(subtractedTerm);
        }
        System.out.println("Current equation: " + equationCopy);

        final Optional<PolynomialTerm> deg2  = leftSideCopy.getTerms().stream().filter(term -> term.getExponent() == 2).findFirst();
        final double a = deg2.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final Optional<PolynomialTerm> deg1 = leftSideCopy.getTerms().stream().filter(term -> term.getExponent() == 1).findFirst();
        final double b = deg1.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final Optional<PolynomialTerm> deg0 = leftSideCopy.getTerms().stream().filter(term -> term.getExponent() == 0).findFirst();
        final double c = deg0.map(PolynomialTerm::getCoefficient).orElse(0.0);

        final double discriminant = Math.pow(b, 2) - 4 * a * c;

        if (discriminant > 0) {
            final double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            final double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return new Double[]{x1, x2};
        } else if (discriminant == 0) {
            final double x = -b / (2 * a);
            return new Double[]{x};
        }

        return null;
    }

    public String toString() {
        return this.getLeftSide().printBody() + " = " + this.getRightSide().printBody();
    }
}
