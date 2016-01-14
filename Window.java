import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
 * This creates a window which is a JFrame object.
 * Therefore Window extends JFrame.
 * This allows to have a graphic interface for the game.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class Window extends JFrame {
	Runnable end;

	/**
	  * Creates a new Window object with given Game, Grid and WordList.
	  *
	  * @param	game	previously created in the Project class in main()
	  * @param	list	previously created in the Project class in main()
	  * @param	grid	previously created in the Project class in main()
	  */
	public Window(Grid grid, WordsList list, Game game) {
		//Creates a DisplayWordsList object, DisplayGrid object and a DisplayGame object.
		DisplayWordsList dlist = new DisplayWordsList(list);
		DisplayGrid dgrid = new DisplayGrid(grid);
		DisplayGame dgame = new DisplayGame(game, dlist, dgrid);

		Runnable reload = () -> {
			Project.load();
			dlist.reload();
			this.repaint();
			this.setVisible(true);
		};

		//Creates a JPanel object and set its layout.
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		//Creates a MenuBar object.
		MenuBar menuBar = new MenuBar(reload);
		this.setJMenuBar(menuBar);

		//Set the layout for the JButtons of the WordList.
		dlist.setLayout(new GridLayout(0,1,0,4));

		// Use 2 columns if words list is too long (more than 3/4 of the height of the screen)
		int screenH = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		int dlistH = dlist.getPreferredSize().height;
		if(screenH*3/4<dlistH) dlist.setLayout(new GridLayout(0,2,0,4));

		//
		dgrid.addMouseListener(dgame);

		//Add dgrid and dlist in the main panel.
		panel.add(dgrid);
		panel.add(dlist);

		//Settings of the window.
		this.setTitle("Le jeu du scrabble-mêlée");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setContentPane(panel);
		//This changes the size of the window to contain all the components inside without letting useless blanks.
		this.pack();
		this.setVisible(true);

		this.end = () -> {
			//Creates a dialog window when the game is finished to ask the player if he wants to try again or exit the game.
			//It also displays the final score.
			Object[] options = {"Try again?", "Exit"};
			int n =JOptionPane.showInternalOptionDialog(this.getContentPane(), "You score is: "+grid.getTotalScore()+".", "End of game",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			switch(n) {
				case 0://If we clicked the button "try again".
					reload.run();
					break;
				case 1://if we clicked the button "Exit".
					System.exit(0);
					break;
			}

		};
	}
}
