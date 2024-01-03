package models.equations.polynomial_equations;

import lombok.Getter;
import models.equations.Equation;
import models.functions.polynomials.PolynomialFunction;
import models.functions.polynomials.PolynomialTerm;
import models.numberUtils.Range;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Class representing a polynomial equation.
 */
@Getter
public class PolynomialEquation implements Equation {

    private final PolynomialFunction leftSide;
    private final PolynomialFunction rightSide;
    private int degree;
    HashMap<Range, Integer> solutions;

    /**
     * Constructs a PolynomialEquation with provided left and right side expressions.
     * Automatically reduces the right side to zero.
     * @param left - left side of the equation
     * @param right - right side of the equation
     */
    public PolynomialEquation(final PolynomialFunction left, final PolynomialFunction right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Null argument(s) passed.");
        }
        this.leftSide = left;
        this.rightSide = right;

        this.reduce();
    }

    /**
     * Reduce the RHS to zero
     */
    protected void reduce() {
        while (!this.rightSide.getTerms().isEmpty()) {
            PolynomialTerm term = this.rightSide.getTerms().get(0);
            PolynomialTerm subtractedTerm = new PolynomialTerm(-1 * term.getCoefficient(), term.getVarName(), term.getExponent());
            this.leftSide.addTerm(subtractedTerm);
            this.rightSide.addTerm(subtractedTerm);
        }
        this.degree = computeDegree();
    }

    /**
     * Computes the degree of the polynomial equation.
     */
    private int computeDegree() {
        return Stream.of(this.leftSide.getTerms(), this.rightSide.getTerms())
                .map(terms -> terms.stream()
                        .mapToInt(PolynomialTerm::getExponent)
                        .max()
                        .orElse(0))
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public void solve() {
        // TODO: Implement solver for more polynomial equations degrees
        if (this.degree == 1) {
            final LinearEquation linearEquation =  new LinearEquation(this.leftSide, this.rightSide);
            linearEquation.solve();
            this.solutions = linearEquation.getSolutions();
        } else if (this.degree == 2) {
            final QuadraticEquation quadraticEquation = new QuadraticEquation(this.leftSide, this.rightSide);
            quadraticEquation.solve();
            this.solutions = new HashMap<>(quadraticEquation.getSolutions());
        } else {
            throw new UnsupportedOperationException("Unimplemented solver for polynomial equations of degree " + this.degree + ".");
        }
    }

    /**
     * Approximate solution of the equation.
     */
    public void approxSolve() {
    }

    public static void main(String[] args) {
        final PolynomialFunction lhs = new PolynomialFunction(new LinkedList<>(){{
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
        }}, "f", "x");
        final PolynomialFunction rhs = new PolynomialFunction(new LinkedList<>(){{
            add(PolynomialTerm.builder()
                    .coefficient(-1)
                    .varName("x")
                    .exponent(2)
                    .build());
            add(PolynomialTerm.builder()
                    .coefficient(3)
                    .varName("x")
                    .exponent(0)
                    .build());
        }}, "g", "x");

        final PolynomialEquation equation = new PolynomialEquation(lhs, rhs);

        int[] signsMax = equation.getLeftSide().runDescartesRuleOfSign();
        System.out.println("Max positive signs: " + signsMax[0]);
        System.out.println("Max negative signs: " + signsMax[1]);

        System.out.println(equation.print());
        equation.approxSolve();
    }
}
