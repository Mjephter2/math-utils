package univariate.models.functions;

import calculus.univariate.models.functions.FunctionType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTypeTests {

    @Test
    public void fromTests() {
        final String funcTypeName1 = "Polynomial";
        final String funcTypeName2 = "Exponential";
        final String funcTypeName3 = "unknown";

        final FunctionType polynomialFuncType = FunctionType.fromString(funcTypeName1);
        final FunctionType exponentialFuncType = FunctionType.fromString(funcTypeName2);
        final FunctionType otherType = FunctionType.fromString(funcTypeName3);

        assertEquals(FunctionType.POLYNOMIAL, polynomialFuncType);
        assertEquals(FunctionType.EXPONENTIAL, exponentialFuncType);
        assertEquals(FunctionType.OTHER, otherType);
    }
}
