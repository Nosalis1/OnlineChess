package utility.junitTest;

import org.junit.*;
import utility.math.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VectorTest {

    @BeforeClass
    public static void setUpClass(){}

    @AfterClass
    public static void tearDownClass(){}

    @Before
    public void setUp(){}

    @After
    public void tearDown(){}

    @Test
    public void addTest() {
        Vector vector = new Vector(1, 6);
        Vector expected = new Vector(3, 12);

        vector.add(2, 6);

        assertEquals(expected.x, vector.x);
        assertEquals(expected.y, vector.y);
    }

    @Test
    public void subtractTest() {
        Vector vector = new Vector(1, 6);
        Vector expected = new Vector(-5, 5);

        vector.subtract(new Vector(6, 1));

        assertEquals(expected.x, vector.x);
        assertEquals(expected.y, vector.y);
    }

    @Test
    public void multiplyTest(){
        Vector vector = new Vector(1, 6);
        Vector expected = new Vector(2, 12);

        vector.multiply(2);

        assertEquals(expected.x, vector.x);
        assertEquals(expected.y, vector.y);
    }

    @Test
    public void divideTest(){
        Vector vector = new Vector(1, 6);
        Vector expected = new Vector(0, 2);

        vector.divide(3);

        assertEquals(expected.x, vector.x);
        assertEquals(expected.y, vector.y);
    }

    @Test
    public void inBoundsTest() {
        Vector vector = new Vector(1, 6);

        assertTrue(vector.inBounds(0, 8));
    }

    @Test
    public void clampTest(){
        Vector vector = new Vector(1, 6);
        Vector expected = new Vector(3, 5);

        vector.clamp(3,5);

        assertEquals(expected.x, vector.x);
        assertEquals(expected.y, vector.y);
    }

    @Test
    public void linearInterpolationTest(){
        Vector start = new Vector(1, 6);
        Vector end = new Vector(5, 10);
        int t = 50;

        Vector interpolated = Vector.linearInterpolation(start, end, t);

        assertEquals(3, interpolated.x);
        assertEquals(8, interpolated.y);
    }
}
