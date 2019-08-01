package TreePackage;

/**
 * Class for the nodes of a binary tree used in our textbook.
 * The class is neither public or private, meaning that it is
 * "package private" ... it can only be accessed by code within
 * the same package.
 * 
 * @author Frank Carrano
 */
class BinaryNode < T > {
    private T data;
    private BinaryNode < T > left;
    private BinaryNode < T > right;
    
    /**
     * New empty tree
     */
    public BinaryNode () {
        this (null); // call next constructor
    } // end default constructor

    /**
     * New tree with data in the root
     * @param dataPortion Root data
     */
    public BinaryNode (T dataPortion) {
        this (dataPortion, null, null); // call next constructor
    } // end constructor

    /**
     * New tree with given root data and specified left and right subtrees.
     * @param dataPortion Data in root.
     * @param leftChild Left subtree
     * @param rightChild Right subtree
     */
    public BinaryNode (T dataPortion, BinaryNode < T > leftChild, BinaryNode < T > rightChild) {
        data = dataPortion;
        left = leftChild;
        right = rightChild;
    } // end constructor

    /**
     * Return data from root
     * @return Root data
     */
    public T getData () {
        return data;
    } // end getData

    /**
     * Change root data
     * @param newData New data in root
     */
    public void setData (T newData) {
        data = newData;
    } // end setData

    /**
     * Return left sub-tree
     * @return Left sub-tree
     */
    public BinaryNode < T > getLeftChild () {
        return left;
    } // end getLeftChild

    /**
     * Change left sub-tree
     * @param leftChild Tree that replaces left tree
     */
    public void setLeftChild (BinaryNode < T > leftChild) {
        left = leftChild;
    } // end setLeftChild

    /**
     * Whether there is a left sub-tree
     * @return True if the left tree is not null, false otherwise
     */
    public boolean hasLeftChild () {
        return left != null;
    } // end hasLeftChild

    /**
     * Whether node is a leaf, meaning it has no children
     * @return True if node is a leaf
     */
    public boolean isLeaf () {
        return (left == null) && (right == null);
    } // end isLeaf


    /**
     * Return left sub-tree
     * @return Left sub-tree
     */
    public BinaryNode < T > getRightChild () {
        return right;
    } // end getRightChild

    /**
     * Change right sub-tree
     * @param rightChild Tree that replaces right tree
     */
    public void setRightChild (BinaryNode < T > rightChild) {
        right = rightChild;
    } // end setRightChild

    /**
     * Whether there is a right sub-tree
     * @return True if the right tree is not null, false otherwise
     */
    public boolean hasRightChild () {
        return right != null;
    } // end hasRightChild

    /**
     * Copies this node and its subtrees
     * @return Copy of this node
     */
    public BinaryNode < T > copy ()
    {
        BinaryNode < T > newRoot = new BinaryNode < T > (data);
        if (left != null)
            newRoot.left = (BinaryNode < T > ) left.copy ();
        if (right != null)
            newRoot.right = (BinaryNode < T > ) right.copy ();
        return newRoot;
    } // end copy

    /**
     * Height of tree descending from this node.
     * This is a kick-off for the actual recursive method.
     * 
     * @return Height of tree from this node.
     */
    public int getHeight () {
        return getHeight (this); // call private getHeight
    } // end getHeight

    /**
     * Recursive height calculation
     * @param node Starting node
     * @return Height from starting node downward.
     */
    private int getHeight (BinaryNode < T > node) {
        int height = 0;
        if (node != null)
            height = 1 + Math.max (getHeight (node.left), getHeight (node.right));
        return height;
    } // end getHeight

    /**
     * Number of nodes in the tree for which this node is the root.
     * @return Number of nodes.
     */
    public int getNumberOfNodes() {
        int leftNumber = 0;
        int rightNumber = 0;
        if (left != null)
            leftNumber = left.getNumberOfNodes();
        if (right != null)
            rightNumber = right.getNumberOfNodes();
        return 1 + leftNumber + rightNumber;
 
    } // end getNumberOfNodes
    public String toString() {
    	String result = "";
    	if(left != null) result += "("+left.toString()+")";
    	result += "-" + data + "-";
    	if(right != null) result += "("+right.toString()+")";
    	return result;    	
    }
    
} // end BinaryNode