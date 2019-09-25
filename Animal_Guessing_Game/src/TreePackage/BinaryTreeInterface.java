package TreePackage;

/**
 * Interface for Binary Trees from our book, but with the TreeIteratorInterface removed.
 * @author Frank Carrano
 */
public interface BinaryTreeInterface < T > extends TreeInterface < T > {
    /** Sets this binary tree to a new one-node binary tree.
    @param rootData an object that is the data in the new trees root  */
    public void setTree (T rootData);
    
    /** Sets this binary tree to a new binary tree.
    @param rootData an object that is the data in the new trees root
    @param leftTree the left subtree of the new tree
    @param rightTree the right subtree of the new tree */    
    public void setTree (T rootData, BinaryTreeInterface < T > leftTree, BinaryTreeInterface < T > rightTree);
            
} // end BinaryTreeInterface