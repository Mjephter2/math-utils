package models.functions.radicals;

import com.google.common.collect.Range;
import lombok.Builder;
import lombok.Getter;
import models.equations.Equation;
import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.FunctionType;
import utils.SuperscriptUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class implementing a Radical function
 */
@Getter
@Builder
public class RadicalFunction implements Function {
    private static final Logger logger = Logger.getLogger(RadicalFunction.class.getName());

    private final String funcName;
    private final String varName;
    private final int rootIndex;
    private final Function body;

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
        return FunctionType.RADICAL;
    }

    @Override
    public Range<Double> getDomain() {
        // TODO: Implement domain for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'getDomain'");
    }

    @Override
    public Range<Double> getRange() {
        // TODO: Implement range for RadicalFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return Math.pow(body.evaluate(values), 1.0 / rootIndex);
    }

    @Override
    public Function simplify() {
        if (this.body instanceof ConstantFunction) {
            return ConstantFunction.builder()
                    .value(Math.pow(this.body.evaluate(), 1.0 / rootIndex))
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
        return SuperscriptUtil.convertToSuperscript(rootIndex) + "√(" + body.printBody() + ")";
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
