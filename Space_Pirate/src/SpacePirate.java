import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;

import com.sun.glass.ui.Timer;

public class SpacePirate extends GraphicsProgram {
	// Width of the main window in pixels
	private static final int WIDTH = 1920;
	// Height of the main window
	private static final int HEIGHT = 1080;
	// number of landing pads
	private static final int NUMBER_OF_PADS = 5;
	// dimensions of each landing pad
	private static final int PAD_WIDTH = WIDTH/(3*NUMBER_OF_PADS+3);
	private static final int PAD_HEIGHT = 20;
	// lander dimensions
	private static final int LANDER_HEIGHT = 16;
	private static final int LANDER_WIDTH = 24;
	// asteroid parameters
	private static final int NUMBER_ASTEROIDS_X = 10;
	private static final int NUMBER_ASTEROIDS_Y = 10;
	private static final int ASTEROID_SIZE = 22;
	// number of pads which have been visited
	private int padsVisited = 0;
	// Declaring the space ship, waves, and current landing pad
	private GCompound lander;
	private GRect waveLeft;
	private GRect waveRight;
	private GObject currentPad;
	//Declare GLabels for pause and score
	private GLabel pause = new GLabel ("Paused" , WIDTH/2-100, HEIGHT/2);
	private GLabel score= new GLabel ("Score: "+padsVisited, WIDTH-500, 50);

	// waveLevel determines how large the waves get as it is incremented
	private int waveLevel = 0;

	//Declare velocity variables
	private double landerDeltaX = 0;
	private double landerDeltaY = 0;
	// status of lander thrusters
	private boolean leftThrust = false;
	private boolean rightThrust = false;
	private boolean upThrust = false;
	// game status
	private boolean gameRunning = false;
	private boolean hasLanded = false;
	private boolean paused = false;
	// game speed
	// serialVersionUID prevents a warning in Eclipse due to a technical detail
	// about the serializable Interface that is beyond the scope of this project
	private static final long serialVersionUID = 1L;

