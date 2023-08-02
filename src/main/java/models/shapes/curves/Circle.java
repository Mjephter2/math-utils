package models.shapes.curves;

import static java.lang.Math.PI;
import static utils.NumberUtils.round;

public class Circle implements ClosedCurve {

    /**
     * The radius of the circle.
     */
    private final double radius;

    public Circle(final double radius) {
        this.radius = radius;
    }

    @Override
    public double perimeter() {
        return round(2 * PI * this.radius, 2);
    }

    @Override
    public double area() {
        return round(PI * Math.pow(this.radius, 2), 2);
    }
}
