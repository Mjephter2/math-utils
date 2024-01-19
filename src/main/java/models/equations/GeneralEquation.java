package models.equations;

import lombok.Builder;
import lombok.Getter;
import models.functions.Function;
import models.numberUtils.Range;

import java.util.HashMap;

@Getter
@Builder
public class GeneralEquation implements Equation {

    final Function leftSide;
    final Function rightSide;

    @Override
    public Function getLeftSide() {
        return null;
    }

    @Override
    public Function getRightSide() {
        return null;
    }

    @Override
    public HashMap<Range, Integer> getSolutions() {
        return null;
    }

    @Override
    public void solve() {

    }

    @Override
    public String print() {
        return Equation.super.print();
    }
}
