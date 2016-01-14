import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * This provides a JPanel contaning buttons for each word in the words list.
 * Implements ActionListener to handle clicks on buttons.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class DisplayWordsList extends JPanel implements ActionListener {
	private JButton selectedButton; // Last clicked button.
	private WordsList list;

	/**
	 * Creates a new DisplayWordsList object with given WordsList object.
	 *
	 * Creates a JButton with text from each word in WordsList.
	 * Events on JButtons are handled by this object itself.
	 * @param	list	previously created WordsList object
	 */
	public DisplayWordsList(WordsList list) {
		this.list = list;
		this.reload();
	}

	/**
	 * Reload all buttons
	 */
	public void reload() {
		this.removeAll();
		String[] ls = list.getWordsList();

		for(int i=0 ; i< ls.length ; i++){
			JButton button = new JButton(ls[i]);
			button.addActionListener(this);
			this.add(button);
		}
		this.setVisible(true);
	}

	/**
	 * Disable the last clicked button.
	 */
	public void disableSelectedButton() {
		this.selectedButton.setEnabled(false);
	}

	/**
	 * Returns the text contained in the last clicked button.
	 * @return	text from last clicked button
	 */
	public String getSelectedButtonText() {
		return this.selectedButton.getText();
	}

	/**
	 * Check if a button was previously clicked and if it is not already disabled.
	 * @return	true if the conditions are met, false otherwise
	 */
	public boolean isSelectedButtonAvailable() {
		return (this.selectedButton != null) && this.selectedButton.isEnabled();
	}
	/**
	 * Handles mouse click events for each JButton. Remembers last clicked button.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Reset previous selected button color if selection is modified
		if(isSelectedButtonAvailable()) this.selectedButton.setBackground(null);
		this.selectedButton = (JButton) e.getSource();
		// Highlight selected button
		this.selectedButton.setBackground(Color.pink);
	}
}
