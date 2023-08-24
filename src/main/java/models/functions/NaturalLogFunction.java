package models.functions;

/**
 * A natural logarithm function.
 */
public class NaturalLogFunction extends LogFunction {

    NaturalLogFunction(final String funcName, final String varName, final Function body) {
        super(funcName, varName, body, Math.E);
    }

    @Override
    public String printBody() {
        return "ln_" + this.getBase() + "(" + this.getBody().printBody() + ")";
    }
}
