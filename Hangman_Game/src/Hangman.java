import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Hangman {
	//global variables that are used throughout the code
	private static final int WORDLENGTH = 5;
	private static final int DICTIONARYSIZE = 8548;
	private static int instance;
	private static String[] dictionary = new String[DICTIONARYSIZE]; 
	private static boolean[] inPlay = new boolean[DICTIONARYSIZE];
	public static void main(String[] args) throws FileNotFoundException{
		//Input in the dictionary to the dictionary array utilizing the method inputsDictionary()
		inputsDictionary();
		Scanner console = new Scanner(System.in);
		//While the user has not said yes or no, ask them if they want to play Hangman
		while (yesTo("Do you want to play Hangman?", console)){
			int remainingWords = DICTIONARYSIZE;
			boolean letterIsPresent = false;
			char input;
			char[] guess = {'*','*','*','*','*'};
			instance=0;
			String word = null;
			String wrongGuessed = "";
			boolean createWord = true;
			int currentGuess = 1;
			//reset the inPlay boolean array to all true in order to allow the user to play multiple times with a complete dictionary
			resetInPlay();
			//Ask the user how many guesses they would like and stores it in the variable guesses
			int guesses = howManyGuesses(console);
			//Ask the user to guess a character until they use all of their guesses or guessed the word, winning the game
			while(currentGuess <=guesses) {
				//Get a char from the user
				input = getChar(currentGuess,console);
				//Calculate the remaining words after eliminating all words the user entered
				remainingWords = wordsInPlay(input, remainingWords);
				//Check if the user has eliminated 80% of the dictionary using ifGameCheats. If ifGameCheats returns false pick 
				//a word and check if the letter they entered is present in the guessed word
				if(!ifGameCheats(remainingWords)){
					//If createWord is true, call pickWord() to pick a word and set createWord to false. By setting createWord 
					//to false, the word will not be picked again in future loops through.
					if(createWord) {
						word = pickWord(remainingWords);
						createWord = false;
					}
					//Check if the letter the user guessed is present by passing the word, the word the user has guessed so far,
					//and the character the user just guessed to the boolean method checkIfLetterIsPresent
					letterIsPresent = checkIfLetterIsPresent(word, guess, input);
				}
				//Check if the user has won or lost the game yet by calling checkWin which returns an int value
				//If the user has won or lost, it returns 1000. Else, it returns the current value of currentGuess 
				//to allow the game to continue prompting the user to guess letters.
				printHangman(currentGuess, guesses );
				currentGuess = checkWin(guess, word, guesses, currentGuess, remainingWords);

				//If the user has not won or lost the game, print out if their guessed character is correct and the incorrect
				//guesses they have had so far.
				if(currentGuess<=guesses) {
					//If the letterIsPresent, tell the user good job and set letterIsPresent to false to allow the user to 
					if(letterIsPresent) {
						System.out.println("Good job! "+ input + " is present in the word.");
						letterIsPresent = false;
					}
					//If the user's guess is wrong, add the wrong guess to the wrongGuesses char array, increment guesses,
					//and call wrongGuesses to return the characters the user has used already
					else {
						wrongGuessed+=input;
						currentGuess++;
						System.out.println("Wrong: " + input + " is not present in the word");
					}
					wrongGuesses(wrongGuessed, guesses-currentGuess);
				}
			}
		}
	}
	//checkIfLetterIsPresent returns a boolean if the user's guessed char is present in the word they are trying to guess.
	//If the char is present, replace the dashes in the word shown to the user with the correct character. Else, return false
	//to indicate that the letter is not present
	public static boolean checkIfLetterIsPresent(String word, char[] guess, char input) { 
		for(int letter = 0; letter<WORDLENGTH; letter++) {
			if(word.charAt(letter) == input) {
				guess[letter]= input;
				return true;
			}
		}
		return false;
	}
	//method that will print the hangman based on how many wrong guesses the user has input
	public static void printHangman(int currentGuesses, int guesses) {
		if(instanceOfHangman(currentGuesses,guesses)==1) {
			printLines(1);
			System.out.println("  O");//the head
			printLines(2);
		}
		else if(instanceOfHangman(currentGuesses,guesses)==2) {
			printLines(1);
			System.out.println("  O\n  |");//head and body
			printLines(2);
		}
		else if(instanceOfHangman(currentGuesses,guesses)==3) {
			printLines(1);
			System.out.println("  O\n -|-");//head, body and arms
			printLines(2);
		}
		else if(instanceOfHangman(currentGuesses,guesses)==4) {
			printLines(1);
			System.out.println("  O\n -|-\n /");//head, body, arms, and left leg
			printLines(1);
		}
		else if(instanceOfHangman(currentGuesses,guesses)==5) {
			printLines(1);
			System.out.println("  O\n -|-\n / \\");//head, body, arms, and both legs
			printLines(1);
		}
		//		  O		little hangman drawing for reference
		//		 -|-
		//		 / \
	}
	//simple method that prints blanks lines based on the parameter.
	public static void printLines(int lines) {
		for(int i=0;i<lines;i++)//a parameter of 0 will prints 0 lines. A parameter of 1 will print 1 line and so on.
			System.out.println();
	}
	//method that stores what stage of the Hangman stick figure he is on.
	//only the head shows up, stage one all the way to the whole body on stage 5
	public static int instanceOfHangman(int currentGuesses, int guesses) {
		//every 20% of the guesses used sets instance to plus one.
		for(int i=1;i<=5;i++) {
			if(guesses/5*i==currentGuesses)
				instance=i;
		}
		return instance;
	}
	//initializeWrongGuesses returns how many guesses the user has left in the form of question marks. This output will
	//be concatenated to the end of the wrong characters the user has guessed so far
	public static String initializeWrongGuesses(int guesses) {
		if(guesses <0)
			return "";
		return "?" + initializeWrongGuesses(guesses-1);

	}
	//wrongGuesses returns what wrong characters the user has inputed so far and how many guesses they have left in the form of
	//question marks. It does this by taking the user's wrong characters and concatenating it with the output of 
	//initializeWrongGuesses, which returns the number of guesses the user has left in the form of question marks
	public static void wrongGuesses(String wrongGuesses, int numOfGuesses) {
		System.out.println("The incorrect letters guessed so far are: " + wrongGuesses + initializeWrongGuesses(numOfGuesses));
	}
	//returnWordSoFar prints out the word the user has guessed so far in the game
	public static void returnWordSoFar(char[] guess) {
		System.out.print("Word: ");
		for(char n: guess) {
			System.out.print(n);
		}
		System.out.println();
	}
	//checkWin takes in five parameters: the user's guess so far, the correct word, the total number of guesses, the 
	//current guess the user is on, and the remaining words. With these parameters, checkWin determines if the user has won 
	//or not by returning a number to the variable that controls the guesses loop. If the user's guess matches the correct 
	//word, they win and the method returns 1000, ending the game. Else, if the user has guessed the total number of guesses,
	//return 1000 to end the game, tell the user they lost and the word they failed to guess. Finally, if both these conditions 
	//are false, return the currentGuess so that the user can continue guessing. In the case the game has not picked a word 
	//and the user has lost, checkWin calls pick word and returns that word as the word the user failed to guess.
	public static int checkWin(char[] userGuess, String word, int totalGuesses, int currentGuess, int remainingWords) {
		boolean matches = true;
		//Check to avoid NullPointerException if word has not been generated yet
		if(word != null) {
			//Assume that the user's word is correct until proven otherwise by checking each char against the real word.
			//If a char does not match, set matches to false. 
			for(int x = 0; x<userGuess.length;x++) {
				if(userGuess[x] != word.charAt(x)) {
					matches = false;
				}
			}
			//If the user's word matches, tell them they won and end the game by returning 1000
			if(matches) {
				System.out.println("Congrats!!! You won in "+currentGuess + " guesses.\nThe word was " + word);
				return 1000;
			}
			//In the case the word does not match and the user has used all of their guesses, tell them that they lost
			//and the word they attempted to guess
			else if(currentGuess >= totalGuesses && !matches) {
				System.out.println("You lost. The word was: "+ word+"\nBetter luck next time!");
				return 1000;
			}
		}
		//If the user has utilized all of their guesses and the game has not picked a word yet, pick a word and tell the user 
		//they lost and the word they failed to guess
		else if(word == null&& currentGuess >= totalGuesses) {
			word = pickWord(remainingWords);
			System.out.println("You lost. The word was: "+ word+"\nBetter luck next time!");
			return 1000;
		}
		//In the case the user has not lost or won and a word has , print the state of the guessed word and return the 
		//current value of guesses
		returnWordSoFar(userGuess);
		return currentGuess;
	}
	//pickWord generates the word the user must guess. It does this by calculating a random int with the remaining words
	//as the bounds which will act as the index of the word picked. A while loop is then used to find the word in the dictionary
	//by taking into account the words that are eliminated from the inPlay[] boolean array. This will find the nth inPlay word in the 
	//dictionary array
	public static String pickWord(int remainingWords) {
		Random r = new Random();
		int counter =0;
		int word =0;
		int index = r.nextInt(remainingWords);
		while(counter!=index) {
			if(inPlay[word] == true) {
				counter++;
			}
			word++;
		}
		return dictionary[word-1];
	}
	//gameCheats is a method that returns true if the user has reached the threshold of 20% 
	//of the remaining words left.
	public static boolean ifGameCheats(int remainingWords) {
		if((double)remainingWords/DICTIONARYSIZE <=0.20) {
			return false;
		}
		return true;
	}
	//wordsInPlay returns the number of words left with letters the user has not guessed yet
	//It checks the dictionary array for words with that letter and sets that corresponding
	//index to false in the inPlay array
	public static int wordsInPlay(char input, int remainingWords) {
		//The int counter holds the number of words that are eliminated due to the user entering a char
		int counter = 0;
		//If a word has a letter that the user inputed, set that index to false in the inPlay array and
		//add one to the counter. Once finished searching the words, return the difference of remainingWords
		//and counter
		for(int i = 0; i<DICTIONARYSIZE;i++ ) {
			for(int j = 0; j< WORDLENGTH;j++) {
				if(dictionary[i].charAt(j)==input && inPlay[i] == true) {
					inPlay[i] = false;
					counter++;
					j = WORDLENGTH;
				}
			}
		}

		return remainingWords-counter;
	}
	// returns the number of words in the dictionary that are in play that have a particular letter
	public static int numWithLetter(char letter){
		int count = 0;
		for(int i = 0; i<DICTIONARYSIZE;i++ ) {
			for(int j = 0; j< WORDLENGTH;j++) {
				if(dictionary[i].charAt(j)==letter) {
					count++;
					j = WORDLENGTH;
				}
			}
		}
		return count;
	}	

	// inputs the dictionary from the file into the dictionary array
	public static void inputsDictionary() throws FileNotFoundException{
		//Create a scanner with the input stream of hangmanWords.txt
		Scanner word = new Scanner(new File("hangmanWords.txt"));
		for(int x = 0; x<DICTIONARYSIZE; x++) {
			dictionary[x] = word.next();
		}
		word.close();
	}
	// resetInPlay sets all boolean values to true, allowing all words to be utilized in the game at the start
	public static void resetInPlay() {
		for(int x = 0; x<inPlay.length; x++)
			inPlay[x] = true;
	}

	//utility function to ask user yes or no
	//No modifications are necessary for this method.
	//It uses a forever loop -- but the loop stops when something is returned.
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
	//howManyGuesses asks the user for a positive int- if they do not provide one, it keeps asking them for one.
	//Once they enter a number that follows this condition, it then asks them if they are sure if they want that number.
	public static int howManyGuesses(Scanner console) {
		boolean var = false;
		int guesses = 0;
		//while var is false, ask the user for an int that is positive and ask them if they are certain they want that int
		while(!var) {
			System.out.print("How many guesses would you like? :");
			//If the user does not enter an int, ask them again with an error message
			while(!console.hasNextInt()) {
				System.out.print("Please enter an int that is greater than 0: ");
				console.next();
			}
			guesses = console.nextInt();
			if(guesses < 1 || guesses > 26) {
				System.out.println("Please enter an int that is greater than 0 and less than 27");
			}
			else if(yesTo("Is " + guesses + " the number you would like? ", console)) {
				var = true;
			}
		}
		return guesses;
	}
	//getChar asks the user for a character until they return an alphabetic char
	public static char getChar(int guess, Scanner console) {
		while(true) {
			System.out.print("Guess "+ guess +" enter a letter: ");
			String c = console.next();
			if(c.length() ==1 && Character.isAlphabetic(c.charAt(0))){
				return c.charAt(0);
			}
			else
				System.out.println("Error: please enter a letter");
		}
	}
}

