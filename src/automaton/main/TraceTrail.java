package automaton.main;

import java.util.ArrayList;

import automaton.structs.AutomatonStatus;
import util.TokenizedLines;

/**
 * @author jose
 * 
 *  Temporal data structure used during a trace.
 *  
 *  Keeps the history of a given "trace trail" 
 *  (a possible path in a non-deterministic trace).
 *  
 *  Used for presenting the trace of a successful possibility.
 */
@SuppressWarnings("serial")
public class TraceTrail extends ArrayList<AutomatonStatus>{
    public AutomatonStatus getLast(){
        return this.get(this.size()-1);
    }
    
    /**
     * Produce the representation of a trace trail, according to the internal IO convention.
     * @return  Array of string arrays representing this trace trail.
     */
    public TokenizedLines asTokenizedLines(){
        
        TokenizedLines tokenizedLines = new TokenizedLines();
        
        for (AutomatonStatus as : this){
            tokenizedLines.add(as.asStringArray());
        }
        
        return tokenizedLines;
    }
    
    // TODO find out how deep
    /**
     * Deep "enough" since I'm not completely sure whether it's deep.
     * @return Deep copy
     */
    protected TraceTrail deepEnoughCopy(){
        
        TraceTrail copyTrail = new TraceTrail();
        copyTrail.addAll(this);
        
        return copyTrail;
    }
}
