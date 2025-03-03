package univariate.models.shapes.polygons;

import org.junit.jupiter.api.Test;
import geometry.shapes.polygons.Polygon;
import geometry.shapes.polygons.Triangle;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriangleTests {

    @Test
    public void triangle_succeeds() {
        final Polygon triangle = new Triangle(new double[]{3, 4, 5});

        assertEquals(6.0, triangle.area());
        assertEquals(12.0, triangle.perimeter());
        assertEquals(3, triangle.sides().length);
        assertEquals(3, triangle.angles().length);

        assertEquals(3.0, triangle.sides()[0]);
        assertEquals(4.0, triangle.sides()[1]);
        assertEquals(5.0, triangle.sides()[2]);

        assertEquals(36.87, triangle.angles()[0]);
        assertEquals(53.13, triangle.angles()[1]);
        assertEquals(90.0, triangle.angles()[2]);

        assertEquals(180.0, triangle.anglesSum());
    }

    @Test
    public void triangle_exceptions() {
        try {
            new Triangle(new double[]{-1, 0, 0});
        } catch (IllegalArgumentException e) {
            assertEquals("Triangle sides must be positive.", e.getMessage());
        }

        try {
            new Triangle(new double[]{1, 2});
        } catch (IllegalArgumentException e) {
            assertEquals("Triangle must have 3 sides.", e.getMessage());
        }
    }
}
