import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * This creates a JMenuBar at the top of the window.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class MenuBar extends JMenuBar implements ActionListener{
	private JMenu menuFile, menuHelp; //the differents menus in the MenuBar.
	private JMenuItem menuItemRestart, menuItemExit, menuItemRules, menuItemAbout; //the differents menuItem in the menus.
	private Runnable reload;

	/**
	 * Class Constructor
	 * @param	reload	Runnable functional interface to call when we want to restart the game.
	 */
	public MenuBar(Runnable reload){
		this.reload = reload;
		//Creates the menus of the JMenuBar.
		menuFile = new JMenu("File");
		menuHelp = new JMenu("Help");

		//Creates the menuItems of the menus.
		menuItemRestart = new JMenuItem("Restart");
		menuItemExit = new JMenuItem("Exit");
		menuItemRules = new JMenuItem("Rules");
		menuItemAbout = new JMenuItem("About Us");

		//Add shortcuts to the menuItems.
		menuItemRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_MASK));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
		menuItemRules.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_MASK));
		menuItemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));

		//Add a ActionListener to each menuItem.
		menuItemRestart.addActionListener(this);
		menuItemExit.addActionListener(this);
		menuItemRules.addActionListener(this);
		menuItemAbout.addActionListener(this);

		//Add the menuItems in the menus
		menuFile.add(menuItemRestart);
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemRules);
		menuHelp.add(menuItemAbout);

		//Add the menus in the MenuBar.
		this.add(menuFile);
		this.add(menuHelp);

		setVisible(true);
	}

	/**
	 * Called when a menuItem is selected
	 * @param  e   ActionEvent object
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == menuItemRestart){
			reload.run();
		}
		if(e.getSource() == menuItemExit){
			System.exit(0);
		}
		if(e.getSource() == menuItemRules){
			JOptionPane rules = new JOptionPane();
			String r = " You have to place the words provided on the right of the screen in the grid. \n" +
						" Use the right button of your mouse to place the word horizontally. Use the left \n" +
						" to place it vertically. Each square has its own value and when you fill a square \n" +
						" your score will raise of the exact value of the square. The game is finished \n" +
						" when there is no more words to place or if there is not enough blanks to place \n" +
						" the remaining words.";
			rules.showMessageDialog(null, r , "Rules", JOptionPane.INFORMATION_MESSAGE);
		}
		if(e.getSource() == menuItemAbout){
			JOptionPane about = new JOptionPane();
			String s = " Authors : \n NOVAC Pierre-Emmmanuel \n RENOUX Alexandre \n" +
						" Creation date : 14/01/2016 \n"+
						" Contacts : piernov@piernov.org  alexbankai96@googlemail.com";
			about.showMessageDialog(null, s , "About Us", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
