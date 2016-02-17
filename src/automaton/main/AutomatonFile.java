package automaton.main;

import java.io.IOException;
import java.util.ArrayList;

import util.StringProcessing;
import util.TokenizedLines;

public abstract class AutomatonFile {
    /**
     * Standardizes input file, preparing it for automaton data extraction.
     * @param fileName Path to an automaton file (arbitrary file structure)
     * @return Standardized (expected) lines for reading automaton data.
     * @throws IOException
     */
    public static TokenizedLines prepareAutomatonData(String fileName) throws IOException {
        
        ArrayList<String> lines = StringProcessing.readFileLines(fileName);
        
        lines = StringProcessing.stripComments(lines, "#");
        
        lines = StringProcessing.removeAllEmptyLines(lines);

        TokenizedLines tokenizedLines = StringProcessing.tokenizeLines(lines, "\\s+");
        
        return tokenizedLines;
    }
}
