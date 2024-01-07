package models.linearAlgebra;

import lombok.Builder;
import lombok.Getter;

import java.rmi.MarshalException;

/**
 * Class representing a matrix.
 */
@Getter
public class Matrix {

    private final String name;
    private final Dimension dimension;
    private final double[][] body;

    public Matrix(String name, double[][] body) {
        this.name = name;
        this.body = body;
        this.dimension = new Dimension(body.length, body[0].length);
    }

    /**
     * Checks if the matrix is square.
     * @return true if the matrix is square, false otherwise.
     */
    public boolean isSquare() {
        return this.dimension.getNumRows() == this.dimension.getNumCols();
    }

    /**
     * Checks if the matrix is a column vector.
     * @return true if the matrix is a column vector, false otherwise.
     */
    public boolean isColumnVector() {
        return this.dimension.getNumCols() == 1;
    }

    /**
     * Checks if the matrix is a row vector.
     * @return true if the matrix is a row vector, false otherwise.
     */
    public boolean isRowVector() {
        return this.dimension.getNumRows() == 1;
    }

    public Matrix negate() {
        final double[][] result = new double[this.dimension.getNumRows()][this.dimension.getNumCols()];
        for (int i = 0; i < this.dimension.getNumRows(); i++) {
            final double[] row = body[i];
            for (int j = 0; j < this.dimension.getNumCols(); j++) {
                result[i][j] = -row[j];
            }
        }
        return new Matrix("-" + name, result);
    }

    public Matrix add(final Matrix other) {
        final int numRows = this.dimension.getNumRows();
        final int numCols = this.dimension.getNumCols();

        if (numRows != other.dimension.getNumRows() || numCols != other.dimension.getNumCols()) {
            throw new IllegalArgumentException("Matrices must have the same dimensions.");
        }
        final double[][] result = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            final double[] row = body[i];
            final double[] otherRow = other.body[i];
            for (int j = 0; j < numCols; j++) {
                result[i][j] = row[j] + otherRow[j];
            }
        }
        return new Matrix(name + " + " + other.name, result);
    }

    public Matrix subtract(final Matrix other) {
        return add(other.negate());
    }

    public Matrix multiply(final double scalar) {
        final int numRows = this.dimension.getNumRows();
        final int numCols = this.dimension.getNumCols();

        final double[][] result = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            final double[] row = body[i];
            for (int j = 0; j < numCols; j++) {
                result[i][j] = scalar * row[j];
            }
        }
        return new Matrix(scalar + name, result);
    }



    public Matrix transpose() {
        final int numRows = this.dimension.getNumRows();
        final int numCols = this.dimension.getNumCols();

        final double[][] result = new double[numCols][numRows];
        for (int i = 0; i < numRows; i++) {
            final double[] row = body[i];
            for (int j = 0; j < numCols; j++) {
                result[j][i] = row[j];
            }
        }
        return new Matrix(name + "^T", result);
    }

    public boolean equals(final Matrix other) {
        final int numRows = this.dimension.getNumRows();
        final int numCols = this.dimension.getNumCols();

        if (numRows != other.dimension.getNumRows() || numCols != other.dimension.getNumCols()) {
            return false;
        }
        for (int i = 0; i < numRows; i++) {
            final double[] row = body[i];
            final double[] otherRow = other.body[i];
            for (int j = 0; j < numCols; j++) {
                if (row[j] != otherRow[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        final int numRows = this.dimension.getNumRows();
        final int numCols = this.dimension.getNumCols();

        final StringBuilder sb = new StringBuilder();
        sb.append(name).append(" =\n");
        for (int i = 0; i < numRows; i++) {
            sb.append("\t[");
            for (int j = 0; j < numCols; j++) {
                sb.append(body[i][j]);
                if (j != numCols - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}

@Getter
class Dimension {
    private final int numRows;
    private final int numCols;

    public Dimension(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }
}
