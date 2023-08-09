package models.functions;

import com.google.common.collect.Range;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Class implementing a Complex function.
 * By Complex function we mean a function that is the sum of several functions.
 */
@Getter
@AllArgsConstructor
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
    public Function derivative() {
        // TODO: implement
        return null;
    }

    @Override
    public Function integral() {
        // TODO: implement
        return null;
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: implement
        return 0;
    }
}
