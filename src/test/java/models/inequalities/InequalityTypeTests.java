package models.inequalities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InequalityTypeTests {

    @Test
    public void from_tests() {
        assertEquals(InequalityType.LESS_THAN, InequalityType.fromSymbol("<"));
        assertEquals(InequalityType.LESS_THAN_OR_EQUAL_TO, InequalityType.fromSymbol("<="));
        assertEquals(InequalityType.GREATER_THAN, InequalityType.fromSymbol(">"));
        assertEquals(InequalityType.GREATER_THAN_OR_EQUAL_TO, InequalityType.fromSymbol(">="));
        assertEquals(InequalityType.EQUAL_TO, InequalityType.fromSymbol("="));
        assertEquals(InequalityType.NOT_EQUAL_TO, InequalityType.fromSymbol("!="));
        assertNull(InequalityType.fromSymbol("invalid"));
    }

    @Test
    public void reverse_tests() {
        assertEquals(InequalityType.GREATER_THAN, InequalityType.reverse(InequalityType.LESS_THAN));
        assertEquals(InequalityType.GREATER_THAN_OR_EQUAL_TO, InequalityType.reverse(InequalityType.LESS_THAN_OR_EQUAL_TO));
        assertEquals(InequalityType.LESS_THAN, InequalityType.reverse(InequalityType.GREATER_THAN));
        assertEquals(InequalityType.LESS_THAN_OR_EQUAL_TO, InequalityType.reverse(InequalityType.GREATER_THAN_OR_EQUAL_TO));
        assertEquals(InequalityType.NOT_EQUAL_TO, InequalityType.reverse(InequalityType.EQUAL_TO));
        assertEquals(InequalityType.EQUAL_TO, InequalityType.reverse(InequalityType.NOT_EQUAL_TO));
    }

    @Test
    public void fromSymbol_tests() {
        assertEquals(InequalityType.LESS_THAN, InequalityType.fromSymbol("<"));
        assertEquals(InequalityType.LESS_THAN_OR_EQUAL_TO, InequalityType.fromSymbol("<="));
        assertEquals(InequalityType.GREATER_THAN, InequalityType.fromSymbol(">"));
        assertEquals(InequalityType.GREATER_THAN_OR_EQUAL_TO, InequalityType.fromSymbol(">="));
        assertEquals(InequalityType.EQUAL_TO, InequalityType.fromSymbol("="));
        assertEquals(InequalityType.NOT_EQUAL_TO, InequalityType.fromSymbol("!="));
        assertNull(InequalityType.fromSymbol("invalid"));
    }
}
