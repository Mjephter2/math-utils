package models.shapes.polygons;

import java.util.Arrays;

/**
 * Class to represent a triangle.
 */
public class Triangle implements Polygon {

    private final double[] sides;
    private final double[] angles;

    public Triangle(final double[] sides) {
        if (Arrays.stream(sides).anyMatch(side -> side <= 0)) {
            throw new IllegalArgumentException("Triangle sides must be positive.");
        }
        if (sides.length != 3) {
            throw new IllegalArgumentException("Triangle must have 3 sides.");
        }
        this.sides = sides;
        this.angles = Triangle.calculateAngles(sides);
    }

    @Override
    public double area() {
        // use Heron's formula
        // reference: https://en.wikipedia.org/wiki/Heron%27s_formula
        final double s = this.perimeter() / 2;
        final double area =  Math.sqrt(s * (s - this.sides[0]) * (s - this.sides[1]) * (s - this.sides[2]));
        return Math.round(area * 100.0) / 100.0;
    }

    /**
     * @return the perimeter of the triangle.
     */
    @Override
    public double perimeter() {
        return Arrays.stream(this.sides).sum();
    }

    /**
     * @return the sides of the triangle.
     */
    @Override
    public double[] sides() {
        return this.sides;
    }

    private static double[] calculateAngles(final double[] sides) {
        // use the law of cosines
        // reference: https://en.wikipedia.org/wiki/Law_of_cosines
        double[] angles = new double[3];
        for (int i = 0; i < sides.length; i++) {
            final float cosine = (float) ((Math.pow(sides[(i + 1) % 3], 2) + Math.pow(sides[(i + 2) % 3], 2) - Math.pow(sides[i], 2)) / (2 * sides[(i + 1) % 3] * sides[(i + 2) % 3]));
            angles[i] = Math.round(Math.toDegrees(Math.acos(cosine)) * 100.0) / 100.0;
        }
        return angles;
    }

    /**
     * @return the angles of the triangle.
     */
    @Override
    public double[] angles() {
        return this.angles;
    }
}
