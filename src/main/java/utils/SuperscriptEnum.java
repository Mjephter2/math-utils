package utils;

public enum SuperscriptEnum {
    ZERO("\u2070"),
    ONE("\u00b9"),
    TWO("\u00b2"),
    THREE("\u00b3"),
    FOUR("\u2074"),
    FIVE("\u2075"),
    SIX("\u2076"),
    SEVEN("\u2077"),
    EIGHT("\u2078"),
    NINE("\u2079");

    private final String superscript;

    SuperscriptEnum(final String superscript) {
        this.superscript = superscript;
    }

    public String getSuperscript() {
        return superscript;
    }
}
