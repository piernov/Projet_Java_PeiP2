import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

/**
 * This class handles reading a list of words from a given file and storing it in memory.
 *
 * @author Alexandre Renoux
 * @author Pierre-Emmanuel Novac
 */

public class WordsList {
	private String[] wordsList;
	private String filename;
	private final int MAXMOTS = 30;
	private final String DEFFILENAME = "wordsList.txt";

	/**
	 * Creates a new WordList object.
	 */
	public WordsList() {
	}

	/** Set name of the file to load.
	 * @param	filename	read words from this file
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Reads a text file containing one word per line and stores it in the object.
	 */
	public void load() {
		if(filename == null) {
			System.err.println("No file name set. Using default: "+DEFFILENAME);
			this.filename = DEFFILENAME;
		}
		BufferedReader words = null;
		String[] tmp = new String[this.MAXMOTS]; // Temporary array of size = 30
		int i = 0;
		try{
			words = new BufferedReader(new FileReader(filename)); // Open the file
		}
		catch(FileNotFoundException e) {
			System.err.println("Cannot open " + filename + ": File not found.");
			System.exit(2);
		}
		try{
			String res;
			while((res = words.readLine()) != null){ // Read it line by line
					res = res.trim(); // Remove useless leading and trailing blanks
					if("".equals(res)) continue; // Don't import empty lines
					tmp[i] = res; // Stores the line into our array
					i++;
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("List too long. Only "+MAXMOTS+" entries loaded."); // 30 wasn't enough
		}
		catch (IOException e) {
			System.err.println("Cannot read file: Input/Output error.");
			System.exit(3);
		}
		finally {
			try {
				words.close(); // Always close the file
			}
			catch (IOException e) {
				System.err.println("Cannot close file: Input/Output error.");
				System.exit(4);
			}
		}

		this.wordsList = new String[i]; // Creates a new array of the correct size
		for ( int j = 0 ; j < this.wordsList.length ; j++){
			this.wordsList[j] = tmp[j]; // And copy all words from our temporary array
		}
	}

	/**
	 * Internal generic method used by maxLength() or minLength().
	 * @param	ext	either Stream::min or Stream::max
	 * @return	minimum or maximum String length found in the list
	 */
	private int extLength(BiFunction<Stream<String>, Comparator<String>, Optional<String>> ext) { // Parameter type designed to match Stream::min and Stream::max.
		// Apply the min or max method on a stream created from the list of words, using the comparison between strings length.
		// Make sure we get a String and not an Optional with orElse(""), and return its length.
		return ext.apply(Arrays.stream(wordsList).filter(Objects::nonNull), Comparator.comparing(String::length)).orElse("").length();
	}

	/**
	 * Get max String length in WordsList, calling internal extLength() with method reference to Stream::max.
	 * @return	maximum String length in the list
	 */
	public int maxLength() {
		return extLength(Stream::max);
	}

	/**
	 * Get minimum String length in WordsList, calling internal extLength() with method reference to Stream::min.
	 * @return	minimum String length in the list
	 */
	public int minLength() {
		return extLength(Stream::min);
	}

	/**
	 * Returns an array containing all words.
	 * @return	an array containing all words
	 */
	public String[] getWordsList(){
		return this.wordsList;
	}

	/**
	 * Deletes a word from the list.
	 * @param	word	word to delete
	 */
	public void deleteWord(String word) {
		// We declare a new functional interface for use with filter() in order to remove only the first occurence of the given word
		Predicate<String> remove = new Predicate<String>() {
			boolean found = false;
			@Override public boolean test(String str) {
				// So if we didn't find the word previously and the current element of the stream matches, then set found to true and return false which will not include this element in the stream output.
				// If it doesn't match or we found one already, return true so that this element is copied in the stream output.
				if(!found && str.equals(word)) return !(found = true);
				else return true;
			}
		};
		// Filter a stream coming from our array of words, and insert it in a new array. This has the effect of returning an array sized exactly for the elements it contains.
		// We replace the old reference by the new one.
		wordsList = Arrays.stream(wordsList).filter(remove).toArray(String[]::new);
	}

	/**
	 *Deletes the word at the given index from the list.
	 * @param	index	index of word to delete from list
	 */
	public void deleteWord(int index) {
		if(index < 0 || index >= wordsList.length) return;
		wordsList[index] = null;
		return;
	}

	/**
	 * Returns a string representation of the list.
	 *
	 * Words are printed one after another, separated by a coma. The whole list is enclosed by parenthesis.
	 *
	 * @return a string representation of the list
	 */
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i< wordsList.length ; i++){
			if( wordsList[i] != null) s +="(" + i + ")" + wordsList[i] + ", ";
		}
		return s;
	}
}
