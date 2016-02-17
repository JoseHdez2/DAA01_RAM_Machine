package pushdown.structs;

import java.io.IOException;
import java.util.HashSet;

import automaton.main.AutomatonFile;
import automaton.structs.AutomatonData;
import automaton.structs.State;
import automaton.structs.StateSet;
import automaton.structs.Symbol;
import automaton.structs.SymbolSet;
import pushdown.main.FrameConstPushdown;
import util.TokenizedLines;

/**
 * @author jose
 *
 *  Semantically represents the data 
 *  of an automaton definition.
 *  
 *  Extracted and given semantic meaning 
 *  by the AutomataIO class.
 */
@SuppressWarnings("serial")
public class PushdownData extends AutomatonData implements FrameConstPushdown{

    HashSet<Symbol> stackAlphabet;
    Symbol initialStackSymbol;
    
    public PushdownData(HashSet<State> stateSet, 
                HashSet<Symbol> inputAlphabet,
                HashSet<Symbol> stackAlphabet,
                State initialState,
                Symbol initialStackSymbol,
                PushdownTransitionSet transitionRules,
                HashSet<State> acceptStates){
        super(stateSet, inputAlphabet, initialState, acceptStates, transitionRules);

        this.stackAlphabet = stackAlphabet;
        this.initialStackSymbol = initialStackSymbol;
    }

    public PushdownData() {};
    
    public PushdownData(String fullFilePath) throws IOException {
            
        TokenizedLines tokLines = AutomatonFile.prepareAutomatonData(fullFilePath);

        stateSet = new StateSet(tokLines.get(IN_FILE_STATE_SET));

        inputAlphabet = new SymbolSet(tokLines.get(IN_FILE_INPUT_ALPH));

        stackAlphabet = new SymbolSet(tokLines.get(IN_FILE_STACK_ALPH));

        initialState = new State(tokLines.get(IN_FILE_INIT_STATE).get(0));

        initialStackSymbol = new Symbol(tokLines.get(IN_FILE_INIT_STACK).get(0));

        acceptStates = new StateSet(tokLines.get(IN_FILE_ACCEPT_STATES));
        
        TokenizedLines transitionLines = 
                new TokenizedLines(tokLines.subList(IN_FILE_TRANS_FUNCT, tokLines.size()));
        
        transitionRules = new PushdownTransitionSet(transitionLines);
    }
    
    /*
     * Getters.
     * No setters since this is considered a static object.
     */
    
    
}
