package models.functions;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

public class PolynomialFunctionTests {

    @Test
    public void builder_Test() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x

        assertEquals("P", func1.getFuncName());
        assertEquals("x", func1.getVarName());
        assertEquals(1, func1.getTerms().size());
        assertEquals(1.1, func1.getTerms().get(0).getCoefficient());
        assertEquals(1, func1.getTerms().get(0).getExponent());
    }

    @Test
    public void constructor_tests() {
        final List<PolynomialTerm> terms = new LinkedList<>(){{
            add(new PolynomialTerm(2.0, "x", 1));
            add(new PolynomialTerm(0.0, "x", 3));
            add(new PolynomialTerm(1.0, "x", 2));
        }};
        PolynomialFunction func1 = new PolynomialFunction(terms, "P", "x");
        // Test to check that zero terms are removed
        assertEquals(2, func1.getTerms().size());
        // Tests to check that the terms are sorted by exponent
        assertEquals(2, func1.getTerms().get(0).getExponent());
        assertEquals(1, func1.getTerms().get(1).getExponent());

        // Test to check that the constructor throws an exception when a term contains a different variable
        final List<PolynomialTerm> terms2 = new LinkedList<>(){{
            add(new PolynomialTerm(2.0, "x", 1));
            add(new PolynomialTerm(0.0, "y", 3));
            add(new PolynomialTerm(1.0, "x", 2));
        }};
        assertThrows(IllegalArgumentException.class, () -> new PolynomialFunction(terms2, "P", "x"));
    }

    @Test
    public void zero_function_tests() {
        final PolynomialFunction zeroFunc = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(0.0, "x", 1));
            add(new PolynomialTerm(0.0, "x", 3));
            add(new PolynomialTerm(0.0, "x", 2));
        }}, "P", "x");
        assertTrue(zeroFunc.isZeroFunction());

        final PolynomialFunction nonZeroFunc = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(2.0, "x", 1));
            add(new PolynomialTerm(0.0, "x", 3));
            add(new PolynomialTerm(1.0, "x", 2));
        }}, "P", "x");
        assertFalse(nonZeroFunc.isZeroFunction());
    }

    @Test
    public void from_tests() {
        final String funcString1 = "+ 1.1x";
        final String funcString2 = "2.1 + 2.3x^2";
        final String funcString3 = "+ 3.1x^2 + 3.2x^3 + 3.3x";

        final String variable = "x";
        final PolynomialFunction func1 = PolynomialFunction.from(funcString1,"P", variable);
        final PolynomialFunction func2 = PolynomialFunction.from(funcString2,"Q", variable);
        final PolynomialFunction func3 = PolynomialFunction.from(funcString3,"S", variable);

        assertEquals("P", func1.getFuncName());
        assertEquals("x", func1.getVarName());
        assertEquals(1, func1.getTerms().size());
        assertEquals("P(x) = 1.1x", func1.toString(false));
        assertEquals(FunctionType.POLYNOMIAL, func1.getFuncType());

        assertEquals("Q", func2.getFuncName());
        assertEquals("x", func2.getVarName());
        assertEquals(2, func2.getTerms().size());
        assertEquals("Q(x) = 2.3x² + 2.1", func2.toString(false));
        assertEquals(FunctionType.POLYNOMIAL, func2.getFuncType());

        assertEquals("S", func3.getFuncName());
        assertEquals("x", func3.getVarName());
        assertEquals(3, func3.getTerms().size());
        assertEquals("S(x) = 3.2x³ + 3.1x² + 3.3x", func3.toString(false));
        assertEquals(FunctionType.POLYNOMIAL, func3.getFuncType());
    }


    @Test
    public void domain_tests() {
        PolynomialFunction func1 = functionSample1().get(0);
        assertFalse(func1.getDomain().hasLowerBound());
        assertFalse(func1.getDomain().hasUpperBound());
    }

    @Test
    public void range_tests() {
        PolynomialFunction func1 = functionSample1().get(0);
        assertFalse(func1.getRange().hasLowerBound());
        assertFalse(func1.getRange().hasUpperBound());
    }

    @Test
    public void negate_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        PolynomialFunction func2 = func1.negate();

        assertEquals(-1.1, func2.getTerms().get(0).getCoefficient());
    }

    @Test
    public void power_tests() {
        final PolynomialFunction func1 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2

        final PolynomialFunction func2 = func1.power(0);
        assertEquals("Q(x) = 1", func2.toString(false));

        final PolynomialFunction func3 = func1.power(1);
        assertEquals("Q(x) = 2.3x² + 2.1", func3.toString(false));

        final PolynomialFunction func4 = func1.power(2);
        assertEquals("Q(x) = 5.3x⁴ + 9.7x² + 4.4", func4.toString(false));

        assertThrows(IllegalArgumentException.class, () -> func1.power(-1));
    }

    @Test
    public void evaluate_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        assertEquals(1.1, func1.evaluate(1.0));
        assertEquals(11.0, func1.evaluate(10.0));

        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        assertEquals(4.4, func2.evaluate(1.0));
        assertTrue(Math.abs ((232.1 - func2.evaluate(10.0)) / func2.evaluate(10.0)) <  0.000000000000001);

        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x
        assertTrue(Math.abs ((9.6 - func3.evaluate(1.0)) / func2.evaluate(1.0)) <  0.000000000000001);
        assertTrue(Math.abs ((3543.0 - func3.evaluate(10.0)) / func2.evaluate(10.0)) <  0.000000000000001);

        PolynomialFunction func4 = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(1.0, "x", 1));
            add(new PolynomialTerm(1.0, "x", 2));
        }}, "P", "x");
        assertThrows(IllegalArgumentException.class, () -> func4.evaluate(null));
        assertThrows(IllegalArgumentException.class, func4::evaluate);
        assertThrows(IllegalArgumentException.class, () -> func4.evaluate(1.0, 2.0));
    }

    @Test
    public void add_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x

        // add func2 to func1
        func2.add(func1);
        // add func2 to func3
        func3.add(func2);

        assertEquals("Q(x) = 2.3x² + 1.1x + 2.1", func2.toString(false));
        assertEquals("S(x) = 3.2x³ + 5.4x² + 4.4x + 2.1", func3.toString(false));

        // add func2 to func3
        func3.add(func2);

        assertEquals("S(x) = 3.2x³ + 7.7x² + 5.5x + 4.2", func3.toString(false));

        PolynomialTerm yTerm = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("y")
                .exponent(1)
        .build();
        LinkedList<PolynomialTerm> yTerms = new LinkedList<>();
        yTerms.add(yTerm);
        PolynomialFunction yFunc = PolynomialFunction.builder()
                .varName("y")
                .funcName("Y")
                .terms(yTerms)
                .build();
        assertThrows(IllegalArgumentException.class, () -> func1.add(yFunc));
    }

    @Test
    public void subtract_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x

        // subtract func1 from func2
        func2.subtract(func1);
        // subtract func2 from func3
        func3.subtract(func2);

        assertEquals("Q(x) = 2.3x² - 1.1x + 2.1", func2.toString(false));

        // 0.9x^2 should be 0.8x^2.
        assertEquals("S(x) = 3.2x³ + 0.8x² + 4.4x - 2.1", func3.toString(false));

        // subtract func2 from func3
        func3.subtract(func2);

        assertEquals("S(x) = 3.2x³ - 1.5x² + 5.5x - 4.2", func3.toString(false));

        PolynomialTerm yTerm = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("y")
                .exponent(1)
        .build();
        LinkedList<PolynomialTerm> yTerms = new LinkedList<>();
        yTerms.add(yTerm);
        PolynomialFunction yFunc = PolynomialFunction.builder()
                .varName("y")
                .funcName("Y")
                .terms(yTerms)
                .build();
        assertThrows(IllegalArgumentException.class, () -> func1.subtract(yFunc));
    }

    @Test
    public void multiply_tests() {
        PolynomialFunction f1 = this.functionSample2().get(0); // P(x) = x
        PolynomialFunction f2 = this.functionSample2().get(1); // Q(x) = x + 1
        PolynomialFunction f3 = this.functionSample2().get(2); // S(x) = x - 1

        // multiply f2 by f1
        PolynomialFunction f1f2 = f1.multiplyBy(f2);
        // multiply f1 by f3
        PolynomialFunction f1f3 = f1.multiplyBy(f3);
        // multiply f2 by f3
        PolynomialFunction f2f3 = f2.multiplyBy(f3);

        assertEquals("P(x) = x² + x", f1f2.toString(false));
        assertEquals("P(x) = x² - x", f1f3.toString(false));
        assertEquals("Q(x) = x² - 1", f2f3.toString(false));
    }

    @Test
    public void compose_tests() {
        final PolynomialFunction func1 = PolynomialFunction.builder()
                .varName("x")
                .funcName("f")
                .terms(new LinkedList<>(){{
                    add(new PolynomialTerm(1.0, "x", 2));
                    add(new PolynomialTerm(2.0, "x", 1));
                    add(new PolynomialTerm(3.0, "x", 0));
                }})
                .build();
        final PolynomialFunction func2 = PolynomialFunction.builder()
                .funcName("g")
                .varName("x")
                .terms(new LinkedList<>(){{
                    add(new PolynomialTerm(1.0, "x", 1));
                    add(new PolynomialTerm(-1.0, "x", 0));
                }})
                .build();
        assertEquals("f(x) = x² + 2", func1.composeWith(func2).toString(false));
        assertEquals("g(x) = x² + 2x + 2", func2.composeWith(func1).toString(false));
    }

    @Test
    public void derivative_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x

        assertEquals("P'(x) = 1.1", ((PolynomialFunction) func1.derivative()).toString(false));
        assertEquals("Q'(x) = 4.6x", ((PolynomialFunction) func2.derivative()).toString(false));
        assertEquals("S'(x) = 9.6x² + 6.2x + 3.3", ((PolynomialFunction) func3.derivative()).toString(false));
    }

    @Test
    public void integral_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x

        assertEquals("∫P(x)dx = 0.6x² + C", ((PolynomialFunction) func1.integral()).toString(true));
        assertEquals("∫Q(x)dx = 0.8x³ + C", ((PolynomialFunction) func2.integral()).toString(true));
        assertEquals("∫S(x)dx = 0.8x⁴ + 1x³ + 1.6x² + C", ((PolynomialFunction) func3.integral()).toString(true));

        assertEquals(2.2, func1.integral(0, 2));
        assertEquals(0.77, func2.integral(0, 1));
        assertEquals(3.48, func3.integral(0, 1));
    }

    @Test
    public void toString_test() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = (1.1)x
        assertEquals("P(x) = 1.1x", func1.toString(false));
        assertEquals("P(x) = 1.1x", func1.toString());

        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = 2.1 + (2.3)x^2
        assertEquals("Q(x) = 2.3x² + 2.1", func2.toString(false));
        assertEquals("Q(x) = 2.3x² + 2.1", func2.toString());

        PolynomialFunction func3 = functionSample1().get(2); // S(x) = (3.1)x^2 + (3.2)x^3 + (3.3)x
        assertEquals("S(x) = 3.2x³ + 3.1x² + 3.3x", func3.toString(false));
        assertEquals("S(x) = 3.2x³ + 3.1x² + 3.3x", func3.toString());

        PolynomialFunction zeroFunc = functionSample1().get(3);
        assertEquals("S(x) = 0.0", zeroFunc.toString(false));
        assertEquals("S(x) = 0.0", zeroFunc.toString());
    }

    @Test
    public void limit_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x

        assertEquals(2.2, func1.limit(2));
        assertEquals(2.1, func2.limit(0));
        assertEquals(0.0, func3.limit(0));
    }

    private List<PolynomialFunction> functionSample1() {
        final PolynomialTerm term1_1 = PolynomialTerm.builder()
                .coefficient(1.1)
                .varName("x")
                .exponent(1)
                .build();
        final LinkedList<PolynomialTerm> func1Terms = new LinkedList<>();
        func1Terms.add(term1_1);
        // P(x) = + 1.1x
        final PolynomialFunction func1 = PolynomialFunction.builder()
                .funcName("P")
                .varName("x")
                .terms(func1Terms)
                .build();

        final PolynomialTerm term2_1 = PolynomialTerm.builder()
                .coefficient(0.0)
                .varName("x")
                .exponent(2)
                .build();
        final PolynomialTerm term2_2 = PolynomialTerm.builder()
                .coefficient(2.1)
                .varName("x")
                .exponent(0)
                .build();
        final PolynomialTerm term2_3 = PolynomialTerm.builder()
                .coefficient(2.3)
                .varName("x")
                .exponent(2)
                .build();
        final PolynomialTerm term2_4 = PolynomialTerm.builder()
                .coefficient(0.0)
                .varName("x")
                .exponent(2)
                .build();
        final LinkedList<PolynomialTerm> func2Terms = new LinkedList<>();
        func2Terms.add(term2_1);
        func2Terms.add(term2_2);
        func2Terms.add(term2_3);
        func2Terms.add(term2_4);
        // Q(x) = + 2.1 + 2.3x^2
        final PolynomialFunction func2 = PolynomialFunction.builder()
                .funcName("Q")
                .varName("x")
                .terms(func2Terms)
                .build();

        final PolynomialTerm term3_1 = PolynomialTerm.builder()
                .coefficient(3.1)
                .varName("x")
                .exponent(2)
                .build();
        final PolynomialTerm term3_2 = PolynomialTerm.builder()
                .coefficient(3.2)
                .varName("x")
                .exponent(3)
                .build();
        final PolynomialTerm term3_3 = PolynomialTerm.builder()
                .coefficient(3.3)
                .varName("x")
                .exponent(1)
                .build();
        final LinkedList<PolynomialTerm> func3Terms = new LinkedList<>();
        func3Terms.add(term3_1);
        func3Terms.add(term3_2);
        func3Terms.add(term3_3);
        // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x
        final PolynomialFunction func3 = PolynomialFunction.builder()
                .funcName("S")
                .varName("x")
                .terms(func3Terms)
                .build();

        final PolynomialTerm term4_1 = PolynomialTerm.builder()
                .coefficient(0)
                .varName("x")
                .exponent(2)
                .build();
        final PolynomialTerm term4_2 = PolynomialTerm.builder()
                .coefficient(0)
                .varName("x")
                .exponent(3)
                .build();
        final PolynomialTerm term4_3 = PolynomialTerm.builder()
                .coefficient(0)
                .varName("x")
                .exponent(1)
                .build();
        final LinkedList<PolynomialTerm> func4Terms = new LinkedList<>();
        func3Terms.add(term4_1);
        func3Terms.add(term4_2);
        func3Terms.add(term4_3);
        final PolynomialFunction func4 = PolynomialFunction.builder()
                .funcName("S")
                .varName("x")
                .terms(func4Terms)
                .build();

        final LinkedList<PolynomialFunction> funcList = new LinkedList<>();
        funcList.add(func1);
        funcList.add(func2);
        funcList.add(func3);
        funcList.add(func4);

        return funcList;
    }

    private List<PolynomialFunction> functionSample2() {
        final PolynomialTerm term1_1 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(1)
                .build();
        final LinkedList<PolynomialTerm> func1Terms = new LinkedList<>();
        func1Terms.add(term1_1);
        // P(x) = + 1.0x
        final PolynomialFunction func1 = PolynomialFunction.builder()
                .funcName("P")
                .varName("x")
                .terms(func1Terms)
                .build();

        final PolynomialTerm term2_1 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(1)
                .build();
        final PolynomialTerm term2_2 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(0)
                .build();
        final LinkedList<PolynomialTerm> func2Terms = new LinkedList<>();
        func2Terms.add(term2_1);
        func2Terms.add(term2_2);
        // Q(x) = + 1.0x + 1
        final PolynomialFunction func2 = PolynomialFunction.builder()
                .funcName("Q")
                .varName("x")
                .terms(func2Terms)
                .build();

        final PolynomialTerm term3_1 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(1)
                .build();
        final PolynomialTerm term3_2 = PolynomialTerm.builder()
                .coefficient(-1.0)
                .varName("x")
                .exponent(0)
                .build();
        final LinkedList<PolynomialTerm> func3Terms = new LinkedList<>();
        func3Terms.add(term3_1);
        func3Terms.add(term3_2);
        // S(x) = + 1.0x - 1
        final PolynomialFunction func3 = PolynomialFunction.builder()
                .funcName("S")
                .varName("x")
                .terms(func3Terms)
                .build();

        final LinkedList<PolynomialFunction> funcList = new LinkedList<>();
        funcList.add(func1);
        funcList.add(func2);
        funcList.add(func3);

        return funcList;
    }
}
