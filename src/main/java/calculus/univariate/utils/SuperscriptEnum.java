package calculus.univariate.utils;

import lombok.Getter;

/**
 * Enum for digit superscript characters
 */
@Getter
public enum SuperscriptEnum {
    ZERO("⁰"),
    ONE("¹"),
    TWO("²"),
    THREE("³"),
    FOUR("⁴"),
    FIVE("⁵"),
    SIX("⁶"),
    SEVEN("⁷"),
    EIGHT("⁸"),
    NINE("⁹"),
    DOT("˙");

    private final String superscript;

    SuperscriptEnum(final String superscript) {
        this.superscript = superscript;
    }

}
