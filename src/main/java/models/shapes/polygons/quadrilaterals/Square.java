package models.shapes.polygons.quadrilaterals;

import models.shapes.polygons.quadrilaterals.Rectangle;

/**
 * Class to represent a square.
 * A square is a rectangle with all sides equal.
 */
public class Square extends Rectangle {

    public Square(final double sideLength) {
        super(sideLength, sideLength);
    }
}