	// init method runs when the applet starts	
	public void init() 
	{
		resize(WIDTH, HEIGHT); // set the window size
		addKeyListeners();	   // setup program to accept keyboard info

		setBackground(Color.BLACK);

		// print the instructions onto the screen
		GLabel title1 = new GLabel ("Welcome to Space Pirate" , WIDTH/3-140, HEIGHT/2-300);
		title1.setColor(Color.WHITE);
		title1.setFont(new Font("Helvetica", Font.BOLD, 80));
		GLabel title2 = new GLabel ("Press 'n' to begin",WIDTH/3+80 ,HEIGHT/2+400);
		title2.setColor(Color.WHITE);
		title2.setFont(new Font("Helvetica", Font.BOLD, 55));
		GLabel instru0 = new GLabel ("How to play", WIDTH/3+160, HEIGHT/2-160);
		instru0.setFont(new Font("Helvetica", Font.BOLD, 55));
		instru0.setColor(Color.WHITE);
		GLabel instru1 = new GLabel ("Use the up, left, and right arrow keys for thrusters", WIDTH/4-130, HEIGHT/2-90);
		instru1.setColor(Color.WHITE);
		instru1.setFont(new Font("Helvetica", Font.PLAIN, 55));
		GLabel instru2 = new GLabel ("Press 's' to start moving, p to pause, 'n' to start", WIDTH/4-80, HEIGHT/2-20);
		instru2.setFont(new Font("Helvetica", Font.PLAIN, 55));
		instru2.setColor(Color.WHITE);
		GLabel instru3 = new GLabel (" a new game, and 'q' to exit the game", WIDTH/4+20, HEIGHT/2+50);
		instru3.setFont(new Font("Helvetica", Font.PLAIN, 55));
		instru3.setColor(Color.WHITE);
		GLabel instru4 = new GLabel ("", WIDTH/4+240, HEIGHT/2+120);
		instru4.setFont(new Font("Helvetica", Font.PLAIN, 55));
		instru4.setColor(Color.WHITE);

		add(title1);
		add(title2);
		add(instru0);
		add(instru1);
		add(instru2);
		add(instru3);
		add(instru4);
		add(score);

	}
	// Ff a key is pressed and matches one of the defined functions, it will activate the directional boolean and call updateVelocity()
	// for the arrow keys, or start the game if the key is 's', pause the game if the key is 'p', and quit the game if it is 'q'.
	// On each key press, the game checks to see if the lander has landed. If it has, it sets the respective landing pad to not
	// visible and starts the motion of the lander again by changing hasLanded to false
	public void keyPressed(KeyEvent evt) {

		int key = evt.getKeyCode();  // Keyboard code for the pressed key.
		// arrow keys for the thrusters
		if (key == KeyEvent.VK_LEFT) {  // left-arrow key
			leftThrust = true;		
			updateVelocity();
		}
		else if (key == KeyEvent.VK_RIGHT) {  // right-arrow key
			rightThrust = true;
			updateVelocity();
		}
		else if (key == KeyEvent.VK_UP) {  // up-arrow key
			upThrust = true;
			updateVelocity();
			if(hasLanded) {
				currentPad.setVisible(false);
				hasLanded = false;
			}
		}
		else if(key == KeyEvent.VK_N) { // letter n key
			gameSetup();
		}	
		else if(key == KeyEvent.VK_S) { // letter n key
			gameRunning = true;
			if(paused) {
				pauseGame();
			}
		}	
		else if(key == KeyEvent.VK_P) {
			if(!paused) {
				pauseGame();
			}

		}
		else if(key == KeyEvent.VK_Q) {
			System.exit(0);
		}
	}
	//pauseGame() inverts the value of pause uses pause's value to display or remove the word pause from the screen. 
	public void pauseGame() {
		paused = !paused;
		pause.setColor(Color.WHITE);
		pause.setFont(new Font("Paused", Font.PLAIN, 55));
		if(paused) {

			add(pause);
		}
		else
			remove(pause);
	}
	// When a directional key is released, wait 50 ms and set that respective directional boolean to false
	public void keyReleased(KeyEvent evt) {
		int key = evt.getKeyCode();  // Keyboard code for the pressed key
		// arrow keys for the thrusters
		if (key == KeyEvent.VK_LEFT) {  // left-arrow key
			pause(50);
			leftThrust = false;
		}
		else if (key == KeyEvent.VK_RIGHT) {  // right-arrow key
			pause(50);
			rightThrust = false;
		}
		else if (key == KeyEvent.VK_UP) {  // up-arrow key
			pause(50);
			upThrust = false;

		}
	}


	// set up a new game
	private void gameSetup() {

		clearScreen(); // clear all objects

		// display the map of the game
		initOcean();
		initLandingPads();			
		placeLander();		
		initAsteroids();

		// initialize game function variables
		gameRunning = false;
		padsVisited = 0;	
		hasLanded=false;
		paused = false;	
	}

	// clearScreen() removes all objects from the screen in order to start or end a game
	private void clearScreen() {
		removeAll();
	}


	// draw the ocean
	private void initOcean() {
		GRect ocean = new GRect(0,HEIGHT-4*PAD_HEIGHT, WIDTH, HEIGHT);
		ocean.setFilled(true);
		ocean.setColor(Color.BLUE);
		add(ocean);
	}
	//updateScore() removes the score that was added in init() and updates it with the current value of padsLanded
	private void updateScore() {
		remove(score);
		score = new GLabel ("Score: "+padsVisited, WIDTH-500, 50);
		score.setColor(Color.CYAN);
		score.setFont(new Font("Helvetica", Font.BOLD, 40));
		add(score);
	}
	// waveCycle is a method that returns the inputed wave and starting point and moves the wave a certain number of pixels. This number of pixels is determined by
	// the value of waveLevel
	private GRect waveCycle(int start, GRect wave) {
		wave = new GRect(start+6*waveLevel,HEIGHT-5*PAD_HEIGHT-waveLevel/2,WIDTH/10+6*waveLevel,HEIGHT);
		wave.setFilled(true);
		wave.setColor(Color.BLUE);
		return wave;
	}
	//addWaves() calls waveCycle() with both the left and right waves and puts in their starting positions. It then adds the waves
	//to be displayed and increments waveLevel which controls the position of the wave returned in waveCycle()
	//Once waveLevel equals 60, set it equal to zero to simulate the wave returning to its starting position
	private void addWaves() {
		if(!paused) {
			waveLeft = waveCycle(0, waveLeft);
			waveRight =waveCycle(WIDTH/2+50, waveRight);
			add(waveLeft);
			add(waveRight);
			if(waveLevel==60)
				waveLevel = 0;
			waveLevel++;
		}
	}
	//Remove the waves in order to allow addWaves() to reassign the value of waveLeft and waveRight and simulate the movement of a wave
	private void removeWaves() {
		if(!paused) {
			remove(waveLeft);
			remove(waveRight);
		}
	}

