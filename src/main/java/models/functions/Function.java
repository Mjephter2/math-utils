package models.functions;

import com.google.common.collect.Range;

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
     * Returns the domain of the function
     */
    Range<Double> getDomain();

    /**
     * Returns the range of the function
     */
    Range<Double> getRange();

    /**
     * Evaluates the function at the given set of values
     */
    double evaluate(final Double ...values);

    /**
     * Returns the derivative of the function with respect to its independent variable
     */
    Function derivative();

    /**
     * Returns the integral of the function with respect to its independent variable
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
    String toString();

    /*
     * Returns only the function's expression
     */
    String printBody();
}
