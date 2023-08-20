package models.functions;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Range;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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

    private final List<RadicalFunction> radicalFactors;

    private final List<ExponentialFunction> exponentialFunctions;

    public CompositeFunction(final String funcName, final List<Function> compositeFactors) {
        this.funcName = funcName;
        this.varName = compositeFactors.get(0).getVarName();

        this.polynomialFactors = compositeFactors.stream()
                .filter(PolynomialFunction.class::isInstance)
                .map(PolynomialFunction.class::cast)
                .collect(Collectors.toList());

        this.radicalFactors = compositeFactors.stream()
                .filter(RadicalFunction.class::isInstance)
                .map(RadicalFunction.class::cast)
                .collect(Collectors.toList());

        this.exponentialFunctions = compositeFactors.stream()
                .filter(ExponentialFunction.class::isInstance)
                .map(ExponentialFunction.class::cast)
                .collect(Collectors.toList());

        if (polynomialFactors.size() + radicalFactors.size() + exponentialFunctions.size() != compositeFactors.size()) {
            throw new IllegalArgumentException("Composite functions is only implemented for polynomial and radical functions");
        }
    }

    public CompositeFunction(final String funcName, final List<PolynomialFunction> polynomialFactors, final List<RadicalFunction> radicalFactors, final List<ExponentialFunction> exponentialFunctions) {
        this.funcName = funcName;
        this.polynomialFactors = polynomialFactors;
        this.radicalFactors = radicalFactors;
        this.exponentialFunctions = exponentialFunctions;
    }

    /**
     * Multiplies all the factors of the composite function.
     * @return the product of all the factors
     */
    public CompositeFunction reduce() {
        final PolynomialFunction constantUnitFunc = PolynomialFunction.builder()
                .terms(new LinkedList<>(){{
                    add(new PolynomialTerm(1.0, polynomialFactors.get(0).getVarName(), 0));
                }})
                .varName(polynomialFactors.get(0).getVarName())
                .funcName(polynomialFactors.get(0).getFuncName())
                .build();
        
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
        // TODO: Implement
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
    public CompositeFunction derivative() {
        // TODO: Implement
        throw new UnsupportedOperationException("Unimplemented method 'derivative'");
    }

    @Override
    public CompositeFunction integral() {
        // TODO: Implement
        throw new UnsupportedOperationException("Unimplemented method 'integral'");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement
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

        for (final RadicalFunction radicalFunction : this.radicalFactors) {
            stringBuilder.append(" ").append(radicalFunction.printBody()).append(" )");
        }

        for (final ExponentialFunction exponentialFunction : this.exponentialFunctions) {
            stringBuilder.append("( ").append(exponentialFunction.printBody()).append(" )");
        }

        return stringBuilder.toString();
    }

    @Override
    public Function deepCopy() {
        return CompositeFunction.builder()
                .funcName(this.funcName)
                .polynomialFactors(this.polynomialFactors.stream()
                        .map(PolynomialFunction::deepCopy)
                        .map(PolynomialFunction.class::cast)
                        .collect(Collectors.toList()))
                .radicalFactors(this.radicalFactors.stream()
                        .map(RadicalFunction::deepCopy)
                        .map(RadicalFunction.class::cast)
                        .collect(Collectors.toList()))
                .build();
    }
}
