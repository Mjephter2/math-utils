package models.functions;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;
import org.checkerframework.checker.units.qual.C;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static models.functions.TrigonometricFunctionType.COSINE;
import static models.functions.TrigonometricFunctionType.SINE;
import static models.functions.TrigonometricFunctionType.TANGENT;
import static models.functions.TrigonometricFunctionType.COTANGENT;
import static models.functions.TrigonometricFunctionType.SECANT;
import static models.functions.TrigonometricFunctionType.COSECANT;

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
    public Function derivative() {
        // TODO: Implement
        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public Function integral() {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'limit'");
    }

    @Override
    public String printBody() {
        return TrigonometricFunctionType.toShortName(trigonometricFunctionType) + "(" + innerFunction.printBody() + ")";
    }
}
