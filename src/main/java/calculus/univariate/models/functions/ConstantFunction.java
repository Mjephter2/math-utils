package calculus.univariate.models.functions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import calculus.univariate.models.numberUtils.Range;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Class implementing a Constant function
 */
@AllArgsConstructor
@Builder
@Getter
public class ConstantFunction implements Function {

    private String funcName;
    private double value;
    private boolean isIndefiniteIntegral;


    @Override
    public String getVarName() {
        return null;
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.CONSTANT;
    }

    @Override
    public List<Range> getDomain() {
        return List.of(Range.all());
    }

    @Override
    public List<Range> getRange() {
        return List.of(Range.singleton(value));
    }

    @Override
    public double evaluate(Double... values) {
        return this.value;
    }

    /**
     * Does nothing, since a ConstantFunction is already in its simplest form
     * @return the current function
     */
    @Override
    public Function simplify() {
        return this;
    }

    @Override
    public Function derivative() {
        return ConstantFunction.builder()
                .funcName(this.funcName + "'")
                .value(0.0)
                .build();
    }

    @Override
    public Function derivative(String variable) {
        return derivative();
    }

    public static Function defaultFunc() {
        return ConstantFunction.builder()
                .funcName("DefaultConstantFunction")
                .value(1)
                .isIndefiniteIntegral(false)
                .build();
    }

    public static Function zeroFunction() {
        return ConstantFunction.builder()
                .funcName("ZeroFunction")
                .value(0)
                .isIndefiniteIntegral(false)
                .build();
    }

    // This method should never be called as a ConstantFunction does not have an explicit independent variable
    @Override
    public Function integral() {
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    /**
     * Returns the integral of the function with respect to the given variable
     *
     * @param integralVarName the name of the variable with respect to which the integral is taken
     * @return the integral of the function with respect to the given variable
     */
    public Function integral(final String integralVarName) {
        return new PolynomialFunction(
                new LinkedList<>() {{ add(new PolynomialTerm(value, integralVarName, 1)); }},
                "∫" + this.funcName,
                integralVarName);
    }

    @Override
    public double integral(final double lowerBound, final double upperBound) {
        final Function indefiniteIntegral = this.integral("x");
        return indefiniteIntegral.evaluate(upperBound) - indefiniteIntegral.evaluate(lowerBound);
    }

    @Override
    public double limit(final double value) {
        return this.value;
    }

    @Override
    public String printBody() {
        return new DecimalFormat("#.##").format(this.value);
    }

    @Override
    public String toString() {
        return this.funcName + "()" + " = " + this.printBody();
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return ConstantFunction.builder()
                .funcName(newFuncName)
                .value(this.value)
                .build();
    }

    /**
     * Checks if the given ConstantFunction is equal to the current one.
     * @param other -> the other ConstantFunction
     * @return true if the two functions are equal, false otherwise
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof ConstantFunction) {
            final ConstantFunction otherConstantFunction = (ConstantFunction) other;
            return this.value == otherConstantFunction.value;
        }
        return false;
    }

    @Override
    public double getMaxValue() {
        return this.value;
    }

    @Override
    public double getMaxValue(Range range) {
        return this.value;
    }

    @Override
    public double getMinValue() {
        return this.value;
    }

    @Override
    public double getMinValue(Range range) {
        return this.value;
    }
}
