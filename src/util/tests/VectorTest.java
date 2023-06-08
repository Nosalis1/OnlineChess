package util.tests;

import org.junit.Test;
import util.Vector;

import static org.junit.Assert.assertEquals;

public class VectorTest {
    @Test
    public void testAdd() {
        util.Vector vector = new Vector(1, 5);
        vector.add(6, 2);
        assertEquals(7, vector.x, vector.y);
    }

    @Test
    public void testSubtract() {

    }
}