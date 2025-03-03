package calculus.univariate.models.equations.polynomial_equations;

import lombok.Getter;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.numberUtils.Range;

import java.util.HashMap;

/**
 * Class representing a linear equation.
 */
@Getter
public class LinearEquation extends PolynomialEquation {

    private HashMap<Range, Integer> solutions;

    public LinearEquation(final PolynomialFunction leftSide, final PolynomialFunction rightSide) {
        super(leftSide, rightSide);

        if (leftSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1) ||
                rightSide.getTerms().stream().anyMatch(term -> term.getExponent() > 1)) {
            throw new IllegalArgumentException("Invalid degree(s) passed.");
        }
    }

    @Override
    public void solve() {
        PolynomialFunction leftSideCopy = this.getLeftSide().deepCopy(this.getLeftSide().getFuncName());
        PolynomialFunction rightSideCopy = this.getRightSide().deepCopy(this.getRightSide().getFuncName());

        for (PolynomialTerm term : leftSideCopy.getTerms()) {
            if (term.getExponent() == 0) {
                PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), 0);
                leftSideCopy.addTerm(subtractedTerm);
                rightSideCopy.addTerm(subtractedTerm);
            }
        }

        for (PolynomialTerm term : rightSideCopy.getTerms()) {
            if (term.getExponent() > 0) {
                PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
                leftSideCopy.addTerm(subtractedTerm);
                rightSideCopy.addTerm(subtractedTerm);
            }
        }

        final double leftCoefficient = !leftSideCopy.getTerms().isEmpty() ? leftSideCopy.getTerms().get(0).getCoefficient() : 0.0;
        final double rightCoefficient = !rightSideCopy.getTerms().isEmpty() ? rightSideCopy.getTerms().get(0).getCoefficient() : 0.0;

        if (leftCoefficient == 0.0 && rightCoefficient != 0.0) {
            return;
        } else if (leftCoefficient == 0.0 && rightCoefficient == 0.0) {
            this.solutions = new HashMap<>(){{
                put(Range.all(), -1);
            }};
            return;
        } else if (rightCoefficient == 0.0) {
            this.solutions = new HashMap<>(){{
                put(Range.singleton(0.0), 1);
            }};
            return;
        }

        this.solutions = new HashMap<>(){{
            put(Range.singleton(rightCoefficient / leftCoefficient), 1);
        }};
    }

    public String toString() {
        return this.getLeftSide().printBody() + " = " + this.getRightSide().printBody();
    }
}
