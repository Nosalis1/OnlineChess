package utility.junitTest;

import org.junit.*;
import utility.math.Array;

import static org.junit.Assert.*;


public class ArrayTest {

    @BeforeClass
    public static void setUpClass(){}

    @AfterClass
    public static void tearDownClass(){}

    @Before
    public void setUp(){}

    @After
    public void tearDown(){}

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOutOfBounds() {
        Array<String> array = new Array<>(3);
        array.set(3, "test");
    }

    @Test
    public void setTest() {
        Array<Integer> array = new Array<>(5);
        array.set(0, 1);
        array.set(2, 3);
        array.set(4, 5);

        assertEquals(1, (int) array.get(0));
        assertNull(array.get(1));
        assertEquals(3, (int) array.get(2));
        assertNull(array.get(3));
        assertEquals(5, (int) array.get(4));
    }

    @Test
    public void shiftLeftTest(){
        Array<Integer> array = new Array<>(4);
        array.set(0, 1);
        array.set(1, 2);
        array.set(2, 3);
        array.set(3, 4);

        array.shiftLeft();

        assertEquals(2, (int) array.get(0));
        assertEquals(3, (int) array.get(1));
        assertEquals(4, (int) array.get(2));
        assertEquals(1, (int) array.get(3));
    }

    @Test
    public void shiftRightTest(){
        Array<Integer> array = new Array<>(4);
        array.set(0, 1);
        array.set(1, 2);
        array.set(2, 3);
        array.set(3, 4);

        array.shiftRight();

        assertEquals(4, (int) array.get(0));
        assertEquals(1, (int) array.get(1));
        assertEquals(2, (int) array.get(2));
        assertEquals(3, (int) array.get(3));
    }

    @Test
    public void addTest() {
        Array<Integer> array = new Array<>(5);
        array.add(0, 1);
        array.add(1, 2);
        array.add(3, 4);

        assertEquals(1, (int) array.get(0));
        assertEquals(2, (int) array.get(1));
        assertNull(array.get(2));
        assertEquals(4, (int) array.get(3));
        assertNull(array.get(4));
    }

    @Test
    public void insertTest(){
        Array<String> array = new Array<>(5);
        array.insert(0, "a");
        array.insert(1, "c");
        array.insert(1, "b");
        array.insert(3, "e");

        assertEquals("a", array.get(0));
        assertEquals("b", array.get(1));
        assertEquals("c", array.get(2));
        assertEquals("e", array.get(3));
        assertNull(array.get(4));
    }

    @Test
    public void insertTest2(){
        Array<String> array = new Array<>(5);
        array.insert(0, new String[]{"a", "b"});
        array.insert(2, new String[]{"c", "d", "e"});

        assertEquals("a", array.get(0));
        assertEquals("b", array.get(1));
        assertEquals("c", array.get(2));
        assertEquals("d", array.get(3));
        assertEquals("e", array.get(4));
        assertNull(array.get(5));
    }

    @Test
    public void getTest(){
        Array<Integer> array = new Array<>(5);
        array.set(0, 10);
        array.set(1, 20);
        array.set(2, 30);

        assertEquals(Integer.valueOf(10), array.get(0));
        assertEquals(Integer.valueOf(20), array.get(1));
        assertEquals(Integer.valueOf(30), array.get(2));
    }

    @Test
    public void findIndexOfTest(){
        Array<String> array = new Array<>(4);
        array.set(0, "Apple");
        array.set(1, "Banana");
        array.set(2, "Cherry");

        int index = array.findIndexOf("Banana");

        assertEquals(1, index);
    }

    @Test
    public void removeTest(){
        Array<String> array = new Array<>(5);
        array.set(0, "Apple");
        array.set(1, "Banana");
        array.set(2, "Cherry");

        array.remove(1);

        assertEquals("Apple", array.get(0));
        assertEquals("Cherry", array.get(1));
        assertNull(array.get(2));
        assertNull(array.get(3));
    }

    @Test
    public void removeTest2() {
        Array<String> array = new Array<>(5);
        array.set(0, "Apple");
        array.set(1, "Banana");
        array.set(2, "Cherry");

        array.remove("Banana");

        assertEquals("Apple", array.get(0));
        assertEquals("Cherry", array.get(1));
        assertNull(array.get(2));
        assertNull(array.get(3));
    }

    @Test
    public void containsTest(){
        Array<String> array = new Array<>(4);
        array.set(0, "Apple");
        array.set(1, "Banana");
        array.set(2, "Cherry");

        boolean containsBanana = array.contains("Banana");
        boolean containsOrange = array.contains("Orange");

        assertTrue(containsBanana);
        assertFalse(containsOrange);
    }

    @Test
    public void swapTest(){
        Array<Integer> array = new Array<>(5);
        array.set(0, 10);
        array.set(1, 20);
        array.set(2, 30);
        array.set(3, 40);
        array.set(4, 50);

        array.swap(1, 3);

        assertEquals(10, (int) array.get(0));
        assertEquals(40, (int) array.get(1));
        assertEquals(30, (int) array.get(2));
        assertEquals(20, (int) array.get(3));
        assertEquals(50, (int) array.get(4));
    }

    @Test
    public void forEachTest(){
        Array<Integer> array = new Array<>(5);
        array.set(0, 10);
        array.set(1, 20);
        array.set(2, 30);
        array.set(3, 40);
        array.set(4, 50);

        StringBuilder stringBuilder = new StringBuilder();

        array.forEach(item -> stringBuilder.append(item).append(" "));

        assertEquals("10 20 30 40 50 ", stringBuilder.toString());
    }
}