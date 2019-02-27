import java.util.Arrays;

public class Bioinformatics {

	/**
	 * Determines the length of the longest common subsequence
	 * between two DNA strands. Note that valid DNA strings can
	 * only contain the letters A, T, C, and G. This slow
	 * version of the method simply uses the recursive score
	 * calculation, which makes it very inefficient.
	 * 
	 * @param dna1 A DNA string containing only the letters A, T, C, and G.
	 * @param dna2 Another DNA string containing only the letters A, T, C, and G.
	 * @return Length of the longest common subsequence in a global alignment
	 *         of the two DNA strands.
	 */
	public static int slowDNAScore(String dna1, String dna2) {
		// The kick-off specifies that last indexes of each string so that both complete strings are checked
		return slowDNAScore(dna1, dna2, dna1.length() - 1, dna2.length() - 1);
	}

	/**
	 * Determines the length of the longest common subsequence
	 * between two DNA strands. Note that valid DNA strings can
	 * only contain the letters A, T, C, and G. This slow
	 * version of the method simply uses the recursive score
	 * calculation, which makes it very inefficient.
	 *
	 * @param dna1 A DNA string containing only the letters A, T, C, and G.
	 * @param dna2 Another DNA string containing only the letters A, T, C, and G.
	 * @param i Last character in DNA1 string
	 * @param j Last character in DNA2 string
	 * @return Length of the longest common subsequence in a global alignment
	 *         of the two DNA strands.
	 */
	private static int slowDNAScore(String dna1, String dna2, int i, int j) {
		int otherMax = 0, tempMax;
		//Base case, one or both of the strings is empty
		if(i == -1 || j ==-1)
			return 0;
		else {

			int DNA1Length = slowDNAScore(dna1, dna2, i-1, j); // Length of DNA1 strand
			int DNA2Length = slowDNAScore(dna1, dna2, i, j-1); // Length of DNA2 strand

			//Max of these statements
			tempMax = Math.max(DNA1Length, DNA2Length);		

			if(dna1.charAt(i) == dna2.charAt(j)) // If last characters of DNA1 and DNA2 match

				otherMax = 1+ slowDNAScore(dna1, dna2, i-1, j-1); // Add 1 and decrement by 1
		}
		return Math.max(otherMax, tempMax); // Return the max of either tempMax or otherMax
	}	

	/**
	 * This method should return the exact same answers as your
	 * slowDNAScore method. However, it must use memoization to
	 * perform faster. This means that previously calculated
	 * results will be saved for reuse later. You should save
	 * your previously calculated results in a 2D array that
	 * is passed between all recursive method calls. Otherwise,
	 * the code is very similar to the slowDNAScore method.
	 * 
	 * @param dna1 A DNA string containing only the letters A, T, C, and G.
	 * @param dna2 Another DNA string containing only the letters A, T, C, and G.
	 * @return Length of the longest common subsequence in a global alignment
	 *         of the two DNA strands.
	 */
	public static int fastDNAScore(String dna1, String dna2) {

		// known[i][j] will store results of fastDNAScore(known, dna1, dna2, i, j)
		int[][] known = new int[dna1.length()][dna2.length()];
		for(int i = 0; i < known.length; i++) {
			Arrays.fill(known[i], -1);
		}
		return fastDNAScore(known, dna1, dna2, dna1.length() - 1, dna2.length() - 1);
	}

	/**
	 *
	 * Determines the length of the longest common subsequence
	 * between two DNA strands. Note that valid DNA strings can
	 * only contain the letters A, T, C, and G. This slow
	 * version of the method simply uses the recursive score
	 * calculation, which makes it very inefficient.
	 * 
	 * @param known Array to store the known values that we have already computed
	 * @param i The last index of DNA1 strand
	 * @param j The last index of DNA2 strand
	 * @param dna1 A DNA string containing only the letters A, T, C, and G.
	 * @param dna2 Another DNA string containing only the letters A, T, C, and G.
	 * @return Length of the longest common subsequence in a global alignment
	 *         of the two DNA strands.
	 */
	private static int fastDNAScore(int[][] known, String dna1, String dna2, int i, int j) {
		int otherMax = 0;
		int tempMax;
		if(i == -1 || j ==-1) // Base case
			return 0;
		else if (known[i][j] != -1) {
			return known[i][j]; // We already have it sorted
		}
		else {
			int DNA1Length = fastDNAScore(known, dna1, dna2, i-1, j); // Length of DNA1 strand
			int DNA2Length = fastDNAScore(known, dna1, dna2, i, j-1); // Length of DNA2 strand
			//Max of these statements
			tempMax = Math.max(DNA1Length, DNA2Length);		
			if(dna1.charAt(i) == dna2.charAt(j)) // If last characters of DNA1 and DNA2 match
				otherMax = 1+ fastDNAScore(known, dna1, dna2, i-1, j-1); // Add 1 and decrement by 1

			int result = Math.max(tempMax, otherMax);// Longest subsequence
			known[i][j] = result;  
			return result; 
		}
	}	

