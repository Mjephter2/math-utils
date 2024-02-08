package models.functions;

//import com.google.common.collect.Range;// replacing with models.Range to keep external dependencies to a minimum
import models.numberUtils.Range;

import java.util.List;

/**
 * Represents a mathematical function
 */
public interface Function {

    /*
     * Returns the name of the function
     */
    String getFuncName();

    /*
     * Returns the name of the independent variable of the function
     */
    String getVarName();

    /**
     * Returns the type of the function
     */
    FunctionType getFuncType();

    /**
     * Returns whether the function is an indefinite integral
     * @return true if the function is an indefinite integral
     *        false otherwise
     *        (default is false)
     * Needed for the indefinite integral of a function
     */
    boolean isIndefiniteIntegral();

    /**
     * Returns the domain of the function
     */
    List<Range> getDomain();

    /**
     * Returns the range of the function
     */
    List<Range> getRange();

    /**
     * Evaluates the function at the given set of values
     */
    double evaluate(final Double ...values);

    /**
     * Simplifies / reduces the function
     * @return the simplified function
     */
    Function simplify();

    /**
     * Returns the derivative of the function with respect to its independent variable
     */
    Function derivative();

    /**
     * Returns the indefinite integral of the function with respect to its independent variable
     */
    Function integral();

    /**
     * Returns the integral of the function with respect to its independent variable
     * over the given range
     */
    double integral(final double lowerBound, final double upperBound);

    /**
     * Returns the limit of the function as it approaches the given value
     */
    double limit(final double value);

    /**
     * Returns the string representation of the function
     */
    default String printFunc() {
        return this.getFuncName() + "(" + this.getVarName() + ")" + " = " + this.printBody();
    };

    /*
     * Returns only the function's expression
     */
    String printBody();

    /*
     * Returns a deep copy of the function
     */
    Function deepCopy(final String newFuncName);

    /**
     * Returns true if the given function is equal to this function
     */
    boolean equals(Object other);
}
