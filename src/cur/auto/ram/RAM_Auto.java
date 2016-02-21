package cur.auto.ram;

import java.util.ArrayList;
import java.util.HashMap;

import cur.auto.base.Automaton;

public class RAM_Auto implements Automaton{
    
    ArrayList<Instruction> instructions;
    Integer pc;    // RAM Machine program counter AKA instruction pointer. Points to current instruction.
    Integer acc;   // RAM machine accumulator. Starts at zero.
    ArrayList<Integer> regs;    // RAM machine registers.
    HashMap<String,Integer> gotos; // "Goto"s in the code. TODO: explain better.
    
    ArrayList<Integer> tapeIn;  // RAM machine input tape.
    ArrayList<Integer> tapeOut; // RAM machine output tape.
    
    SimState simState;  // Current state of the simulation: initial, ongoing or final.
    
    public RAM_Auto(){
        initialize();
    }
    
    /*
     * AutomatonImpl methods.
     */
    
    /*
     * Automaton methods.
     */

    @Override
    public void loadTransitions(String dataContent) {
        initialize();
        simState = SimState.INITIAL;
    }

    @Override
    public void initialize() {
        // We could initialize the instructions, but what's the point?
        acc = 0;
        regs = new ArrayList<Integer>();
        simState = SimState.FINAL;
    }

    @Override
    public void step() {
        // TODO Auto-generated method stub
        // findPossibleMoves
        
        // move
        
    }

    @Override
    public String showStatus() {
        String str = "";
        str += String.format("stack: %s", acc);
        str += String.format("inputTape: %s", tapeIn);
        str += String.format("outputTape: %s", tapeOut);
        return str;
    }

    @Override
    public SimState showSimState() {
        return simState;
    }
}
