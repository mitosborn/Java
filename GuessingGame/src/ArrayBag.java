/**
 * This is a skeleton for the ArrayBag implementation
 * detailed in our text book. You should fill in
 * all methods and constructors based on the code
 * in the book (or use what we worked on in class).
 * Note that you do not need to worry about resizing
 * the underlying array container when the array is
 * full. This is a fixed-size bag.
 * 
 * @author Jacob Schrum
 * Last modified 5/29/18
 */
public class ArrayBag<T> implements BagInterface<T> {

	private static final int DEFAULT_CAPACITY = 25;
	private T[] bag;
	private int size;

	public ArrayBag() {
		this(DEFAULT_CAPACITY);
	}
	/*
	 * Create a new bag with fixed capacity
	 * @param capacity Max number of items bag can ever contain
	 */
	public ArrayBag(int capacity) {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[capacity];
		bag = temp;
		size = 0;
	}

	@Override
	public int getCurrentSize() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public boolean add(T newEntry) {
		if(size == bag.length)
			return false; //Cannot add to bag
		else {
			//Postincrement- size will increase by one after it is used
			//Preincrement- size will increase by one before it is used
			bag[size++] = newEntry;
			//This is a postincrement
			return true; //Add successful
		}
	}

	@Override
	public T remove() {
		if(size == 0) {
			//Nothing to remove, return null
			return null;
		}
		else {
			//Predecrement
			T result = bag[--size];
			bag[size] = null;
			return result;
		}

	}

	@Override
	public boolean remove(T anEntry) {
		int removeIndex = -1;
		//Loop from right to left
		for(int i=size-1; i>=0;i--) {
			if(bag[i].equals(anEntry)) {
				removeIndex = i;
				break; //Exits the loop
			}
		}
		if(removeIndex == -1) {
			// Item not located, nothing to remove
			return false;
		} else {
			for(int i = removeIndex+1; i< size; i++) {
				bag[i-1] = bag[i]; //Shift item to the left
			}
			//Decrease size and set former last position to null
			bag[--size] = null;
			return true; // Item was removed 
		}
	}

	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			bag[i] = null;
		}
		size = 0;
	}

	@Override
	public int getFrequencyOf(T anEntry) {
		int frequency = 0;
		for(T element: bag) {
			if(element!= null && element.equals(anEntry)) {
				frequency++;
			}
		}
		return frequency;
	}

	@Override
	public boolean contains(T anEntry) {
		for(T element: bag) {
			if(element!= null && element.equals(anEntry)) {
				return true;
			}
		}
		return false;	
	}

	@Override
	public T[] toArray() {
		T[] result = (T[]) new Object[size];
		for(int i = 0; i<size; i++) {
			result[i] = bag[i];
		}
		return result;
	}
}
