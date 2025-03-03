package calculus.univariate.models.functions.combinations;

import calculus.univariate.models.equations.GeneralEquation;
import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.FunctionType;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.utils.DerivativeUtils;

import calculus.univariate.models.numberUtils.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Class implementing a Rational function
 */
@Getter
@AllArgsConstructor
@Builder
public class RationalFunction implements Function {

    final Function numerator;
    final Function denominator;
    final String funcName;
    final String varName;
    private boolean isIndefiniteIntegral;

    public static Function defaultFunc() {
        return RationalFunction.builder()
                .funcName("DefaultRationalFunction")
                .numerator(ConstantFunction.defaultFunc())
                .denominator(PolynomialFunction.defaultFunc())
                .varName("x")
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
        return FunctionType.RATIONAL;
    }

    @Override
    public List<Range> getDomain() {
        final GeneralEquation equation = GeneralEquation.builder()
                .leftSide(this.denominator)
                .rightSide(ConstantFunction.builder().value(0).build())
                .build();
        return equation.getSolutions().keySet().stream().toList();
    }

    @Override
    public List<Range> getRange() {
        // TODO: Implement range for RationalFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return numerator.evaluate(values) / denominator.evaluate(values);
    }

    @Override
    public Function simplify() {
        // TODO: Implement 'simplify' for RationalFunction
        throw new UnsupportedOperationException("Unimplemented method 'simplify'");
    }

    @Override
    public Function derivative() {
        return DerivativeUtils.quotientRule(this.numerator, this.denominator);
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for RationalFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }
    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for RationalFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        // TODO: Implement limit for RationalFunction
        throw new UnsupportedOperationException("Unimplemented method 'limit'");
    }

    public String toString() {
        return this.printFunc();
    }

    @Override
    public String printBody() {
        return "( " + this.numerator.printBody() + " ) / ( " + this.denominator.printBody() + " )";
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return new RationalFunction(
                this.numerator.deepCopy(this.numerator.getFuncName()),
                this.denominator.deepCopy(this.denominator.getFuncName()),
                newFuncName,
                this.varName,
                this.isIndefiniteIntegral);
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof RationalFunction) {
            final RationalFunction otherRationalFunction = (RationalFunction) other;
            return this.numerator.equals(otherRationalFunction.numerator) && this.denominator.equals(otherRationalFunction.denominator);
        }
        return false;
    }

    @Override
    public double getMaxValue() {
        // TODO: Implement getMaxValue for RationalFunction
        throw new UnsupportedOperationException("Unimplemented function 'getMaxValue'");
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
        // TODO: Implement getMinValue for RationalFunction
        throw new UnsupportedOperationException("Unimplemented function 'getMinValue'");
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
