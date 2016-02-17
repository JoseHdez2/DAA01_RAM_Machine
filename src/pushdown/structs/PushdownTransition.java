package pushdown.structs;

import java.util.ArrayList;

import automaton.structs.AutomatonTransition;
import automaton.structs.State;
import automaton.structs.Symbol;

/**
 * @author jose
 * 
 *	Represents a transition rule, 
 *	which the automata use to move between states.
 */
public class PushdownTransition extends AutomatonTransition {
    
	Symbol requiredStackSymbol;	// Must be in top of stack.
	ArrayList<Symbol> stackSymbolsToPush;
	
	public PushdownTransition(State prevState, State nextState,
			Symbol inputCharacter,
			Symbol inputStackSymbol,
			ArrayList<Symbol> stackSymbolsToPush){
	    super(prevState, nextState, inputCharacter);
		this.requiredStackSymbol = inputStackSymbol;
		this.stackSymbolsToPush = stackSymbolsToPush;
	}
	
	/*
	 * Equals and hashCode.
	 */
	
	public boolean equals(Object ob){
		if (!super.equals(ob)) return false;
		
		PushdownTransition other = (PushdownTransition)ob;
		if (!requiredStackSymbol.equals(other.requiredStackSymbol)) return false;
		if (!stackSymbolsToPush.equals(other.stackSymbolsToPush)) return false;

		return true;
	}
	
	public int hashCode() {
		return super.hashCode() ^ requiredStackSymbol.hashCode() ^
				stackSymbolsToPush.hashCode();
	}
	
    /*
     * Getters.
     * No setters since this is considered a static object.
     */

	public Symbol getRequiredStackSymbol() {
		return requiredStackSymbol;
	}
	
	public ArrayList<Symbol> getStackSymbolsToPush() {
		return stackSymbolsToPush;
	}
}
