package models.functions;

/**
 * Represents the type of mathematical function
 */
public enum FunctionType {

    POLYNOMIAL("Polynomial"),
    TRIGONOMETRIC("Trigonometric"),
    EXPONENTIAL("Exponential"),
    LOGARITHMIC("Logarithmic"),
    RATIONAL("Rational"),
    RADICAL("Radical"),
    ABSOLUTE_VALUE("Absolute Value"),
    PIECEWISE("Piecewise"),
    OTHER("Other");

    private final String name;

    FunctionType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static FunctionType fromString(final String name) {
        for (final FunctionType type : FunctionType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return FunctionType.OTHER;
    }
}
