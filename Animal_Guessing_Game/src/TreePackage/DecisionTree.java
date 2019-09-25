package TreePackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This class is a binary tree in which each internal
 * node is a question and each leaf node is an answer.
 * When traversing the tree, going left indicates a
 * response of No to the question in that node, and
 * going right indicates a response of Yes. In addition
 * to maintaining the tree, there is also a current node
 * used for traversing the tree.
 * 
 * Implement and comment all methods with a TODO directive. 
 * 
 * @author Jacob Schrum
 * Last Modified: 7/11/18
 */
public class DecisionTree extends BinaryTree<String> implements DecisionTreeInterface<String> {

	// Represents current point in the decision process
	private BinaryNode<String> current;

	/**
	 * Create new tree with root node containing specified text with no children.
	 * @param text Contents of node.
	 */
	public DecisionTree(String text) {
		super(text);
		// Current node starts at root of tree
		current = this.getRootNode();
	}

	/**
	 * Create new tree with given question contained in root node, and
	 * provided left (no) and right (yes) subtrees.
	 * @param question String data in root node
	 * @param no Left subtree to traverse if answer to question is no
	 * @param yes Right subtree to traverse if answer to question is yes
	 */
	public DecisionTree(String question, DecisionTree no, DecisionTree yes) {
		super(question, no, yes);
		// Current node starts at root of tree
		current = this.getRootNode();
	}

	@Override
	/**
	 * getCurrentData() returns the data of the current node in the tree
	 * @return String data of the current node or null if the current node is null
	 */
	public String getCurrentData() {
		//Return the data of the node utilizing the method getData()
		return current.getData();
	}

	@Override
	/**
	 * setCurrentData() sets the data field of the current node to the string 
	 * inputed into the method with the precondition the current node is not null
	 * @param newData the new data for the current node
	 */
	public void setCurrentData(String newData) {
		// TODO: Implement and comment based on interface comments: 3 points
		current.setData(newData);
	}

	@Override
	/**
	 * setAnswers() Declares two nodes for a correct answer and an incorrect answer.
	 * These nodes are set to be the left and right children of the current node
	 * @param answerForNo new data object for left child
	 * @param answerForYes new data object for right child
	 */
	public void setAnswers(String answerForNo, String answerForYes) {
		//Declare BinaryNodes to hold strings for yes and no of the current node
		BinaryNode<String> yes = new BinaryNode<String>(answerForYes);
		BinaryNode<String> no = new BinaryNode<String>(answerForNo);
		current.setLeftChild(no);
		current.setRightChild(yes);

	}

	@Override
	/**
	 * isAnswer() returns whether a certain node is a leaf, which indicates that it is the end of a tree 
	 * and thus is an answer.
	 * @return boolean if the current node is an answer
	 */
	public boolean isAnswer() {
	//Return whether the current node is a leaf or not, indicating if an answer is present at that node.
		return current.isLeaf();
	}

	@Override
	/**
	 * advanceToNo() advances the current node to its left child in the event the left child is not null.
	 * If the left child is null, current is set to be null.
	 * advanceToNo() has a precondition that the current node is not null.
	 */
	public void advanceToNo() {
		//If the left child of the current node is null, set current to null. Else, set current to its left child
		if(!current.hasLeftChild())
			current = null;
		else {
			current = current.getLeftChild();
		}
	}

	@Override
	/**
	 * advanceToYes() advances the current node to its right child in the event the right child is not null.
	 * If the right child is null, current is set to be null.
	 * advanceToYes() has a precondition that the current node is not null.

	 */
	public void advanceToYes() {
		//If the right child of the current node is null, set current to null. Else, set current to its right child
		if(!current.hasRightChild())
			current = null;
		else {
			current = current.getRightChild();
		}
	}

	@Override
	/**
	 * reset() sets the current node to be the root node of the entire tree. This allows for the game to be reset and played continually
	 * without having to restart the program.
	 */
	public void reset() {
		current = this.getRootNode();
	}

