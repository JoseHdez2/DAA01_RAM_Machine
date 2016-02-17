package turing.structs;

import java.util.ArrayList;

import automaton.structs.AutomatonTransition;
import automaton.structs.State;
import automaton.structs.Symbol;
import turing.main.TuringIOConst;
import util.StringArray;

/**
 * @author jose
 * 
 *	Represents a transition rule, 
 *	which the automata use to move between states.
 */
public class TuringTransition extends AutomatonTransition implements TuringIOConst {

	Symbol outputCharacter;
	Movement movement;
	
	public TuringTransition(State prevState, State nextState,
			Symbol inputCharacter,
			Symbol outputCharacter,
			Movement movement){
	    super(prevState, nextState, inputCharacter);
		this.outputCharacter = outputCharacter;
		this.movement = movement;
	}
	
	public ArrayList<String> asStringArray(){
	    ArrayList<String> strArr = StringArray.dummyTokensLine(TuringIOConst.OUT_TRAN_TOK_NUM);
        strArr.set(TuringIOConst.OUT_TRAN_INPUT_STATE, prevState.toString());
        strArr.set(TuringIOConst.OUT_TRAN_INPUT_CHAR, inputCharacter.toString());
        strArr.set(TuringIOConst.OUT_TRAN_OUTPUT_STATE, nextState.toString());
        strArr.set(TuringIOConst.OUT_TRAN_OUTPUT_CHAR, outputCharacter.toString());
        strArr.set(TuringIOConst.OUT_TRAN_MOVEMENT, movement.toString());
	    return strArr;
	}
	
	public String toString(){
	    return String.format("{%s,%s,%s,%s,%s}", prevState, inputCharacter, nextState, outputCharacter, movement);
	}
	
	/*
	 * Equals and hashCode.
	 */
	
	public boolean equals(Object ob){
	    if (!super.equals(ob)) return false;
	    
	    TuringTransition other = (TuringTransition)ob;
		if (!outputCharacter.equals(other.outputCharacter)) return false;
		if (!movement.equals(other.movement)) return false;

		return true;
	}
	
	public int hashCode() {
		return super.hashCode() ^ outputCharacter.hashCode() ^
				movement.hashCode();
	}

    /*
     * Getters.
     * No setters since this is considered a static object.
     */

    public Symbol getOutputCharacter() {
        return outputCharacter;
    }

    public Movement getMovement() {
        return movement;
    }


}
