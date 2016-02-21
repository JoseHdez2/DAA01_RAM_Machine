package cur.auto.ram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.*;

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
    
    static String SEP = "|";    // Separator, for visibly reducing whitespace etc.
    
    public RAM_Auto(){
        initialize();
    }
    
    /*
     * AutomatonImpl methods.
     */
    
    /*
     * Automaton methods.
     */

    Predicate<String> notCode(){
        return p -> p.length() == 1;
    }
    
    @Override
    public void loadTransitions(String dataContent) {
        initialize();
        simState = SimState.INITIAL;
        
        
        String[] lines = dataContent.split(System.getProperty("line.separator")); // http://stackoverflow.com/a/1096633/3399416
        
        ArrayList<String> lines2 = new ArrayList<String>(Arrays.asList(lines));

        System.out.println(lines2);
        
        for (int i = 0; i < lines.length; i++){
            // Remove comments.
            String[] toks = lines[i].split(";");
            assert(toks.length != 0);
            lines[i] = toks[0]; // Only save what's before the separator.
            // Replace whitespace with separators.
            lines[i] = lines[i].trim().replaceAll("\\s+", "|");
        }
        lines2 = new ArrayList<String>(Arrays.asList(lines));

        System.out.println(lines2);
        // Only keep the lines with code.
        lines2.removeIf(notCode());
        System.out.println(lines2);
        System.exit(0);
        for (int i = 0; i < lines.length; i++){
            
        }
        // Do actual parsing
        for (int i = 0; i < lines.length; i++){
            
        }
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
