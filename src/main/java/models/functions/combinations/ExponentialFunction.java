package models.functions.combinations;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;
import models.functions.Function;
import models.functions.FunctionType;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import utils.NumberUtils;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public FunctionType getFuncType() {
        return FunctionType.EXPONENTIAL;
    }

    @Override
    public Range<Double> getDomain() {
        return Range.closedOpen(0.0, Double.POSITIVE_INFINITY);
    }

    @Override
    public Range<Double> getRange() {
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return Math.pow(base, exponent.evaluate(values));
    }

    @Override
    // This implementation assumes that the base is a constant
    public Function derivative() {
        return new CompositeFunction(this.funcName, Stream.of(
                PolynomialFunction.builder()
                        .terms(new LinkedList<>() {{
                            add(PolynomialTerm.builder()
                                    .varName(exponent.getVarName())
                                    .coefficient(Math.log(base))
                                    .exponent(0)
                                    .build());
                        }})
                        .funcName(this.funcName)
                        .varName(this.varName)
                        .build(),
                exponent.derivative(),
                this.deepCopy()
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
    public Function deepCopy() {
        return ExponentialFunction.builder()
                .funcName(this.funcName)
                .varName(this.varName)
                .base(this.base)
                .exponent(this.exponent.deepCopy())
                .build();
    }

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof ExponentialFunction) {
            final ExponentialFunction otherExp = (ExponentialFunction) other;
            return this.base == otherExp.base && this.exponent.equals(otherExp.exponent);
        }
        return false;
    }
}
