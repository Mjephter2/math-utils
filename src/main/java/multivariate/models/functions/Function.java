package multivariate.models.functions;

import lombok.Getter;
import lombok.NonNull;
import univariate.models.Variable;
import univariate.models.functions.FunctionType;
import univariate.models.numberUtils.Range;

import java.util.List;
import java.util.Map;

@Getter
public abstract class Function {
    @NonNull private final String funcName;
    @NonNull private final List<Variable> variableList;
    @NonNull private final FunctionType functionType;
    private final Boolean isIndefiniteIntegral;
    @NonNull private final Map<Variable, Range> domain;
    @NonNull private final List<Range> range;
    private final Boolean isEval;
    private Map<Variable, Double> evalValues = null;

    public Function(
            final String funcName,
            final List<Variable> variableList,
            final FunctionType functionType,
            final Boolean isIndefiniteIntegral) {
        this.isEval = false;

        // Minor validations
        if (funcName == null || funcName.isEmpty() || Character.isDigit(funcName.charAt(0))) {
            throw new IllegalArgumentException("Function Name cannot be empty and must start with a letter!");
        }

        this.funcName = funcName;
        this.variableList = variableList;
        this.functionType = functionType;
        this.isIndefiniteIntegral = isIndefiniteIntegral;
        this.domain = computeDomain();
        this.range = computeRange();
    }

    public Function(
            final String funcName,
            final List<Variable> variableList,
            final FunctionType functionType,
            final Boolean isIndefiniteIntegral,
            final Map<Variable, Double> evalValues
    ) {
        this.isEval = true;
        this.evalValues = evalValues;

        // Minor validations
        if (funcName == null || funcName.isEmpty() || Character.isDigit(funcName.charAt(0))) {
            throw new IllegalArgumentException("Function Name cannot be empty and must start with a letter!");
        }

        this.funcName = funcName;
        this.variableList = variableList;
        this.functionType = functionType;
        this.isIndefiniteIntegral = isIndefiniteIntegral;
        this.domain = computeDomain();
        this.range = computeRange();
    }

    public abstract boolean containsVariable(final Variable variable);

    public abstract boolean isConstant();

    public abstract Map<Variable, Range> computeDomain();

    public abstract List<Range> computeRange();

    public abstract void simplify();

    public abstract Function partialEvaluate(Map<Variable, Double> values);

    public abstract Double evaluate(Map<Variable, Double> values);

    public abstract Function partialDerivative(final Variable variable);

    public abstract Function add(Function other);

    public abstract Function substract(Function other);

    public abstract Function multiply(Function other);

    public abstract Function divide(Function other);
}