	//draw the number of asteroids specified in NUMBER_ASTEROIDS_Y and NUMBER_ASTEROIDS_X by using a nested for loop 
	private void initAsteroids() {
		for(int b = 0; b<NUMBER_ASTEROIDS_Y;b++) {
			for(int rows = 0; rows <NUMBER_ASTEROIDS_X; rows++){
				GOval ball = new GOval(100+(WIDTH-200)/(NUMBER_ASTEROIDS_X-1)*rows, 
						100+(HEIGHT-300)/(NUMBER_ASTEROIDS_Y-1)*b,
						ASTEROID_SIZE,ASTEROID_SIZE);
				ball.setFilled(true);
				ball.setColor(Color.WHITE);
				add(ball);
			}
		}

	}
	//Place the specified number of landing pads using a loop that calls the placeLandingPad() method
	private void initLandingPads()
	{
		for(int num = 0; num <NUMBER_OF_PADS; num++) {
			placeLandingPad(Color.YELLOW,num);
		}

	}	
	//Place a landing pad at a location determined by what number landing pad is being printed. This number is entered as i
	private void placeLandingPad(Color c, int i) {
		GRect square = new GRect((3*i+3)*(PAD_WIDTH), 
				HEIGHT-5*PAD_HEIGHT, PAD_WIDTH, PAD_HEIGHT);
		square.setFilled(true);
		square.setColor(c);
		add(square);
	}

	// draw the lander/pirate ship
	private void placeLander()
	{		
		lander = new GCompound();		
		GOval ball = new GOval(2, (LANDER_HEIGHT-10)/2+1, 
				LANDER_WIDTH-4,LANDER_HEIGHT-10);
		ball.setFilled(true);
		lander.add(ball);
		GRect leftwing = new GRect(0, 0, 2, LANDER_HEIGHT);
		leftwing.setFilled(true);
		lander.add(leftwing);
		GRect rightwing = new GRect(LANDER_WIDTH-1,	0, 2, LANDER_HEIGHT);
		rightwing.setFilled(true);
		lander.add(rightwing);

		lander.setColor(Color.GREEN);
		add(lander);		
		lander.move(WIDTH/2-12,25);		
		pause(100);
	}

	// sets lander speed to zero in each direction
	private void setZeroVelocity() {	
		landerDeltaX = 0;
		landerDeltaY = 0;
	}

	// updateVelocity is called in the run() method and updates the lander's velocity by adding or subtracting to the respective
	// velocity variable if that direction boolean (ea leftThrust) is true. The direction booleans are triggered in the keyPressed method()
	private void updateVelocity() {
		if (leftThrust) {
			landerDeltaX -= 40;
		}
		if (rightThrust) {
			landerDeltaX += 40;
		}
		if (upThrust) {
			landerDeltaY -= 40;
		}
	}
	//run() is the method that drives the whole game. It runs until the game is closed and calls the movement and boundary check
	//methods during the game.
	public void run() 
	{	
		while(true) {
			while(!gameRunning) {
				pause(500);
			}
			while(gameRunning) {
				//The if statement below controls the gravity and movement of the lander.
				//If hasLanded and paused are either true, the gravity and 
				//movement of the lander will cease.
				if(!hasLanded && !paused) {
					landerDeltaY+=5;
					lander.move(landerDeltaX, landerDeltaY);
				}
				//The mechanics of the game work by adding waves, setting velocity to zero, checking if lander is out of bounds, checking if it has hit an asteroid, checking if it has landed
				//on a landing pad or the ocean, waiting 50 ms, and removing the waves.
				updateScore();
				addWaves();
				setZeroVelocity();
				checkForOutOfBounds();
				checkForAsteroidCollision();
				checkForLanding();
				pause(50);
				removeWaves();


			}

		}
	}


