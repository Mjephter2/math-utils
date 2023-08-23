package models.functions;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;

@Getter
@Builder
public class LogFunction implements Function {

    private String funcName;
    private String varName;
    private Function body;
    private double base;

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
        return FunctionType.LOGARITHMIC;
    }

    @Override
    public Range<Double> getDomain() {
        // TODO: Implement domain for LogFunction
        throw new UnsupportedOperationException("Unimplemented method 'getDomain'");
    }

    @Override
    public Range<Double> getRange() {
        return Range.open(0.0, Double.POSITIVE_INFINITY);
    }

    @Override
    public double evaluate(Double... values) {
        return Math.log(body.evaluate(values)) / Math.log(base);
    }

    @Override
    public Function derivative() {
        // TODO: Implement derivative for LogFunction
        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for LogFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for LogFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        return evaluate(value);
    }

    @Override
    public String printBody() {
        return "log_" + this.base + "(" + body.printBody() + ")";
    }

    @Override
    public Function deepCopy() {
        return LogFunction.builder()
                .body(body.deepCopy())
                .base(base)
                .build();
    }
}
