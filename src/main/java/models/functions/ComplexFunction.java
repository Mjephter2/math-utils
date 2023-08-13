package models.functions;

import com.google.common.collect.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String getFuncName() {
        return this.funcName;
    }

    @Override
    public String getVarName() {
        return this.varName;
    }

    @Override
    public Range<Double> getDomain() {
        return functions.stream()
                .map(CompositeFunction::getDomain)
                .reduce(Range::intersection)
                .orElse(null);
    }

    @Override
    public Range<Double> getRange() {
        // TODO: implement
        return null;
    }

    @Override
    public double evaluate(Double... values) {
        return functions.stream()
                .mapToDouble(func -> func.evaluate(values))
                .sum();
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
}
