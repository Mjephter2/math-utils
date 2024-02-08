package models.functions.combinations;

import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.FunctionType;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;
import utils.NumberUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents an exponential function
 */
@Builder
@Getter
public class ExponentialFunction implements Function {

    private final double base;
    private final String funcName;
    private final String varName;
    private final Function exponent;
    private boolean isIndefiniteIntegral;

    @Override
    public FunctionType getFuncType() {
        return FunctionType.EXPONENTIAL;
    }

    @Override
    public List<Range> getDomain() {
        return List.of(Range.atLeast(0.0));
    }

    @Override
    public List<Range> getRange() {
        // TODO: Implement range for ExponentialFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return Math.pow(base, exponent.evaluate(values));
    }

    @Override
    public Function simplify() {
        if (this.exponent instanceof ConstantFunction) {
            return ConstantFunction.builder()
                    .funcName(this.funcName)
                    .value(Math.pow(base, exponent.evaluate()))
                    .build();
        } else {
            return ExponentialFunction.builder()
                    .funcName(this.funcName)
                    .varName(this.varName)
                    .base(this.base)
                    .exponent(this.exponent.simplify())
                    .build();
        }
    }

    @Override
    // This implementation assumes that the base is a constant
    public Function derivative() {
        return new CompositeFunction(this.funcName + "'",
                Stream.of(
                        new PolynomialFunction(new LinkedList<>() {{
                            add(PolynomialTerm.builder()
                                .varName(exponent.getVarName())
                                .coefficient(Math.log(base))
                                .exponent(0)
                            .build());
                        }}, this.funcName, this.varName),
                        exponent.derivative(),
                        this.deepCopy(this.funcName)
        ).collect(Collectors.toList()));
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for ExponentialFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for ExponentialFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        // TODO: Handle special limit cases for ExponentialFunction
        return Math.pow(base, exponent.limit(value));
    }

    @Override
    public String printBody() {
        return String.format("%s^(%s)", NumberUtils.round(base, 2), exponent.printBody());
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return ExponentialFunction.builder()
                .funcName(newFuncName)
                .varName(this.varName)
                .base(this.base)
                .exponent(this.exponent.deepCopy(this.exponent.getFuncName()))
                .build();
    }

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof ExponentialFunction) {
            final ExponentialFunction otherExp = (ExponentialFunction) other;
            return this.base == otherExp.base && this.exponent.printBody().equals(otherExp.exponent.printBody());
        }
        return false;
    }
}
