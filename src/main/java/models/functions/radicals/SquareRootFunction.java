package models.functions.radicals;

import lombok.Getter;
import models.functions.Function;
import utils.SuperscriptUtil;

/**
 * Class implementing a Square Root function.
 */
@Getter
public class SquareRootFunction extends RadicalFunction {

    private static final String SQUARE_ROOT = "√";

    SquareRootFunction(final String funcName, final String varName, final Function body) {
        super(funcName, varName, 2, body);
    }

    @Override
    public String printBody() {
        return SuperscriptUtil.convertToSuperscript(2) + SQUARE_ROOT + "(" + this.getBody().printBody() + ")";
    }
}
