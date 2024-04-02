package univariate.models;

import lombok.Getter;
import lombok.NonNull;


@Getter
public class Variable {
    @NonNull
    private final String name;

    public Variable(@NonNull final String varName) {
        this.name = varName;
    }

    public static Variable defaultVariable() {
        return new Variable("x");
    }
}
