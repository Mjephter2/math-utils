package univariate.models.functions;

import calculus.univariate.models.functions.ConstantFunction;
import calculus.univariate.models.functions.Function;
import calculus.univariate.models.functions.FunctionType;
import calculus.univariate.models.functions.polynomials.PolynomialFunction;
import calculus.univariate.models.functions.polynomials.PolynomialTerm;
import calculus.univariate.models.functions.radicals.RadicalFunction;
import calculus.univariate.models.functions.radicals.SquareRootFunction;
import calculus.univariate.models.numberUtils.Range;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RadicalFunctionTests {

    @Test
    public void builderTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc)
                .build();

        assertEquals("f(x) = [3.0]√(2x²)", func.printFunc());

        assertEquals("f", func.getFuncName());
        assertEquals("x", func.getVarName());
        assertEquals(3, func.getRootIndex());
        assertEquals(innerFunc, func.getBody());
        assertEquals(FunctionType.RADICAL, func.getFuncType());
        assertFalse(func.isIndefiniteIntegral());

        final Function copy = func.deepCopy("copy");
        assertEquals("copy(x) = [3.0]√(2x²)", copy.printFunc());
    }

    @Test
    public void evaluateTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc)
                .build();

        assertEquals(8.0, func.evaluate(1.0), 0.001);
        assertEquals(512.0, func.evaluate(2.0), 0.0);
    }

    @Test
    public void domainTests() {
        final PolynomialFunction innerFunc1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");
        final PolynomialFunction innerFunc2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(3)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func1 = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc1)
                .build();
        final RadicalFunction func2 = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(2)
                .body(innerFunc2)
                .build();

        assertEquals("f(x) = [3.0]√(2x²)", func1.printFunc());
        assertEquals("f(x) = [2.0]√(2x³)", func2.printFunc());

        assertEquals("f", func1.getFuncName());
        assertEquals("x", func1.getVarName());
        assertEquals(3, func1.getRootIndex());
        assertEquals(innerFunc1, func1.getBody());
        assertEquals(FunctionType.RADICAL, func1.getFuncType());

        assertEquals("f", func2.getFuncName());
        assertEquals("x", func2.getVarName());
        assertEquals(2, func2.getRootIndex());
        assertEquals(innerFunc2, func2.getBody());
        assertEquals(FunctionType.RADICAL, func2.getFuncType());

        final List<Range> domain1 = func1.getDomain();
        final List<Range> domain2 = func2.getDomain();
        assertEquals(1, domain1.size());
        assertEquals(1, domain2.size());
        assertEquals(Range.all(), domain1.get(0));
        assertEquals(Range.allPositive(true), domain2.get(0));
    }

    @Test
    public void simplifyTest() {
        final ConstantFunction innerFunc1 = new ConstantFunction("innerFunc", 9.0, false);
        final PolynomialFunction innerFunc2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func1 = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(1.0/2.0)
                .body(innerFunc1)
                .build();

        final RadicalFunction func2 = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(1.0/4.0)
                .body(innerFunc2)
                .build();

        assertEquals("f(x) = [0.5]√(9)", func1.printFunc());
        assertEquals("f() = 3", func1.simplify().toString());

        assertEquals("f(x) = [0.25]√(2x²)", func2.printFunc());
        assertEquals("f(x) = [0.25]√(2x²)", func2.simplify().printFunc());
    }

    @Test
    public void derivativeTests() {
        final PolynomialFunction innerFunc1 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");
        final PolynomialFunction innerFunc2 = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(3)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func1 = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc1)
                .build();
        final RadicalFunction func2 = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(2)
                .body(innerFunc2)
                .build();

        assertEquals("f(x) = [3.0]√(2x²)", func1.printFunc());
        assertEquals("f(x) = [2.0]√(2x³)", func2.printFunc());

        assertEquals("f", func1.getFuncName());
        assertEquals("x", func1.getVarName());
        assertEquals(3, func1.getRootIndex());
        assertEquals(innerFunc1, func1.getBody());
        assertEquals(FunctionType.RADICAL, func1.getFuncType());

        assertEquals("f", func2.getFuncName());
        assertEquals("x", func2.getVarName());
        assertEquals(2, func2.getRootIndex());
        assertEquals(innerFunc2, func2.getBody());
        assertEquals(FunctionType.RADICAL, func2.getFuncType());

        final Function derivative1 = func1.derivative();
        final Function derivative2 = func2.derivative();
        assertEquals("f'(x) = ( 3 )( 4x )( [2.0]√(2x²) )", derivative1.printFunc());
        assertEquals("f'(x) = ( 2 )( 6x² )( [1.0]√(2x³) )", derivative2.printFunc());
    }

    @Test
    public void exceptionsTest() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");

        final RadicalFunction func = RadicalFunction.builder()
                .funcName("f")
                .varName("x")
                .rootIndex(3)
                .body(innerFunc)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> func.integral());
        assertThrows(UnsupportedOperationException.class, () -> func.integral(0.0, 1.0));
    }

    @Test
    public void squareRootTests() {
        final PolynomialFunction innerFunc = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(2)
                    .varName("x")
                    .exponent(2)
                    .build());
        }}, "innerFunc", "x");

        final SquareRootFunction func = new SquareRootFunction("f", "x", innerFunc);

        assertEquals("f(x) = ²√(2x²)", func.printFunc());
    }
}
