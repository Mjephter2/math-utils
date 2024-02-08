package models.functions.combinations;

import models.functions.ConstantFunction;
import models.functions.Function;
import models.functions.FunctionType;
import models.numberUtils.Range;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.functions.radicals.RadicalFunction;
import models.functions.radicals.SquareRootFunction;
import models.functions.trigonometric.TrigonometricFunction;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

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
    private boolean isIndefiniteIntegral;

    private final List<PolynomialFunction> polynomialFactors;
    private final List<RadicalFunction> radicalFactors;
    private final List<ExponentialFunction> exponentialFunctions;
    private final List<RationalFunction> rationalFunctions;
    private final List<TrigonometricFunction> trigonometricFunctions;
    private List<Function> others = new LinkedList<>();
    private List<ConstantFunction> constantFunctions;

    public CompositeFunction(final String funcName, final List<Function> compositeFactors) {
        this.funcName = funcName;
        this.varName = compositeFactors.get(0).getVarName();

        this.constantFunctions = compositeFactors.stream()
                .filter(ConstantFunction.class::isInstance)
                .map(ConstantFunction.class::cast)
                .collect(Collectors.toList());

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

        this.others = compositeFactors.stream()
                .filter(func -> !(
                        func instanceof ConstantFunction ||
                        func instanceof PolynomialFunction ||
                        func instanceof SquareRootFunction ||
                        func instanceof ExponentialFunction ||
                        func instanceof RationalFunction ||
                        func instanceof TrigonometricFunction
                ))
                .collect(Collectors.toList());

        if (constantFunctions.size() + polynomialFactors.size() + radicalFactors.size() + exponentialFunctions.size() + rationalFunctions.size() + trigonometricFunctions.size() + this.others.size() != compositeFactors.size()) {
            throw new IllegalArgumentException("Composite functions is not implemented for requested function types");
        }
        this.reduceConstantFunctions();
    }

    private void reduceConstantFunctions() {
        final double combValue = this.constantFunctions.stream().map(func -> func.getValue()).reduce(1.0, (d1, d2) -> d1 * d2);
        this.constantFunctions = List.of(ConstantFunction.builder()
                        .value(combValue)
                        .funcName("combConstant")
                .build());
    }

    public CompositeFunction(
            final String funcName,
            final List<PolynomialFunction> polynomialFactors,
            final List<RadicalFunction> radicalFactors,
            final List<ExponentialFunction> exponentialFunctions,
            final List<RationalFunction> rationalFunctions,
            final List<TrigonometricFunction> trigonometricFunctions) {
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
                .varName(this.varName)
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
    public List<Range> getDomain() {
        // TODO: Implement domain for CompositeFunction
//        return Stream.of(this.polynomialFactors, this.radicalFactors)
//                .flatMap(List::stream)
//                .map(Function::getDomain)
//                .reduce(Range::intersection)
//                .orElse(null);
        throw new UnsupportedOperationException("Unimplemented method 'getDomain'");
    }

    @Override
    public List<Range> getRange() {
        // TODO: Implement range for CompositeFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        return Stream.of(
                this.polynomialFactors,
                this.radicalFactors,
                this.rationalFunctions,
                this.exponentialFunctions,
                this.trigonometricFunctions
                )
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

        if (this.constantFunctions != null && !this.constantFunctions.isEmpty()) {
            for (final ConstantFunction constantFunction : this.constantFunctions) {
                stringBuilder.append("( ").append(constantFunction.printBody()).append(" )");
            }
        }

        if (this.polynomialFactors != null && !this.polynomialFactors.isEmpty()) {
            for (final PolynomialFunction polynomialFunction : this.polynomialFactors) {
                stringBuilder.append("( ").append(polynomialFunction.printBody()).append(" )");
            }
        }

        if (this.radicalFactors != null && !this.radicalFactors.isEmpty()) {
            for (final RadicalFunction radicalFunction : this.radicalFactors) {
                stringBuilder.append(" ").append(radicalFunction.printBody()).append(" )");
            }
        }

        if (this.exponentialFunctions != null && !this.exponentialFunctions.isEmpty()) {
            for (final ExponentialFunction exponentialFunction : this.exponentialFunctions) {
                stringBuilder.append("( ").append(exponentialFunction.printBody()).append(" )");
            }
        }

        if (this.rationalFunctions != null && !this.rationalFunctions.isEmpty()) {
            for (final RationalFunction rationalFunction : this.rationalFunctions) {
                stringBuilder.append("( ").append(rationalFunction.printBody()).append(" )");
            }
        }

        if (this.trigonometricFunctions != null && !this.trigonometricFunctions.isEmpty()) {
            for (final TrigonometricFunction trigonometricFunction : this.trigonometricFunctions) {
                stringBuilder.append("( ").append(trigonometricFunction.printBody()).append(" )");
            }
        }

        if (this.others != null && !this.others.isEmpty()) {
            for (final Function other : this.others) {
                stringBuilder.append("( ").append(other.printBody()).append(" )");
            }
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
                        .map(RadicalFunction.class::cast)
                        .collect(Collectors.toList()))
                .trigonometricFunctions(this.trigonometricFunctions.stream()
                        .map(func -> func.deepCopy(func.getFuncName()))
                        .map(TrigonometricFunction.class::cast)
                        .collect(Collectors.toList()))
                .exponentialFunctions(this.exponentialFunctions.stream()
                        .map(func -> func.deepCopy(func.getFuncName()))
                        .map(ExponentialFunction.class::cast)
                        .collect(Collectors.toList()))
                .rationalFunctions(this.rationalFunctions.stream()
                        .map(func -> func.deepCopy(func.getFuncName()))
                        .map(RationalFunction.class::cast)
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
