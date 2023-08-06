package models.functions;

import com.google.common.collect.Range;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import models.inequalities.InequalityType;
import models.inequalities.LinearInequality;

import java.util.Collections;

/**
 * Class implementing a Radical function.
 * For now, implementing only square root
 * TODO: Implement nth root
 */
@Getter
@Setter
@Builder
public class RadicalFunction implements Function {

    private static final String SQUARE_ROOT = "√";

    private static final double exponent = 0.5;

    private final Function function;

    public RadicalFunction(final Function function) {
        this.function = function;
    }

    @Override
    public Range<Double> getDomain() {
        return LinearInequality.builder()
                .type(InequalityType.GREATER_THAN_OR_EQUAL_TO)
                // TODO: Generalize for any kind of function
                .leftSide((PolynomialFunction) function)
                .rightSide(
                        new PolynomialFunction(
                                Collections.singletonList(new PolynomialTerm(0.0, "x", 0)),
                                ((PolynomialFunction) function).getFuncName(),
                                ((PolynomialFunction) function).getVarName())
                )
                .build().solve();
    }

    @Override
    public Range<Double> getRange() {
        return Range.atLeast(0.0);
    }

    @Override
    public double evaluate(Double... values) {
        return Math.pow(function.evaluate(values), exponent);
    }

    @Override
    public Function derivative() {
        // TODO: Implement
        return null;
    }

    @Override
    public Function integral() {
        // TODO: Implement
        return null;
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder rep = new StringBuilder(SQUARE_ROOT + "(");
        rep.append(((PolynomialFunction) function).printBody());
        rep.append(")");
        return rep.toString();
    }

    @Override
    public String getFuncName() {
        return this.function.getFuncName();
    }

    @Override
    public String getVarName() {
        return this.function.getVarName();
    }
}
