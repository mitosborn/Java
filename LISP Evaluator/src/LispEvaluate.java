import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//Mitchell Osborn, Amir Samiepour
//Dr. Schrum - Computer Science II
//February 18th, 2019
// We have acted with honesty and integrity in producing this work and are unaware of anyone who has not.

/**
 * This program reads valid arithmetic LISP expressions
 * from an input text file one line at a time, and
 * evaluates the expressions to calculate the numeric
 * outputs. Each expression and the resulting answer
 * are printed to the console. The code for evaluating
 * LISP expressions must be provided by you, and will
 * make use of stacks.
 *
 */
public class LispEvaluate {

	/**
	 * Main method reads LISP expressions from "Expressions.txt" one
	 * line at a time and evaluates each one. Results are printed
	 * to the console.
	 * 
	 * @param args Ignored
	 * @throws FileNotFoundException If there is a problem finding "Expressions.txt"
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner exp = new Scanner(new File("Expressions.txt"));
		while(exp.hasNextLine()) {
			String line = exp.nextLine();
			System.out.println(line + " evaluates to " + lispCalculate(line));
		}
		exp.close(); // Free Scanner resources
	}

	/**
	 * 
	 * Given a String containing a valid LISP expression using only
	 * the operators +, -, /, and *, evaluate the expression and
	 * return the result.
	 * 
	 * NOTE: You are NOT allowed to use ArrayLists. Your algorithm(s)
	 * should be based on stacks.
	 * 
	 * @param lispExpression Valid arithmetic LISP expression.
	 * @return Result of evaluating LISP expression.
	 */
	/**
	 * lispCalculate is essentially the main method of this program. It takes in the raw lispExpression, spaces the expression
	 * out by calling spaceOutExpression, rearranges it to be in post-fix form using the method prefixToPostfix, and ultimately 
	 * solves the expression by returning the method evaluate with the spaced out postfix expression as the parameter.
	 * @param lispExpression The raw lisp expression entered by the user
	 * @return The result of evaluating the lisp expression in the form of a double
	 */
	public static double lispCalculate(String lispExpression) {
		String spaced= spaceOutExpression(lispExpression);
		System.out.println(spaced);
		String postFix = prefixToPostfix(spaced);
		System.out.println(postFix);
		return evaluate(postFix);
	}
	/**
	 * spaceOutExpression takes a raw lispExpression as its parameter and returns the expression with spaces between
	 * every character. This makes evaluating the lisp expression easier later on in evaluate.
	 * @param lispExpression the raw lisp expression provided by the user
	 * @return the lispExpression spaced out with no characters touching each other
	 */
	private static String spaceOutExpression(String lispExpression) {
		String result = "";
		//For the length of the lispExpression, add spaces around the ( ) so that evaluation is easier
		for(int i = 0; i< lispExpression.length(); i++) {
			//If the char at i is a ( or ), add spaces around it and add that to result
			if(lispExpression.charAt(i) == '('|| lispExpression.charAt(i) == ')') {
				result += " " + lispExpression.charAt(i) + " ";
			}
			//Else, leave the char alone, just copy over into result

			else {
				result+= lispExpression.charAt(i);
			}

		}
		return result;
	}
	/**
	 * prefixToPostfix takes in a spaced prefix expression and outputs the expression in post-fix form
	 * @param spacedExpression
	 * @return expression in post-fix form
	 */
	private static String prefixToPostfix(String spacedExpression)
	{
		StackInterface<String> ops = new ArrayStack<>();
		Scanner s = new Scanner(spacedExpression);
		String result = "";
		//While there are tokens, place operands and ( in the result string and operators on the stack. When a ) is encountered,
		//pop off an operator and place a closed parenthesis in result. This will create a post-fix expression
		while(s.hasNext()) {
			String token = s.next(); //Next in input
			switch(token) {
			case "+": case "*": case "-": case "/":
				ops.push(token);
				break;
			case ")":
				result += " " + ops.pop() + " ) ";
				break;
			default:
				result += " " + token;
				//TODO



			}
		}
		return result;
	}
	/**
	 * Takes in a spaced out post-fix expression and returns the result of the operations
	 * @param expression Expression that is in post-fix notation and is spaced out
	 * @return the result of the operations specified in the post-fix expression
	 */
	public static double evaluate(String expression) {
		//Declare scanner used to take in the contents of the expression
		//and the stack utilized to process operations.
		Scanner s = new Scanner(expression);
		StackInterface<Double> stack = new ArrayStack<>();
		//While there are tokens from the expression, place operands in the stack and place a NaN in the stack when a 
		//open parenthesis is encountered. Furthermore, start operating once an operator is encountered and do that operation
		//by popping numbers off the stack until a NaN is encountered.
		while(s.hasNext()){ 
			String token = s.next();
			//notAnumPresent is utilized throughout the method to indicate when to stop evaluating for a certain operator.
			//It is true until a NaN is encountered.
			boolean notAnumPresent = true;
			switch(token) {
			case "+":
				//While NaN is not present, call addition to add up the operands. Addition returns a boolean if NaN has been 
				//encountered, notifying evaluate when to stop evaluating addition
				while(notAnumPresent){
					notAnumPresent = addition(stack);
				}
				break;
			case "-":
				//While NaN is not present, sum up all the numbers to the right of the very first operand and subtract 
				//the sum from the first number to get the difference by calling the method subtraction. Subtraction returns a
				//boolean if NaN has been encountered, notifying evaluate when to stop evaluating subtraction
				while(notAnumPresent) {
					notAnumPresent = subtraction(stack);
				}
				break;
			case "*":
				//Until NaN is encountered, keep solving for the product of the numbers located between
				//NaN and the operator * by calling multiplication. Multiplication returns a boolean if NaN has been 
				//encountered, notifying evaluate when to stop evaluating multiplication
				while(notAnumPresent){
					notAnumPresent = multiplication(stack);
				}
				break;
			case "/":
				//While a NaN is not present, multiply all terms to the right of the very first term and divide the first term 
				//by the product at the very end when a NaN is present by calling division. Division returns a boolean if NaN has
				//been encountered, notifying evaluate when to stop evaluating division
				while(notAnumPresent){
					notAnumPresent = division(stack);
				}
				break;
				//If a ( is encountered, push it as a NaN onto the stack
			case "(": 
				stack.push(Double.NaN);
				break;	
				//Discard )s as they are not utilized in the calculation
			case ")":
				break;
			default:
				// Convert String tokens to double values
				double value = Double.parseDouble(token);
				stack.push(value);
			}
		}
		return stack.peek();

	}
	/**
	 * Division calculates the quotient of the expression and pushes it into the stack. It works by multiplying all terms to the 
	 * right of the very first term and dividing the first term 
	 * by the product at the very end when a NaN is present.
	 * @param stack Stack that contains the numbers to be divided
	 * @return Boolean if a NaN has been encountered yet
	 */
	private static boolean division(StackInterface<Double> stack) {
		double firstValue, secondValue;
		secondValue = stack.pop();
		//If the value after the first popped number is a NaN, pop the NaN off and push the 
		//inverse of the number onto the stack
		if(stack.peek().equals(Double.NaN)) {
			stack.pop(); //Get rid of the NaN
			stack.push(1/secondValue);
			return false;
		}
		firstValue = stack.pop();
		//If the second number is popped and a NaN is after it, pop the NaN and divide the 
		//firstValue by the second value
		if(stack.peek().equals(Double.NaN)) {
			stack.pop();
			stack.push(firstValue / secondValue);
			return false;
		}
		//Otherwise, multiply the two values and divide the very first number in the expression
		//by the product once it is reached in the stack
		else {
			stack.push(firstValue*secondValue);
		}			
		return true;
	}
	/**
	 * Multiplication works by finding the product of all the terms and pushing this number onto the stack.
	 * It returns a boolean that indicates to the loop in evaluate if a NaN has been encountered and it is time to 
	 * end multiplication operations
	 * @param stack Stack containing numbers to be multiplied 
	 * @return Boolean if it is fine to keep evaluating multiplication depending on if a NaN has been encountered or not
	 */
	private static boolean multiplication(StackInterface<Double> stack) {
		//To verify that NaN is present, the top number is popped off and stored and peek()
		//checks what is behind that second value. If it is not NaN, that firstValue is popped
		//off and multiplied with the second value. Else, a NaN being present after the second
		//value means that multiplication is finished.
		double firstValue, secondValue;
		if(stack.peek().equals(Double.NaN)){
			stack.pop(); //Get rid of the NaN
			stack.push(1.00);
			return false;
		}
		else {
			//If the value after the * operator is a number and there is a number after that, 
			//push the product of the two values onto the stack
			secondValue = stack.pop();
			if(!stack.peek().equals(Double.NaN)) {
				firstValue = stack.pop();
				stack.push(firstValue * secondValue);
			}
			//If the value after the first number popped is a NaN, pop the NaN and push that number 
			//back onto the stack  
			else {
				stack.pop();  //Get rid of the NaN
				stack.push(secondValue); //Store the result of the operation 
				return false;

			}	
		}		return true;
	}
	/**
	 * Addition sums up the numbers in the stack up until a NaN is encountered and pushes the sum onto the stack
	 * @param stack Stack of numbers that will be summed
	 * @return Boolean whether a NaN has been encountered yet. If NaN has not been encountered, the parent method, evaluate, 
	 * will keep calling addition to sum up numbers.
	 */
	public static boolean addition(StackInterface<Double> stack) {
		double firstValue, secondValue;
		if(stack.peek().equals(Double.NaN)) {
			stack.pop(); //Get rid of the NaN
			stack.push(0.0);
			return false;
		}
		else {
			secondValue = stack.pop();
			//If there is not a NaN after the second operand, pop the next number and push the 
			//sum of the first and second values onto the stack
			if(!stack.peek().equals(Double.NaN)) {
				firstValue = stack.pop();
				stack.push(firstValue + secondValue);
			}
			//Else, if there is a NaN after the firstValue, pop the NaN off and place the secondValue
			//onto the stack
			else {
				stack.pop(); //Get rid of the NaN
				stack.push(secondValue);
				return false;
			}	
		}
		return true;
	}
	/**
	 * Subtraction finds the difference of the numbers in the stack up until a NaN is encountered. It does this in conjunction
	 * with evaluate, which holds a loop that calls subtraction while subtraction does not encounter a NaN
	 * @param stack Stack of numbers to find the difference of 
	 * @return Boolean if a NaN has been encountered yet. 
	 */
	public static boolean subtraction(StackInterface<Double> stack) {
		double firstValue, secondValue;
		secondValue = stack.pop();
		//If there is only one number, pop the NaN and push the negative of the number
		//onto the stack
		if(stack.peek().equals(Double.NaN)) {
			stack.pop(); //Get rid of the NaN
			stack.push(-secondValue);
			return false;
		}
		//Else, pop the firstValue and check to see if a NaN is after it. If one is present, take the difference of
		//the first and second value. Otherwise, sum the two terms.
		else {
			firstValue = stack.pop();
			//Else if the value after the two numbers is a NaN, pop the NaN and push the
			//difference of the two numbers onto the stack
			if(stack.peek().equals(Double.NaN)) {
				stack.pop(); //Get rid of the NaN
				stack.push(firstValue - secondValue);
				return false;
			}
			//Else, sum up all numbers to the right of the very first number 
			else {
				stack.push(firstValue + secondValue);
			}

		}
		return true;
	}


}
