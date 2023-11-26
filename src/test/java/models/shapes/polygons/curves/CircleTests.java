package models.shapes.polygons.curves;

import models.shapes.curves.Circle;
import models.shapes.curves.ClosedCurve;
import org.junit.jupiter.api.Test;

public class CircleTests {

    @Test
    public void circle_succeeds() {
        final ClosedCurve circle = new Circle(3);

        assert circle.area() == 28.27;
        assert circle.perimeter() == 18.85;
    }
}
