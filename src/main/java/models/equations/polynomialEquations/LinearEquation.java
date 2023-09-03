package models.equations.polynomialEquations;

import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;

/**
 * Class representing a linear equation.
 */
public class LinearEquation extends PolynomialEquation {

    public LinearEquation(final PolynomialFunction leftSide, final PolynomialFunction rightSide) {
        super(leftSide, rightSide);

        if (leftSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1) ||
                rightSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1)) {
            throw new IllegalArgumentException("Invalid degree(s) passed.");
        }
    }

    @Override
    public Double[] solve() {
        PolynomialFunction leftSideCopy = this.getLeftSide().deepCopy();
        PolynomialFunction rightSideCopy = this.getRightSide().deepCopy();
        LinearEquation equationCopy = new LinearEquation(leftSideCopy, rightSideCopy);

        System.out.println("Starting to solve linear equation: " + equationCopy);
        System.out.println("Reducing left side...");
        for (PolynomialTerm term : leftSideCopy.getTerms()) {
            if (term.getExponent() == 0) {
                System.out.println(equationCopy);
                PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), 0);
                leftSideCopy.addTerm(subtractedTerm);
                rightSideCopy.addTerm(subtractedTerm);
            }
        }
        System.out.println(equationCopy);

        System.out.println("Reducing right side...");
        for (PolynomialTerm term : rightSideCopy.getTerms()) {
            if (term.getExponent() > 0) {
                System.out.println(equationCopy);
                PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
                leftSideCopy.addTerm(subtractedTerm);
                rightSideCopy.addTerm(subtractedTerm);
            }
        }
        System.out.println(equationCopy);

        System.out.println("Dividing left side by coefficient of x...");

        final double leftCoefficient = !leftSideCopy.getTerms().isEmpty() ? leftSideCopy.getTerms().get(0).getCoefficient() : 0.0;
        final double rightCoefficient = !rightSideCopy.getTerms().isEmpty() ? rightSideCopy.getTerms().get(0).getCoefficient() : 0.0;

        if (leftCoefficient == 0.0 && rightCoefficient != 0.0) {
            return null;
        } else if (rightCoefficient == 0.0) {
            return new Double[] { 0.0 };
        }

        return new Double[] { rightCoefficient / leftCoefficient };
    }

    public String toString() {
        return this.getLeftSide().printBody() + " = " + this.getRightSide().printBody();
    }
}
