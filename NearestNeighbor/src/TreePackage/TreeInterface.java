package TreePackage;

/**
 * Basic tree interface from our textbook.
 * @author Frank M Carrano
 */
public interface TreeInterface < T >
{
	/**
	 * Return data stored in root
	 * @return data in root node
	 */
    public T getRootData ();
    /**
     * Height of tree
     * @return height
     */
    public int getHeight ();
    /**
     * Number of nodes contained in tree
     * @return Number of nodes
     */
    public int getNumberOfNodes ();
    /**
     * Whether tree is empty
     * @return True if empty, false otherwise
     */
    public boolean isEmpty ();
    /**
     * Makes the tree empty
     */
    public void clear ();

} // end TreeInterface