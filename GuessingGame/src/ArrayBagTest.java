
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Kirby Steckel and Mitchell Osborn
 * 1/30/19
 * We have acted with honesty and integrity in producing this work and are unaware of anyone who has not
 * 
 * 
 * @author Jacob Schrum
 * Last modified 5/28/18
 */
public class ArrayBagTest {

	private static final int ARRAYBAG_DEFAULT_CAPACITY = 25;

	public ArrayBag<Integer> bag;
	public ArrayBag<Integer> smallBag;
	public ArrayBag<Integer> emptyBag;
	public ArrayBag<String> stringBag;

	/**
	 * 1 point for adding to stringBag
	 * 
	 * Reinitialize bag contents before each test.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		bag = new ArrayBag<Integer>();
		bag.add(4);
		bag.add(2);
		bag.add(2);
		bag.add(4);
		bag.add(5);
		bag.add(100);
		bag.add(-30);
		bag.add(-10);
		bag.add(-10);

		emptyBag = new ArrayBag<Integer>();

		smallBag = new ArrayBag<Integer>(3);
		smallBag.add(4);
		smallBag.add(4);
		smallBag.add(4);

		//TODO: Add initialization for stringBag.
		//      Fill it with interesting data.
		stringBag = new ArrayBag<String>();
		stringBag.add("H3110 Y3110w");
		stringBag.add("HELLO YELLOW");
		stringBag.add("1234 764");
		stringBag.add("Vinc3ntV4nG0gH");
		stringBag.add("SouthWestern University");
		stringBag.add("");
		stringBag.add("hey, how 4re U ?");
		stringBag.add("");
		stringBag.add("SouthWestern University");
		stringBag.add("Vinc3ntV4nG0gH");
		stringBag.add("Vinc3ntV4nG0gH");
	}

	/**
	 * 1 point
	 */
	@Test
	public void testGetCurrentSize() {
		assertEquals(9, bag.getCurrentSize());
		assertEquals(0, emptyBag.getCurrentSize());
		// TODO: Test for smallBag
		assertEquals(3, smallBag.getCurrentSize());
		// TODO: Test for stringBag
		assertEquals(11, stringBag.getCurrentSize());
	}

	/**
	 * 1 point
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(emptyBag.isEmpty());
		assertFalse(bag.isEmpty());
		// TODO: Test for smallBag
		assertFalse(smallBag.isEmpty());
		// TODO: Test for stringBag
		assertFalse(stringBag.isEmpty());

	}

	/**
	 * 1 point
	 */
	@Test
	public void testAdd() {
		assertTrue(bag.add(77));
		assertTrue(bag.contains(77));
		// TODO: Test for smallBag
		assertFalse(smallBag.add(43));
		assertFalse(smallBag.contains(43));
		assertFalse(smallBag.contains(23));

		// TODO: Test for stringBag
		assertTrue(stringBag.add("Hello World!"));
		assertTrue(stringBag.contains("Hello World!"));
		assertFalse(stringBag.contains("hELLO wORLD!"));


		// Fill bag to capacity: 25 is the private DEFAULT_CAPACITY in ArrayBag
		for(int i = 0; i < ARRAYBAG_DEFAULT_CAPACITY; i++) {
			assertTrue(emptyBag.add(9));
		}
		assertTrue(emptyBag.contains(9));
		// No more additions allowed
		assertFalse(emptyBag.add(9));
	}

	/**
	 * 4 points
	 */
	@Test
	public void testRemove() {
		assertEquals(new Integer(-10), bag.remove());
		assertEquals(new Integer(-10), bag.remove());
		assertEquals(new Integer(-30), bag.remove());
		assertEquals(new Integer(100), bag.remove());
		assertEquals(new Integer(5), bag.remove());
		assertEquals(new Integer(4), bag.remove());
		assertEquals(new Integer(2), bag.remove());
		assertEquals(new Integer(2), bag.remove());
		assertEquals(new Integer(4), bag.remove());
		assertEquals(null, bag.remove());

		assertEquals(null, emptyBag.remove());

		// TODO: Add similar tests for smallBag.
		assertEquals(new Integer(4), smallBag.remove());
		assertEquals(new Integer(4), smallBag.remove());
		assertEquals(new Integer(4), smallBag.remove());

		// TODO: Add similar tests for stringBag.
		assertEquals("Vinc3ntV4nG0gH",stringBag.remove());
		assertEquals("Vinc3ntV4nG0gH",stringBag.remove());
		assertEquals("SouthWestern University",stringBag.remove());
		assertEquals("",stringBag.remove());
		assertEquals("hey, how 4re U ?", stringBag.remove());
		assertEquals("",stringBag.remove());
		assertEquals("SouthWestern University",stringBag.remove());
		assertEquals("Vinc3ntV4nG0gH",stringBag.remove());
		assertEquals("1234 764",stringBag.remove());
		assertEquals("HELLO YELLOW",stringBag.remove());
		assertEquals("H3110 Y3110w",stringBag.remove());
		assertNotEquals("bob", stringBag.remove());



	}

