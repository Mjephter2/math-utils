package models.inequalities;

import com.google.common.collect.Range;

import lombok.Builder;
import lombok.Getter;
import models.equations.LinearEquation;
import models.functions.Function;
import models.functions.PolynomialFunction;
import models.functions.PolynomialTerm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Collectors;


/**
 * Class representing an inequality
 */
@Getter
@Builder
public class LinearInequality {

    private InequalityType type;
    private PolynomialFunction leftSide;
    private PolynomialFunction rightSide;

    public LinearInequality(final InequalityType type, final PolynomialFunction leftSide, final PolynomialFunction rightSide) {
        if (leftSide == null || rightSide == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }

        if (leftSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1) ||
                rightSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1)) {
            throw new IllegalArgumentException("Invalid degree(s) passed.");
        }

        this.type = type;
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    /**
     * Solves the inequality
     * @return the range of values that satisfy the inequality
     */
    public Range<Double> solve() {
        PolynomialFunction leftSideCopy = this.leftSide.deepCopy();
        PolynomialFunction rightSideCopy = this.rightSide.deepCopy();
        LinearInequality linearInequality = new LinearInequality(this.type, leftSideCopy, rightSideCopy);

        System.out.println("Starting to solve linear inequality: " + linearInequality);
        System.out.println("Reducing left side...");
        for (PolynomialTerm term : leftSideCopy.getTerms()) {
            if (term.getExponent() == 0) {
                System.out.println(linearInequality);
                PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), 0);
                leftSideCopy.addTerm(subtractedTerm);
                rightSideCopy.addTerm(subtractedTerm);
            }
        }
        System.out.println(linearInequality);

        System.out.println("Reducing right side...");
        for (PolynomialTerm term : rightSideCopy.getTerms()) {
            if (term.getExponent() > 0) {
                System.out.println(linearInequality);
                PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
                leftSideCopy.addTerm(subtractedTerm);
                rightSideCopy.addTerm(subtractedTerm);
            }
        }
        System.out.println(linearInequality);

        if (leftSideCopy.getTerms().get(0).getCoefficient() < 0.0) {
            System.out.println("Dividing by negative number...");
            System.out.println(linearInequality);
            rightSideCopy.getTerms().get(0).setCoefficient(rightSideCopy.getTerms().get(0).getCoefficient() / leftSideCopy.getTerms().get(0).getCoefficient());
            leftSideCopy.getTerms().get(0).setCoefficient(1.0);
            linearInequality.type = InequalityType.reverse(linearInequality.type);
            System.out.println(linearInequality);
        } else {
            System.out.println("Dividing by positive number...");
            System.out.println(linearInequality);
            rightSideCopy.getTerms().get(0).setCoefficient(rightSideCopy.getTerms().get(0).getCoefficient() / leftSideCopy.getTerms().get(0).getCoefficient());
            leftSideCopy.getTerms().get(0).setCoefficient(1.0);
            System.out.println(linearInequality);
        }

        // TODO: Handle cases where leftSide is 0 when reduced

        if (linearInequality.type == InequalityType.LESS_THAN) {
            return Range.lessThan(rightSideCopy.getTerms().get(0).getCoefficient());
        } else if (linearInequality.type == InequalityType.LESS_THAN_OR_EQUAL_TO) {
            return Range.atMost(rightSideCopy.getTerms().get(0).getCoefficient());
        } else if (linearInequality.type == InequalityType.GREATER_THAN) {
            return Range.greaterThan(rightSideCopy.getTerms().get(0).getCoefficient());
        } else if (linearInequality.type == InequalityType.GREATER_THAN_OR_EQUAL_TO) {
            return Range.atLeast(rightSideCopy.getTerms().get(0).getCoefficient());
        } else if (linearInequality.type == InequalityType.EQUAL_TO) {
            return Range.singleton(rightSideCopy.getTerms().get(0).getCoefficient());
        }

        return null;
    }

    public String toString() {
        return this.leftSide.printBody() + " " +  this.type.getSymbol() + " " + this.rightSide.printBody();
    }
}
