import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Client class that runs the guessing game.
 * Includes methods for interacting with the
 * console. You don't need to modify this
 * file, but you should use its methods in
 * your program. In particular, do NOT use
 * System.out. for any output. Use the methods
 * in the GuessingGame's client instance instead.
 * 
 * @author Jacob Schrum
 * Last Modified: 5/27/17
 */
public class Client {
	// Default file to save and load decision trees to/from.
	// Alternate files are used by GuessingGameTest.java
	public static final String DEFAULT_DATA_FILE = "animals.txt";
	
	/**
	 * Launches the guessing game with text input coming from
	 * the user console (System.in) and text output also going
	 * to the user console (System.out). The animal database is
	 * loaded/saved from the default location: animals.txt
	 * 
	 * @param args Ignored
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		// This class actually constructs an instance of itself in its own main method
		Client c = new Client(console, System.out, DEFAULT_DATA_FILE);
		c.play();
	}
	
	// Note that these are non-static instance variables
	// belonging to a specific instance of Client.
	private Scanner console; // for reading input
	private PrintStream out; // for directing interactive output
	private String databaseFileName; // for saving animal database
	
	/**
	 * Create Client instance with the given input and output sources,
	 * and loading from and saving to the designated file for animal
	 * database information.
	 * 
	 * @param console Scanner to read input to the program
	 * @param out PrintStream to direct output from the program
	 * @param databaseFileName Name of file for animal database
	 */
	public Client(Scanner console, PrintStream out, String databaseFileName) {
		this.console = console;
		this.out = out;
		this.databaseFileName = databaseFileName;
	}

	/**
	 * Like System.out.print but redirects to PrintStream out.
	 * USE THIS METHOD INSTEAD OF System.out.print TO ASSURE CORRECT BEHAVIOR
	 * OF TESTS IN GuessingGameTest.java.
	 * @param message Message to print
	 */
	public void print(String message) {
		out.print(message);
	}	
	
	/**
	 * Like System.out.println but redirects to PrintStream out.
	 * USE THIS METHOD INSTEAD OF System.out.println TO ASSURE CORRECT BEHAVIOR
	 * OF TESTS IN GuessingGameTest.java.
	 * 
	 * Since there is no parameter, this method just prints a carriage return.
	 */
	public void println() {
		out.println();
	}
	
	/**
	 * Like System.out.println but redirects to PrintStream out.
	 * USE THIS METHOD INSTEAD OF System.out.println TO ASSURE CORRECT BEHAVIOR
	 * OF TESTS IN GuessingGameTest.java.
	 * @param message Message to print
	 */
	public void println(String message) {
		out.println(message);
	}

	
	/**
	 * Plays the guessing game as many times as the user wants,
	 * learning after each try. The databaseFileName file is loaded
	 * if it exists, but a default starting point is provided
	 * otherwise. Text input comes from console and text output
	 * goes to the PrintStream out. Once the user decides to quit, 
	 * the updated decision tree is saved to databaseFileName.
	 */
	public void play() {
		GuessingGame game; 
		// Use of "this" gives the GuessingGame access to the console output
		// methods inside of this Client instance.
		try {
			// Load from pre-existing animal database
			game = new GuessingGame(this, databaseFileName);
		} catch (FileNotFoundException e) {
			// If no database exists, use a default starting point
			out.println("Starting new game database: " + databaseFileName);
			game = new GuessingGame(this, "Is the animal a mammal?", "Squid", "Cow");
		}
		// Play until the user says otherwise
		while(yesNoQuery("Do you want to play the animal guessing game? ")){
			game.play();
		}
		// Save the updated decision tree
		game.save(databaseFileName);		
	}
	
	/**
	 * Print a question to PrintStream out and wait for a yes/no response from the user.
	 * @param prompt Question to ask user
	 * @return True if user types y or yes, false if n or no was typed.
	 */
	public boolean yesNoQuery(String prompt) {
		out.println(prompt);
		return isUserResponseYes();
	}
	
	/**
	 * Print a question to PrintStream out and take whatever is entered as the user response.
	 * @param prompt Question to ask user.
	 * @return Line of text entered by user at console.
	 */
	public String stringQuery(String prompt) {
		out.println(prompt + " ");
		return console.nextLine();
	}
	
	/**
	 * Keep looping until the user/console responds with a (y)es or (n)o.
	 * @return True on (y)es and false on (n)o
	 */
	public boolean isUserResponseYes() {
		while(true) {
			String response = console.nextLine().trim().toLowerCase();
			if(response.equals("yes") || response.equals("y")) {
				return true;
			} else if(response.equals("no") || response.equals("n")) {
				return false;
			} else {
				out.print("Must respond yes or no. Try again: ");
			}
		}
	}
}
