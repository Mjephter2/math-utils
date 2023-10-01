package models.inequalities;

import com.google.common.collect.Range;

import lombok.Builder;
import lombok.Getter;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;

import java.util.Collections;
import java.util.List;


/**
 * Class representing an inequality
 */
@Getter
@Builder
public class LinearInequality implements Inequality {

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
    @Override
    public List<Range<Double>> solve() {
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

        // Case 1: left side is zero, right side is zero and inequality is not strict
        if (
                leftSideCopy.getTerms().isEmpty() &&
                rightSideCopy.getTerms().isEmpty() &&
                (type == InequalityType.LESS_THAN_OR_EQUAL_TO || type == InequalityType.GREATER_THAN_OR_EQUAL_TO)
        ) {
            return Collections.singletonList(Range.all());
        // Case 2: left side is zero, right side is zero and inequality is strict
        } else if (
                leftSideCopy.getTerms().isEmpty() &&
                rightSideCopy.getTerms().isEmpty() &&
                (type == InequalityType.LESS_THAN || type == InequalityType.GREATER_THAN)
        ) {
            return Collections.emptyList();
        // Case 3: left side is zero, right side is negative and inequality type is less than or less than or equal to
        } else if (leftSideCopy.getTerms().isEmpty() &&
                rightSideCopy.getTerms().get(0).getCoefficient() < 0.0 &&
                (type == InequalityType.LESS_THAN || type == InequalityType.LESS_THAN_OR_EQUAL_TO)
            ) {
            return Collections.singletonList(Range.all());
        // Case 4: left side is zero, right side is negative and inequality type is greater than or greater than or equal to
        } else if (leftSideCopy.getTerms().isEmpty() &&
                rightSideCopy.getTerms().get(0).getCoefficient() < 0.0 &&
                (type == InequalityType.GREATER_THAN || type == InequalityType.GREATER_THAN_OR_EQUAL_TO)
            ) {
            return Collections.emptyList();
        // Case 5: left side is zero, right side is positive and inequality type is less than or less than or equal to
        } else if (leftSideCopy.getTerms().isEmpty() &&
                rightSideCopy.getTerms().get(0).getCoefficient() > 0.0 &&
                (type == InequalityType.LESS_THAN || type == InequalityType.LESS_THAN_OR_EQUAL_TO)
            ) {
            return Collections.singletonList(Range.all());
        // Case 6: left side is zero, right side is positive and inequality type is greater than or greater than or equal to
        } else if (leftSideCopy.getTerms().isEmpty() &&
                rightSideCopy.getTerms().get(0).getCoefficient() > 0.0 &&
                (type == InequalityType.GREATER_THAN || type == InequalityType.GREATER_THAN_OR_EQUAL_TO)
            ) {
            return Collections.emptyList();
        }

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

        if (linearInequality.type == InequalityType.LESS_THAN) {
            return Collections.singletonList(Range.lessThan(rightSideCopy.getTerms().get(0).getCoefficient()));
        } else if (linearInequality.type == InequalityType.LESS_THAN_OR_EQUAL_TO) {
            return Collections.singletonList(Range.atMost(rightSideCopy.getTerms().get(0).getCoefficient()));
        } else if (linearInequality.type == InequalityType.GREATER_THAN) {
            return Collections.singletonList(Range.greaterThan(rightSideCopy.getTerms().get(0).getCoefficient()));
        } else if (linearInequality.type == InequalityType.GREATER_THAN_OR_EQUAL_TO) {
            return Collections.singletonList(Range.atLeast(rightSideCopy.getTerms().get(0).getCoefficient()));
        } else if (linearInequality.type == InequalityType.EQUAL_TO) {
            return Collections.singletonList(Range.singleton(rightSideCopy.getTerms().get(0).getCoefficient()));
        }

        return null;
    }

    public String toString() {
        return this.leftSide.printBody() + " " +  this.type.getSymbol() + " " + this.rightSide.printBody();
    }
}
