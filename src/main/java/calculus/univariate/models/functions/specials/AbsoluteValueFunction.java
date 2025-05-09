package calculus.univariate.models.functions.specials;

import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.FunctionType;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.numberUtils.Range;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;

/**
 * Class implementing an Absolute Value function
 */
@Getter
@Builder
public final class AbsoluteValueFunction implements Function {

    private final String varName;
    private final String funcName;
    private final Function innerFunction;
    private final boolean isIndefiniteIntegral;

    public static Function defaultFunction() {
        return AbsoluteValueFunction.builder()
                .funcName("DefaultAbsoluteValueFunction")
                .varName("x")
                .innerFunction(PolynomialFunction.defaultFunc())
                .build();
    }

    @Override
    public String getFuncName() {
        return this.funcName;
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.ABSOLUTE_VALUE;
    }

    @Override
    public List<Range> getDomain() {
        return this.innerFunction.getDomain();
    }

    @Override
    public List<Range> getRange() {
        return this.innerFunction.getRange().stream().map(Range::abs).collect(Collectors.toList());
    }

    @Override
    public double evaluate(Double... values) {
        return Math.abs(this.innerFunction.evaluate(values));
    }

    @Override
    public Function simplify() {
        // TODO: Implement simplify for AbsoluteValueFunction
        throw new UnsupportedOperationException("Unimplemented method 'simplify' for AbsoluteValueFunction");
    }

    @Override
    public Function derivative() {
        // TODO: Implement derivative for AbsoluteValueFunction
        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for AbsoluteValueFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for AbsoluteValueFunction
        throw new UnsupportedOperationException("Unimplemented definite 'integral'");
    }

    @Override
    public double limit(double value) {
        // TODO: Implement limit for AbsoluteValueFunction
        throw new UnsupportedOperationException("Unimplemented method 'limit'");
    }

    @Override
    public String printBody() {
        return "| " + this.innerFunction.printBody() + " |";
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return AbsoluteValueFunction.builder()
                .funcName(newFuncName)
                .varName(this.varName)
                .innerFunction(this.innerFunction.deepCopy(this.innerFunction.getFuncName()))
                .build();
    }

    @Override
    public boolean equals(final Object other) {
        if (this.hashCode() == other.hashCode()) {
            return true;
        }
        if (other instanceof AbsoluteValueFunction) {
            return this.varName.equals(((AbsoluteValueFunction) other).varName) &&
                    this.innerFunction.equals(((AbsoluteValueFunction) other).innerFunction);
        }
        return false;
    }

    @Override
    public double getMaxValue() {
        return Math.max(
                Math.abs(this.getInnerFunction().getMaxValue()),
                Math.abs(this.getInnerFunction().getMinValue())
        );
    }

    @Override
    public double getMaxValue(Range range) {
        final Range overlap = range.intersection(this.getDomain().get(0));
        double maxValue = Double.MIN_VALUE;
        for (double i = overlap.getLowerBound(); i <= overlap.getUpperBound(); i += 0.01) {
            if (this.evaluate(i) > maxValue) {
                maxValue = this.evaluate(i);
            }
        }
        return maxValue;
    }

    @Override
    public double getMinValue() {
        return Math.min(
                Math.abs(this.getInnerFunction().getMaxValue()),
                Math.abs(this.getInnerFunction().getMinValue())
        );
    }

    @Override
    public double getMinValue(Range range) {
        final Range overlap = range.intersection(this.getDomain().get(0));
        double minValue = Double.MAX_VALUE;
        for (double i = overlap.getLowerBound(); i <= overlap.getUpperBound(); i += 0.01) {
            if (this.evaluate(i) < minValue) {
                minValue = this.evaluate(i);
            }
        }
        return minValue;
    }
}
