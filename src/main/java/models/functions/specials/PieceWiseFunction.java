package models.functions.specials;

import lombok.Builder;
import lombok.Getter;
import models.functions.Function;
import models.functions.FunctionType;
import models.numberUtils.Range;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class implementing a Piece Wise function.
 */
@Getter
public class PieceWiseFunction implements Function {

    private String funcName;
    private String varName;
    private Map<Function, Range> functionsToRangesMap;

    @Override
    public String getFuncName() {
        return this.funcName;
    }

    @Override
    public String getVarName() {
        return this.varName;
    }

    public PieceWiseFunction(final String functionName, final String variableName, final Map<Function, Range> funcToRangeMap) {
        this.funcName = functionName;
        this.varName = variableName;
        this.functionsToRangesMap = funcToRangeMap;
        if (!validateDomains()) {
            throw new IllegalArgumentException("Overlapping Domains found!");
        }
    }

    @Override
    public FunctionType getFuncType() {
        return FunctionType.PIECEWISE;
    }

    /**
     * Validate the set of given domains
     * Two domains must not overlap and must be a subset of the domain of the corresponding function
     * i.e. the intersection between all the provided domains must be null
     */
    public boolean validateDomains() {
        final Range[] domains = this.functionsToRangesMap.values().toArray(new Range[0]);
        // Compare all ranges against one another and check for overlaps
        for (int i = 0; i < domains.length - 1; i++) {
            if (domains[i].intersection(domains[i + 1]) != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Range> getDomain() {
        // TODO: Implement getDomain for PieceWiseFunction
        throw new UnsupportedOperationException("Unimplemented method 'getDomain'");
    }

    @Override
    public List<Range> getRange() {
        // TODO: Implement getRange for PieceWiseFunction
        throw new UnsupportedOperationException("Unimplemented method 'getRange'");
    }

    @Override
    public double evaluate(Double... values) {
        final Optional<Function> funcToEval = functionsToRangesMap.keySet().stream().filter(
                function -> functionsToRangesMap.get(function).includes(values[0])
        ).findFirst();

        if (!funcToEval.isEmpty()) {
            return funcToEval.get().evaluate(values);
        }

        throw new IllegalArgumentException("This function is not defined for the provided value!");
    }

    @Override
    public Function simplify() {
        // TODO: Implement simplify for PieceWiseFunction
        throw new UnsupportedOperationException("Unimplemented method 'simplify'");
    }

    @Override
    public Function derivative() {
        Map<Function, Range> newFunctionsToRangesMap = this.functionsToRangesMap.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().derivative(), Map.Entry::getValue));
        return new PieceWiseFunction(this.funcName + "'", this.varName, newFunctionsToRangesMap);
    }

    @Override
    public Function integral() {
        // TODO: Implement integral for PieceWiseFunction
        throw new UnsupportedOperationException("integral() not yet implemented for PieceWiseFunction");
    }

    @Override
    public double integral(double lowerBound, double upperBound) {
        // TODO: Implement definite integral for PieceWiseFunction
        throw new UnsupportedOperationException("integral(l,h) not yet implemented for PieceWiseFunction");
    }

    @Override
    public double limit(double value) {
        // TODO: Implement limit for PieceWiseFunction
        throw new UnsupportedOperationException("limit() not yet implemented for PieceWiseFunction");
    }

    @Override
    public String printBody() {
        return this.functionsToRangesMap.entrySet().stream()
                .map(entry ->  entry.getKey().printBody() + "  for " + entry.getValue().toString())
                .collect(Collectors.joining(", "));
    }

    @Override
    public Function deepCopy(final String newFuncName) {
        return new PieceWiseFunction(
                newFuncName,
                this.varName,
                this.functionsToRangesMap.entrySet().stream()
                        .collect(Collectors.toMap(entry -> entry.getKey().deepCopy(entry.getKey().getFuncName()), Map.Entry::getValue)));
    }
}
