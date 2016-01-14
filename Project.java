import java.io.*;

/**
 * The main class which contains the method main().
 * This the class to run in order to execute the program.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */
public class Project {
	private static char mode = 'a';
	private static Grid grid;
	private static WordsList list;
	private static Game game;
	private static boolean interactive;
	private static int gridSize;
	private static String filename;

	/**
	 * Initialize by creating a WordList object, a Grid object and a Game object.
	 */
	private static void init() {
		list = new WordsList();
		grid = new Grid();
		game = new Game(grid, list);
	}

	/**
	 * This loads the list of Words and the Grid by taking into account the params entered in the Terminal
	 */
	public static void load() {
		list.load(); // Load words in WordsList from file
		grid.setSize(gridSize > 0 ? gridSize : list.maxLength()); // Use given grid size, otherwise use longest word length in list
		grid.init(); // Initialize grid with random numbers
	}

	/**
	 * This asks whether the player wants to play with the graphic interface or the Terminal
	 * An error can come up with the StdInput.readlnChar() so we put "throws IOException".
	 */
	private static void askMode() throws IOException {
		while((mode != 'm') && (mode != 'k')) {
			System.out.println("Do you want to play with the mouse or the keyboard? (m/k)");
			mode = StdInput.readlnChar();
		}
	}

	/**
	 * Display help message on error output.
	 */
	private static void usage() {
		String tmp = "Usage : java Project [OPTION]... [FILE]\n"
		+ "\n"
		+ "\tFILE\tlist of words to use\n"
		+ "\t-h\tdisplay this help and exit\n"
		+ "\t-i\tinteractive mode: do not start graphical interface\n"
		+ "\t-s\tset grid size";
		System.err.println(tmp);
		System.exit(1);
	}

	/**
	 * This handles the params written when we write in the Terminal "java Project params".
	 */
	private static void parseArgs(String[] args) {
		boolean getSizeArg = false;
		boolean gotFilename = false;
		for(String arg: args) {
			if(arg.equals("-h")) // Show help
				usage();
			else if(arg.equals("-i")) // Run in non-graphical mode
				interactive = true;
			else if(arg.equals("-s")) // Set grid size
				getSizeArg = true;
			else if(getSizeArg) {
				try {
					gridSize = Integer.parseInt(arg); // Get grid size parameter
				}
				catch (NumberFormatException e) { // Invalid parameter
					usage();
				}
				getSizeArg = false;
			}
			else if(!gotFilename) { // Get filename
				filename = arg;
				gotFilename = true;
			}
			else usage(); // Invalid argument
		}
	}

	/**
	 * This starts the graphical interface.
	 */
	private static void startGui() {
		Window win = new Window(grid, list, game);
		game.setEnd(win.end); // Bind end function with graphical one
	}

	/**
	 * This starts the game with the Terminal.
	 */
	private static void startTerm() {
		Terminal ter = new Terminal(game);
		game.setEnd(Terminal::end); // Bind end function with non-graphical one
		while(true) {
			System.out.println(grid);
			System.out.println(list);
			ter.setWordTerminal();
		}
	}

	/**
	 * Method main which is called when we run the program.
	 * @param	args	array of command-line arguments
	 */
	public static void main(String[] args) throws IOException {
		init();
		parseArgs(args);
		list.setFilename(filename);
		load();
		if(interactive) startTerm();
		else {
			askMode();
			switch(mode) {
				case 'm':
					startGui();
					break;
				case 'k':
					startTerm();
					break;
		}
		}
	}
}
