package geometry.shapes.polygons;

/**
 * This interface is used to represent a shape.
 */
public interface Polygon {

    /**
     * @return the area of the polygon.
     */
    double area();

    /**
     * @return the perimeter of the polygon.
     */
    double perimeter();

    /**
     * @return the sides of the polygon.
     */
    double[] sides();

    /**
     * @return the angles of the polygon.
     */
    double[] angles();

    /**
     * @return a string representation of the polygon.
     */
    String toString();

    /**
     * @return the sum of the angles of the polygon.
     */
    default double anglesSum() {
        return 180 * (this.sides().length - 2);
    }
}
