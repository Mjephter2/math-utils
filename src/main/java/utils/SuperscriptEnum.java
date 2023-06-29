package utils;

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
    NINE("⁹");

    private final String superscript;

    SuperscriptEnum(final String superscript) {
        this.superscript = superscript;
    }

    public String getSuperscript() {
        return superscript;
    }
}
