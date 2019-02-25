import java.util.Arrays;
import java.util.EmptyStackException;

public class ArrayStack<T> implements StackInterface<T>{
	private T[] stack;
	private int size;
	private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
	public ArrayStack() {
		this(DEFAULT_CAPACITY);

	}
	public ArrayStack(int capacity) {
		//Prevents that memory is left insecure by determining if enough memory is available. If not, throws
		//an exception
		checkCapacity(capacity);
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity];
		stack = temp;
		size = 0;
		initialized  = true; //The size was legal, init successful
	}
	private void checkCapacity(int capacity) {
		if(capacity > MAX_CAPACITY) {
			throw new IllegalStateException("Capacity excceeds max");
		}
	}
	private void checkInitialized() {
		if(!initialized) {
			throw new SecurityException("ArrayBag not properly initialized");
		}
	}
	@Override
	public void push(T newEntry) {
		checkInitialized();
		increaseSize();
		stack[size++] = newEntry;

	}

	private void increaseSize() {
		//No more room left in the array
		if(size == stack.length) {
			int newSize = size*2;
			checkCapacity(newSize); //Is this size too big?
			stack = Arrays.copyOf(stack, newSize);
		}
	}
	@Override
	public T pop() {
		checkInitialized();
		T result = peek();
		stack[--size] = null;
		return result;		
	}

	@Override
	public T peek() {
		checkInitialized();
		if(isEmpty())
			throw new EmptyStackException();
		return stack[size-1];
	}

	@Override
	public boolean isEmpty() {
		checkInitialized();
		return size == 0;
	}

	@Override
	public void clear() {
		checkInitialized();
		for(int i =0; i <size; i++) {
			stack[i] = null;
		}
		size=0;
		
	}

}
