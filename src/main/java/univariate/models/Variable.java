package univariate.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@EqualsAndHashCode
@Getter
public class Variable implements Comparable<Variable> {
    @NonNull
    private final String name;

    /**
     * Blanket Variable to apply to all variables
     */
    public static final Variable ALL = new Variable("ALL");

    public Variable(@NonNull final String varName) {
        this.name = varName;
    }

    public static Variable defaultVariable() {
        return new Variable("x");
    }

    @Override
    public int compareTo(Variable o) {
        return this.name.compareTo(o.name);
    }
}
