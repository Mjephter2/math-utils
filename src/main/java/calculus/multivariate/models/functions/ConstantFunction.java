package calculus.multivariate.models.functions;

import lombok.Getter;
import calculus.univariate.models.Variable;
import calculus.univariate.models.functions.FunctionType;
import calculus.univariate.models.numberUtils.Range;

import java.util.List;
import java.util.Map;

@Getter
public class ConstantFunction extends Function {
    private final Double value;

    public ConstantFunction(final Double funcValue, final String funcName, final Boolean isIndefiniteIntegral) {
        super(funcName, List.of(), FunctionType.CONSTANT, isIndefiniteIntegral);
        this.value = funcValue;
    }

    public ConstantFunction(final Double funcValue, final String funcName, final Boolean isIndefiniteIntegral, final Map<Variable, Double> evalValues) {
        super(funcName, List.of(), FunctionType.CONSTANT, isIndefiniteIntegral, evalValues);
        this.value = funcValue;
    }

    @Override
    public boolean containsVariable(final Variable variable) {
        // Will always be false regardless of variable
        return false;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public Map<Variable, Range> computeDomain() {
        return Map.of(Variable.ALL, Range.all());
    }

    @Override
    public List<Range> computeRange() {
        return List.of(Range.all());
    }

    @Override
    public void simplify() {
        // Do nothing
    }

    @Override
    public Function partialEvaluate(final Map<Variable, Double> values) {
        return new ConstantFunction(value, this.getFuncName(), this.getIsIndefiniteIntegral(), values);
    }

    @Override
    public Double evaluate(final Map<Variable, Double> values) {
        return this.value;
    }

    @Override
    public Function partialDerivative(final Variable variable) {
        return new ConstantFunction(0.0, this.getFuncName() + "'", this.getIsIndefiniteIntegral());
    }

    @Override
    public Function add(final Function other) {
        if (other instanceof ConstantFunction) {
            return new ConstantFunction(
                    this.value + ((ConstantFunction) other).value,
                    String.format("(%s + %s)", this.getFuncName(), other.getFuncName()),
                    this.getIsIndefiniteIntegral());
        } else {
            throw new UnsupportedOperationException(
                    String.format("Unable to add a constant to a non-constant function: %s!", other.getClass().getName()));
        }
    }

    @Override
    public Function substract(final Function other) {
        if (other instanceof ConstantFunction) {
            return new ConstantFunction(
                    this.value - ((ConstantFunction) other).value,
                    String.format("(%s - %s)", this.getFuncName(), other.getFuncName()),
                    this.getIsIndefiniteIntegral());
        } else {
            throw new UnsupportedOperationException(
                    String.format("Unable to substract a constant from a non-constant function: %s!", other.getClass().getName()));
        }
    }

    @Override
    public Function multiply(final Function other) {
        if (other instanceof ConstantFunction) {
            return new ConstantFunction(
                    this.value * ((ConstantFunction) other).value,
                    String.format("(%s * %s)", this.getFuncName(), other.getFuncName()),
                    this.getIsIndefiniteIntegral());
        } else {
            throw new UnsupportedOperationException(
                    String.format("Unable to multiply a constant with a non-constant function: %s!", other.getClass().getName()));
        }
    }

    @Override
    public Function divide(final Function other) {
        if (other instanceof ConstantFunction) {
            return new ConstantFunction(
                    this.value / ((ConstantFunction) other).value,
                    String.format("(%s / %s)", this.getFuncName(), other.getFuncName()),
                    this.getIsIndefiniteIntegral());
        } else {
            throw new UnsupportedOperationException(
                    String.format("Unable to divide a constant by a non-constant function: %s!", other.getClass().getName()));
        }
    }
}
