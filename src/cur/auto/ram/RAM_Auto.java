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
        assert(Instruction.isNumericOperand(opStr)); // Is already checked at load time.
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
        
        // Only keep the lines with code.
        lines2.removeIf(emptyLine());
        
        System.out.println(lines2);

        // Do actual parsing
        for (int i = 0; i < lines2.size(); i++){
            String dummy = lines2.get(i); // split() is destructive.
            String[] thisLine = dummy.split(SEP);
            System.out.println(lines2.get(i));
            System.out.println(String.format("thisLine[1]: '%s'", thisLine[1]));
            // Detect tags/"goto"s and add them
            if (thisLine.length > 1 && thisLine[1].equals(":")){
                System.out.println("doopy woop!");
                gotos.put(thisLine[0], i);  // Add goto with key= token 0 and value= this line's index.
                dummy = lines2.get(i); // split() is destructive.
                thisLine = dummy.substring(dummy.indexOf(':')+2).split(SEP);
//                thisLine = dummy.split(":")[1].split(SEP);  // 'Remove' tag/goto.
            }
            
            // Parse instruction and optional operand
            System.out.println(String.format("thisLine: '%s'", thisLine));
            assert(thisLine.length <= 2);
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
                System.err.println("Unrecognized instruction: Halting load process.");
                break;
            }
            if (thisLine.length == 2){
                
                // Check for valid operand syntax.
                assert(Instruction.isNameOperand(thisLine[1]) || Instruction.isNumericOperand(thisLine[1]));
                instructions.add(new Instruction(insType, thisLine[1]));
            }
            else instructions.add(new Instruction(insType));
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
