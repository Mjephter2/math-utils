package univariate.models.functions.radicals;

import lombok.Getter;
import univariate.models.functions.Function;
import univariate.utils.SuperscriptUtil;

/**
 * Class implementing a Square Root function.
 */
@Getter
public class SquareRootFunction extends RadicalFunction {

    private static final String SQUARE_ROOT = "√";

    public SquareRootFunction(final String funcName, final String varName, final Function body) {
        super(funcName, varName, 0.5, body, body.isIndefiniteIntegral());
    }

    @Override
    public String printBody() {
        return SuperscriptUtil.convertToSuperscript(2) + SQUARE_ROOT + "(" + this.getBody().printBody() + ")";
    }
}
