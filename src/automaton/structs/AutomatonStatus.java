package automaton.structs;

import java.util.ArrayList;

/**
 * @author jose
 *  Temporal data structure used during a trace.
 *  
 *	Represents the status of a given machine
 *  as a frozen frame during the evaluation of a string.
 */
public abstract class AutomatonStatus {
    
    /*
     * Output function.
     */
    
    public abstract ArrayList<String> asStringArray();
    
	/*
	 * Equals and hashCode.
	 */
	
	public abstract boolean equals(Object ob);
	
	public abstract int hashCode();
    
}
