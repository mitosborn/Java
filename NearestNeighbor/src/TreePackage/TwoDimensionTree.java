package TreePackage;
import java.util.Stack;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Author of assignment: Dr. Jacob Schrum
 * Student completing assignment: Mitchell Osborn
 * 
 * The purpose of this project was to utilize the power of a binary search tree in combination with a point class containing x and y
 * coordinates to determine the nearest neighbor to another point. This was a challenging task as it required finding a way to sort
 * a BST when there are two variables that can be used as criteria per node, traversing this tree in an efficent manner, adding new
 * points in their proper sorted location, and finally writing the algorithm that would determine the nearest neighbor to a certain 
 * point. 
 * 
 * Alternate method: Some may suggest that it is possible to achieve a similar result to this project in a much simplier manner 
 * by calculating the distance between the point the user entered and all other points, selecting the lowest distance, and 
 * locating the nearest neighbor. However, the former method suggested is grossly inefficent in terms of Big O. While this 
 * project's implementation may be more complicated, it is able to locate the nearest point in O(logn) complexity due to the nature 
 * of a binary search tree. In comparison, the brute force method of calculating the nearest point by calculating all the distances
 * and selecting the minimum has a time complexity of O(n). 
 * 
 * 
 * 
 */
public class TwoDimensionTree<T extends Located2D> extends BinaryTree<T>{

	/**
	 * Inner class that allows reference to a location in 2D space.
	 * Like the built-in Java Point class, but implements Located2D
	 * and does not allow modification of coordinates after construction.
	 * 
	 * DO NOT CHANGE
	 * 
	 * @author Jacob Schrum
	 */
	public static class Point implements Located2D {
		// Coordinate values cannot be modified after construction
		private final double x;
		private final double y;

		/**
		 * Create new Point with specified x/y coordinates
		 * @param x x-coordinate
		 * @param y y-coordinate
		 */
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * retrieve the x-coordinate (from Located2D)
		 */
		@Override
		public double getX() {
			return x;
		}

		/**
		 * retrieve the y-coordinate (from Located2D)
		 */
		@Override
		public double getY() {
			return y;
		}

