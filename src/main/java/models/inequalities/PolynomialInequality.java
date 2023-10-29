package models.inequalities;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;
import models.functions.polynomials.PolynomialFunction;

import java.util.List;

@Getter
@Builder
class PolynomialInequality implements Inequality {

    private PolynomialFunction leftSide;
    private PolynomialFunction rightSide;
    private InequalityType type;

    @Override
    public List<Range<Double>> solve() {
        // If both sides are linear equations, solve as a linear inequality
        if (leftSide.getDegree() <= 1 && rightSide.getDegree() <= 1) {
            return new LinearInequality(this.type, this.leftSide, this.rightSide).solve();
        }

        // TODO: Implement solving for polynomial inequalities

        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }
}
