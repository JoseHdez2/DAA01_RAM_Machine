package automaton.structs;

import java.util.ArrayList;

import automaton.main.AutomatonIOConst;
import util.StringArray;

/**
 * @author jose
 * 
 *	Represents a transition rule, 
 *	which the automata use to move between states.
 */
public class AutomatonTransition implements AutomatonIOConst {
    protected State prevState;
    protected State nextState;
    protected Symbol inputCharacter;
    
    public AutomatonTransition(State prevState, State nextState, Symbol inputCharacter){
        this.prevState = prevState;
        this.nextState = nextState;
        this.inputCharacter = inputCharacter;
    }
    
    public ArrayList<String> asStringArray(){
        StringArray strArr = (StringArray) StringArray.dummyTokensLine(OUT_TRAN_TOK_NUM);
        strArr.set(OUT_TRAN_INPUT_STATE, prevState.toString());
        strArr.set(OUT_TRAN_INPUT_CHAR, inputCharacter.toString());
        strArr.set(OUT_TRAN_OUTPUT_STATE, nextState.toString());
        return new StringArray();
    }
    
    /*
     * Equals and hashCode.
     */
    
    public boolean equals(Object ob){
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        AutomatonTransition other = (AutomatonTransition)ob;
        if (!prevState.equals(other.prevState)) return false;
        if (!nextState.equals(other.nextState)) return false;
        if (!inputCharacter.equals(other.inputCharacter)) return false;

        return true;
    }
    
    public int hashCode() {
        return prevState.hashCode() ^ nextState.hashCode() ^ 
                inputCharacter.hashCode();
    }

    /*
     * Getters.
     * No setters since this is considered a static object.
     */
    
    public State getPrevState() {
        return prevState;
    }

    public State getNextState() {
        return nextState;
    }

    public Symbol getInputCharacter() {
        return inputCharacter;
    }
}