		/**
		 * Points are equal if they have the same x and y coordinates
		 */
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Point) {
				Point other = (Point) obj;
				return x == other.x && y == other.y;
			}
			return false;
		}
		public String toString() {
			return "["+x+", "+y+"]";

		}

	}	

	/**
	 * Create new empty 2d-tree
	 */
	public TwoDimensionTree() {
		super();
	}

	/**
	 * Disallow modification of data in the tree so that the sorted order is not lost.
	 * Overrides method from BinaryTree.
	 */
	@Override
	public void setTree(T rootData) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Disallow modification of data in the tree so that the sorted order is not lost.
	 * Overrides method from BinaryTree.
	 */
	@Override
	public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method that recursively locates if an entry is present within a binary tree
	 *
	 * @param rootNode Node from the tree
	 * @param entry Some sort of item with a location. It might be a simple Point, or have type T
	 * @param depth The depth that rootNode is at in the tree.
	 * @return A value of type T from the tree
	 */
	private <S extends Located2D> T findEntry(BinaryNode<T> rootNode, S entry, int depth) {
		//Determine if the entry is greater than or less than the entry at the current node
		int compareResult = compareBasedOnDepth(entry,rootNode.getData(),depth);
		//Base Case:If compareResult is 0, the entry and entry in the node are equal. Return the data in the node, indicating the value was present/found
		if(compareResult == 0) {
			return rootNode.getData();
		}
		//Recursive Case 1: Else, if the result is a -1, the entry is less than the current node and the method should recurse and search the left child of the current node
		else if(compareResult == -1) {
			//If the rootNode has no more children, the value is not in the tree and null is returned
			if(!rootNode.hasLeftChild())
				return null;
			//Recurse searching the left child of the rootNode and incrementing the depth, meaning compare the next entry based on the opposite criteria of the current recursive call
			return findEntry(rootNode.getLeftChild(), entry, depth + 1);

		}
		//Recursive Case 2: Else, if the result is a 1, the entry is greater than the current node and the method should recurse and search the right child of the current node
		else{
			//If the rootNode has no more children, the value is not in the tree and null is returned
			if(!rootNode.hasRightChild())
				return null;
			//Recurse searching the right child of the rootNode and incrementing the depth, meaning compare the next entry based on the opposite criteria of the current recursive call
			return findEntry(rootNode.getRightChild(), entry, depth + 1);
		}
	}

	/**
	 * Determine if an entry and the data in a tree node are equal by their locations. This method works by comparing 
	 * the x and y coordinates of the entry and tree node data depending on the depth. If the depth is even, it compares
	 * by x. Else, if the entry is odd, the entry is compared by y coordinates. If the two are equal, zero is returned. 
	 * If the entry is less than the tree data, -1 is returned, Alternatively, if the entry is greater than the tree entry,
	 * 1 is returned by the method.
	 * 
	 * @param entry Being searched for
	 * @param data From the tree
	 * @param depth Current tree depth (root is 0)
	 * @return -1 if entry would be left of the data, 1 if to the right, 0 for same location
	 */
	private <S extends Located2D> int compareBasedOnDepth(S entry, T data, int depth) {
		//If the x and y coordinates of the entry equal the node's, the locations of the two are the same. Return zero
		if(entry.getX() == data.getX() && entry.getY() == data.getY()) {
			return 0; // Only return 0 if the points have identical coordinates
		}else if(depth % 2 == 0) { // Even depth, compare x
			if(entry.getX() < data.getX()) {
				return -1; // Entry is to left of tree data (it's x value is less than the data)
			} else {
				//Else, the entry's x value is greater, return 1
				return 1;
			}
		} else { // Odd depth, compare y
			if(entry.getY() < data.getY()) {
				return -1; // Entry is to left of tree data (it is less than the data)
			}
			else {
				return 1; //Else, the entry's y value is greater, return 1
			}
		}


	}

	/**
	 * Indicate whether any object in this tree is located at the exact
	 * coordinates designated by a given Point.
	 * 
	 * Best case: O(1): object found at root of tree.
	 * Average case: O(log n): object found deep within roughly balanced tree
	 * Worst case: O(n): tree is completely unbalanced, and object is at bottom of tree  
	 * 
	 * @param location Coordinates to search for
	 * @return True if an object in the tree has these exact coordinates, false otherwise
	 */
	public boolean containsLocation(Point location) {
		//If the inputed location is not null and the tree is not empty, search the tree for the presence of the inputed location
		//utilizing findEntry().
		return location != null && !isEmpty() && location.equals(findEntry(getRootNode(), location, 0));
	}

	/**
	 * Indicate whether a specific object of type T is located in this tree.
	 * Note that the presence of the specific object should depend on the
	 * equals method of the object. Furthermore, it is possible for two objects
	 * to have the same location (x/y coordinates) but not be equal.
	 * 
	 * Best case: O(1): object found at root of tree.
	 * Average case: O(log n): object found deep within roughly balanced tree
	 * Worst case: O(n): tree is completely unbalanced, and object is at bottom of tree  
	 * 
	 * @param entry Specific object to search for
	 * @return True if the object is in the tree, false otherwise
	 */
	public boolean containsEntry(T entry) {
		//If the inputed entry is not null and the tree is not empty, search for the entry utilizing findEntry
		return entry != null && !isEmpty() && entry.equals(findEntry(getRootNode(), entry, 0));
	}	

	/**
	 * Retrieve the object from the tree that has the coordinates
	 * designated by the provided Point, or return null if no object
	 * in the tree has the designated coordinates.
	 * 
	 * Best case: O(1): object found at root of tree.
	 * Average case: O(log n): object found deep within roughly balanced tree
	 * Worst case: O(n): tree is completely unbalanced, and object is at bottom of tree  
	 * 
	 * @param location Coordinates to search for
	 * @return Object in tree that has these exact coordinates, or 
	 *         null if no object has these coordinates
	 */
	public T getEntry(Point location) {
		//If the inputed point is not null and the tree is not empty, return the entry at the location using findEntry(). Otherwise 
		//return null.
		if(location == null || isEmpty())
			return null;
		return findEntry(getRootNode(), location, 0);
	}

	/**
	 * Add a new entry at the appropriate place in the tree, potentially
	 * replacing a previously inserted object with the same location coordinates.
	 * If such an object was present, it is returned by the method. Otherwise,
	 * null is returned.
	 * 
	 * Best case: O(1): object found at root of tree.
	 * Average case: O(log n): object found deep within roughly balanced tree
	 * Worst case: O(n): tree is completely unbalanced, and object is at bottom of tree  
	 * 
	 * @param newEntry Object to insert into the tree
	 * @return Object that was replaced by the new entry, or null
	 *         if no such entry was replaced.
	 */
	public T add(T newEntry) {
		//Create variable to hold the former value of the location where an addition will take place
		T result = null;
		//If the tree is empty, establish the newEntry as the root
		if(isEmpty()) {
			// Make new entry the only node in the tree
			setRootNode(new BinaryNode<T>(newEntry));
			//Else, add the newEntry at its respective position in the tree utilizing addEntry()
		} else {
			result = addEntry(getRootNode(), newEntry, 0);
		}
		//Return the former value held at an existing point. Or, return null if a new point was formed.
		return result;
	}
	/**
	 * addEntry() is a recursive method that places an inputed entry into the TwoDimensionTree based on the entry's value.
	 * @param rootNode the root of the TwoDimensionTree
	 * @param newEntry the entry being inserted into the tree
	 * @param depth the current depth of where the method is searching in the tree
	 * @return the former value held at the point where the entry is being inserted. (Null if no value is held at the location)
	 * 
	 * Best: O(1): the object is the first searched (it is located in the root of the tree)
	 * Average: O(log n): object found deep within roughly balanced tree
	 * Worst: O(n): object is at bottom of tree or the tree is unbalanced
	 */
	private T addEntry(BinaryNode<T> rootNode, T newEntry, int depth) {
		T result = null;
		//If the newEntry is not null, retrieve the current value in the rootNode, compare it to the entry using compareBasedOnDepth,
		//and determine its location in the tree based on that result. Once the location is determined, place it into the tree.
		if(newEntry!=null) {
			T currentData = rootNode.getData();
			int compareResult = compareBasedOnDepth(newEntry, currentData, depth);
			if(compareResult < 0) { // newEntry < currentData: Search left 
				if(rootNode.hasLeftChild()) { // See if there is a left child to search
					result = addEntry(rootNode.getLeftChild(), newEntry, depth+1);
				} else { // No child to left: Make new node with newEntry
					rootNode.setLeftChild(new BinaryNode<T>(newEntry));
				}
			} else if(compareResult > 0) { // newEntry > currentData: Search right
				if(rootNode.hasRightChild()) { //See if there is a right child to search
					result = addEntry(rootNode.getRightChild(),newEntry, depth+1);
				}
				else { // No child to the right: make a new node with newEntry
					rootNode.setRightChild(new BinaryNode<T>(newEntry));
				}
			} else { // Found the newEntry: replace the data
				assert compareResult == 0;
				result = currentData;
				rootNode.setData(newEntry);
			}
		}
		return result; // Return old/previous entry (or null)
	}

	/**
	 * InOrderIterator() is an Iterator with the two functions of hasNext() and next() that allow it to traverse the binary tree that it
	 * is instantiated on. It iterates utilizing an In-Order traversal.
	 *
	 */
	public class InOrderIterator implements Iterator<T> {
		//Declare a stack and node utilized to insert entries into that stack
		private Stack<BinaryNode<T>> nodeStack;
		private BinaryNode<T> currentNode;
		//Default constructor: intialize stack and retrieve rootNode to be the currentNode
		public InOrderIterator() {
			nodeStack = new Stack<>();
			currentNode = getRootNode();
		}
		/**
		 * Returns whether the Iterator has another entry to traverse over utilizing next()
		 * Best, Average, Worst: O(1): This is an action that does not increase in complexity with the number of items present.
		 * @return boolean if another value is present or not for next() to iterate over
		 */
		public boolean hasNext() {
			//If the stack is empty of the current node is null, there is not a next entry. Otherwise, another entry exists.
			return !nodeStack.isEmpty() || (currentNode!= null);
		}
		/**
		 * next() returns the next entry of an in-order traversal of the TwoDimensionalTree
		 * Worst: O(n): The first run of next() requires loading up the stack with the contents of the tree, an action dependent on the number
		 * of entries present as all values must be accessed to be added, making it O(n).
		 * Best, Average: O(1): Once the stack has been created, traversing over the data is an action that does not increase in complexity with
		 * the number of entries.
		 * @return the next entry resulting from an in-order traversal of the tree
		 */
		public T next() {
			//While the currentNode is not null, push the tree nodes into a stack with a root, leftNode pushing pattern
			BinaryNode<T> nextNode = null;
			while(currentNode!=null){
				nodeStack.push(currentNode);
				currentNode = currentNode.getLeftChild();
			}
			//If the nodeStack is not empty, pop off a node and set currentNode to be the right child of that current node
			if(!nodeStack.isEmpty()) {
				nextNode = nodeStack.pop();
				assert nextNode != null;
				currentNode = nextNode.getRightChild();
			}
			///If the nodeStack is empty, throw a NoSuchElementException
			else
				throw new NoSuchElementException();
			//Return the data held in the entry being iterated over
			return nextNode.getData();

		}
	}
	/**
	 * getInOrderIterator() returns Iterator with the two functions of hasNext() and next() that allow it to traverse the binary tree that it
	 * is instantiated on in an in-order manner.
	 *  @return an Iterator for the binary tree that calls InOrderIterator()
	 */
	public Iterator<T> getInorderIterator() {
		//Return an iterator with the functions of next() and hasNext()
		return new InOrderIterator();
	}
	/**
	 * Class that allows for the storing of a value of any type. Useful for retaining access to a type that does not allow access/change
	 * by reference. Utilized to retain the best guess point and best distance between two points in the nearestNeighbor algorithm.
	 * @author mitos
	 *
	 * @param <E>
	 */
	private class Reference<E>{
		private E entry;
		public Reference(E data) {
			entry = data;
		}
		public void setData(E data) {
			entry = data;
		}
		public E getData() {
			return entry;
		}
	}

	/**
	 * (This is a kick-off method for nearestNeighbor)
	 * Checking for the nearest neighbor of a given query point is one of the main
	 * uses of 2d-trees. This method returns the entry in the tree that is
	 * closest to the query Point in terms of distance. However, to get credit for
	 * this method, you must implement it efficiently in accordance with the 
	 * assignment description (simply comparing against all points is not allowed).
	 *
	 * Best case: O(1): object found at root of tree.
	 * Average case: O(log n): object found deep within roughly balanced tree
	 * Worst case: O(n): tree is completely unbalanced, and object is at bottom of tree   
	 * 
	 * @param query Point whose nearest neighbor in tree is to be found.
	 * @return Entry from tree that is nearest to query Point, or null if tree is empty.
	 */
	public T nearestNeighbor(Point query) { 
		//Declare variables to reference the root node, nearest node to the query, and shortest distance so far to the query
		BinaryNode<T> node = getRootNode();
		Reference<T> guessRef = new Reference<T>(null);
		Reference<Double> bestDistRef = new Reference<Double>(Double.POSITIVE_INFINITY);
		//Call nearestNeighbor with the paramaters of the query being searched for, the root node of the tree, the nearest point so far as null, 
		//and the bestDistRef as positive infinity
		nearestNeighbor(query, node, guessRef, bestDistRef, 0);
		//Return the closest point reference that nearestNeighbor placed inside of guessRef
		return guessRef.getData();
	}
	/**
	 * Helper method that determines the closest point contained within the TwoDimensional tree to the query inputed.
	 * @param query The base point that the algorithm tries to determine the closest point to
	 * @param node The current node within the TwoDimensionTree
	 * @param guessRef The reference to the point that is closest to the query at the moment
	 * @param bestDistRef The reference to the best distance to the query from a point (lowest distance)
	 * @param depth The current depth of the tree necessary to determine what criteria to search with (x or y coordinates)
	 */
	private void nearestNeighbor(Point query, BinaryNode<T> node, Reference<T> guessRef, Reference<Double> bestDistRef, int depth) {
		//Base Case: The node is null and the method halts execution
		//Recursive Case: If the node is not null, keep searching for the closest node to the query
		if(node != null) {
			double queryPoint;
			double nodePoint;
			//Determine if the current node is closer to the query than the current lowest guess
			double distance = distance(query, node.getData());
			//If the current node is closer to the query than the bestDistRef, set the bestDistRef to that 
			//new lowest distance and set the currentGuess to that closer node's data
			if(distance < bestDistRef.getData()) {
				bestDistRef.setData(distance);
				guessRef.setData(node.getData());
			}
			//If the depth is even, determine which side to take by x values
			if(depth%2 == 0) {
				queryPoint = query.getX();
				nodePoint = node.getData().getX();
			}
			//Else, if the depth is odd, determine which side to take by y values
			else {
				queryPoint = query.getY();
				nodePoint = node.getData().getY();
			}
			//Determine the difference between the x or y values of the query and current node
			double diff = Math.abs(queryPoint-nodePoint);
			//If the query has a smaller x or y point than the current node, search to the left of the current node
			if(queryPoint < nodePoint) {
				nearestNeighbor(query, node.getLeftChild(), guessRef, bestDistRef, depth+ 1);
				//If the x or y distance between the nodePoint and queryPoint is less than the best distance so far, search to the right of the node
				//to ensure a potentially closer point is not missed in searching
				if(diff< bestDistRef.getData()) {
					nearestNeighbor(query, node.getRightChild(), guessRef, bestDistRef, depth+1);
				}
			}
			//Query's point coordinate >= node's point coordinate
			else {
				//If the query's x or y coordinate is greater than the node's, search to the right of the current node
				nearestNeighbor(query, node.getRightChild(), guessRef, bestDistRef, depth+1);
				//If the x or y distance between the nodePoint and queryPoint is less than the best distance so far, search to the left of the current 
				//node to ensure a potentially closer point is not missed in searching
				if(diff< bestDistRef.getData()) {
					nearestNeighbor(query,node.getLeftChild(),guessRef,bestDistRef,depth+1);
				}
			}
		}
	}

	/**
	 * Useful helper method that uses the Pythagorean Theorem
	 * to determine the (Euclidean) distance between two Located2D
	 * instances.
	 * 
	 * @param p1 A non-null object in 2D space
	 * @param p2 A non-null object in 2D space
	 * @return Euclidean distance between p1 and p2
	 */
	private static double distance(Located2D p1, Located2D p2) {
		double xDiff = (p1.getX() - p2.getX()); // leg length a
		double yDiff = (p1.getY() - p2.getY()); // leg length b
		// The leg lengths might be negative, but the multiplication below cancels that out
		return Math.sqrt(xDiff*xDiff + yDiff*yDiff); // Hypotenuse length c
	}
}