	//checkForOutofBounds() ensures that the user does not go off the level horizontally. This is done by making sure the left end of the
	//lander does not go into negative x and the right side going past the width of the game. The right side is created by adding the width
	//of the lander to the lander.getX() method as this method works from the top left of the lander. 
	private void checkForOutOfBounds() {
		//If the user goes out of bounds, clear the screen and state that they lose.
		if(lander.getX() <= 0 || lander.getX()+ LANDER_WIDTH >= WIDTH){
			clearScreen();
			gameOver("Out of bounds. You lose");
		}
	}
	//checkCollision() reduces redundancy when checking what object the lander is in contact with. It takes the lander, color, and x and y
	//coordinates of what lander corner to check when it is called. It also assigns currentPad the object the lander runs so that
	//the pad can be modified later on.
	//This is an example of a short-circuit boolean as to avoid a null pointer exception, the condition checks if an object exists
	//by seeing if it does not equal null, and then if the object exists, it tests the color.
	public boolean checkCollision(GCompound land, Color color, int x, int y) {
		if(getElementAt(land.getX()+x,land.getY()+y) != null&&getElementAt(land.getX()+x,land.getY()+y).getColor() == Color.YELLOW) {
			currentPad = getElementAt(land.getX()+x,land.getY()+y);
		}
		return getElementAt(land.getX()+x,land.getY()+y) != null && getElementAt(land.getX()+x,land.getY()+y).getColor() == color;
	}
	//checkForAsteroidCollision utilizes checkCollision() to see if the lander makes contact with an asteroid. 
	//For checkCollision to return true, an object must be present at the specified corner of the lander
	//and that object must be white. If this is true, checkForAsteroidCollision calls gameOver and ends the game.
	private void checkForAsteroidCollision() {
		if(checkCollision(lander, Color.WHITE, -1, -1)||checkCollision(lander, Color.WHITE,LANDER_WIDTH+1,LANDER_HEIGHT+1)||checkCollision(lander, Color.WHITE,LANDER_WIDTH+1,-1)||checkCollision(lander, Color.WHITE,-1,LANDER_HEIGHT+1)) {
			gameOver("You hit an asteroid. You lose");
		}
	}
	//checkForLanding() utilizes checkCollision() to see if the lander makes contact with a landing pad or the ocean.
	//If the lander hits the ocean, it ends the game calling gameOver(). Else, if the lander lands on a landing pad, 
	//the lander stops on the pad and the pad turns red. padsVisited is incremented and once the user moves, gravity
	//will turn back on and the user must land on another pad. Once the user lands on NUMBER_OF_PADS, win() is called
	//and they win the game.
	private void checkForLanding() {
		if(checkCollision(lander, Color.YELLOW, 0,LANDER_HEIGHT+2) &&checkCollision(lander, Color.YELLOW, LANDER_WIDTH,LANDER_HEIGHT+2)) {
			currentPad.setColor(Color.RED);
			hasLanded = true;
			padsVisited++;
			if(padsVisited == NUMBER_OF_PADS)
				win();
		}
		else if(checkCollision(lander, Color.YELLOW,-1,LANDER_HEIGHT+1)||checkCollision(lander, Color.YELLOW,LANDER_WIDTH+1,LANDER_HEIGHT+1))
			gameOver("The lander broke in half");
		else if(checkCollision(lander, Color.BLUE,LANDER_WIDTH+1,LANDER_HEIGHT+1)||checkCollision(lander, Color.BLUE,-1,LANDER_HEIGHT+1))
			gameOver("You crashed into the ocean");


	}



	//gameOver() gives the user a message telling them how they lost the game and then ends the
	//game after clearing the screen and displaying the message
	private void gameOver(String message) {
		clearScreen();
		GLabel gameover = new GLabel (message , WIDTH/3-180, HEIGHT/2);
		gameover.setColor(Color.WHITE);
		gameover.setFont(new Font(message, Font.BOLD, 80));
		add(gameover);
		gameRunning = false;

	}

	//win() is called when the user lands on all of the landing pads without dying. It tells the user they won
	//with a message on the screen and then ends the game.
	private void win() {
		clearScreen();
		GLabel won = new GLabel ("You won!!!" , WIDTH/3, HEIGHT/2);
		won.setColor(Color.WHITE);
		won.setFont(new Font("You won!!!", Font.BOLD, 80));
		add(won);
		gameRunning = false;
	}

}
