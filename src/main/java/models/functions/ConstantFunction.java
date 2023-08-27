package models.functions;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;

/**
 * Class implementing a Constant function
 */
@Builder
@Getter
public class ConstantFunction implements Function {

    private String funcName;
    private double value;

    @Override
    public String getFuncName() {
        return this.funcName;
    }

    @Override
    public String getVarName() {
        return null;
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.CONSTANT;
    }

    @Override
    public Range<Double> getDomain() {
        return Range.all();
    }

    @Override
    public Range<Double> getRange() {
        return Range.singleton(value);
    }

    @Override
    public double evaluate(Double... values) {
        return this.value;
    }

    @Override
    public Function derivative() {
        return ConstantFunction.builder()
                .funcName(this.funcName + "'")
                .value(0.0)
                .build();
    }

    @Override
    public Function integral() {
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    /**
     * Returns the integral of the function with respect to the given variable
     *
     * @param integralFuncName the name of the integral function
     * @param varName          the name of the variable with respect to which the integral is taken
     * @return the integral of the function with respect to the given variable
     */
    public Function integral(final String integralFuncName, final String varName) {
        return PolynomialFunction.builder()
                .funcName(integralFuncName)
                .varName(varName)
                .terms(new LinkedList<>() {{
                    add(new PolynomialTerm(1, varName, 1));
                }})
                .build();
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        final Function indefiniteIntegral = this.integral("f", "x");
        return indefiniteIntegral.evaluate(upperBound) - indefiniteIntegral.evaluate(lowerBound);
    }

    @Override
    public double limit(double value) {
        return this.value;
    }

    @Override
    public String printBody() {
        return String.valueOf(this.value);
    }

    @Override
    public String toString() {
        return this.funcName + "()" + " = " + this.printBody();
    }

    @Override
    public Function deepCopy() {
        return ConstantFunction.builder()
                .funcName(this.funcName)
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
}
