package cur.auto.ram;

import java.util.ArrayList;
import java.util.HashMap;

import cur.auto.base.Automaton;

public class RAM_Auto implements Automaton{
    
    ArrayList<Instruction> instructions;
    Integer pc;    // RAM Machine program counter AKA instruction pointer. Points to current instruction.
    Integer acc;   // RAM machine accumulator. Starts at zero.
    ArrayList<Integer> regs;    // RAM machine registers.
    HashMap<String,Integer> gotos; // "Goto"s (AKA tags) in the code.
    
    ArrayList<Integer> tapeIn;  // RAM machine input tape.
    ArrayList<Integer> tapeOut; // RAM machine output tape.
    
    SimState simState;  // Current state of the simulation: initial, ongoing or final.
    
    static String SEP = "#";    // Token separator, for visibly reducing whitespace, etc. TODO: weird phrasing
    
    public RAM_Auto(){
        initialize();
    }
    
    /*
     * AutomatonImpl methods.
     */

    /*
     * Simulation methods
     */
    
    public Integer getOperandValue(String opStr){
//        assert(Instruction.isNumericOperand(opStr)); // Is already checked at load time.
        int op = Integer.valueOf(opStr.substring(1));
        if(opStr.charAt(0) == '=') return op; 
        if(opStr.charAt(0) == '*') return regs.get(regs.get(op));
        else {
            assert(opStr.matches("\\d+"));
            return regs.get(op);
        }
    }
    
    /*
     * Automaton methods.
     */

    
    @Override
    public void loadTransitions(String programFileContent) {
        initialize();
        simState = SimState.INITIAL;
        
        String programFileContent2 = programFileContent;
        instructions = InstructionParser.parseInstructions(programFileContent);
        gotos = InstructionParser.parseGotos(programFileContent);
        assert(programFileContent.equals(programFileContent2));
    }

    @Override
    public void initialize() {
        if (instructions == null){
            instructions = new ArrayList<Instruction>();
            simState = SimState.FINAL;  // There are no valid instructions.
        }
        // We don't wipe the transitions, to allow for these two use cases:
        // Use case 1: call initialize() directly to retry simulation. Keep same transitions.
        // Use case 2: call loadTransitions(), which calls this AND loads new transitions.
        pc = 0; // Point program counter to instruction zero.
        acc = 0; // Set accumulator to zero.
        regs = new ArrayList<Integer>(); // Wipe RAM registers.
        gotos = new HashMap<String,Integer>();
    }

    @Override
    public void step() {
        // findPossibleMoves()
        Instruction inst = instructions.get(pc);
        // move()
        
    }

    @Override
    public String showStatus() {
        String str = "";
        str += String.format("pc: %s%n", pc);
        str += String.format("acc: %s%n", acc);
        str += String.format("outputTape: %s", tapeOut);
        return str;
    }

    @Override
    public SimState showSimState() {
        return simState;
    }
}
