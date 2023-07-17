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
        return 0;
    }

    public String toString() {
        return this.leftSide.printBody() + " = " + this.rightSide.printBody();
    }
}
