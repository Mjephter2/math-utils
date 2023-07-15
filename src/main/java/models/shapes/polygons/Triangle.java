package models.shapes.polygons;

import java.util.Arrays;

public class Triangle implements Polygon {

    private final double[] sides;

    public Triangle(final double[] sides) {
        if (sides.length != 3) {
            throw new IllegalArgumentException("Triangle must have 3 sides.");
        }
        this.sides = sides;
    }

    @Override
    public double area() {
        // use Heron's formula
        final double s = this.perimeter() / 2;
        final double area =  Math.sqrt(s * (s - this.sides[0]) * (s - this.sides[1]) * (s - this.sides[2]));
        return Math.round(area * 100.0) / 100.0;
    }

    @Override
    public double perimeter() {
        return Arrays.stream(this.sides).sum();
    }

    @Override
    public double[] sides() {
        return this.sides;
    }
}
