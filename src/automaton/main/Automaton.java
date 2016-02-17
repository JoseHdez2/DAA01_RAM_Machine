package automaton.main;

import java.util.ArrayList;

import automaton.structs.AutomatonData;
import automaton.structs.AutomatonStatus;
import automaton.structs.AutomatonTransition;
import automaton.structs.AutomatonTransitionSet;
import util.Debug;
import util.TokenizedLines;

/**
 * @author jose
 *
 *  The automaton itself. The only class that knows how to work with
 *  the MachineData it contains (e.g. apply transitions).
 */
public abstract class Automaton {
    protected String debugStr = "";
    
	protected AutomatonData data;
	protected TokenizedLines traceHist = new TokenizedLines();
	
	public Automaton(AutomatonData data){
	    if (!correctMachineDefinition(data)){
	        System.err.println("Incoherent automaton definition!");
	        // TODO: Figure out what to throw
	    }
		this.data = data;
	}
	
	/**
	 * Evaluates an input string, non-deterministically. The main purpose of a logical automaton.
	 * In case of success, the first successful trace is recorded into traceHist, for later retrieval.
	 * @return	Boolean indicating acceptance of input string.
	 */
	public boolean evaluateString(String inputString){
	    
	    Debug debug = new Debug(true);
	    
	    ArrayList<TraceTrail> possibleTrails = new ArrayList<TraceTrail>();
	    ArrayList<TraceTrail> newTrails = new ArrayList<TraceTrail>();
	    
		// Add the initial configuration into the statuses array.
		TraceTrail initialTrail = new TraceTrail();
		initialTrail.add(createInitialStatus(inputString));
		debug.out("Initial status %s", initialTrail.getLast());
		possibleTrails.add(initialTrail);
		
		// Iterate until all possibilities are exhausted.
		while (!possibleTrails.isEmpty()){

		    for (TraceTrail tt : possibleTrails){

		        debug.out("Finding applicable transitions for status %s", tt.getLast());
    			AutomatonTransitionSet applicableTransitions = findApplicableTransitionRules(tt.getLast());
    			debug.out("Applicable transitions found: %s", applicableTransitions);
    			
    			// Apply each of them to create a new status, that will be evaluated next round.
    			for (AutomatonTransition tr : applicableTransitions){
                    TraceTrail newTrail = tt.deepEnoughCopy();
                    
    				AutomatonStatus newStatus = applyTransition(tt.getLast(), tr);
    				debug.out("%s --> %s", tt.getLast(), newStatus);
    				newTrail.add(newStatus);

    				if (acceptanceStatus(newStatus)){
    				    // Record (first) successful trace into history.
    				    // TODO: Think about giving each class their own bit of IO functionality.
    				    traceHist = newTrail.asTokenizedLines();
    				    debug.out("Ended with accept status %s", newStatus);
    				    return true;
    				}

    				newTrails.add(newTrail);
    			}
    		}
    		possibleTrails.clear();
    		possibleTrails.addAll(newTrails);
    		newTrails.clear();
		}
		debug.out("Exhausted all possible trace trails. String rejected.");
		return false;
	}

    /*
     * ABSTRACT METHODS
     * 
     * To be implemented by inheriting classes.
     */
	   
    /**
     * Check the provided machine definition data is correct (self-coherent).
     * @param data Machine definition data.
     * @return Whether the provided machine data is correct.
     */
    protected abstract boolean correctMachineDefinition(AutomatonData data);
	
    // TODO: Write descriptions for these.
    
    protected abstract AutomatonStatus createInitialStatus(String inputString);
    
    protected abstract AutomatonTransitionSet findApplicableTransitionRules(AutomatonStatus ms);
    
    /**
     * Note: Being in an acceptance state does not necessarily mean being in an acceptance status.
     * @param as
     * @return Whether the given state has the machine accept the string.
     */
    protected abstract boolean acceptanceStatus(AutomatonStatus as);
    
    /**
     * Given an automaton status and applicable transition rule,
     * compute and return the resulting automaton status.
     * 
     * Note: The transition rule should have previously been deemed applicable
     * for the given automaton status (no checks are made here).
     * @param as    Machine status.
     * @param tr    Transition rule to be applied to the status.
     * @return  New automaton status.
     */
    public abstract AutomatonStatus applyTransition(AutomatonStatus as, AutomatonTransition tr);
	
	/*
	 * Getters and setters.
	 */
	
    public TokenizedLines getTraceHist() {
        return traceHist;
    }

    public String getDebugStr() {
        return debugStr;
    }
}