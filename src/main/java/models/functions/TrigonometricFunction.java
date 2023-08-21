package models.functions;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static models.functions.TrigonometricFunctionType.COSINE;
import static models.functions.TrigonometricFunctionType.SINE;

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

    @Override
    public FunctionType getFuncType() {
        return FunctionType.TRIGONOMETRIC;
    }

    @Override
    public Range<Double> getDomain() {
        return this.innerFunction.getDomain();
    }

    @Override
    public Range<Double> getRange() {
        return null;
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
    // Only implemented for cosine and sine
    public Function derivative() {
        if (this.trigonometricFunctionType == COSINE) {
            return new CompositeFunction(this.funcName, Stream.of(
                    PolynomialFunction.builder()
                            .terms(new LinkedList<>() {{
                                add(PolynomialTerm.builder()
                                        .varName(innerFunction.getVarName())
                                        .coefficient(-1)
                                        .exponent(1)
                                        .build());
                            }})
                            .funcName(this.funcName)
                            .varName(this.varName)
                            .build(),
                    innerFunction.derivative(),
                    innerFunction.deepCopy()
            ).collect(Collectors.toList()));
        } else if (this.trigonometricFunctionType == SINE) {
            return new CompositeFunction(this.funcName, Stream.of(
                    innerFunction.derivative(),
                    innerFunction.deepCopy()
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
    public Function deepCopy() {
        return TrigonometricFunction.builder()
                .funcName(this.funcName)
                .varName(this.varName)
                .trigonometricFunctionType(this.trigonometricFunctionType)
                .innerFunction(this.innerFunction.deepCopy())
                .build();
    }
}
