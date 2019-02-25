/**
 * Interface for stack abstract data type.
 * 
 * @author Frank M. Carrano
 *
 * @param <T> type contained in stack.
 */
public interface StackInterface<T> {
	/**
	 * Add element to top of stack.
	 * @param newEntry item to add to stack.
	 */
	public void push(T newEntry);
	/**
	 * Remove and return top element from stack.
	 * @return top element.
	 */
	public T pop();
	/**
	 * Return top element from stack without modifying stack.
	 * @return Element on top of stack.
	 * @throws EmptyStackException if the stack is empty
	 */
	public T peek();
	/**
	 * Whether stack is empty.
	 * @return True if stack is empty, false otherwise.
	 * @throws EmptyStackException if the stack is empty
	 */
	public boolean isEmpty();
	/**
	 * Remove all elements from stack.
	 */
	public void clear();
}
