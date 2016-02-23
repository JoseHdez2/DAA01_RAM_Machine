package auto.ram;

import java.util.ArrayList;
import java.util.HashMap;

import auto.base.Automaton;
import auto.ram.Instruction.OpType;

public class RAM_Sim implements Automaton{
    
    ArrayList<Instruction> instructions;
    Integer pc;    // RAM Machine program counter AKA instruction pointer. Points to current instruction.
    Integer acc;   // RAM machine accumulator. Starts at zero.
    ArrayList<Integer> regs;    // RAM machine registers.
    HashMap<String,Integer> gotos; // "Goto"s (AKA tags) in the code.
    
    ArrayList<Integer> tapeIn;  // RAM machine input tape.
    ArrayList<Integer> tapeOut; // RAM machine output tape.
    
    SimState simState;  // Current state of the simulation: initial, ongoing or final.
    
    static String SEP = "#";    // Token separator, for visibly reducing whitespace, etc. TODO: weird phrasing
    
    public RAM_Sim(){
        initialize();
    }

    /*
     * Simulation methods
     */
    
    /**
     * @param opType Type of numeric operand reference.
     * @param opStr String with operand.
     * @return
     */
    public Integer getOperandValue(OpType opType, String opStr){
//        assert(Instruction.isNumericOperand(opStr)); // Is already checked at load time.
        int opVal = Integer.valueOf(opStr);
        if(opType == OpType.NUM_LITERAL) return opVal; 
        if(opType == OpType.NUM_INDIRECT) return getFrom(regs, getFrom(regs, opVal));
        else {
            assert(opStr.matches("\\d+"));
            return getFrom(regs, opVal);
        }
    }
    
    /**
     * Write to infinite list.
     * @param infList Infinite list.
     * @param ind Index.
     * @param val Value.
     */
    private void writeTo(ArrayList<Integer> infList, int ind, int val){
        while(ind > infList.size()-1)
            infList.add(0); // Inflate list with zeroes until we can place the value.
        infList.set(ind, val);
    }
    
    /**
     * Get from infinite list.
     * @param infList Infinite list.
     * @param ind Index.
     */
    private Integer getFrom(ArrayList<Integer> infList, int ind){
        if (ind > infList.size()-1) return 0;
        else return infList.get(ind);
    }
    
    /*
     * Automaton methods.
     */
    
    @Override
    public void loadTransitions(String programFileContent) {
        initialize();
        simState = SimState.INITIAL;
        
        String programFileContent2 = programFileContent; // assert non-destructiveness.
        instructions = InstructionParser.parseInstructions(programFileContent);
        gotos = InstructionParser.parseGotos(programFileContent);
        assert(programFileContent2.equals(programFileContent)); // assert non-destructiveness.
        
        readyForTakeoff(); // simState = INITIAL if nothing else is missing.
    }

    /**
     * Sets simState to INITIAL if everything is OK.
     * This allows to load each part in any order, and only the final load (and call to this function)
     * will set the simState to INITIAL.
     */
    private void readyForTakeoff(){
        if ((pc != null) && (acc != null) && (regs != null) && (gotos != null)
                && (tapeIn != null) && (tapeOut != null))
            simState = SimState.INITIAL;
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
        gotos = new HashMap<String,Integer>();  // Wipe gotos dictionary.
        tapeOut = new ArrayList<Integer>(); // Wipe output tape.
        readyForTakeoff(); // simState = INITIAL if nothing else is missing.
    }

    @Override
    public void step() {
        if (simState == SimState.FINAL){
            System.out.println("Machine has no more moves available.");
            return;
        }
        // findPossibleMoves()
        Instruction inst = instructions.get(pc);
        // move()
        switch(inst.insType){
        case LOAD: acc = getOperandValue(inst.opType, inst.op); pc++; break;
        case STORE: writeTo(regs, getOperandValue(inst.opType, inst.op), acc); pc++; break;
        case READ: writeTo(regs, getOperandValue(inst.opType, inst.op), tapeIn.remove(0)); pc++; break;
        case WRITE: tapeOut.add(getOperandValue(inst.opType, inst.op)); pc++; break;
        case ADD: acc += getOperandValue(inst.opType, inst.op); pc++; break;
        case SUB: acc -= getOperandValue(inst.opType, inst.op); pc++; break;
        case MUL: acc *= getOperandValue(inst.opType, inst.op); pc++; break;
        case DIV: acc /= getOperandValue(inst.opType, inst.op); pc++; break;
        case HALT: simState = SimState.FINAL; break;
        case JUMP: pc = gotos.get(inst.op); break;
        case JZERO: if(acc == 0) pc = gotos.get(inst.op); break;
        case JGTZ: if(acc != 0) pc = gotos.get(inst.op); break;
        }
        if (simState == SimState.INITIAL) simState = SimState.ONGOING;
    }

    @Override
    public String showStatus() {
        String str = "";
        if (simState == SimState.FINAL)
            str += String.format("Machine has no more moves available. %n");
        str += String.format("pc: %s%n", pc);
        str += String.format("acc: %s%n", acc);
        str += String.format("regs: %s%n", regs);
        str += String.format("outputTape: %s", tapeOut);
        return str;
    }

    @Override
    public SimState showSimState() {
        return simState;
    }

    @Override
    public void setInput(String tapeIn) {
        this.tapeIn = new ArrayList<Integer>();
        for(int i = 0; i < tapeIn.length(); i++)
            this.tapeIn.add(Integer.valueOf(tapeIn.charAt(i)));
        
        System.out.println("tape is " + this.tapeIn.toString());
        readyForTakeoff(); // simState = INITIAL if nothing else is missing.
    }
}
