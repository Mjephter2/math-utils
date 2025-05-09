package calculus.univariate.models.functions.logarithmic;

import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.FunctionType;
import calculus.univariate.models.functions.combinations.CompositeFunction;
import calculus.univariate.models.functions.combinations.RationalFunction;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.inequalities.GeneralInequality;
import calculus.univariate.models.inequalities.InequalityType;
import calculus.univariate.models.numberUtils.Range;

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
    private boolean isIndefiniteIntegral;

    public static Function defaultFunction() {
        return LogFunction.builder()
                .funcName("DefaultLogFunction")
                .varName("x")
                .base(Math.E)
                .body(PolynomialFunction.defaultFunc())
                .build();
    }

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
        if (this.body instanceof PolynomialFunction) {
            final PolynomialFunction numerator = (PolynomialFunction) this.body.derivative();
            final CompositeFunction denominator = new CompositeFunction(
                    "denom",
                    List.of(
                            ConstantFunction.builder().value(Math.log(this.base)).build(),
                            this.body.deepCopy(this.funcName + "bodyCopy")
                    )
            );
            return RationalFunction.builder()
                    .numerator(numerator)
                    .denominator(denominator)
                    .funcName(this.funcName + "'")
                    .varName(this.varName)
                    .build();
        }
        // TODO: Implement LogFunction derivative for other cases
        throw new UnsupportedOperationException("LogFunction Derivative is not implemented for this use case");
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
        return Math.log(body.limit(value)) / Math.log(base);
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

    @Override
    public double getMaxValue() {
        return Math.log(body.getMaxValue()) / Math.log(base);
    }

    @Override
    public double getMaxValue(Range range) {
        final Range overlap = range.intersection(this.getDomain().get(0));
        double maxValue = Double.NEGATIVE_INFINITY;
        for (double i = overlap.getLowerBound(); i <= overlap.getUpperBound(); i += 0.01) {
            final double value = this.evaluate(i);
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    @Override
    public double getMinValue() {
        return Math.log(body.getMinValue()) / Math.log(base);
    }

    @Override
    public double getMinValue(Range range) {
        final Range overlap = range.intersection(this.getDomain().get(0));
        double minValue = Double.POSITIVE_INFINITY;
        for (double i = overlap.getLowerBound(); i <= overlap.getUpperBound(); i += 0.01) {
            final double value = this.evaluate(i);
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }
}
