package pushdown.structs;

import java.util.ArrayList;

import automaton.structs.AutomatonTransitionSet;
import automaton.structs.State;
import automaton.structs.Symbol;
import pushdown.main.FrameConstPushdown;
import util.TokenizedLines;

/**
 * @author jose
 * 
 *	Workaround.
 *  Inheritance problems: Java doesn't seem to find the relation between
 *  returning a HashSet<AutomatonTransition> and a HashSet<PushdownTransition>.
 *  
 *  So, we do it more explicitly here.
 */
public class PushdownTransitionSet extends AutomatonTransitionSet implements FrameConstPushdown {

    public PushdownTransitionSet(TokenizedLines transitionLines) {
        super(transitionLines);
    }

    public PushdownTransitionSet() {};

    @Override
    protected AutomatonTransitionSet readFromLines(TokenizedLines transitionLines) {
        PushdownTransitionSet readTransitions = new PushdownTransitionSet();
        
        for (ArrayList<String> line : transitionLines){
            State prevState = new State(line.get(IN_TRAN_PREV_STATE));
            State nextState = new State(line.get(IN_TRAN_NEXT_STATE));
            Symbol inputChar = new Symbol(line.get(IN_TRAN_REQ_INP_CHAR));
            Symbol inputStackSym = new Symbol(line.get(IN_TRAN_REQ_STACK_SYM));
            ArrayList<Symbol> stackSymToPush = new SymbolList(line.get(IN_TRAN_STACK_SYM_TO_PUSH),",");
            
            readTransitions.add(
                    new PushdownTransition(prevState, nextState, inputChar, inputStackSym, stackSymToPush));
        }
        return readTransitions;
    }
}
