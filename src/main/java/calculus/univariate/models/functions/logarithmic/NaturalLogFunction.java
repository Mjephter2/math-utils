package calculus.univariate.models.functions.logarithmic;

import calculus.univariate.models.functions.Function;

/**
 * A natural logarithm function.
 */
public class NaturalLogFunction extends LogFunction {

    public NaturalLogFunction(final String funcName, final String varName, final Function body) {
        super(funcName, varName, body, Math.E, body.isIndefiniteIntegral());
    }

    @Override
    public String printBody() {
        return "ln" + "(" + this.getBody().printBody() + ")";
    }

    @Override
    public String printFunc() {
        return this.getFuncName() + "(" + this.getVarName() + ") = " + this.printBody();
    }
}
