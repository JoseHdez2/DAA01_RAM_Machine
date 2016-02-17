package turing.structs;

import java.util.ArrayList;

import automaton.structs.AutomatonStatus;
import automaton.structs.State;

/**
 * @author jose
 *  Temporal data structure used during a trace.
 *  
 *	Represents the status of a given Turing machine
 *  as a frozen frame during the evaluation of a string.
 */
public class TuringStatus extends AutomatonStatus{
	State currentState;
	Tape tape;
	
	public TuringStatus(State currentState, Tape tape){
		this.currentState = currentState;
		this.tape = tape;
	}

    @Override
    public ArrayList<String> asStringArray() {
        
        ArrayList<String> strArr = new ArrayList<String>();
        
        // TODO Auto-generated method stub
        return strArr;
    }
	
	/*
	 * Equals and hashCode.
	 */
	
	public boolean equals(Object ob){
		if (ob == null) return false;
		if (ob.getClass() != getClass()) return false;
		TuringStatus other = (TuringStatus)ob;
		if (!currentState.equals(other.currentState)) return false;
		if (!tape.equals(other.tape)) return false;

		return true;
	}
	
	public int hashCode() {
		return 	currentState.hashCode() ^
				tape.hashCode();
	}
	
	public String toString(){
//	    return "[" + currentState.toString() + "," + tape.toString()  + "]";
	    return String.format("{%s,%s}", currentState, tape);
	}
	
    /*
     *  Getters.
     *  Setters do not exist, as objects of this class 
     *  are meant to be static representations.
     */

    public State getCurrentState() {
        return currentState;
    }

    public Tape getTape() {
        return tape;
    }
    
}
