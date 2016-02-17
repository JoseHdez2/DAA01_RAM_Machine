package util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author jose
 * 
 *  Collection of functions that help
 *  with string processing.
 */
public abstract class StringProcessing {
	final static Charset ENCODING = StandardCharsets.UTF_8;

	/**
	 * Reads the lines of a file and returns them as an ArrayList.
	 * @param fileName
	 * @return An ArrayList<String> where each of the elements is a line.
	 * @throws IOException
	 */
	public static ArrayList<String> readFileLines(String fileName) throws IOException {
		Path path = Paths.get(fileName);
		ArrayList<String> fileLines = new ArrayList<String>();
		
		try (Scanner scanner = new Scanner(path, ENCODING.name())) {
			while (scanner.hasNextLine()) {
				fileLines.add(scanner.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileLines;
	}
	
	/**
	 * For each string in 'lines', only keep the left part 
	 * from the leftmost given separator (if there is any).
	 * @param lines
	 * @return	stringList with all of its comments stripped.
	 */
	public static ArrayList<String> stripComments(ArrayList<String> lines, String delimiter) {
		ArrayList<String> strippedStringList = new ArrayList<String>();
	    
		for (String str : lines) {
		    ArrayList<String> splat = new ArrayList<String>(Arrays.asList(str.split(delimiter)));
            if(!splat.isEmpty()){
                strippedStringList.add(splat.get(0));
            }
			
		}
		
		return strippedStringList;
	}
	
	/**
	 * Removes all empty and whitespace lines from an ArrayList<String>.
	 * @param lines
	 * @return A copy of stringList, with all empty (and whitespace) lines removed.
	 */
	public static ArrayList<String> removeAllEmptyLines(ArrayList<String> lines) {

		return removeAllLinesMatching(lines, "\\s*");
	}
	
	/**
	 * Removes all Strings from 'lines' that match 'match'.
	 * @param lines List of strings to be compared (and possibly removed).
	 * @param match String that will be compared with each line/string.
	 * @return List without the strings that matched 'match'.
	 */
	public static ArrayList<String> removeAllLinesMatching(ArrayList<String> lines, String match){
	    
	    ArrayList<String> emptyLines = new ArrayList<String>();
        
        for (String str : lines) {
            if (Pattern.matches(match, str))
                emptyLines.add(str);
        }
        lines.removeAll(emptyLines);
        
        return lines;
	}
	
	/**
	 * Tokenizes each of the strings in 'lines'. Separator is whitespace.
	 * @param lines
	 * @return
	 */
	public static TokenizedLines tokenizeLines(ArrayList<String> lines, String delimiter) {
		TokenizedLines tokenizedLines = new TokenizedLines();
		
		for (String str : lines) {
			ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(str.split(delimiter)));
			tokenizedLines.add(tokens);
		}
		
		return tokenizedLines;
	}
}
