package models.functions;

import com.google.common.collect.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConstantFunctionTests {

    @Test
    public void builder_test() {
        final ConstantFunction constantFunction = ConstantFunction.builder()
                .funcName("f")
                .value(1.0)
                .build();

        assertEquals("f", constantFunction.getFuncName());
        assertEquals(1.0, constantFunction.getValue(), 0.0);
        assertNull(constantFunction.getVarName());
        assertEquals(FunctionType.CONSTANT, constantFunction.getFuncType());
        assertEquals(Range.all(), constantFunction.getDomain());
        assertEquals(Range.singleton(1.0), constantFunction.getRange());
        assertEquals(1.0, constantFunction.evaluate(1000.0), 0.0);

        assertEquals("f() = 1.0", constantFunction.toString());
    }

    @Test
    public void derivative_test() {
        final ConstantFunction constantFunction = ConstantFunction.builder()
                .funcName("f")
                .value(1.0)
                .build();

        final ConstantFunction derivative = (ConstantFunction) constantFunction.derivative();
        assertEquals("f'() = 0.0", derivative.toString());
    }

    @Test
    public void integral_test() {
        final ConstantFunction constantFunction = ConstantFunction.builder()
                .funcName("f")
                .value(1.0)
                .build();

        final PolynomialFunction integral = (PolynomialFunction) constantFunction.integral("f", "x");
        assertEquals("f(x) = x", integral.toString());
    }

    @Test
    public void def_integral_test() {
        final ConstantFunction constantFunction = ConstantFunction.builder()
                .funcName("f")
                .value(1.0)
                .build();

        assertEquals(1.0, constantFunction.integral(0.0, 1.0), 0.0);
    }

    @Test
    public void exceptions_test() {
        final ConstantFunction constantFunction = ConstantFunction.builder()
                .funcName("f")
                .value(1.0)
                .build();

        assertThrows(UnsupportedOperationException.class, constantFunction::integral);
    }

    @Test
    public void deep_copy_test() {
        final ConstantFunction constantFunction = ConstantFunction.builder()
                .funcName("f")
                .value(1.0)
                .build();

        final ConstantFunction copy = (ConstantFunction) constantFunction.deepCopy();
        assertEquals("f() = 1.0", copy.toString());
    }

    @Test
    public void limit_test() {
        final ConstantFunction constantFunction = ConstantFunction.builder()
                .funcName("f")
                .value(1.0)
                .build();

        assertEquals(1.0, constantFunction.limit(1000.0), 0.0);
    }
}
