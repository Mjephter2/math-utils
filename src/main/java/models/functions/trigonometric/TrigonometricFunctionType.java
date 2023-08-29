package models.functions.trigonometric;

import lombok.Getter;

/**
 * Represents the type of trigonometric function
 */
@Getter
public enum  TrigonometricFunctionType {

    COSINE("Cosine"),
    SINE("Sine"),
    TANGENT("Tangent"),
    SECANT("Secant"),
    COSECANT("Cosecant"),
    COTANGENT("Cotangent");

    private final String name;

    TrigonometricFunctionType(final String name) {
        this.name = name;
    }

    public static TrigonometricFunctionType fromString(final String name) {
        for (final TrigonometricFunctionType type : TrigonometricFunctionType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No trigonometric function type with name " + name);
    }

    public static String toShortName(final TrigonometricFunctionType type) {
        switch (type) {
            case COSINE:
                return "cos";
            case SINE:
                return "sin";
            case TANGENT:
                return "tan";
            case SECANT:
                return "sec";
            case COSECANT:
                return "csc";
            case COTANGENT:
                return "cot";
            default:
                throw new IllegalArgumentException("No trigonometric function type with name " + type);
        }
    }
}
