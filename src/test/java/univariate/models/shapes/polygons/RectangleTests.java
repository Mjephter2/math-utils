package univariate.models.shapes.polygons;

import geometry.shapes.polygons.Polygon;
import geometry.shapes.polygons.quadrilaterals.Rectangle;
import org.junit.jupiter.api.Test;


public class RectangleTests {

    @Test
    public void test() {
        final Polygon rectangle = new Rectangle(2, 3);

        assert rectangle.area() == 6;
        assert rectangle.perimeter() == 10;
        assert rectangle.sides().length == 4;
        assert rectangle.angles().length == 4;

        assert rectangle.sides()[0] == 2;
        assert rectangle.sides()[1] == 3;
        assert rectangle.sides()[2] == 2;
        assert rectangle.sides()[3] == 3;

        assert rectangle.angles()[0] == 90;
        assert rectangle.angles()[1] == 90;
        assert rectangle.angles()[2] == 90;
        assert rectangle.angles()[3] == 90;

        assert rectangle.anglesSum() == 360;
    }
}
