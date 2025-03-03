package geometry.shapes.curves;

/**
 * This interface is used to represent a closed curve.
 */
public interface ClosedCurve {

    /**
     * @return the perimeter of the closed curve.
     */
    double perimeter();

    /**
     * @return the area of the closed curve.
     */
    double area();
}
