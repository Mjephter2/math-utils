package models.shapes.polygons;

import java.util.LinkedList;

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
     * @return a string representation of the polygon.
     */
    String toString();
}
