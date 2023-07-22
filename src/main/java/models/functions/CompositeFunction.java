package models.functions;

import java.util.LinkedList;

/**
 * This class implements a composite function.
 * A composite function is a function that is made up of two or more functions as factors.
 */
public class CompositeFunction {

    public LinkedList<PolynomialFunction> factors;

    public CompositeFunction(final LinkedList<PolynomialFunction> compositeFactors) {
        this.factors = compositeFactors;
    }

    /**
     * Multiplies all the factors of the composite function.
     * @return the product of all the factors
     */
    public PolynomialFunction reduce() {
        final PolynomialFunction constantUnitFunc = PolynomialFunction.builder()
                .terms(new LinkedList<>(){{
                    add(new PolynomialTerm(1.0, factors.get(0).getVarName(), 0));
                }})
                .varName(factors.get(0).getVarName())
                .funcName(factors.get(0).getFuncName())
                .build();

        return PolynomialFunction.builder()
                .funcName(this.factors.get(0).getFuncName())
                .varName(this.factors.get(0).getVarName())
                .terms(this.factors.stream().reduce(constantUnitFunc, PolynomialFunction::multiplyBy).getTerms())
                .build();
    }
}