	/**
	 * 4 points
	 */
	@Test
	public void testRemoveT() {
		assertTrue(bag.remove(2));
		assertTrue(bag.remove(2));
		assertFalse(bag.remove(2));
		assertTrue(bag.remove(-10));
		assertTrue(bag.remove(-10));
		assertFalse(bag.remove(-10));
		assertTrue(bag.remove(-30));
		assertFalse(bag.remove(-30));
		assertFalse(bag.remove(34));
		assertTrue(bag.remove(100));
		assertFalse(bag.remove(100));
		assertTrue(bag.remove(5));
		assertFalse(bag.remove(5));
		assertTrue(bag.remove(4));
		assertTrue(bag.remove(4));
		assertFalse(bag.remove(4));
		assertFalse(bag.remove(345));

		assertFalse(emptyBag.remove(32));

		// TODO: Add similar tests for smallBag.
		assertTrue(smallBag.remove(4));
		assertTrue(smallBag.remove(4));
		assertTrue(smallBag.remove(4));
		assertFalse(smallBag.remove(4));
		
		// TODO: Add similar tests for stringBag.
		assertTrue(stringBag.remove("H3110 Y3110w"));
		assertTrue(stringBag.remove(""));
		assertTrue(stringBag.remove(""));
		assertTrue(stringBag.remove("Vinc3ntV4nG0gH"));
		assertTrue(stringBag.remove("HELLO YELLOW"));
		assertTrue(stringBag.remove("1234 764"));
		assertTrue(stringBag.remove("SouthWestern University"));
		assertTrue(stringBag.remove("hey, how 4re U ?"));
		assertTrue(stringBag.remove("SouthWestern University"));
		assertTrue(stringBag.remove("Vinc3ntV4nG0gH"));
		assertTrue(stringBag.remove("Vinc3ntV4nG0gH"));
		assertFalse(stringBag.remove("The bag should be empty now"));

	}

	/**
	 * 1 point
	 */
	@Test
	public void testClear() {
		assertFalse(bag.isEmpty());
		bag.clear();
		assertTrue(bag.isEmpty());

		assertTrue(emptyBag.isEmpty());
		emptyBag.clear();
		assertTrue(emptyBag.isEmpty());

		// TODO: Add similar tests for smallBag.
		assertFalse(smallBag.isEmpty());
		smallBag.clear();
		assertTrue(smallBag.isEmpty());

		// TODO: Add similar tests for stringBag.
		assertFalse(stringBag.isEmpty());
		stringBag.clear();
		assertTrue(stringBag.isEmpty());
	}

	/**
	 * 4 points
	 */
	@Test
	public void testGetFrequencyOf() {
		assertEquals(2, bag.getFrequencyOf(-10));
		assertEquals(2, bag.getFrequencyOf(2));
		assertEquals(1, bag.getFrequencyOf(100));
		assertEquals(1, bag.getFrequencyOf(-30));
		assertEquals(1, bag.getFrequencyOf(5));
		assertEquals(2, bag.getFrequencyOf(4));
		assertEquals(0, bag.getFrequencyOf(534));

		assertEquals(0, emptyBag.getFrequencyOf(4));

		// TODO: Add similar tests for smallBag.
		assertEquals(3, smallBag.getFrequencyOf(4));
		assertEquals(0, smallBag.getFrequencyOf(200));
		assertEquals(0, smallBag.getFrequencyOf(3423));
		assertNotEquals(0, smallBag.getFrequencyOf(4));
		
		// TODO: Add similar tests for stringBag.
		assertEquals(2, stringBag.getFrequencyOf(""));
		assertEquals(2, stringBag.getFrequencyOf("SouthWestern University"));
		assertEquals(3, stringBag.getFrequencyOf("Vinc3ntV4nG0gH"));
		assertEquals(1, stringBag.getFrequencyOf("hey, how 4re U ?"));
		assertEquals(1, stringBag.getFrequencyOf("HELLO YELLOW"));
		assertNotEquals(2, stringBag.getFrequencyOf("Vinc3ntV4nG0gH"));
		assertNotEquals(2, stringBag.getFrequencyOf("HELLO YELLOW") );
	}

	/**
	 * 4 points
	 */
	@Test
	public void testContains() {
		assertTrue(bag.contains(-10));
		assertTrue(bag.contains(2));
		assertTrue(bag.contains(100));
		assertTrue(bag.contains(-30));
		assertTrue(bag.contains(5));
		assertTrue(bag.contains(4));
		assertFalse(bag.contains(46));
		assertFalse(bag.contains(4345));

		assertFalse(emptyBag.contains(46));

		// TODO: Add similar tests for smallBag.
		assertTrue(smallBag.contains(4));
		assertFalse(smallBag.contains(9));
		assertFalse(smallBag.contains(773));
		assertFalse(smallBag.contains(0));
		assertFalse(smallBag.contains(902893875));
		
		// TODO: Add similar tests for stringBag.
		assertTrue(stringBag.contains("HELLO YELLOW"));
		assertTrue(stringBag.contains(""));
		assertTrue(stringBag.contains(""));
		assertTrue(stringBag.contains("SouthWestern University"));
		assertTrue(stringBag.contains("Vinc3ntV4nG0gH"));
		assertTrue(stringBag.contains("hey, how 4re U ?"));
		assertTrue(stringBag.contains("1234 764"));
		assertTrue(stringBag.contains("H3110 Y3110w"));
		assertFalse(stringBag.contains("HUGS"));
		assertFalse(stringBag.contains("83758.8374957923"));
		assertFalse(stringBag.contains(" "));
	}

	/**
	 * 4 points
	 */
	@Test
	public void testToArray() {		
		Object[] result1 = bag.toArray();
		assertArrayEquals(new Integer[] {4,2,2,4,5,100,-30,-10,-10}, result1);

		Object[] result2 = emptyBag.toArray();
		assertArrayEquals(new Integer[0], result2);

		// TODO: Add similar tests for smallBag.
		Object[] result3 = smallBag.toArray();
		assertArrayEquals(new Integer[] {4, 4, 4} , result3);
		
		// TODO: Add similar tests for stringBag.
		Object[] result4 = stringBag.toArray();
		assertArrayEquals(new String[] {"H3110 Y3110w","HELLO YELLOW","1234 764","Vinc3ntV4nG0gH","SouthWestern University","","hey, how 4re U ?", "", "SouthWestern University","Vinc3ntV4nG0gH","Vinc3ntV4nG0gH"} , result4);

	}

}
