/**
 * This class allows interaction between the grid and the words list.
 * Links together Grid and WordsList objects.
 * Provides and generic method for both graphical and non-graphical mode for placing words and ending the game.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class Game {
	Grid grid;
	WordsList list;
	private Runnable end; // Run this function at the end of the game

	/**
	 * Creates a new Game object with given Grid and WordsList.
	 * @param	list	previously created WordsList object
	 * @param	grid	previously created Grid object
	 */
	public Game(Grid grid, WordsList list) {
		this.grid = grid;
		this.list = list;
	}

	/**
	 * Returns true if the conditions for ending the game are met.
	 *
	 * Those conditions are either that all words have been placed, or that there is no room left for another word.
	 * @return	true if the game is finished
	 */
	public boolean checkEnd() {
			int maxContiguous = grid.maxContiguous();
			int minWordSize = list.minLength();
			if(maxContiguous < minWordSize) { // No room left
				System.out.println("LOST");
				return true;
			}
			else if(minWordSize == 0) { // No words left
				System.out.println("WIN");
				return true;
			}
			return false;
	}

	/**
	 * Calls Grid::setWord, delete the placed word, run callback and end the game if applicable.
	 * @param	word	word to place on the grid
	 * @param	l	line to begin at
	 * @param	c	column to begin at
	 * @param	v	if true, places the word vertically, otherwise places it horizontally
	 * @param	callback Runnable functional interface called after a word has been placed and before the end of the game if applicable
	 * @return	true if the game has ended, false otherwise
	 */
	public boolean setWord(String word, int l, int c, boolean v, Runnable callback) {
		if(grid.setWord(word,l,c,v)) // Word successfully placed
		{
			list.deleteWord(word); // So delete if from the list
			callback.run(); // Run the callback to notify caller of update
			if(checkEnd()) end.run(); // And if we reached the end of the game run the appropriate method
			return true;
		}
		return false;
	}

	/**
	 * Get the word at the given index, calls Grid::setWord with it, delete the placed word and end the game if applicable.
	 * @param	index	index of word to place in words list
	 * @param	l	line to begin at
	 * @param	c	column to begin at
	 * @param	v	if true, places the word vertically, otherwise places it horizontally
	 * @return      true if the game has ended, false otherwise
	 */
	public boolean setWord(int index, int l, int c, boolean v) {
		String[] wordsList = list.getWordsList();
		if(index < 0 || index >= wordsList.length || wordsList[index] == null) return false; // If wrong index
		if(grid.setWord(wordsList[index],l,c,v)) // Word successfully placed
		{
			list.deleteWord(index); // So delete if from the list
			if(checkEnd()) end.run(); // And if we reached the end of the game run the appropriate method
			return true;
		}
		return false;
	}

	/**
	 * Set the method to run at the end of the game to the provided one.
	 * @param	end	Runnable functional interface to call at the end of the game
	 */
	public void setEnd(Runnable end) {
		this.end = end;
	}
}
