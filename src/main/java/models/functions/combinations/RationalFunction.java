package models.functions.combinations;

import models.equations.GeneralEquation;
import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.FunctionType;
import utils.DerivativeUtils;

import models.numberUtils.Range;

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
                this.varName);
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof RationalFunction) {
            final RationalFunction otherRationalFunction = (RationalFunction) other;
            return this.numerator.equals(otherRationalFunction.numerator) && this.denominator.equals(otherRationalFunction.denominator);
        }
        return false;
    }
}
