package multivariate.models.functions.polynomials;

import lombok.NonNull;
import multivariate.models.functions.Function;
import univariate.models.Variable;
import univariate.models.functions.FunctionType;
import univariate.models.numberUtils.Range;

import java.util.List;
import java.util.Map;

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
            @NonNull List<PolynomialTerm> terms) {
        super(funcName, variableList, functionType, isIndefiniteIntegral);
        this.terms = terms;
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

}
