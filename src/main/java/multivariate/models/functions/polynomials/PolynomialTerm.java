package multivariate.models.functions.polynomials;

import lombok.Getter;
import univariate.models.Variable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

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

    public String toString() {
        if (isConstant()) {
            return coefficient.toString();
        }
        if (coefficient == 0.0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder(coefficient == 1.0 ? "" : coefficient.toString());
        sb.append(coefficient == 1.0 ? "" : " * ");

        sb.append(variableToExponentMap.entrySet().stream()
                .filter(entry -> entry.getValue() != 0.0)
                .map(entry -> entry.getKey().getName() + "^" + entry.getValue())
                .reduce((s1, s2) -> s1 + " * " + s2)
                .orElse("")
        );

        return sb.toString();
    }
}
