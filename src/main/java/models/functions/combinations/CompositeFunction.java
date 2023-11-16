package models.functions.combinations;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import models.functions.Function;
import models.functions.FunctionType;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.radicals.SquareRootFunction;
import models.functions.trigonometric.TrigonometricFunction;

/**
 * This class implements a composite function.
 * A composite function is a function that is made up of two or more functions as factors.
 */
@Builder
@Getter
@AllArgsConstructor
public class CompositeFunction implements Function {

    private final String funcName;
    private String varName;

    private final List<PolynomialFunction> polynomialFactors;
    private final List<SquareRootFunction> radicalFactors;
    private final List<ExponentialFunction> exponentialFunctions;
    private final List<RationalFunction> rationalFunctions;
    private final List<TrigonometricFunction> trigonometricFunctions;

    public CompositeFunction(final String funcName, final List<Function> compositeFactors) {
        this.funcName = funcName;
        this.varName = compositeFactors.get(0).getVarName();

        this.polynomialFactors = compositeFactors.stream()
                .filter(PolynomialFunction.class::isInstance)
                .map(PolynomialFunction.class::cast)
                .collect(Collectors.toList());

        this.radicalFactors = compositeFactors.stream()
                .filter(SquareRootFunction.class::isInstance)
                .map(SquareRootFunction.class::cast)
                .collect(Collectors.toList());

        this.exponentialFunctions = compositeFactors.stream()
                .filter(ExponentialFunction.class::isInstance)
                .map(ExponentialFunction.class::cast)
                .collect(Collectors.toList());

        this.rationalFunctions = compositeFactors.stream()
                .filter(RationalFunction.class::isInstance)
                .map(RationalFunction.class::cast)
                .collect(Collectors.toList());

        this.trigonometricFunctions = compositeFactors.stream()
                .filter(TrigonometricFunction.class::isInstance)
                .map(TrigonometricFunction.class::cast)
                .collect(Collectors.toList());

        if (polynomialFactors.size() + radicalFactors.size() + exponentialFunctions.size() + rationalFunctions.size() + trigonometricFunctions.size() != compositeFactors.size()) {
            throw new IllegalArgumentException("Composite functions is not implemented requested function types");
        }
    }

    public CompositeFunction(final String funcName, final List<PolynomialFunction> polynomialFactors, final List<SquareRootFunction> radicalFactors, final List<ExponentialFunction> exponentialFunctions, List<RationalFunction> rationalFunctions, List<TrigonometricFunction> trigonometricFunctions) {
        this.funcName = funcName;
        this.polynomialFactors = polynomialFactors;
        this.radicalFactors = radicalFactors;
        this.exponentialFunctions = exponentialFunctions;
        this.rationalFunctions = rationalFunctions;
        this.trigonometricFunctions = trigonometricFunctions;
    }

    /**
     * Multiplies all the factors of the composite function.
     * @return the product of all the factors
     */
    public CompositeFunction reduce() {
        final PolynomialFunction constantUnitFunc = new PolynomialFunction(
                new LinkedList<>(){{ add(new PolynomialTerm(1.0, polynomialFactors.get(0).getVarName(), 0)); }},
                polynomialFactors.get(0).getFuncName(),
                polynomialFactors.get(0).getVarName());
        
        final PolynomialFunction reducedPolynomialFunction = this.polynomialFactors.stream()
            .reduce(constantUnitFunc, PolynomialFunction::multiplyBy);

        return CompositeFunction.builder()
                .polynomialFactors(new LinkedList<PolynomialFunction>() {{
                    add(reducedPolynomialFunction);
                }})
                .radicalFactors(this.radicalFactors)
                .funcName(this.funcName)
                .build();
    }

    @Override
    public String getVarName() {
        return this.varName;
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.OTHER;
    }

    @Override
    public Range<Double> getDomain() {
        return Stream.of(this.polynomialFactors, this.radicalFactors)
                .flatMap(List::stream)
                .map(Function::getDomain)
                .reduce(Range::intersection)
                .orElse(null);
    }

    @Override
    public Range<Double> getRange() {
        // TODO: Implement range for CompositeFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return Stream.of(this.polynomialFactors, this.radicalFactors)
                .flatMap(List::stream)
                .mapToDouble(func -> func.evaluate(values))
                .reduce(1, (a, b) -> a * b);
    }

    @Override
    public Function simplify() {
        // TODO: Implement 'simplify' for CompositeFunction
        throw new UnsupportedOperationException("Unimplemented method 'simplify'");
    }

    @Override
    public CompositeFunction derivative() {
        // TODO: Implement derivative for CompositeFunction
        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public CompositeFunction integral() {
        // TODO: Implement integral for CompositeFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for CompositeFunction
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double limit(double value) {
        return Stream.of(this.polynomialFactors, this.radicalFactors)
                .flatMap(List::stream)
                .mapToDouble(func -> func.limit(value))
                .reduce(1, (a, b) -> a * b);
    }

    /**
     * Returns a string representation of the composite function
     */
    public String toString() {
        return this.printFunc();
    }

    @Override
    public String printBody() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (final PolynomialFunction polynomialFunction : this.polynomialFactors) {
            stringBuilder.append("( ").append(polynomialFunction.printBody()).append(" )");
        }

        for (final SquareRootFunction squareRootFunction : this.radicalFactors) {
            stringBuilder.append(" ").append(squareRootFunction.printBody()).append(" )");
        }

        for (final ExponentialFunction exponentialFunction : this.exponentialFunctions) {
            stringBuilder.append("( ").append(exponentialFunction.printBody()).append(" )");
        }

        for (final RationalFunction rationalFunction : this.rationalFunctions) {
            stringBuilder.append("( ").append(rationalFunction.printBody()).append(" )");
        }

        for (final TrigonometricFunction trigonometricFunction : this.trigonometricFunctions) {
            stringBuilder.append("( ").append(trigonometricFunction.printBody()).append(" )");
        }

        return stringBuilder.toString();
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return CompositeFunction.builder()
                .funcName(this.funcName)
                .polynomialFactors(this.polynomialFactors.stream()
                        .map(func -> func.deepCopy(func.getFuncName()))
                        .map(PolynomialFunction.class::cast)
                        .collect(Collectors.toList()))
                .radicalFactors(this.radicalFactors.stream()
                        .map(func -> func.deepCopy(func.getFuncName()))
                        .map(SquareRootFunction.class::cast)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public boolean equals(final Object other) {
        if (other.hashCode() == this.hashCode()) {
            return true;
        }
        if (other instanceof CompositeFunction) {
            final CompositeFunction otherCompositeFunction = (CompositeFunction) other;
            return this.polynomialFactors.equals(otherCompositeFunction.polynomialFactors) && this.radicalFactors.equals(otherCompositeFunction.radicalFactors);
        }
        return false;
    }
}
