package models.functions;

import com.google.common.collect.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    public Range<Double> getDomain() {
        return denominator.getDomain().intersection(numerator.getDomain());
    }

    @Override
    public Range<Double> getRange() {
        // TODO
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return numerator.evaluate(values) / denominator.evaluate(values);
    }

    @Override
    public Function derivative() {
        // TODO
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

    public String toString() {
        return this.printFunc();
    }

    @Override
    public String printBody() {
        return "( " + this.numerator.printBody() + " ) / ( " + this.denominator.printBody() + " )";
    }

    @Override
    public Function deepCopy() {
        return new RationalFunction(this.numerator.deepCopy(), this.denominator.deepCopy(), this.funcName, this.varName);
    }
}
