import java.awt.event.*;
import javax.swing.*;

/**
 * This class allows interaction with the GUI.
 * Links together Game, DisplayWordsList and DisplayGrid objects.
 * Extends MouseAdapter to provide mouse events handling for use with DisplayGrid.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class DisplayGame extends MouseAdapter {
	private Game game;
	private DisplayWordsList dlist;
	private DisplayGrid dgrid;

	/**
	 * Creates a new DisplayGame object with given Game, DisplayWordsList and DisplayGrid.
	 * @param	game	previously created Game object
	 * @param	dlist	previously created DisplayWordsList object
	 * @param	dgrid	previously created DisplayGrid object
	 */
	public DisplayGame(Game game, DisplayWordsList dlist, DisplayGrid dgrid) {
		this.game = game;
		this.dlist = dlist;
		this.dgrid = dgrid;
	}

	/**
	 * Calls Game::setWord and updates the GUI.
	 * @param	word	word to place on the grid
	 * @param	l	line to begin at
	 * @param	c	column to begin at
	 * @param	v	if true, places the word vertically, otherwise places it horizontally
	 */
	private void setWord(String word, int l, int c, boolean v) {
		this.game.setWord(word, l, c, v, () -> {
			this.dlist.disableSelectedButton(); // Disable the button if the word was successfully placed.
			this.dgrid.repaint(); // Don't forget to repaint the grid to actually display the changes.
		});
	}

	/**
	 * Handles mouse click events for DisplayGrid.
	 *
	 * Called after button has been pressed then released.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// No button was previously clicked or the word was already placed so we return immediately.
		if(!this.dlist.isSelectedButtonAvailable()) return;

		// Translates mouse coordinates into column and line numbers.
		int c = (int) Math.floor(e.getX() / this.dgrid.getCellWidth());
		int l = (int) Math.floor(e.getY() / this.dgrid.getCellWidth());

		boolean v = false; // Word placed horizontally by default.

		switch(e.getButton()) {
			case 3: // Right click
				v = true; // Set word orientation to vertical if placed with a right click.
				// No break statement because we want setWord to be called in both cases.
			case 1: // Left click
				this.setWord(this.dlist.getSelectedButtonText(), l, c, v);
		}
	}
}
