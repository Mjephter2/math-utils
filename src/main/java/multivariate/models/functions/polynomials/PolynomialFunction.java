package multivariate.models.functions.polynomials;

import lombok.NonNull;
import multivariate.models.functions.Function;
import univariate.models.Variable;
import univariate.models.functions.FunctionType;
import univariate.models.numberUtils.Range;

import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.stream.Stream;

/** This class implements a multi term multi variate polynomial.
 * Assume the form:
 *               ____
 *              |    c11*x1^p11*x2^p21*...*xn^pn1
 *              |  + c21*x1^p12*x2^p22*...*xn^pn2
 *  f(x) = ---- |  + c31*x1^p13*x2^p23*...*xn^pn3
 *              |  + ...
 *              |  + cm1*x1^p1m*x2^p2m*...*xn^pnm
 *              ____
 *  where c<ij> are coefficients, p<ij> are exponents and x<ij> are independent variables
 *  accounting for n variables and m terms
 * c is the coefficient and p is the exponent of the independent variable x
 */
public class PolynomialFunction extends Function {

    @NonNull private final List<PolynomialTerm> terms;

    public PolynomialFunction(
            @NonNull final String funcName,
            final List<Variable> variableList,
            final FunctionType functionType,
            final Boolean isIndefiniteIntegral,
            @NonNull final List<PolynomialTerm> terms) {
        super(funcName, variableList, functionType, isIndefiniteIntegral);
        this.terms = terms;
    }

    public PolynomialFunction(
            @NonNull final String funcName,
            final List<Variable> variableList,
            final FunctionType functionType,
            final Boolean isIndefiniteIntegral,
            @NonNull List<PolynomialTerm> terms,
            final Map<Variable, Double> evalValues) {
        super(funcName, variableList, functionType, isIndefiniteIntegral, evalValues);
        this.terms = terms;
    }

    @Override
    public boolean containsVariable(final Variable variable) {
        return this.terms.stream().anyMatch(term -> term.containsVariable(variable));
    }

    @Override
    public boolean isConstant() {
        return this.terms.stream().allMatch(PolynomialTerm::isConstant);
    }

    @Override
    public Map<Variable, Range> computeDomain() {
        return Map.of();
    }

    @Override
    public List<Range> computeRange() {
        return List.of();
    }

    @Override
    public void simplify() {

    }

    @Override
    public Function partialEvaluate(Map<Variable, Double> variableToValuesMap) {
        final List<PolynomialTerm> newTerms = this.terms.stream()
                .map(term -> term.partialEvaluate(variableToValuesMap))
                .filter(term -> term.getCoefficient() != 0.0)
                .toList();

        final List<Variable> newVariableList = this.getVariableList().stream()
                .filter(variable -> !variableToValuesMap.containsKey(variable))
                .toList();

        return new PolynomialFunction(
                this.getFuncName(),
                newVariableList,
                this.getFunctionType(),
                this.getIsIndefiniteIntegral(),
                newTerms,
                variableToValuesMap
        );
    }

    @Override
    public Double evaluate(final Map<Variable, Double> values) {
        return terms.stream().map(term -> term.evaluate(values)).reduce(0.0, Double::sum);
    }

    @Override
    public Function partialDerivative(Variable variable) {
        return null;
    }

    @Override
    public Function add(final Function other) {
        return null;
    }

    @Override
    public Function substract(final Function other) {
        return null;
    }

    @Override
    public Function multiply(final Function other) {
        return null;
    }

    @Override
    public Function divide(final Function other) {
        return null;
    }

    @Override
    public String toString() {
        String lh = this.getFuncName() + "(";
        if (this.getIsEval()) {
            lh += Stream.concat(this.getVariableList().stream(), this.getEvalValues().keySet().stream())
                    .sorted(Comparator.comparing(Variable::getName))
                    .map(variable -> {
                        final String valueString = this.getEvalValues().get(variable) == null ? "" : "=" + this.getEvalValues().get(variable).toString();
                        return variable.getName() + valueString;
                    })
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("");
        } else {
            lh += this.getVariableList().stream()
                    .map(Variable::getName)
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse("");
        }
        lh += ") = ";
        return lh + this.terms.stream()
                .map(PolynomialTerm::toString)
                .reduce((s1, s2) -> s1 + " + " + s2)
                .orElse("");
    }

}
