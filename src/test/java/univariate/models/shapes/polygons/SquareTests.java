package univariate.models.shapes.polygons;

import geometry.shapes.polygons.Polygon;
import geometry.shapes.polygons.quadrilaterals.Square;
import org.junit.jupiter.api.Test;

public class SquareTests {

    @Test
    public void square_test_succeeds() {
        final Polygon square = new Square(2);

        assert square.area() == 4;
        assert square.perimeter() == 8;
        assert square.sides().length == 4;
        assert square.angles().length == 4;

        assert square.sides()[0] == 2;
        assert square.sides()[1] == 2;
        assert square.sides()[2] == 2;
        assert square.sides()[3] == 2;

        assert square.angles()[0] == 90;
        assert square.angles()[1] == 90;
        assert square.angles()[2] == 90;
        assert square.angles()[3] == 90;

        assert square.anglesSum() == 360;
    }
}
