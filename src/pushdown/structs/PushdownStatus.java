package pushdown.structs;

import java.util.Stack;

import automaton.structs.State;
import automaton.structs.Symbol;

/**
 * @author jose
 *  Temporal data structure used during a trace.
 *  
 *	Represents the status of a given automaton
 *  as a frozen frame during the evaluation of a string.
 */
public class PushdownStatus {
	State currentState;
	String remainingInputString;
	Stack<Symbol> currentStack;
	
	public PushdownStatus(State currentState,
			String remainingInputString, 
			Stack<Symbol> currentStack){
		this.currentState = currentState;
		this.remainingInputString = remainingInputString;
		this.currentStack = currentStack;
	}

	/*
	 * Equals and hashCode.
	 */
	
	public boolean equals(Object ob){
		if (ob == null) return false;
		if (ob.getClass() != getClass()) return false;
		PushdownStatus other = (PushdownStatus)ob;
		if (!currentState.equals(other.currentState)) return false;
		if (!remainingInputString.equals(other.remainingInputString)) return false;
		if (!currentStack.equals(other.currentStack)) return false;

		return true;
	}
	
	public int hashCode() {
		return 	currentState.hashCode() ^
				remainingInputString.hashCode() ^ 
				currentStack.hashCode();
	}
	
	/*
	 *  Getters.
	 *  Setters do not exist, as objects of this class 
	 *  are meant to be static representations.
	 */

	public State getCurrentState() {
		return currentState;
	}

	public String getRemainingInputString() {
		return remainingInputString;
	}

	public Stack<Symbol> getCurrentStack() {
		return currentStack;
	}
}
