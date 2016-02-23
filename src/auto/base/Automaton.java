package auto.base;

/**
 * Automaton interface.
 * 
 * @author jose
 *
 */
/**
 * @author jose
 *
 */
public interface Automaton {
    
    // TODO: no guarantee of possible moves?
    enum SimState {
        INITIAL,    // Automaton at initial state (no guarantee of possible moves).
        ONGOING,    // Not at initial state, but more moves possible.
        FINAL   // No more possible moves. Also denotes invalid state (e.g. no instructions loaded).
    }
    
    /**
     * Given a text, read the transitions from it and save them into this automaton's internal memory.
     * {@link Automaton#initialize()} is also called to reset the simulation.
     * @param dataContent   Text with transitions/instructions (usually one per line).
     */
    public void loadTransitions(String dataContent);
    
    /**
     * Initialize the state of the automaton.
     */
    public void initialize();
    
    /**
     * Set or reset the initial input of the automaton.
     */
    public void setInput(String input);
    
    /**
     * Take a step in the simulation.
     * That is, check possible moves and move.
     */
    public void step();
    
    /**
     * Show current status of all data structures, etc. of this automaton.
     * @return Current automaton status, as a specific String.
     */
    public String showStatus();
    
    /**
     * Use to check if a {@link Automaton#step()} is possible.
     * Use to check if {@link Automaton#initialize()} would be trivial.
     * @return Current state of the simulation: initial, ongoing or final.
     */
    public SimState showSimState();
}
