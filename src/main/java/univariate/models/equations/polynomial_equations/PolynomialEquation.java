package univariate.models.equations.polynomial_equations;

import lombok.Getter;
import univariate.models.equations.Equation;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialTerm;
import univariate.models.numberUtils.Range;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Class representing a polynomial equation.
 */
@Getter
public class PolynomialEquation implements Equation {

    private final PolynomialFunction leftSide;
    private final PolynomialFunction rightSide;
    private int degree;
    HashMap<Range, Integer> solutions;

    /**
     * Constructs a PolynomialEquation with provided left and right side expressions.
     * Automatically reduces the right side to zero.
     * @param left - left side of the equation
     * @param right - right side of the equation
     */
    public PolynomialEquation(final PolynomialFunction left, final PolynomialFunction right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }
        this.leftSide = left;
        this.rightSide = right;
        this.solutions = new HashMap<>();

        this.reduce();
    }

    /**
     * Reduce the RHS to zero
     */
    protected void reduce() {
        while (!this.rightSide.getTerms().isEmpty()) {
            PolynomialTerm term = this.rightSide.getTerms().get(0);
            PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
            this.leftSide.addTerm(subtractedTerm);
            this.rightSide.addTerm(subtractedTerm);
        }
        this.degree = computeDegree();
    }

    /**
     * Computes the degree of the polynomial equation.
     */
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
        if (this.degree == 1) {
            final LinearEquation linearEquation =  new LinearEquation(this.leftSide, this.rightSide);
            linearEquation.solve();
            this.solutions = linearEquation.getSolutions();
        } else if (this.degree == 2) {
            final QuadraticEquation quadraticEquation = new QuadraticEquation(this.leftSide, this.rightSide);
            quadraticEquation.solve();
            this.solutions = new HashMap<>(quadraticEquation.getSolutions());
        } else {
            final PolynomialEquation eq = this.zeroRightSide();
            eq.leftSide.factor();
            final HashMap<PolynomialFunction, Integer> factors = eq.getLeftSide().getFactorsToMultiplicity();
            factors.keySet().stream()
                    .filter(factor -> factor.getDegree() == 1)
                    .forEach(factor -> {
                        final LinearEquation linearEquation = new LinearEquation(factor, new PolynomialFunction(new LinkedList<>(), "irrelevant", factor.getVarName()));
                        linearEquation.solve();
                        this.solutions.put(linearEquation.getSolutions().keySet().iterator().next(), factors.get(factor));
                    });
        }
    }

    private PolynomialEquation zeroRightSide() {
        PolynomialFunction leftSideCopy = this.getLeftSide().deepCopy(this.getLeftSide().getFuncName());
        PolynomialFunction rightSideCopy = this.getRightSide().deepCopy(this.getRightSide().getFuncName());

        for (PolynomialTerm term : rightSideCopy.getTerms()) {
            PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
            leftSideCopy.addTerm(subtractedTerm);
            rightSideCopy.addTerm(subtractedTerm);
        }

        return new PolynomialEquation(leftSideCopy, rightSideCopy);
    }

    public HashMap<Double, Integer> solutionRangeToDouble() {
        final HashMap<Double, Integer> result = new HashMap<>();
        for (Range range : this.solutions.keySet()) {
            result.put(range.getLowerBound(), this.solutions.get(range));
        }
        return result;
    }
}
