package models.functions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;

/**
 * This class implements a composite function.
 * A composite function is a function that is made up of two or more functions as factors.
 */
@Builder
@Getter
public class CompositeFunction {

    private final String funcName;

    private final List<PolynomialFunction> polynomialFactors;

    private final List<RadicalFunction> radicalFactors;

    public CompositeFunction(final String funcName, final List<Function> compositeFactors) {
        this.funcName = funcName;

        this.polynomialFactors = compositeFactors.stream()
                .filter(PolynomialFunction.class::isInstance)
                .map(PolynomialFunction.class::cast)
                .collect(Collectors.toList());

        this.radicalFactors = compositeFactors.stream()
                .filter(RadicalFunction.class::isInstance)
                .map(RadicalFunction.class::cast)
                .collect(Collectors.toList());

        if (polynomialFactors.size() + radicalFactors.size() != compositeFactors.size()) {
            throw new IllegalArgumentException("Composite functions is only implemented for polynomial and radical functions");
        }
    }

    public CompositeFunction(final String funcName, final List<PolynomialFunction> polynomialFactors, final List<RadicalFunction> radicalFactors) {
        this.funcName = funcName;
        this.polynomialFactors = polynomialFactors;
        this.radicalFactors = radicalFactors;
    }

    /**
     * Multiplies all the factors of the composite function.
     * @return the product of all the factors
     */
    public CompositeFunction reduce() {
        final PolynomialFunction constantUnitFunc = PolynomialFunction.builder()
                .terms(new LinkedList<>(){{
                    add(new PolynomialTerm(1.0, polynomialFactors.get(0).getVarName(), 0));
                }})
                .varName(polynomialFactors.get(0).getVarName())
                .funcName(polynomialFactors.get(0).getFuncName())
                .build();
        
        final PolynomialFunction reducedPolynomialFunction = this.polynomialFactors.stream()
            .reduce(constantUnitFunc, PolynomialFunction::multiplyBy);

        return CompositeFunction.builder()
                .polynomialFactors(new LinkedList<PolynomialFunction>() {{
                    add(reducedPolynomialFunction);
                }})
                .radicalFactors(this.radicalFactors)
                .funcName(this.funcName)
                .build();
    }

    /**
     * Returns a string representation of the composite function
     */
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder(this.funcName + " =");

        for (final PolynomialFunction polynomialFunction : this.polynomialFactors) {
            stringBuilder.append(" ( " + polynomialFunction.printBody() + " )");
        }

        for (final RadicalFunction radicalFunction : this.radicalFactors) {
            stringBuilder.append(" " + radicalFunction.toString() + " ");
        }

        return stringBuilder.toString();
    }
}
