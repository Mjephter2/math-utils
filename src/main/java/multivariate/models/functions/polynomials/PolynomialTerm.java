package multivariate.models.functions.polynomials;

import lombok.Getter;
import univariate.models.Variable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class PolynomialTerm {
    private final Map<Variable, Double> variableToExponentMap;
    private final Double coefficient;

    public PolynomialTerm(final Map<Variable, Double> variableToExponentMap, final Double coefficient) {
        this.variableToExponentMap = new TreeMap<>(variableToExponentMap);
        this.coefficient = coefficient;
    }

    public boolean isConstant() {
        return variableToExponentMap.isEmpty();
    }

    public static PolynomialTerm defaultPolynomialTerm() {
        return new PolynomialTerm(
                new TreeMap<>(Map.of(
                        Variable.defaultVariable(), 1.0,
                        new Variable("y"), 1.0
                )),
                1.0
        );
    }

    public boolean containsVariable(final Variable variable) {
        return this.getVariableToExponentMap().containsKey(variable);
    }

    public PolynomialTerm partialEvaluate(final Map<Variable, Double> inputVariableToValueMap) {
        Double newCoefficient = this.coefficient;
        for (Map.Entry<Variable, Double> entry : inputVariableToValueMap.entrySet()) {
            if (variableToExponentMap.containsKey(entry.getKey())) {
                newCoefficient *= Math.pow(entry.getValue(), variableToExponentMap.get(entry.getKey()));
            }
        }
        final Map<Variable, Double> newVariableToExponentMap = new LinkedHashMap<>();
        for(Map.Entry<Variable, Double> entry : variableToExponentMap.entrySet()) {
            if (!inputVariableToValueMap.containsKey(entry.getKey())) {
                newVariableToExponentMap.put(entry.getKey(), entry.getValue());
            }
        }
        return new PolynomialTerm(newVariableToExponentMap, newCoefficient);
    }

    public PolynomialTerm multiply(final PolynomialTerm other) {
        final Set<Variable> newVarSet = Stream
                .concat(this.variableToExponentMap.keySet().stream(), other.variableToExponentMap.keySet().stream())
                .collect(Collectors.toSet());
        final Map<Variable, Double> newVarToExponentMap = new HashMap<>();
        for (final Variable variable: newVarSet) {
            newVarToExponentMap.put(variable, this.variableToExponentMap.getOrDefault(variable, 0.0) + other.variableToExponentMap.getOrDefault(variable, 0.0));
        }

        return new PolynomialTerm(newVarToExponentMap, this.coefficient * other.getCoefficient());
    }

    public List<PolynomialTerm> add(final PolynomialTerm other) {
        if (this.isLikeTerm(other)) {
            return List.of(new PolynomialTerm(this.variableToExponentMap, this.coefficient + other.coefficient));
        }
        return List.of(this, other);
    }

    public boolean isLikeTerm(final PolynomialTerm other) {
        return this.variableToExponentMap.equals(other.getVariableToExponentMap());
    }

    public String toString() {
        if (isConstant()) {
            return coefficient.toString();
        }
        if (coefficient == 0.0) {
            return "0";
        }

        return (coefficient == 1.0 ? "" : coefficient.toString()) + (coefficient == 1.0 ? "" : " * ") +
                variableToExponentMap.entrySet().stream()
                        .filter(entry -> entry.getValue() != 0.0)
                        .map(entry -> entry.getKey().getName() + "^" + entry.getValue())
                        .reduce((s1, s2) -> s1 + " * " + s2)
                        .orElse("");
    }
}
