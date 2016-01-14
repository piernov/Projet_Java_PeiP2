import java.awt.*;
import javax.swing.*;

/**
 * This provides a JPanel drawing a grid with characters.
 * Actually displays whats contained in the Grid object and the score.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class DisplayGrid extends JPanel {
	private Grid grid;
	private final int cellWidth = 30; // Fixed-width and height (square) grid cell

	/**
	 * Creates a new DisplayGrid object with given Grid object.
	 * @param	grid	previously created Grid object
	 */
	public DisplayGrid (Grid grid) {
		this.grid = grid;
	}

	/**
	 * Returns the cell width. Would be more useful if it wasn't fixed.
	 * @return	cell width
	 */
	public int getCellWidth() {
		return this.cellWidth;
	}

	/**
	 * Actually draws the grid content on the screen.
	 */
	@Override
	public void paintComponent(Graphics g) {
		// Calls the parent's paintComponent, required to clear the drawing area before rendering it again.
		super.paintComponent(g);

		char[][] gr = this.grid.getGrid();

		for(int i=0; i< gr.length; i++) {
			for(int j=0; j< gr[i].length; j++) {
				char c = gr[i][j];

				g.setColor(Color.black);
				g.drawRect(j*this.cellWidth, i*this.cellWidth, this.cellWidth, this.cellWidth);

				// If cell does not contain a letter but a digit, color the digit in red.
				if(Character.isDigit(c)) g.setColor(Color.red);
				// Otherwise if it contains a letter, color it in blue.
				else g.setColor(Color.blue);
				g.drawString(String.valueOf(c), j*this.cellWidth+12, i*this.cellWidth+24);
			}
		}

		// Displays last placed word score and total score.
		g.setColor(Color.black);
		g.drawString("Word's score: " + this.grid.getWordScore(), 10, cellWidth*gr.length+24);
		g.drawString("Total score:   " + this.grid.getTotalScore(), 10, cellWidth*gr.length+36);
	}

	@Override
	public Dimension getPreferredSize() {
		int gridLength = this.grid.getGrid()[0].length*this.cellWidth;
		return new Dimension(gridLength+10, gridLength+48);
	}
}
