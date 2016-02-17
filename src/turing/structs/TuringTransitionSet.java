package turing.structs;

import java.util.ArrayList;

import automaton.structs.AutomatonTransitionSet;
import automaton.structs.State;
import automaton.structs.Symbol;
import turing.main.TuringIOConst;
import util.TokenizedLines;

/**
 * @author jose
 * 
 *  Workaround.
 *  Inheritance problems: Java doesn't seem to find the relation between
 *  returning a HashSet<AutomatonTransition> and a HashSet<PushdownTransition>.
 *  
 *  So, we do it more explicitly here.
 */
@SuppressWarnings("serial")
public class TuringTransitionSet extends AutomatonTransitionSet implements TuringIOConst{
    
    public TuringTransitionSet(TokenizedLines transitionLines) {
        super(transitionLines);
    }

    public TuringTransitionSet() {};
    
    @Override
    protected AutomatonTransitionSet readFromLines(TokenizedLines transitionLines) {
        TuringTransitionSet readTransitions = new TuringTransitionSet();
        
        for (ArrayList<String> line : transitionLines){
            State prevState = new State(line.get(IN_TRAN_INPUT_STATE));
            State nextState = new State(line.get(IN_TRAN_OUTPUT_STATE));
            Symbol inputChar = new Symbol(line.get(IN_TRAN_INPUT_CHAR));
            Symbol outputChar = new Symbol(line.get(IN_TRAN_INPUT_CHAR));
            Movement movement = new Movement(line.get(IN_TRAN_MOVEMENT).charAt(0));
            
            readTransitions.add(
                    new TuringTransition(prevState, nextState, inputChar, outputChar, movement));
        }
        return readTransitions;
    }

}
