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
    
    static String SEP = "|";    // Token separator, for visibly reducing whitespace, etc. TODO: weird phrasing
    
    public RAM_Auto(){
        initialize();
    }
    
    /*
     * AutomatonImpl methods.
     */
    
    public Instruction.RefType parseOperandType(String opStr){
        assert(opStr.matches("[=*]?\\d"));
        int op = Integer.valueOf(opStr.substring(1));
        if(opStr.charAt(0) == '=') return Instruction.RefType.LITERAL; 
        if(opStr.charAt(0) == '*') return Instruction.RefType.INDIRECT;
        else return Instruction.RefType.DIRECT;
    }
    
    /*
     * Simulation methods
     */
    
    public Integer getOperandValue(String opStr){
        assert(opStr.matches("[=*]?\\d"));
        int op = Integer.valueOf(opStr.substring(1));
        if(opStr.charAt(0) == '=') return op; 
        if(opStr.charAt(0) == '*') return regs.get(regs.get(op));
        else return regs.get(op);
    }
    
    /*
     * Automaton methods.
     */

    Predicate<String> emptyLine(){
        return p -> p.isEmpty();
    }
    
    @Override
    public void loadTransitions(String dataContent) {
        initialize();
        simState = SimState.INITIAL;
        
        
        String[] lines = dataContent.split(System.getProperty("line.separator")); // http://stackoverflow.com/a/1096633/3399416
        
        for (int i = 0; i < lines.length; i++){
            // Remove comments. Only keep what's before the ';' (comment separator).
            lines[i] = lines[i].split(";")[0];
            // Replace whitespace with separators.
            lines[i] = lines[i].trim().replaceAll("\\s+", SEP);
        }
        ArrayList<String> lines2 = new ArrayList<String>(Arrays.asList(lines));

        System.out.println(lines2);
        
        // Only keep the lines with code.
        lines2.removeIf(emptyLine());
        
        System.out.println(lines2);
        System.exit(0);

        // Do actual parsing
        for (int i = 0; i < lines2.size(); i++){
            String[] thisLine = lines2.get(i).split(SEP);
            // Detect tags/"goto"s and add them
            if (thisLine.length > 1 && thisLine[1] == ":"){
                gotos.put(thisLine[0], i);  // Add goto with key= token 0 and value= this line's index.
                thisLine = lines2.get(i).split(":")[1].split(SEP);  // 'Remove' tag/goto.
            }
            assert(thisLine.length <= 2); // Instruction and optional operand
            Instruction.InsType insType = null;
            switch(Instruction.InsType.valueOf(thisLine[0].toUpperCase())){
            case ADD: insType = Instruction.InsType.ADD; break;
            case DIV: insType = Instruction.InsType.DIV; break;
            case HALT:insType = Instruction.InsType.HALT; break;
            case JGTZ:insType = Instruction.InsType.JGTZ; break;
            case JUMP:insType = Instruction.InsType.JUMP; break;
            case JZERO:insType = Instruction.InsType.JZERO; break;
            case LOAD:insType = Instruction.InsType.LOAD; break;
            case MUL:insType = Instruction.InsType.MUL; break;
            case READ:insType = Instruction.InsType.READ; break;
            case STORE:insType = Instruction.InsType.STORE; break;
            case SUB:insType = Instruction.InsType.SUB; break;
            case WRITE:insType = Instruction.InsType.WRITE; break;
            default:
                System.out.println("Unrecognized instruction: Halting load process.");
                break;
            }
//            instructions.add(new Instruction());
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
