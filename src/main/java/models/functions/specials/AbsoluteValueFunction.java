package models.functions.specials;

import models.functions.Function;
import models.functions.FunctionType;
import models.numberUtils.Range;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * Class implementing an Absolute Value function
 */
@Getter
@Builder
public class AbsoluteValueFunction implements Function {

    private String varName;
    private String funcName;
    private Function innerFunction;

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
        // TODO: Implement getRange for AbsoluteValueFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
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
}
