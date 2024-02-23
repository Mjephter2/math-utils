package models.functions.trigonometric;

import lombok.Builder;
import lombok.Getter;
import models.functions.ConstantFunction;
import models.functions.combinations.CompositeFunction;
import models.functions.Function;
import models.functions.FunctionType;
import models.numberUtils.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static models.functions.trigonometric.TrigonometricFunctionType.COSINE;
import static models.functions.trigonometric.TrigonometricFunctionType.SINE;

/**
 * Represents a trigonometric function
 */
@Builder
@Getter
public class TrigonometricFunction implements Function {

    private final String funcName;
    private final String varName;
    private final TrigonometricFunctionType trigonometricFunctionType;
    private final Function innerFunction;
    private boolean isIndefiniteIntegral;

    @Override
    public FunctionType getFuncType() {
        return FunctionType.TRIGONOMETRIC;
    }

    @Override
    public List<Range> getDomain() {
        return this.innerFunction.getDomain();
    }

    @Override
    public List<Range> getRange() {
        // TODO: Implement getRange for TrigonometricFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        switch (this.trigonometricFunctionType) {
            case COSINE:
                return Math.cos(this.innerFunction.evaluate(values));
            case SINE:
                return Math.sin(this.innerFunction.evaluate(values));
            case TANGENT:
                return Math.tan(this.innerFunction.evaluate(values));
            case SECANT:
                return 1 / Math.cos(this.innerFunction.evaluate(values));
            case COSECANT:
                return 1 / Math.sin(this.innerFunction.evaluate(values));
            case COTANGENT:
                return 1 / Math.tan(this.innerFunction.evaluate(values));
            default:
                throw new IllegalArgumentException("No trigonometric function type with name " + this.trigonometricFunctionType);
        }
    }

    @Override
    public Function simplify() {
        if (this.innerFunction.getFuncType() == FunctionType.CONSTANT) {
            return ConstantFunction.builder()
                    .funcName(this.funcName)
                    .value(this.evaluate())
                    .build();
        }
        return TrigonometricFunction.builder()
                .funcName(this.funcName)
                .varName(this.varName)
                .trigonometricFunctionType(this.trigonometricFunctionType)
                .innerFunction(this.innerFunction.simplify())
                .build();
    }

    @Override
    // Only implemented for cosine and sine
    public Function derivative() {
        if (this.trigonometricFunctionType == COSINE) {
            return new CompositeFunction(this.funcName + "'", Stream.of(
                    new PolynomialFunction(new LinkedList<>() {{
                        add(PolynomialTerm.builder()
                                .varName(innerFunction.getVarName())
                                .coefficient(-1)
                                .exponent(0)
                                .build());
                    }}, this.funcName, this.varName),
                    innerFunction.derivative(),
                    new TrigonometricFunction(this.funcName, this.varName, SINE, innerFunction.deepCopy(innerFunction.getFuncName()), false)
            ).collect(Collectors.toList()));
        } else if (this.trigonometricFunctionType == SINE) {
            return new CompositeFunction(this.funcName + "'", Stream.of(
                    innerFunction.derivative(),
                    new TrigonometricFunction(this.funcName, this.varName, COSINE, innerFunction.deepCopy(innerFunction.getFuncName()), false)
            ).collect(Collectors.toList()));
        };

        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for TrigonometricFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for TrigonometricFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        // TODO: Implement limit for TrigonometricFunction
        throw new UnsupportedOperationException("Unimplemented method 'limit'");
    }

    @Override
    public String printBody() {
        return TrigonometricFunctionType.toShortName(trigonometricFunctionType) + "(" + innerFunction.printBody() + ")";
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return TrigonometricFunction.builder()
                .funcName(newFuncName)
                .varName(this.varName)
                .trigonometricFunctionType(this.trigonometricFunctionType)
                .innerFunction(this.innerFunction.deepCopy(this.innerFunction.getFuncName()))
                .build();
    }

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof TrigonometricFunction) {
            final TrigonometricFunction otherTrigonometricFunction = (TrigonometricFunction) other;
            return this.trigonometricFunctionType.equals(otherTrigonometricFunction.trigonometricFunctionType) && this.innerFunction.equals(otherTrigonometricFunction.innerFunction);
        }
        return false;
    }

    @Override
    public double getMaxValue() {
        // TODO: Implement getMaxValue for TrigonometricFunction
        throw new UnsupportedOperationException("Unimplemented method 'getMaxValue'");
    }

    @Override
    public double getMinValue() {
        // TODO: Implement getMinValue for TrigonometricFunction
        throw new UnsupportedOperationException("Unimplemented method 'getMinValue'");
    }
}
