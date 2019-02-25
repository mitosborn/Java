import java.util.Scanner;
import java.util.*;
/**
 * Kirby Steckel and Mitchell Osborn
 * 1/30/19
 * We have acted with honesty and integrity in producing this work and are unaware of anyone who has not
 */
public class Guess {

	/**
	 * Create a Scanner and play the game as long as the user
	 * answers "yes" to the main prompt.
	 * @param args Not used
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		while (yesTo("Do you want to play the guessing game?", console)){
			boolean win = false;
			//Ask user for max number in the bag
			int maxValue = positiveInt("What is the largest number that can be in the bag? ", console);
			//Ask user for bag size
			int bagSize = positiveInt("How many items are in the bag? ", console);
			System.out.println("Try to guess the numbers in the bag.");
			System.out.println("There are "+bagSize+" numbers from 1 to " + maxValue);
			//Create bag
			BagInterface<Integer> randomBag = setRandomBag(bagSize, maxValue);

			//While the user has not won, call checkWin to get user guesses and verify them against
			//the randomBag and return if they match (they won) in the form of a boolean win
			while(!win){
				win = checkWin(randomBag, maxValue, bagSize, console);
				//If the user won, tell them that they won. Else, tell them to try again.
				if(win) 
					System.out.println("You won!");
				else
					System.out.println("Guess again.");
			}
		}
	}
	/**
	 * This method setRandomBag generates a new bag filled with random ints 
	 * the user will later guess the values in this random bag
	 * it generates a new random bag for every game
	 * @param size The max size of the bag specified by the user
	 * @param max The maximum value that can be in the bag
	 * @return The bag filled with random ints that the user must guess
	 */
	public static BagInterface<Integer> setRandomBag(int size, int max) {
		//Create a bag that will be filled with random numbers and returned 
		BagInterface<Integer> randomBag = new ArrayBag<Integer>(size);
		Random rand = new Random();
		//Fill the bag with random numbers 1- the max specified by the user
		for(int i = 0; i < size; i++) {
			randomBag.add(rand.nextInt(max) + 1);
		}
		return randomBag;
	}

	/**
	 * Utility function that prompts the user to enter
	 * a single positive integer, which is then returned.
	 * Invalid responses cause the prompt to repeat until
	 * the user provides correct input. Do not change this
	 * method.
	 * @param prompt text of the question prompt
	 * @param console a Scanner of the console
	 * @return positive integer entered by user
	 */
	public static int positiveInt(String prompt, Scanner console) {
		for(;;) {
			System.out.print(prompt + " ");
			if(console.hasNextInt()) {
				int input = console.nextInt();
				if(input > 0) {
					return input;
				}
			} else {
				// Discard non-integer token
				console.next();
			}
			System.out.println("Please answer with a positive integer.");
		}
	}

	/**
	 * Utility function to ask user yes or no.
	 * No modifications are necessary for this method.
	 * It uses a forever loop -- but the loop stops when something is returned.
	 * @param prompt text of the question prompt
	 * @param console a Scanner of the console
	 * @return true if y is entered, false if n is entered
	 */
	public static boolean yesTo(String prompt, Scanner console) {
		for (;;) {
			System.out.print(prompt + " (y/n)? ");
			String response = console.next().trim().toLowerCase();
			if (response.equals("y"))
				return true;
			else if (response.equals("n"))
				return false;
			else
				System.out.println("Please answer y or n.");
		}
	}
	/**
	 * Utility function to get all the user's guesses and return them in a bag
	 * @param size size of the bag (number of guesses)
	 * @param console Scanner used to take in user input from the console
	 * @return bag containing the user guesses
	 */
	public static BagInterface<Integer> getGuesses(int size, Scanner console) {
		BagInterface<Integer> bag = new ArrayBag<>(size);
		//Take in user guesses until bag is full
		for(int i = 1; i<=size; i++) {
			bag.add(positiveInt("Guess for item #" + i+": ", console));
		}
		return bag; //return user guesses in a new bag
	}
	/**
	 * Method that checks if user guesses are the same as the randomBag
	 * to see if user won
	 * Calls the method getGuesses to fill the guessBag with user guesses
	 * @param randomBag The bag randomly generated that the user is guessing
	 * @param maxValue The maximum value specified by the user to be in the bags
	 * @param bagSize The size specified by the user utilized to make the bags
	 * @param console Scanner to take in user guesses
	 * @return boolean if the user won
	 */
	public static boolean checkWin(BagInterface<Integer> randomBag, int maxValue, int bagSize, Scanner console) {
		int correct = 0; //counter to keep track of correct values guessed
		BagInterface<Integer> guessBag = getGuesses(bagSize, console); //creates new bag using the getGuesses method
		//loop that checks the values guessed by the user
		//compares the guesses to what is in the random bag
		for(int i = 1; i<=maxValue; i++) {
			if(guessBag.contains(i) && randomBag.contains(i)) {
				correct+= Math.min(guessBag.getFrequencyOf(i), randomBag.getFrequencyOf(i));
			}	
		}
		//message to user that tells them how many of their guesses are correct
		System.out.print(correct + " of your guesses are correct. ");
		//if all guesses are correct return true (user won); else return false and take new set of guesses
		if(correct == bagSize)
			return true; 
		else
			return false;
	}
}

