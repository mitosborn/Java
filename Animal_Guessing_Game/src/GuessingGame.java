import java.io.FileNotFoundException;
import TreePackage.DecisionTree;

/**
 * Class that runs a guessing game using a DecisionTree.
 * Note that the Client instance that launches the
 * guessing game is stored inside of this class as an
 * instance variable. It is important that only the methods
 * from this class are used for all console interactions
 * to assure correct functioning of tests in GuessingGameTest.java.
 * 
 * @author Jacob Schrum
 * Last Modified: 7/11/18
 */
public class GuessingGame
{
	// Tree for determining which animal the user is thinking of
	private DecisionTree tree;
	// This variable should be used for all console interactions
	private Client client;

	/**
	 * Create a new guessing game and fill the decision tree
	 * with data from the designated filename.
	 * 
	 * @param c Client that is used to handle console interactions
	 * @param filename Name of file to load animal data from
	 * @throws FileNotFoundException If animal file is not found
	 */
	public GuessingGame(Client c, String filename) throws FileNotFoundException {
		client = c;
		tree = DecisionTree.loadTree(filename);
	}

	/**
	 * Create a new guessing game and fill the decision tree
	 * with a small initial tree.
	 * 
	 * @param c Client that is used to handle console interactions
	 * @param question Yes/no question at root of initial tree
	 * @param noAnswer Animal corresponding to a "no" answer to the question
	 * @param yesAnswer Animal corresponding to a "yes" answer to the question
	 */
	public GuessingGame(Client c, String question, String noAnswer, String yesAnswer) {
		client = c;
		DecisionTree no = new DecisionTree(noAnswer);
		DecisionTree yes = new DecisionTree(yesAnswer);
		tree = new DecisionTree(question, no, yes);
	}

	/**
	 * Play the game starting from the root of the decision tree.
	 * All console interaction happens via the client instance variable.
	 */
	public void play() {
		tree.reset();
		while (!tree.isAnswer()) { // while not at a leaf
			// ask current question
			client.println(tree.getCurrentData());
			// Whether to go left or right in tree
			if (client.isUserResponseYes())
				tree.advanceToYes();
			else
				tree.advanceToNo();
		} // end while
		assert tree.isAnswer(); // Assertion: leaf is reached
		// make guess
		client.println("My guess is " + tree.getCurrentData() + ". Am I right?");
		// Whether program won, or need to modify its tree
		if (client.isUserResponseYes())
			client.println("I win.");
		else
			learn(); // You must write this method below
	} // end play

	/**
	 * Method you must write that learns from the user.
	 * The user is first asked what s/he was thinking of,
	 * and is then asked to provide a yes/no question 
	 * that will distinguish what the user was thinking
	 * of from the incorrect answer provided by the decision
	 * tree. Methods in the client MUST be used to gather
	 * this information. This information is then placed
	 * in the decision tree. The exact format of the prompts
	 * you use should be taken from GuessingGameTest.java.
	 */
	private void learn () {
		//Obtain the animal the client expected, the user's expected animal, 
		//and a question differentiating the two animals from the user
		String ourAnimal = new String (tree.getCurrentData());
		String animalInMind = new String(client.stringQuery("I give up. What are you?"));
		String differences = new String(client.stringQuery("Give a question whose answer is yes for "+animalInMind +" and no for " +ourAnimal+":"));
		//Set the current node to be the question that differentiates the two animals
		tree.setCurrentData(differences);
		//Set the answers to the question with the old animal being the answer if the 
		//question is answered as no, and the new animal being the answer if the user
		//answers the question with a yes
		tree.setAnswers(ourAnimal, animalInMind);
		
	} // end learn

	/**
	 * Writes contents of decision tree to file in a form that can be retrieved later.
	 * @param filename Name of file to save data to
	 */
	public void save(String filename) {
		client.println("Saving file: " + filename);
		tree.saveTree(filename);
	}

} // end GuessingGame