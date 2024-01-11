package models.functions.logarithmic;

import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.FunctionType;
import models.functions.polynomials.PolynomialFunction;
import models.inequalities.GeneralInequality;
import models.inequalities.InequalityType;
import models.numberUtils.Range;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

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
    public List<Range> getDomain() {
        final GeneralInequality inequality = GeneralInequality.builder()
                .type(InequalityType.GREATER_THAN)
                .leftSide(body)
                .rightSide(new PolynomialFunction(new LinkedList<>(), this.funcName + "body", this.varName))
                .build();
        inequality.solve();
        return inequality.getSolution();
    }

    @Override
    public List<Range> getRange() {
        return List.of(Range.all());
    }

    @Override
    public double evaluate(Double... values) {
        return Math.log(body.evaluate(values)) / Math.log(base);
    }

    @Override
    public Function simplify() {
        if (body instanceof ConstantFunction) {
            return ConstantFunction.builder()
                    .value(Math.log(body.evaluate()) / Math.log(base))
                    .funcName(this.getFuncName())
                    .build();
        }
        return LogFunction.builder()
                .funcName(this.getFuncName())
                .varName(this.varName)
                .body(body.simplify())
                .base(base)
                .build();
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
    public Function deepCopy(final String newFuncName) {
        return LogFunction.builder()
                .body(body.deepCopy(body.getFuncName()))
                .base(base)
                .varName(varName)
                .funcName(newFuncName)
                .build();
    }

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof LogFunction) {
            final LogFunction otherLogFunction = (LogFunction) other;
            return this.base == otherLogFunction.base && this.body.equals(otherLogFunction.body);
        }
        return false;
    }
}
