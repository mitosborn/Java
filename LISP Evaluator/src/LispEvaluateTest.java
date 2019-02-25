import static org.junit.Assert.*;

import org.junit.Test;

public class LispEvaluateTest {

	public static final double EPSILON = 0.000001;

	@Test
	public void testLispCalculate() {
		
		assertEquals(2465.244, LispEvaluate.lispCalculate("(+ 4 3 4.324 (- 1 5) 56 (* 9 (/ 8.34 3) 32 3))"), 0);
		assertEquals(16.5, LispEvaluate.lispCalculate("(+ (- 6) (* 2 3 4) (/ (+ 3) (*) (- 2 3 1)))"), 0);
		assertEquals(-2302.733333333333, LispEvaluate.lispCalculate("(- 4 6.7 (* 23 100) (+) (/ 4 (+ 2 (- 34 (* 3 2) (- 2 (+ 92))))))"), 0);
		
		// testing subtraction with negative numbers
		assertEquals(2, LispEvaluate.lispCalculate("(- 2 -2 2 -2 2)"), 0);
		assertEquals(18, LispEvaluate.lispCalculate("(- 10 -2 -10 2 2)"), 0);
		
		// testing subtraction with positive numbers
		assertEquals(-7, LispEvaluate.lispCalculate("(- 20 2 9 2 9 2 3)"), 0);
		
		// testing subtraction with decimal numbers
		assertEquals(-5131.7, LispEvaluate.lispCalculate("(- 4 6.7 32 4 2 92 32 4942 23 2)"), 0);
		
		// testing addition with decimal numbers 
		assertEquals(23.3, LispEvaluate.lispCalculate("(+ 6.6 7.9 5.5 3.3)"), EPSILON);
		
		// testing multiplication, division, subtraction and addition all in one equation 
		assertEquals(87.5, LispEvaluate.lispCalculate("( * 1 2 ( / 10 4 2 ) ( + 6 5 ( - 1 2 3 ) ) ( + 2 3 ) )"), 0);
		
		// testing multiplication, division, subtraction and addition all in one equation with negative numbers.
		assertEquals(0, LispEvaluate.lispCalculate("(* (- (/ -100 -2 -25) -2) -3)"), 0);
		
		// testing multiplication, division and addition with negative numbers 
		assertEquals(-4, LispEvaluate.lispCalculate("(* (/ 4 -2) (+ 5 -3))"), 0);
		
		
		
		
		// testing multiplying 0 to any number 
		assertEquals(0, LispEvaluate.lispCalculate("(* (- 600494 75644 8374723) 0)"), 0);
		
	}

}