	/**
	 * saveTree() saves the tree in a file with the specified filename.
	 * @param filename Name of file to save tree to
	 */
	public void saveTree(String filename) {
		try { // Try saving tree
			saveTree(new File(filename));
		} catch (FileNotFoundException e) {
			// Exit if there is an exception
			System.err.println("Could not save to file: " + filename);
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Kick-off for the recursive method that actually saves the tree.
	 * The root node of the tree is sent to the recursive method so that
	 * the tree can be output to file with a pre-order traversal.
	 * 
	 * @param saveFile An instance of File to save the tree to
	 * @throws FileNotFoundException If file cannot be created
	 */
	private void saveTree(File saveFile) throws FileNotFoundException {
		PrintStream ps = new PrintStream(saveFile); // Prepare to output to save file
		saveTree(ps, getRootNode());
		ps.close(); // Close the save file
	}

	/**
	 * This method takes in a binary node and writes its contents,
	 * along with the contents of all descendant nodes, to a file
	 * (represented by a print stream) in pre-order. In other words,
	 * the contents of this node are written to one line of the file,
	 * then the contents of the left subtree are recursively written,
	 * followed by the contents of the right subtree being recursively 
	 * written. Whenever the binary node is null, the word "NULL"
	 * should be written to the file. This means that two lines of "NULL"
	 * will appear in the file whenever a leaf is reached, which will 
	 * allow the data to be parsed when reading it back in from file.
	 * 
	 * @param saveStream A PrintStream linked to a save file.
	 * @param current Node in the binary tree.
	 */
	private void saveTree(PrintStream saveStream, BinaryNode<String> current) {
		// TODO: Implement and comment based on comments above and assignment description: 10 points
		//The current node is null, save it as NULL in the txt file. This indicates the presence of a leaf and the end of a portion of the 
		//tree.
		if(current == null) {
			saveStream.println("NULL");
		}
		//Else, print the data of the current node to the save file in the preorder style
		else {
			//Save current
			saveStream.println(current.getData());
			//Save left
			saveTree(saveStream, current.getLeftChild());
			//Save right
			saveTree(saveStream, current.getRightChild());

		}
	}

	/**
	 * Static method that creates a new instance of DecisionTree filled with
	 * data from the specified input file.
	 * 
	 * @param filename Name of file containing saved DecisionTree output.
	 * @return DecisionTree filled with data from file.
	 * @throws FileNotFoundException File with saved tree could not be found.
	 */
	public static DecisionTree loadTree(String filename) throws FileNotFoundException {

		return loadTree(new File(filename));
	}

	/**
	 * Kick-off method for the recursive method that actually fills the decision
	 * tree with data from the file.
	 * 
	 * @param file Instance of File containing saved DecisionTree
	 * @return DecisionTree constructed from file
	 * @throws FileNotFoundException File with saved tree could not be found.
	 */
	private static DecisionTree loadTree(File file) throws FileNotFoundException {
		Scanner scan = new Scanner(file); // Read from file
		// First line of file is question at root node
		DecisionTree dt = new DecisionTree(scan.nextLine());
		BinaryNode<String> root = dt.getRootNode();
		fillTree(Branch.LEFT, scan, root); // Fill left subtree using Scanner
		fillTree(Branch.RIGHT, scan, root); // Fill right subtree using Scanner
		scan.close(); // Close the file loaded from
		return dt;
	}

	/**
	 * Used by fillTree to determine if the LEFT or RIGHT subtree is currently
	 * being filled from the file.
	 */
	private enum Branch {LEFT, RIGHT};

	/**
	 * Recursively fill the decision tree with the file contents by creating
	 * new child nodes beneath the incoming parent. When the side is LEFT, then
	 * the next line read from the Scanner should be the left child of the
	 * parent. When the side is RIGHT, then the next line read from the Scanner 
	 * should be the right child of the Scanner.
	 * 
	 * @param side Which side of the parent node the next line should be the child of.
	 * @param scanner Scans the input file containing the decision tree.
	 * @param parent Node that read file contents should be attached to.
	 * Created with help from Tim
	 */
	private static void fillTree(Branch side, Scanner scanner, BinaryNode<String> parent) {
		//If the scanner has a next line, take in the line and see if it is "NULL". If it is NULL, do nothing (Base case). Else, set the left or right child
		//to a BinaryNode containing the next string taken in by the scanner depending on the side the method was called with. 
		//Then execute two recursive calls to fill out the rest of that side of the tree..
		if(scanner.hasNext()) {
			BinaryNode <String> entry = new BinaryNode<String>(scanner.nextLine());
			//Base case: If the line is "NULL", do nothing
			if(entry.getData().equals("NULL")) {}
			else {
				//If the side is LEFT, set the left node to the entry
				if(side == Branch.LEFT) 
					parent.setLeftChild(entry);
				//If the side is RIGHT, set the right node to the entry
				else if(side == Branch.RIGHT) 
					parent.setRightChild(entry);
				//Call the method for the left and right sides again to fill out the rest of that side of the tree
				fillTree(Branch.LEFT, scanner, entry);
				fillTree(Branch.RIGHT, scanner, entry);
			}
		}
	}
}
