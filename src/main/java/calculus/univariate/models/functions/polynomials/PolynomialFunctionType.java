package calculus.univariate.models.functions.polynomials;

import lombok.Getter;

/**
 * Enum of different types of Polynomial functions
 */
@Getter
public enum PolynomialFunctionType {

    LINEAR("Linear"),
    QUADRATIC("Quadratic"),
    CUBIC("Cubic"),
    HIGHER_DEGREES("Higher Degrees");

    private final String name;


    PolynomialFunctionType(String name) {
        this.name = name;
    }

    public static PolynomialFunctionType fromString(final String name) {
        for (final PolynomialFunctionType type: PolynomialFunctionType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw  new IllegalArgumentException("No such PolynomialFunction types found!");
    }
}
