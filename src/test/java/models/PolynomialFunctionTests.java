package models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

public class PolynomialFunctionTests {

    @Test
    public void create_Test() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x

        assertEquals("P", func1.getFuncName());
        assertEquals("x", func1.getVarName());
        assertEquals(1, func1.getTerms().size());
        assertEquals(1.1, func1.getTerms().get(0).getCoefficient());
        assertEquals(1, func1.getTerms().get(0).getExponent());
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
        assertEquals("P(x) = + 1.1x", func1.toString());

        assertEquals("Q", func2.getFuncName());
        assertEquals("x", func2.getVarName());
        assertEquals(2, func2.getTerms().size());
        assertEquals("Q(x) = + 2.3x^2 + 2.1", func2.toString());

        assertEquals("S", func3.getFuncName());
        assertEquals("x", func3.getVarName());
        assertEquals(3, func3.getTerms().size());
        assertEquals("S(x) = + 3.2x^3 + 3.1x^2 + 3.3x", func3.toString());
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
        assertEquals("Q(x) = + 1", func2.toString());

        final PolynomialFunction func3 = func1.power(1);
        assertEquals("Q(x) = + 2.3x^2 + 2.1", func3.toString());

        final PolynomialFunction func4 = func1.power(2);
        assertEquals("Q(x) = + 5.3x^4 + 9.7x^2 + 4.4", func4.toString());

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

        assertEquals("Q(x) = + 2.3x^2 + 1.1x + 2.1", func2.toString());
        assertEquals("S(x) = + 3.2x^3 + 5.4x^2 + 4.4x + 2.1", func3.toString());

        // add func2 to func3
        func3.add(func2);

        assertEquals("S(x) = + 3.2x^3 + 7.7x^2 + 5.5x + 4.2", func3.toString());

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

        // subtract func2 from func1
        func2.subtract(func1);
        // subtract func2 from func3
        func3.subtract(func2);

        assertEquals("Q(x) = + 2.3x^2 - 1.1x + 2.1", func2.toString());

        // 0.9x^2 should be 0.8x^2.
        assertEquals("S(x) = + 3.2x^3 + 0.8x^2 + 4.4x - 2.1", func3.toString());

        // subtract func2 from func3
        func3.subtract(func2);

        assertEquals("S(x) = + 3.2x^3 - 1.5x^2 + 5.5x - 4.2", func3.toString());

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

        assertEquals("P(x) = + 1x^2 + 1x", f1f2.toString());
        assertEquals("P(x) = + 1x^2 - 1x", f1f3.toString());
        assertEquals("Q(x) = + 1x^2 - 1", f2f3.toString());
    }

    @Test
    public void toString_test() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = (1.1)x
        assertEquals("P(x) = + 1.1x", func1.toString());

        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = 2.1 + (2.3)x^2
        assertEquals("Q(x) = + 2.1 + 2.3x^2", func2.toString());

        PolynomialFunction func3 = functionSample1().get(2); // S(x) = (3.1)x^2 + (3.2)x^3 + (3.3)x
        assertEquals("S(x) = + 3.1x^2 + 3.2x^3 + 3.3x", func3.toString());

        PolynomialFunction zeroFunc = functionSample1().get(3);
        assertEquals("0.0", zeroFunc.toString());
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
