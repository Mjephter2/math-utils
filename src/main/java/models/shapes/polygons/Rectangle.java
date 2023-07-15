package models.shapes.polygons;

/**
 * Class to represent a rectangle.
 */
public class Rectangle implements Polygon {

    /**
     * The length of the rectangle.
     */
    private final double length;

    /**
     * The width of the rectangle.
     */
    private final double width;

    public Rectangle(final double length, final double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return this.length * this.width;
    }

    @Override
    public double perimeter() {
        return 2 * (this.length + this.width);
    }

    @Override
    public double[] sides() {
        return new double[] {this.length, this.width, this.length, this.width};
    }
}
