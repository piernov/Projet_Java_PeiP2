import java.util.Random;
import java.util.Arrays;

/**
 * This class handles the storage of values in the grid.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class Grid {
	private char grid[][]; // Here we store each character on a cell of a 2D char array
	private int size;
	private int wordScore; // Score for the word that has just been placed
	private int totalScore;

	/**
	 * Creates a new Grid object.
	 */
	public Grid() {
	}

	/** Set size of the grid
	 * @param	size	size of the grid
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Creates a new 2D array as member.
	 *
	 * Simply creates a new 2D array as a member of the class.
	 * Uses previously set size for both dimensions.
	 */
	public void init() {
		grid = new char[size][size];
		this.initRandom();
		wordScore = 0;
		totalScore = 0;
	}

	/**
	 * Returns a string representation of the grid.
	 *
	 * Each of the characters stored in the grid are printed in cells, delimited by a pipe for columns, and a hyphen for lines.
	 *
	 * @return a string representation of the grid
	 */
	@Override
	public String toString() {
		String str = "";
		String delim = "";
		for(char l[]: grid) { // Iterate over lines
			str += "|"; // First border on the left
			delim = "—"; // First border on the bottom
			for(char c: l) { // Iterate over columns
				str += " " + c + " |"; // Add a border on the right of the cell
				delim += "——––"; // Extend bottom border for one char
			}
			str += "\n" + delim + "\n"; // Add the bottom border to the string
		}
		str = delim + "\n" + str; // Add first top border for the table
		return str;
	}

	/**
	 * Places a word in the grid while updating the score and returns true if successful.
	 *
	 * First checks if given parameters are valid, then checks if all the cells needed are empty.
	 * If one of those conditions is not met, returns immediately false.
	 * Else, each of the characters (all being numbers) is replaced one by one using those from the given word.
	 * At the same time, the score for the word is calculated, then the total score is updated.
	 * Inside this method, we uses the ternary operator ?: in order to shorten the code.
	 *
	 * @param	word	word to place on the grid
	 * @param	l	line to begin at
	 * @param	c	column to begin at
	 * @param	v	if true, places the word vertically, otherwise places it horizontally
	 * @return	true if the word is successfully placed, false otherwise
	 */
	public boolean setWord(String word, int l, int c, boolean v) {
		if(l<0 || c<0) return false; // Negative values for coordinates are invalid

		if((word.length() > grid.length - (v ? l : c)) || // Word doesn't fit
			((v ? c : l) >= grid.length)) return false; // Or the other coordinate is out of bounds

		this.wordScore = 0;
		for(int i = 0; i < word.length(); i++) { // This first loop checks if the word fits and computes the score
			int nbt = Character.getNumericValue(grid[v ? l+i :l][v ? c : c+i]); // Used to convert a char into an int
			if(nbt<0 || nbt>9) return false; // It was not a digit so it means the cell is not empty
			this.wordScore += nbt; // Update the score for current word with the number present on the cell
		}
		for(int i = 0; i < word.length(); i++) { // Then actually place the word, characters one by one
			grid[v ? l+i :l][v ? c : c+i] = word.charAt(i); // Gets the char at the specified position in the string and replace the cell content with it
		}
		this.totalScore += this.wordScore; // Update the total score
		return true; // Success!
	}

	/**
	 * Initializes the grid content by random numbers.
	 */
	public void initRandom() {
		Random rand = new Random();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = Character.forDigit(rand.nextInt(10), 10); // Used to convert the random integer to a char
			}
		}
	}

	/**
	 * Calculates the longest empty area in the grid.
	 * @return	max contiguous area in the grid
	 */
	public int maxContiguous() {
		int maxContigCol = 0;
		int maxContigLine = 0;

		// Creates a new array used to store temporarly the longest empty area for each columns
		int[] curContigCols = new int[grid[0].length];

		for (int i = 0; i < grid.length; i++) { // Iterate over lines
			int curContigLine = 0;
			for(int j = 0; j < grid[i].length; j++) { // Iterate over columns
				if(Character.isDigit(grid[i][j])) { // Current cell is empty (contains a number)
					curContigLine++; // So we increment the longest empty area found for the current line
					curContigCols[j]++; // Same for the column
				} else {
					curContigCols[j] = 0; // Reset the counter since the empty area is finished
					curContigLine = 0;
				}
				maxContigLine = Math.max(maxContigLine, curContigLine); // Updates the maximum empty area for lines if the current one is greater
				maxContigCol = Math.max(maxContigCol, curContigCols[j]); // Same for column
			}
		}
		return Math.max(maxContigLine, maxContigCol); // And returns the max between empty area for lines and columns
	}

	/**
	 * Returns the 2D array of the grid.
	 * @return	the grid
	 */
	public char[][] getGrid(){
		return this.grid;
	}

	/**
	 * Returns the total score.
	 * @return	total score
	 */
	public int getWordScore() {
		return this.wordScore;
	}

	/**
	 * Returns the score for the last placed word.
	 * @return	score for last word
	 */
	public int getTotalScore() {
		return this.totalScore;
	}
}
