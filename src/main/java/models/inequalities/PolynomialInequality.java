package models.inequalities;

import lombok.Getter;
import models.equations.polynomial_equations.PolynomialEquation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class PolynomialInequality implements Inequality {

    private PolynomialFunction leftSide;
    private PolynomialFunction rightSide;
    private InequalityType type;
    private int degree;
    private List<Range> solution;

    public PolynomialInequality(final InequalityType type, final PolynomialFunction leftSide, final PolynomialFunction rightSide) {
        if (leftSide == null || rightSide == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }

        this.type = type;
        this.leftSide = leftSide;
        this.rightSide = rightSide;

        this.reduce();
    }

    protected void reduce() {
        while (!this.rightSide.getTerms().isEmpty()) {
            PolynomialTerm term = this.rightSide.getTerms().get(0);
            PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
            this.leftSide.addTerm(subtractedTerm);
            this.rightSide.addTerm(subtractedTerm);
        }
        this.degree = computeDegree();
    }

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
    public void solve() {
        // This may be redundant
        this.reduce();

        // If both sides are linear equations, solve as a linear inequality
        if (leftSide.getDegree() <= 1 && rightSide.getDegree() <= 1) {
            final LinearInequality linearInequality = new LinearInequality(this.type, this.leftSide, this.rightSide);
            linearInequality.solve();
            this.solution = linearInequality.getSolution();
            return;
        }

        final PolynomialFunction reducedLeftSide = this.leftSide.deepCopy(this.leftSide.getFuncName() + "_reduced");
        reducedLeftSide.factor();

        // the only relevant factors are those with degree 1
        List<PolynomialFunction> relevantFactors = new LinkedList<>();
        for (PolynomialFunction factor : reducedLeftSide.getFactorsToMultiplicity().keySet()) {
            if (factor.getDegree() == 1 && reducedLeftSide.getFactorsToMultiplicity().get(factor) % 2 == 1) {
                relevantFactors.add(factor);
            }
        }

        // In there are no factor with degree 1 and odd multiplicity
        if (relevantFactors.isEmpty()) {
            relevantFactors = reducedLeftSide.getFactorsToMultiplicity().keySet().stream().filter(
                    factor -> factor.getDegree() == 1 && reducedLeftSide.getFactorsToMultiplicity().get(factor) % 2 == 0
            ).toList();
        }

        final List<Double> criticalPoints = new LinkedList<>();
        for (PolynomialFunction factor: relevantFactors) {
            final PolynomialEquation eq = new PolynomialEquation(factor, new PolynomialFunction(new LinkedList<>(), "0", "x", false));
            eq.solve();
            criticalPoints.addAll(eq.getSolutions().keySet().stream().map(Range::getLowerBound).toList());
        }
        Collections.sort(criticalPoints);

        final List<Range> criticalRanges = criticalPtsToRanges(criticalPoints);
        this.solution = criticalRanges.stream().filter(
                range ->  {
                    Double testValue = ((range.getLowerBound().isInfinite() ? (range.getUpperBound() - 10) : range.getLowerBound())
                            + (range.getUpperBound().isInfinite() ? (range.getLowerBound() + 10) : range.getUpperBound())) / 2.0;
                    Double eval = reducedLeftSide.evaluate(testValue);
                    if (eval <= 0) {
                        return this.type == InequalityType.LESS_THAN || this.type == InequalityType.LESS_THAN_OR_EQUAL_TO;
                    } else if (eval >= 0) {
                        return this.type == InequalityType.GREATER_THAN || this.type == InequalityType.GREATER_THAN_OR_EQUAL_TO;
                    }
                    return false;
                }
        ).collect(Collectors.toList());
    }

    public List<Range> criticalPtsToRanges(List<Double> criticalPoints) {
        List<Range> ranges = new LinkedList<>();

        final boolean includeBounds = this.type == InequalityType.LESS_THAN_OR_EQUAL_TO || this.type == InequalityType.GREATER_THAN_OR_EQUAL_TO;

        if (criticalPoints.size() > 0) {
            ranges.add(new Range(Double.NEGATIVE_INFINITY, criticalPoints.get(0), false, includeBounds));
            ranges.add(new Range(criticalPoints.get(criticalPoints.size() - 1), Double.POSITIVE_INFINITY, includeBounds, false));
        }

        for (int i = 0; i < criticalPoints.size() - 1; i++) {
            ranges.add(new Range(criticalPoints.get(i), criticalPoints.get(i + 1), includeBounds, includeBounds));
        }
        return ranges;
    }
}
