
public class Queue<T> implements QueueInterface<T> {
//Declare private variables queue, frontIndex, and backIndex that will provide function to the queue
//and declare initialized, DEFAULT_CAPACITY, and MAX_CAPACITY as security measures
	private T[] queue;
	private int frontIndex;
	private int backIndex;
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;
/**
 * Default constructor that calls the other constructor with the DEFAULT_CAPACITY

 */
	public Queue() {
		this(DEFAULT_CAPACITY);
	}
	/**
	 * Constructor that creates a queue under the conditions that its capacity does not exceed the maximum allowed	

	 * @param initialCapacity Capacity of queue specified by the user 
	 */
	public Queue(int initialCapacity) {
		//Make sure that the initialCapacity is not greater than the max allowed
		checkCapacity(initialCapacity);
		//Create temp queue that will be assigned to the queue variable after initialization
		@SuppressWarnings("unchecked")
		T[] tempQueue = (T[]) new Object[initialCapacity+1];
		//Set queue equal to now initialized tempQueue, and frontIndex to zero, backIndex to initialCapacity, and initialized to true to 
		//indcate the queue has been created
		queue = tempQueue;
		frontIndex = 0;
		backIndex = initialCapacity;
		initialized = true;
	}
	/**
	 * Throw an exception if the capacity entered is greater than the max capacity allowed
	 * @param capacity Capacity being verified if it is of the proper size
	 * @throws IllegalStateException in the event the requested capacity is too big
	 */
	private void checkCapacity(int capacity) {
		if(capacity > MAX_CAPACITY)
			throw new IllegalStateException("Too big!");
	}
	/**
	 * If the queue has not been initialized, checkInitialized() will throw an exception to cease execution
	 * @throws SecurityException() throws in the event the queue is not initialized
	 */
	private void checkInitialized() {
		if(!initialized)
			throw new SecurityException("Not initialized!");
	}

	@Override
	/**
	 * enqueue adds a new value to the end of the queue
	 * @param newEntry the object/value being added to the queue
	 */
	public void enqueue(T newEntry) {
		checkInitialized();
		ensureCapacity();
		//Add value to the back of the queue
		backIndex = (backIndex + 1) % queue.length;
		queue[backIndex] = newEntry;
	}
/**
 * ensureCapacity() makes certain there is sufficient room in the queue for additional entries. It does this by doubling the 
 * capacity when the queue is full
 */
	private void ensureCapacity() {
		//Check if the queue is full
		if(frontIndex == (backIndex + 2) % queue.length) {
			//Double the queue size and copy the values over in the event the queue is full
			int oldSize = queue.length;
			int newSize = oldSize * 2;
			checkCapacity(newSize);
			@SuppressWarnings("unchecked")
			T[] newQueue = (T[]) new Object[newSize];
			for(int i = frontIndex, k = 0;
					k < oldSize - 1;
					k++, i = (i+1)%oldSize) {
				newQueue[k] = queue[i];
			}
			//Reset the queue variables to represent the values present in the new queue 
			queue = newQueue;
			frontIndex = 0;
			backIndex = oldSize - 2;
		}
	}
/**
 * dequeue removes the value in the front of the queue and returns it 
 * @return result the value that was removed from the front of the queue
 */
	@Override
	public T dequeue() {
		checkInitialized();
		//Get value from the front of the queue
		T result = getFront(); // throws exception if needed
		//Remove value by setting it to null and increment the frontIndex
		queue[frontIndex] = null;
		frontIndex = (frontIndex + 1) % queue.length;
		return result;
	}
/**
 * getFront() returns the value at the front of the queue. 
 * @throws EmptyQueueException() in the event the queue is empty
 * @return the value at the front of the queue
 */
	@Override
	public T getFront() {
		checkInitialized();
		if(isEmpty()) throw new EmptyQueueException();
		return queue[frontIndex];
	}

	@Override
	// O(1)
	/**
	 * isEmpty() returns if the queue is empty or not by checking if the front and back indexes are within one of each other
	 * @return boolean if the queue is empty or not
	 */
	public boolean isEmpty() {
		checkInitialized();
		return frontIndex == (backIndex + 1) % queue.length;
	}

	@Override
	// O(n)
	/**
	 * clear() empties the contents of the queue
	 */
	public void clear() {
		checkInitialized();
		//Determine the number of elements present in the queue
		int numElements;
		if(frontIndex <= backIndex) {
			numElements = backIndex - frontIndex + 1;
		} else {
			numElements = queue.length - (frontIndex - backIndex + 1);
		}
		//Set those elements present to null in the queue
		for(int i = frontIndex, k = 0; 
				k < numElements; 
				k++, i = (i+1)%queue.length) {
			queue[i] = null;
		}
		//Reset the variables frontIndex and backIndex
		frontIndex = 0;
		backIndex = queue.length - 1;
	}

}
