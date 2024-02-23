package models.functions.combinations;

import models.functions.Function;
import models.functions.FunctionType;
import models.numberUtils.Range;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Class implementing a Complex function.
 * By Complex function we mean a function that is the sum of several functions.
 */
@Getter
@AllArgsConstructor
@Builder
public class ComplexFunction implements Function {

    final String funcName;
    final String varName;
    final List<CompositeFunction> functions;
    private boolean isIndefiniteIntegral;

    @Override
    public String getFuncName() {
        return this.funcName;
    }

    @Override
    public String getVarName() {
        return this.varName;
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.OTHER;
    }

    @Override
    public List<Range> getDomain() {
        // TODO: Implement domain for ComplexFunction
        throw new UnsupportedOperationException("Unimplemented method 'getDomain'");
    }

    @Override
    public List<Range> getRange() {
        // TODO: Implement range for ComplexFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return functions.stream()
                .mapToDouble(func -> func.evaluate(values))
                .sum();
    }

    @Override
    public Function simplify() {
        return ComplexFunction.builder()
                .funcName(this.funcName)
                .varName(this.varName)
                .functions(this.functions.stream()
                        .map(CompositeFunction::simplify)
                        .map(CompositeFunction.class::cast)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ComplexFunction derivative() {
        return ComplexFunction.builder()
                .funcName(this.funcName)
                .varName(this.varName)
                .functions(this.functions.stream()
                        .map(CompositeFunction::derivative).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ComplexFunction integral() {
        return ComplexFunction.builder()
                .funcName(this.funcName)
                .varName(this.varName)
                .functions(this.functions.stream()
                        .map(CompositeFunction::integral).collect(Collectors.toList()))
                .build();
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        return functions.stream()
                .mapToDouble(func -> func.integral(lowerBound, upperBound))
                .sum();
    }

    @Override
    public double limit(double value) {
        return functions.stream()
                .mapToDouble(func -> func.limit(value))
                .sum();
    }

    @Override
    public String printBody() {
        return functions.stream()
                .map(CompositeFunction::printBody)
                .collect(Collectors.joining(" + "));
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return ComplexFunction.builder()
                .funcName(newFuncName)
                .varName(this.varName)
                .functions(this.functions.stream()
                        .map(func -> func.deepCopy(func.getFuncName()))
                        .map(CompositeFunction.class::cast)
                        .collect(Collectors.toList()))
                .build();
    }

    public String toString() {
        return this.printFunc();
    }

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof ComplexFunction) {
            final ComplexFunction otherComplexFunction = (ComplexFunction) other;
            return this.functions.equals(otherComplexFunction.functions);
        }
        return false;
    }

    @Override
    public double getMaxValue() {
        // TODO: Implement getMaxValue for ComplexFunction
        throw new UnsupportedOperationException("Unimplemented function 'getMaxValue'");
    }

    @Override
    public double getMinValue() {
        // TODO: Implement getMinValue for ComplexFunction
        throw new UnsupportedOperationException("Unimplemented function 'getMinValue'");
    }
}
