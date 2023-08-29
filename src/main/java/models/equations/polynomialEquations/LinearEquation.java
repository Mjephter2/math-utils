package models.equations.polynomialEquations;

import models.equations.Equation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;

/**
 * Class representing a linear equation.
 */
public class LinearEquation implements Equation {

    /**
     * Left side of the equation.
     */
    private final PolynomialFunction leftSide;

    /**
     * Right side of the equation.
     */
    private final PolynomialFunction rightSide;

    public LinearEquation(final PolynomialFunction leftSide, final PolynomialFunction rightSide) {

        if (leftSide == null || rightSide == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }

        if (leftSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1) ||
                rightSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1)) {
            throw new IllegalArgumentException("Invalid degree(s) passed.");
        }

        this.leftSide = leftSide;
        this.rightSide = rightSide;
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
        PolynomialFunction leftSideCopy = this.leftSide.deepCopy();
        PolynomialFunction rightSideCopy = this.rightSide.deepCopy();
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

        if (leftCoefficient == 0.0) {
            return null;
        }

        return new Double[] { rightCoefficient / leftCoefficient };
    }

    public String toString() {
        return this.leftSide.printBody() + " = " + this.rightSide.printBody();
    }
}
