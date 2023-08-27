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
 * TODO: Implement nth root for RadicalFunction
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
                // TODO: Generalize RadicalFunction inner function to be generic Function
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
        // TODO: Implement derivative for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        return Math.pow(function.limit(value), exponent);
    }

    @Override
    public String toString() {
        return this.printFunc();
    }

    @Override
    public String printBody() {
        return SQUARE_ROOT + "(" + function.printBody() + ")";
    }

    @Override
    public Function deepCopy() {
        return new RadicalFunction(function.deepCopy());
    }

    @Override
    public String getFuncName() {
        return this.function.getFuncName();
    }

    @Override
    public String getVarName() {
        return this.function.getVarName();
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.RADICAL;
    }

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof RadicalFunction) {
            RadicalFunction otherRadical = (RadicalFunction) other;
            return this.function.equals(otherRadical.function);
        }
        return false;
    }
}
