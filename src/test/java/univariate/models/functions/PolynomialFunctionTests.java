package univariate.models.functions;

import univariate.models.functions.ConstantFunction;
import univariate.models.functions.FunctionType;
import univariate.models.functions.polynomials.PolynomialFunction;
import univariate.models.functions.polynomials.PolynomialFunctionType;
import univariate.models.functions.polynomials.PolynomialTerm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolynomialFunctionTests {

    @Test
    public void builder_Test() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x

        assertEquals("P", func1.getFuncName());
        assertEquals("x", func1.getVarName());
        assertEquals(1, func1.getTerms().size());
        assertEquals(1.1, func1.getTerms().get(0).getCoefficient());
        assertEquals(1, func1.getTerms().get(0).getExponent());
        assertEquals(1, func1.getDegree());
        assertEquals(PolynomialFunctionType.LINEAR, func1.getType());

        PolynomialFunction func2 = func1.deepCopy(func1.getFuncName());
        assertEquals("P", func2.getFuncName());
        assertEquals("x", func2.getVarName());
        assertEquals(1, func2.getTerms().size());
        assertEquals(1.1, func2.getTerms().get(0).getCoefficient());
        assertEquals(1, func2.getTerms().get(0).getExponent());
        assertEquals(1, func2.getDegree());
        assertEquals(PolynomialFunctionType.LINEAR, func2.getType());
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
    public void defaultsTermsTests() {
        final PolynomialTerm term1 = PolynomialTerm.withDefaults();
        assertEquals(1, term1.getCoefficient());
        assertEquals("x", term1.getVarName());
        assertEquals(1, term1.getExponent());

        final PolynomialTerm term2 = PolynomialTerm.withCoefficient(2);
        assertEquals(2, term2.getCoefficient());
        assertEquals("x", term2.getVarName());
        assertEquals(1, term2.getExponent());
    }

    @Test
    public void polynomialTypeTests() {
        final PolynomialFunctionType goodType = PolynomialFunctionType.fromString("Linear");
        assertEquals(PolynomialFunctionType.LINEAR, goodType);

        assertThrows(IllegalArgumentException.class, () -> PolynomialFunctionType.fromString("bad"));
    }

    @Test
    public void termsExceptionTests() {
        assertThrows(IllegalArgumentException.class, () -> new PolynomialTerm(-1, "x", -3));
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
        assertFalse(func1.getDomain().get(0).isIncludeLowerBound());
        assertFalse(func1.getDomain().get(0).isIncludeUpperBound());
    }

    @Test
    public void range_tests() {
        PolynomialFunction func1 = functionSample1().get(0);
        assertFalse(func1.getRange().get(0).isIncludeLowerBound());
        assertFalse(func1.getRange().get(0).isIncludeUpperBound());
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
        assertEquals("Q(x) = 5.29x⁴ + 9.66x² + 4.41", func4.toString(false));

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
        PolynomialFunction yFunc = new PolynomialFunction(yTerms, "Y", "y");
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
        PolynomialFunction yFunc = new PolynomialFunction(yTerms, "Y", "y");
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
    public void divideTests() {
        PolynomialFunction f1 = this.functionSample3().get(0); // P(x) = x + 1
        PolynomialFunction f2 = this.functionSample3().get(1); // Q(x) = x^4 + 7x^2

        HashMap<PolynomialFunction, PolynomialFunction> result = f2.divideBy(f1);

        assertEquals(1, result.size());
        assertEquals("Q(x) = x³ - x² + 8x - 8", result.keySet().toArray()[0].toString());
        assertEquals("Q(x) = 8", result.values().toArray()[0].toString());

        // Testing for equality when reconstructing the original function (dividend)
        PolynomialFunction res = (PolynomialFunction) result.keySet().toArray()[0];
        res = res.multiplyBy(f1);
        PolynomialFunction rem = (PolynomialFunction) result.values().toArray()[0];
        res.add(rem);
        assertEquals(f2.toString(false), res.toString(false));
    }

    @Test
    public void compose_tests() {
        final PolynomialFunction func1 = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(1.0, "x", 2));
            add(new PolynomialTerm(2.0, "x", 1));
            add(new PolynomialTerm(3.0, "x", 0));
        }}, "f", "x");
        final PolynomialFunction func2 = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(1.0, "x", 1));
            add(new PolynomialTerm(-1.0, "x", 0));
        }}, "g", "x");

        assertEquals("f(x) = x² + 2", func1.composeWith(func2).toString(false));
        assertEquals("g(x) = x² + 2x + 2", func2.composeWith(func1).toString(false));
    }

    @Test
    public void factorTests() {
        final PolynomialFunction func = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(5)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(4)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(3)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "f", "x", false); // f(x) = x⁵ + x⁴ - x³ - x²

        final PolynomialFunction expectedFunc1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(1)
                    .build());
        }}, "f", "x", false); // f(x) = x
        final PolynomialFunction expectedFunc2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x", false); // f(x) = x - 1
        final PolynomialFunction expectedFunc3 = new PolynomialFunction(
                new LinkedList<>(){{
                    add(PolynomialTerm.builder()
                            .coefficient(1)
                            .varName("x")
                            .exponent(1)
                            .build());
                    add(PolynomialTerm.builder()
                            .coefficient(1)
                            .varName("x")
                            .exponent(0)
                            .build());
                }}, "f", "x", false); // f(x) = x + 1

        func.factor();

        assertEquals(3, func.getFactorsToMultiplicity().size());

        assertTrue(func.getFactorsToMultiplicity().keySet().contains(expectedFunc1));
        assertTrue(func.getFactorsToMultiplicity().keySet().contains(expectedFunc2));
        assertTrue(func.getFactorsToMultiplicity().keySet().contains(expectedFunc3));

        assertEquals(2, func.getFactorsToMultiplicity().get(expectedFunc1));
        assertEquals(1, func.getFactorsToMultiplicity().get(expectedFunc2));
        assertEquals(2, func.getFactorsToMultiplicity().get(expectedFunc3));

        PolynomialFunction reconstructedFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .varName("x")
                    .exponent(0)
                    .coefficient(1)
                    .build());
        }}, "f", "x", false);
        for (PolynomialFunction factor : func.getFactorsToMultiplicity().keySet()) {
            reconstructedFunc = reconstructedFunc.multiplyBy(factor.power(func.getFactorsToMultiplicity().get(factor)));
        }
        assertEquals("f(x) = x⁵ + x⁴ - x³ - x²", reconstructedFunc.toString(false));
    }

    @Test
    public void factorTests_degree1() {
        final PolynomialFunction func = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(1)
                    .varName("x")
                    .exponent(1)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "f", "x", false); // f(x) = x - 1

        func.factor();

        System.out.println(func.getFactorsToMultiplicity().keySet());
        assertEquals(1, func.getFactorsToMultiplicity().keySet().size());
        assertTrue(func.getFactorsToMultiplicity().containsKey(func));
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
        PolynomialFunction func1Integral = (PolynomialFunction) func1.integral();
        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        PolynomialFunction func2Integral = (PolynomialFunction) func2.integral();
        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x
        PolynomialFunction func3Integral = (PolynomialFunction) func3.integral();

        assertFalse(func1.isIndefiniteIntegral());
        assertFalse(func2.isIndefiniteIntegral());
        assertFalse(func2.isIndefiniteIntegral());

        assertTrue(func1Integral.isIndefiniteIntegral());
        assertTrue(func2Integral.isIndefiniteIntegral());
        assertTrue(func3Integral.isIndefiniteIntegral());

        assertEquals("∫P(x)dx = 0.55x² + C", (func1Integral.toString(true)));
        assertEquals("∫Q(x)dx = 0.7667x³ + C", (func2Integral.toString(true)));
        assertEquals("∫S(x)dx = 0.8x⁴ + 1.0333x³ + 1.65x² + C", (func3Integral.toString(true)));

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

    @Test
    public void simplify_tests() {
        PolynomialFunction f1 = new PolynomialFunction(new LinkedList<>(), "f", "x");

        assertTrue(f1.isZeroFunction());

        PolynomialFunction f2 = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(1.0, "x", 1));
            add(new PolynomialTerm(1.0, "x", 2));
        }}, "f", "x");

        assertEquals("f(x) = x² + x", f2.simplify().toString());

        PolynomialFunction f3 = new PolynomialFunction(new LinkedList<>(List.of(new PolynomialTerm(1.0, "x", 0))), "g", "x");
        assertEquals("g() = 1", f3.simplify().toString());
    }

    @Test
    public void equals_tests() {
        PolynomialFunction func1 = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(1.0, "x", 1));
            add(new PolynomialTerm(1.0, "x", 2));
        }}, "f", "x");
        PolynomialFunction func2 = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(1.0, "x", 0));
            add(new PolynomialTerm(1.0, "x", 2));
        }}, "g", "x");
        PolynomialFunction func3 = func1;

        assertNotEquals(func1, func2);
        assertEquals(func1, func3);

        assertNotEquals(func1, new ConstantFunction("h", 1.0, false));
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
        final PolynomialFunction func1 = new PolynomialFunction(func1Terms, "P", "x");

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
        final PolynomialFunction func2 = new PolynomialFunction(func2Terms, "Q", "x");

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
        final PolynomialFunction func3 = new PolynomialFunction(func3Terms, "S", "x");

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
        final PolynomialFunction func4 = new PolynomialFunction(func4Terms, "S", "x");

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
        final PolynomialFunction func1 = new PolynomialFunction(func1Terms, "P", "x");

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
        final PolynomialFunction func2 = new PolynomialFunction(func2Terms, "Q", "x");

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
        final PolynomialFunction func3 = new PolynomialFunction(func3Terms, "S", "x");

        final LinkedList<PolynomialFunction> funcList = new LinkedList<>();
        funcList.add(func1);
        funcList.add(func2);
        funcList.add(func3);

        return funcList;
    }

    private List<PolynomialFunction> functionSample3() {
        final PolynomialTerm term1_1 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(1)
                .build();
        final PolynomialTerm term1_2 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(0)
                .build();
        final LinkedList<PolynomialTerm> func1Terms = new LinkedList<>();
        func1Terms.add(term1_1);
        func1Terms.add(term1_2);
        // P(x) = x + 1
        final PolynomialFunction func1 = new PolynomialFunction(func1Terms, "P", "x");

        final PolynomialTerm term2_1 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(4)
                .build();
        final PolynomialTerm term2_2 = PolynomialTerm.builder()
                .coefficient(7.0)
                .varName("x")
                .exponent(2)
                .build();
        final LinkedList<PolynomialTerm> func2Terms = new LinkedList<>();
        func2Terms.add(term2_1);
        func2Terms.add(term2_2);
        // Q(x) = x^4 + 7x^2
        final PolynomialFunction func2 = new PolynomialFunction(func2Terms, "Q", "x");

        final PolynomialTerm term3_1 = PolynomialTerm.builder()
                .coefficient(1.0)
                .varName("x")
                .exponent(2)
                .build();
        final PolynomialTerm term3_2 = PolynomialTerm.builder()
                .coefficient(-1.0)
                .varName("x")
                .exponent(0)
                .build();
        final LinkedList<PolynomialTerm> func3Terms = new LinkedList<>();
        func3Terms.add(term3_1);
        func3Terms.add(term3_2);
        // S(x) = x^2 - 1
        final PolynomialFunction func3 = new PolynomialFunction(func3Terms, "S", "x");

        final LinkedList<PolynomialFunction> funcList = new LinkedList<>();
        funcList.add(func1);
        funcList.add(func2);
        funcList.add(func3);

        return funcList;
    }

    @Test
    public void descartesRuleOfSign_tests() {
        PolynomialFunction func1 = functionSample1().get(0); // P(x) = + 1.1x
        PolynomialFunction func2 = functionSample1().get(1); // Q(x) = + 2.1 + 2.3x^2
        PolynomialFunction func3 = functionSample1().get(2); // S(x) = + 3.1x^2 + 3.2x^3 + 3.3x
        PolynomialFunction func4 = new PolynomialFunction(new LinkedList<>(){{
            add(new PolynomialTerm(-1.0, "x", 1));
            add(new PolynomialTerm(1.0, "x", 2));
            add(new PolynomialTerm(-1.0, "x", 3));
        }}, "P", "x");

        final int[] func1SignChanges = func1.runDescartesRuleOfSign();
        assertEquals(0, func1SignChanges[0]);
        assertEquals(0, func1SignChanges[1]);

        final int[] func2SignChanges = func2.runDescartesRuleOfSign();
        assertEquals(0, func2SignChanges[0]);
        assertEquals(0, func2SignChanges[1]);

        final int[] func3SignChanges = func3.runDescartesRuleOfSign();
        assertEquals(0, func3SignChanges[0]);
        assertEquals(2, func3SignChanges[1]);

        final int[] func4SignChanges = func4.runDescartesRuleOfSign();
        assertEquals(2, func4SignChanges[0]);
        assertEquals(0, func4SignChanges[1]);
    }
}
