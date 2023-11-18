package utils;

import models.functions.Function;
import models.functions.combinations.ComplexFunction;
import models.functions.combinations.CompositeFunction;
import models.functions.combinations.RationalFunction;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for calculating derivatives of functions.
 */
public class DerivativeUtils {

    /**
     * Calculates the derivative of the product of two functions.
     * @param f first function
     * @param g second function
     * @return the derivative of f * g
     */
    public static Function productRule(final Function f, final Function g) {
        if (!f.getVarName().equals(g.getVarName())) {
            throw new IllegalArgumentException("Function variables must be the same");
        }

        final Function fPrime = f.derivative();
        final Function gPrime = g.derivative();

        return ComplexFunction.builder()
                .funcName("(" + f.getFuncName() + " * " + g.getFuncName() + ")'")
                .varName(f.getVarName())
                .functions(new LinkedList<>(){{
                    add(new CompositeFunction(fPrime.getFuncName() + " * " + g.getFuncName(), List.of(fPrime, g)));
                    add(new CompositeFunction(f.getFuncName() + " * " + gPrime.getFuncName(), List.of(f, gPrime)));
                }})
        .build();
    }

    public static Function quotientRule(final Function f, final Function g) {
        if (!f.getVarName().equals(g.getVarName())) {
            throw new IllegalArgumentException("Function variables must be the same");
        }

        final Function fPrime = f.derivative();
        final Function gPrime = g.derivative();

        final Function numerator = ComplexFunction.builder()
                .funcName(fPrime.getFuncName() + " * " + g.getFuncName() + " - " + f.getFuncName() + " * " + gPrime.getFuncName())
                .varName(f.getVarName())
                .functions(new LinkedList<>(){{
                    add(new CompositeFunction(fPrime.getFuncName() + " * " + g.getFuncName(), List.of(fPrime, g)));
                    add(new CompositeFunction(f.getFuncName() + " * " + gPrime.getFuncName(), List.of(f, gPrime)));
                }})
        .build();
        final Function denominator = new CompositeFunction(g.getFuncName() + " ^ 2", List.of(g, g));
        final Function rationalFunc = RationalFunction.builder()
                .numerator(numerator)
                .denominator(denominator)
                .build();

        return ComplexFunction.builder()
                .funcName("(" + f.getFuncName() + " / " + g.getFuncName() + ")'")
                .varName(f.getVarName())
                .functions(new LinkedList<>(){{
                    add(new CompositeFunction("( " + numerator.getFuncName() + " / " + denominator.getFuncName() + " )'", List.of(rationalFunc)));
                }})
                .build();
    }
}
