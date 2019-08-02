/**
 * Simple exception used in queue implementations
 * @author Jacob Schrum
 */
@SuppressWarnings("serial")
public class EmptyQueueException extends RuntimeException {
	
	/**
	 * Indicates that the queue is empty
	 */
	public EmptyQueueException() {
		super("Queue is empty");
	}
}
