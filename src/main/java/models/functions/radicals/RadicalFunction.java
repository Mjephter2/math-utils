package models.functions.radicals;

import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.FunctionType;
import models.functions.polynomials.PolynomialFunction;
import models.inequalities.InequalityType;
import models.inequalities.PolynomialInequality;
import models.numberUtils.Range;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.Builder;
import lombok.Getter;

/**
 * Class implementing a Radical function
 */
@Getter
@Builder
public class RadicalFunction implements Function {
    private static final Logger logger = Logger.getLogger(RadicalFunction.class.getName());

    private final String funcName;
    private final String varName;
    private final double rootIndex;
    private final Function body;

    // TODO: Refactor constructor to validate rootIndex to be between 0 and 1.

    @Override
    public FunctionType getFuncType() {
        return FunctionType.RADICAL;
    }

    /**
     * Implemented only for functions with polynomial body
     * TODO: Implement for other types of functions
     */
    @Override
    public List<Range> getDomain() {
        if (rootIndex % 2 == 0) {
            // Even root
            if (body instanceof PolynomialFunction) {
                final PolynomialFunction polyBody = (PolynomialFunction) body;
                final PolynomialInequality equation = new PolynomialInequality(InequalityType.GREATER_THAN_OR_EQUAL_TO, polyBody, new PolynomialFunction(
                        new LinkedList<>(), "tempEquation", this.varName));
                equation.solve();
                return equation.getSolution();
            } else {
                throw new UnsupportedOperationException("RadicalFunction.getDomain() is not implemented for this type of function");
            }
        } else {
            // Odd root
            return body.getRange();
        }
    }

    @Override
    public List<Range> getRange() {
        if (rootIndex % 2 == 0) {
            // Even root
            return List.of(Range.allPositive(true));
        } else {
            return List.of(Range.all());
        }
    }

    @Override
    public double evaluate(Double... values) {
        return Math.pow(body.evaluate(values), rootIndex);
    }

    @Override
    public Function simplify() {
        if (this.body instanceof ConstantFunction) {
            return ConstantFunction.builder()
                    .funcName(this.funcName)
                    .value(Math.pow(this.body.evaluate(), rootIndex))
                    .build();
        } else {
            return RadicalFunction.builder()
                    .rootIndex(this.rootIndex)
                    .body(this.body.simplify())
                    .funcName(this.funcName)
                    .varName(this.varName)
                    .build();
        }
    }

    @Override
    public Function derivative() {
        // TODO: Implement derivative for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        if (this.getDomain().contains(value)) {
            return this.evaluate(value);
        } else {
            final String message = String.format("Limit for function %s at value %d is undefined", this, value);
            logger.log(Level.INFO, message);
            return Double.NaN;
        }
    }

    @Override
    public String printBody() {
        return "[" + rootIndex + "]"+ "√(" + body.printBody() + ")";
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return RadicalFunction.builder()
                .rootIndex(this.rootIndex)
                .body(this.body.deepCopy(this.body.getFuncName()))
                .funcName(newFuncName)
                .varName(this.varName)
                .build();
    }
}
