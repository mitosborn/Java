import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueueTest {
//Declare three queues
	Queue <Integer> q1;
	Queue <Integer> q2;
	Queue <String> q3;


	@BeforeEach
	void setUp() throws Exception {
		//Initialize q1 and q3 with values
		q1 = new Queue<>(5);
		q1.enqueue(3);
		q1.enqueue(2);
		q1.enqueue(47);
		q3 = new Queue<>();
		q3.enqueue("Hello");
		q3.enqueue("This");
		q3.enqueue("Is");


	}

	@Test
	void testEnqueue() {
		q1.enqueue(50);
		//Test if values are present from setUp
		assertEquals(new Integer(3), q1.dequeue());
		assertEquals(new Integer(2), q1.dequeue());
		assertEquals(new Integer(47), q1.dequeue());
		// Test if 50 is present after being enqueued after setup
		assertEquals(new Integer(50), q1.dequeue());
		//Add more numbers and test if they are present when dequeued
		q1.enqueue(1);
		q1.enqueue(2);
		q1.enqueue(3);
		q1.enqueue(4);
		q1.enqueue(5);
		q1.enqueue(6);
		q1.enqueue(7);
		q1.enqueue(8);
		assertEquals(new Integer(1), q1.dequeue());
		assertEquals(new Integer(2), q1.dequeue());
		assertEquals(new Integer(3), q1.dequeue());
		assertEquals(new Integer(4), q1.dequeue());
		assertEquals(new Integer(5), q1.dequeue());
		assertEquals(new Integer(6), q1.dequeue());
		assertEquals(new Integer(7), q1.dequeue());
		assertEquals(new Integer(8), q1.dequeue());
		//Test if Strings are being added properly
		q3.enqueue("A test");
		q3.enqueue("to see if this Queue");
		q3.enqueue("implementation functions");
		q3.enqueue("properly");
		assertEquals("Hello", q3.dequeue());
		assertEquals("This", q3.dequeue());
		assertEquals("Is", q3.dequeue());
		assertEquals("A test", q3.dequeue());
		assertEquals("to see if this Queue", q3.dequeue());
		assertEquals("implementation functions" , q3.dequeue());
		assertEquals("properly", q3.dequeue());

	}

	@Test
	void testDequeue() {
		//Dequeue the numbers present in q1 from setUp
		assertEquals(new Integer(3), q1.dequeue());
		assertEquals(new Integer(2), q1.dequeue());
		assertEquals(new Integer(47), q1.dequeue());
		//q1 should be empty now, meaning the next dequeue should be an exception
		boolean exception = false;
		try {
			q1.dequeue();
		}
		catch(Exception e) {
			exception = true;
		}
		assertTrue(exception);
		//Try exception with a queue declared empty
		q2 = new Queue<>();
		exception = false;
		try {
			q2.dequeue();
		}
		catch(Exception e) {
			exception = true;
		}
		assertTrue(exception);
		//Test if dequeue works on Strings
		assertEquals("Hello", q3.dequeue());
		assertEquals("This", q3.dequeue());
		assertEquals("Is", q3.dequeue());
	}

	@Test
	void testGetFront() {
		//Verify that first value is in the front of the queue
		assertEquals(new Integer(3), q1.getFront());
		//Remove a value and check if getFront returns the correct value
		q1.dequeue();
		assertEquals(new Integer(2), q1.getFront());
		//Remove another value and check for the same test
		q1.dequeue();
		assertEquals(new Integer(47), q1.getFront());
		q1.dequeue();
		//Check if an exception is given if getting the front of the now empty queue
		boolean exception = false;
		try {
			q1.getFront();
		}
		catch(Exception e) {
			exception = true;
		}
		assertTrue(exception);
		//Add a value and see if getFront returns the correct value
		q1.enqueue(25);
		assertEquals(new Integer(25), q1.getFront());
		//Try exception with an empty declared queue
		q2 = new Queue<>();
		exception = false;
		try {
			q2.getFront();
		}
		catch(Exception e) {
			exception = true;
		}
		assertTrue(exception);
		//Test getFront with Strings
		assertEquals("Hello", q3.getFront());
		q3.dequeue();
		assertEquals("This", q3.getFront());
		q3.dequeue();
		assertEquals("Is", q3.getFront());
		q3.dequeue();
		//Verify exception on empty String queue
		exception = false;
		try {
			q3.getFront();
		}
		catch(Exception e) {
			exception = true;
		}
		assertTrue(exception);


	}

	@Test
	void testIsEmpty() {
		//See if q1 returns not empty when it has values in it from setUp
		assertFalse(q1.isEmpty());
		//Empty q1 and see if it returns empty
		q1.dequeue();
		q1.dequeue();
		q1.dequeue();
		assertTrue(q1.isEmpty());
		//See if isEmpty works on a queue just initialized to be empty
		q2 = new Queue<>();
		assertTrue(q2.isEmpty());
		//Test if isEmpty works correctly after adding a value to an empty queue
		q2.enqueue(234);
		assertFalse(q2.isEmpty());
		//Test if isEmpty works correctly after removing a value from an one item queue
		q2.dequeue();
		assertTrue(q2.isEmpty());

	}

	@Test
	void testClear() {
		//Test if clear works on a queue of ints initialized in setUp
		q1.clear();
		assertTrue(q1.isEmpty());
		//Test if clear works after adding values to the now empty q1
		q1.enqueue(1235095432);
		q1.enqueue(4212);
		q1.enqueue(5324);
		q1.enqueue(-3523542);
		assertFalse(q1.isEmpty());
		q1.clear();
		assertTrue(q1.isEmpty());
		//Test if clear works after adding values to an empty queue
		q2 = new Queue<>();
		assertTrue(q2.isEmpty());
		q2.enqueue(243);
		q2.enqueue(32);
		q2.enqueue(23);
		q2.enqueue(3);
		q2.enqueue(2654);
		q2.enqueue(763);
		q2.enqueue(346);
		q2.enqueue(-24543);
		//Ensure the queue is not empty
		assertFalse(q2.isEmpty());
		//Clear the queue and verify that it worked
		q2.clear();
		assertTrue(q2.isEmpty());
	}

}
