package calculus.univariate.models.inequalities;

/**
 * Enum representing the type of inequality
 */
public enum InequalityType {

    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL_TO("<="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL_TO(">="),
    EQUAL_TO("="),
    NOT_EQUAL_TO("!=");

    private final String symbol;

    InequalityType(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static InequalityType fromSymbol(String symbol) {
        for (InequalityType type : InequalityType.values()) {
            if (type.getSymbol().equals(symbol)) {
                return type;
            }
        }
        return null;
    }

    public static InequalityType reverse(InequalityType type) {
        switch (type) {
            case LESS_THAN:
                return GREATER_THAN;
            case LESS_THAN_OR_EQUAL_TO:
                return GREATER_THAN_OR_EQUAL_TO;
            case GREATER_THAN:
                return LESS_THAN;
            case GREATER_THAN_OR_EQUAL_TO:
                return LESS_THAN_OR_EQUAL_TO;
            case EQUAL_TO:
                return NOT_EQUAL_TO;
            default:
                return EQUAL_TO;
        }
    }
}
