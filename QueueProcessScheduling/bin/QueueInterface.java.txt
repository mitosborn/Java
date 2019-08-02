/**
 * General queue interface provided on page 304 of our text.
 * 
 * @author Frank M. Carrano
 *
 * @param <T> type of element in queue
 */
public interface QueueInterface<T> {

	/**
	 * Adds new entry to back of this queue
	 * @param newEntry An object to be added
	 */
	public void enqueue(T newEntry);
	/**
	 * Removes and returns the entry at the front of this queue
	 * @return The object at the front of the queue
	 * @throws EmptyQueueException if queue is empty
	 */
	public T dequeue();
	
	/**
	 * Returns entry at front of this queue without modifying it
	 * @return The object at the front of the queue
	 * @throws EmptyQueueException if queue is empty
	 */
	public T getFront();
	
	/**
	 * Detects whether the queue is empty
	 * @return True if the queue is empty, false otherwise
	 */
	public boolean isEmpty();
	
	/**
	 * Removes all entries from this queue
	 */
	public void clear();
}
