package old.turing.structs;

import java.io.IOException;
import java.util.HashSet;

import old.automaton.main.AutomatonFile;
import old.automaton.structs.AutomatonData;
import old.automaton.structs.AutomatonTransitionSet;
import old.automaton.structs.State;
import old.automaton.structs.StateSet;
import old.automaton.structs.Symbol;
import old.automaton.structs.SymbolSet;
import old.turing.main.TuringIOConst;
import old.util.TokenizedLines;

/**
 * @author jose
 *
 *  Semantically represents the data 
 *  of an automaton definition.
 */

@SuppressWarnings("serial")
public class TuringData extends AutomatonData implements TuringIOConst {

    HashSet<Symbol> outputAlphabet;
    Symbol blankSymbol;
    
    public TuringData(HashSet<State> stateSet, 
                HashSet<Symbol> inputAlphabet,
                HashSet<Symbol> outputAlphabet,
                State initialState,
                Symbol blankSymbol,
                AutomatonTransitionSet transitionRules,
                HashSet<State> acceptStates){
        super(stateSet, inputAlphabet, initialState, acceptStates, transitionRules);
        
        this.outputAlphabet = outputAlphabet;
        this.blankSymbol = blankSymbol;
    }
    
    public TuringData(String fullFilePath) throws IOException{
        
        TokenizedLines tokLines = AutomatonFile.prepareAutomatonData(fullFilePath);
    
        stateSet = new StateSet(tokLines.get(IN_FILE_STATE_SET));
    
        inputAlphabet = new SymbolSet(tokLines.get(IN_FILE_INPUT_ALPH));
    
        outputAlphabet = new SymbolSet(tokLines.get(IN_FILE_OUTPUT_ALPH));
    
        initialState = new State(tokLines.get(IN_FILE_INIT_STATE).get(0));
    
//        blankSymbol = new Symbol(tokLines.get(IN_FILE_BLANK_SYM).get(0));
        // TODO: shortcircuit, override, hotfix.
        blankSymbol = new Symbol("*");
    
        acceptStates = new StateSet(tokLines.get(IN_FILE_ACCEPT_STATES));
        
        TokenizedLines transitionLines = 
                new TokenizedLines(tokLines.subList(IN_FILE_TRANS_FUNCT, tokLines.size()));
        
        transitionRules = new TuringTransitionSet(transitionLines);
    }

    public TuringData() {}

    /*
     * Getters.
     * No setters since this is considered a static object.
     */
    
    public HashSet<Symbol> getOutputAlphabet() {
        return outputAlphabet;
    }

    public Symbol getBlankSymbol() {
        return blankSymbol;
    };


    
}
