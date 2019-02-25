

/**
 * Interface for the operations of a bag of objects.
 * 
 * @author Frank M. Carrano
 *
 * @param <T> contents of bag
 * Last modified 7/7/16
 */
public interface BagInterface<T> {
	/**
	 * Number of elements in bag.
	 * @return Integer number of entries in bag.
	 */
	public int getCurrentSize();
	/**
	 * Sees if bag is empty.
	 * @return True if the bag is empty, false otherwise.
	 */
	
	public boolean isEmpty();
	/**
	 * Adds new entry to the bag.
	 * @param newEntry The object to be added.
	 * @return True if addition was successful, false otherwise.
	 */
	public boolean add(T newEntry);
	/**
	 * Removes one unspecified entry from the bag, if possible.
	 * @return If successful, return removed element, else return null.
	 */
	public T remove();
	/**
	 * Remove one occurrence of specific entry from bag, if possible.
	 * @param anEntry Entry to remove.
	 * @return True if such an entry existed and was removed, false otherwise.
	 */
	public boolean remove(T anEntry);
	/**
	 * Removes all entries from this bag.
	 */
	public void clear();
	/**
	 * Counts number of times a given entry occurs in this bag.
	 * @param anEntry Entry to be counted.
	 * @return Integer number of occurrences of entry.
	 */
	public int getFrequencyOf(T anEntry);
	/**
	 * Whether given entry is contained in bag.
	 * @param anEntry The entry to locate.
	 * @return True if entry is in bag, false otherwise.
	 */
	public boolean contains(T anEntry);
	/**
	 * Returns an array containing all entries from this bag.
	 * @return A newly allocated array of all entries in the bag.
	 */
	public T[] toArray();
	
}