	/** See if the Characters can pair with one another
	 * 
	 * @param i A char from an RNA string 
	 * @param j A char from an RNA string
	 * @return False if the char's cannot pair
	 * 
	 */
	private static boolean canPair(char i, char j) {
		if(i == 'C' && j == 'G') return true; // Can pair
		
		else if(j=='C' && i == 'G') return true;
		
		else if(i == 'A' && j =='U') return true;
		
		else if(j == 'A' && i =='U') return true;
		
		else return false;
	}

	/**
	 * Method determines the maximum number of base pair matches 
	 * in a folded RNA strand with no pseudo-knots (bases that
	 * are part of different loops cannot pair up). This slow
	 * version of the method simply uses the recursive score
	 * calculation, which makes it very inefficient.
	 * 
	 * @param rna String representing RNA strand. Can only contain
	 *            the letters A, U, C, and G.
	 * @return Maximum number of base pairings in folded strand
	 *         with no pseudo-knots.
	 */
	public static int slowRNAScore(String rna) {
		// Initial start and end indexes comprise the entire String
		return slowRNAScore(rna, 0, rna.length() - 1);
	}	


	/**
	 * Method determines the maximum number of base pair matches 
	 * in a folded RNA strand with no pseudo-knots (bases that
	 * are part of different loops cannot pair up).
	 * 
	 * @param i The first index of RNA strand
	 * @param j The last index of RNA strand
	 * @param dna1 A RNA string containing only the letters A, U, C, and G.
	 * @param dna2 Another RNA string containing only the letters A, U, C, and G.
	 * 
	 * @return Length of the longest common subsequence in a global alignment
	 *         of the two RNA strands.
	 */
	private static int slowRNAScore(String rna, int i, int j) {
		int tempMax;
		int otherMax = 0;
		if(i >= j) {
			return 0; // Base case
		}
		else {	// Recursive call
			int rna1Length = slowRNAScore(rna, i+1, j); // Length of RNA string 1
			int rna2Length = slowRNAScore(rna, i, j-1);	// Length if RNA string 2
			tempMax = Math.max(rna1Length, rna2Length); // Set temp pair

			if(canPair(rna.charAt(i),rna.charAt(j))) { // Special case if the two can pair.
				otherMax = 1 + slowRNAScore(rna, i+1, j-1);
				otherMax = Math.max(otherMax, tempMax); // Set new pair
			}

			for(int k = i+1; k+1 < j; k++) { 
				//				System.out.print("k: "+ k +" rna length: " + rna.length());
				otherMax = Math.max(otherMax, slowRNAScore(rna, i,k) + slowRNAScore(rna, k+1, j));		
			}
		}
		return Math.max(tempMax, otherMax); // Return longest subsequence
	}	

	/**
	 * @param rna String representing RNA strand. Can only contain
	 *            the letters A, U, C, and G.
	 * @return Maximum number of base pairings in folded strand
	 *         with no pseudo-knots.
	 */
	public static int fastRNAScore(String rna) {
		// known[i][j] will store results of fastRNAScore(known, rna, i, j)
		int[][] known = new int[rna.length()][rna.length()];
		for(int i = 0; i < known.length; i++) {
			Arrays.fill(known[i], -1);
		}
		return fastRNAScore(known, rna.toUpperCase(), 0, rna.length() - 1);
	}
	/**
	 * Method determines the maximum number of base pair matches 
	 * in a folded RNA strand with no pseudo-knots (bases that
	 * are part of different loops cannot pair up). 
	 * 
	 * @param rna String representing RNA strand. Can only contain
	 *            the letters A, U, C, and G.
	 * @return Maximum number of base pairings in folded strand
	 *         with no pseudo-knots.
	 * @param known The array with maximum number of base pair matches
	 * @param rna The strand of RNA
	 * @param i The first index in the strand
	 * @param j The last index in the strand
	 * @return The maximum number of vase pair matches in the RNA strand
	 * 
	 */
	private static int fastRNAScore(int[][] known, String rna, int i, int j) {
		int tempMax;
		int otherMax = 0;
		if(i >= j) {
			return 0;  // Default case
		}
		else if (known[i][j] != -1) { // Base case known pairs
			return known[i][j];
		}
		else {	// Recursive calls
			int rna1Length = fastRNAScore(known, rna, i+1, j);// Length of RNA string 1
			int rna2Length = fastRNAScore(known, rna, i, j-1);// Length of RNA string 2
			tempMax = Math.max(rna1Length, rna2Length); // Set temp pair

			if(canPair(rna.charAt(i),rna.charAt(j))) {
				otherMax = 1 + fastRNAScore(known, rna, i+1, j-1); // if can pair set new pair
				otherMax = Math.max(otherMax, tempMax); 
			}

			for(int k = i+1; k+1 < j; k++) {
				//				System.out.print("k: "+ k +" rna length: " + rna.length());
				otherMax = Math.max(otherMax, fastRNAScore(known,rna, i,k) + fastRNAScore(known,rna, k+1, j));		
			}
		}
		int result = Math.max(tempMax, otherMax); 
		known[i][j] = result; // Longest subsequence
		return result;
	}

}
