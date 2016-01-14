import java.io.*;
import java.util.*;
import java.text.*;
import java.util.regex.*;

/**
 * This class allows interaction with the grid based on a command line in the GNU.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */
public class Terminal {
	Game game;

	/**
	 * Class constructor
	 * @param      game    previously created in the Project class in main()
	 */
	public Terminal(Game game) {
		this.game = game;
	}

	/**
	 * Place words in the grid based on a command line previously read and used
	 */
	public void setWordTerminal() {
		try {//A try/catch is necesssary because StdInput.readLine() can generate an error.
			boolean vert = false;
			String command = StdInput.readLine();
			//Creation of a regexp which matches only the command line type given in instructions
			Pattern pexp = Pattern.compile("([0-9]+)([hv])\\(([0-9]+),([0-9]+)\\)");
			Matcher m1 = pexp.matcher(command.trim());
			if(m1.matches()) {
				if(m1.group(2).equals("v")) vert = true;

				//Each group created with the regexp is String so it is necessary to add Integer.valueOf in order to convert it to Int
				if(!this.game.setWord(Integer.valueOf(m1.group(1)),
					Integer.valueOf(m1.group(3)) , Integer.valueOf(m1.group(4)) , vert)) {
						System.out.println("Wrong placement");
				}
			}
			else System.out.println("Error in command line");
		} catch(IOException e) {System.err.println("Input/Output Error.");}
	}

	/**
	 * Print "End" and exit the game when the game is finished.
	 */
	public static void end() {
		System.out.println("End");
		System.exit(0);
	}
}
