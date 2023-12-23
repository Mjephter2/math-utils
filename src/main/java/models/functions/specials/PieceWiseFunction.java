package models.functions.specials;

import lombok.Builder;
import lombok.Getter;
import models.functions.Function;
import models.functions.FunctionType;
import models.numberUtils.Range;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class implementing a Piece Wise function.
 */
@Getter
@Builder
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

    @Override
    public FunctionType getFuncType() {
        return FunctionType.PIECEWISE;
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
        return this.functionsToRangesMap.keySet().stream()
                .filter(function -> function.getDomain().get(0).includes(values[0]))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .evaluate(values);
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
        return PieceWiseFunction.builder()
                .funcName(this.funcName + "'")
                .varName(this.varName)
                .functionsToRangesMap(newFunctionsToRangesMap)
                .build();
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
        return PieceWiseFunction.builder()
                .funcName(newFuncName)
                .varName(this.varName)
                .functionsToRangesMap(this.functionsToRangesMap.entrySet().stream()
                        .collect(Collectors.toMap(entry -> entry.getKey().deepCopy(entry.getKey().getFuncName()), Map.Entry::getValue)))
                .build();
    }
}
