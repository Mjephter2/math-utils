package models.equations;

import models.functions.Function;
import models.functions.PolynomialFunction;
import models.functions.PolynomialTerm;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class representing a linear equation.
 */
public class LinearEquation implements Equation {

    private PolynomialFunction leftSide;
    private PolynomialFunction rightSide;

    public LinearEquation(final PolynomialFunction leftSide, final PolynomialFunction rightSide) {

        if (leftSide == null || rightSide == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }

        final int leftSideDegree = Collections.max(leftSide.getTerms()
                .stream()
                .map(PolynomialTerm::getExponent)
                .collect(Collectors.toList()));

        final int rightSideDegree = Collections.max(rightSide.getTerms()
                .stream()
                .map(PolynomialTerm::getExponent)
                .collect(Collectors.toList()));

        if (leftSideDegree != 1 || rightSideDegree != 1) {
            throw new IllegalArgumentException("Invalid degree(s) passed.");
        }

        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public Function leftSide() {
        return this.leftSide;
    }

    @Override
    public Function rightSide() {
        return this.rightSide;
    }

    @Override
    public double solve() {
        // TODO: Implement this method.
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

        final double leftCoefficient = leftSideCopy.getTerms().get(0).getCoefficient();
        final double rightCoefficient = rightSideCopy.getTerms().get(0).getCoefficient();

        return rightCoefficient / leftCoefficient;
    }

    public String toString() {
        return this.leftSide.printBody() + " = " + this.rightSide.printBody();
    }
}
