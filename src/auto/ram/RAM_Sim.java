package auto.ram;

import java.util.ArrayList;
import java.util.HashMap;

import auto.base.Automaton;
import auto.ram.Instruction.InsType;
import auto.ram.Instruction.OpType;

public class RAM_Sim implements Automaton{
    
    ArrayList<Instruction> instructions;
    Integer pc;    // RAM Machine program counter AKA instruction pointer. Points to current instruction.
    Integer acc;   // RAM machine accumulator. Starts at zero.
    HashMap<Integer,Integer> regs;    // RAM machine registers.
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
     * Given an instruction, deduce the value of its operand,
     * using the opType and opVal, and occasionally the instType as well.
     * @return
     */
    public Integer getOperandValue(Instruction inst){
//        assert(Instruction.isNumericOperand(opStr)); // Is already checked at load time.
        InsType insType = inst.insType;
        OpType opType = inst.opType;
        System.out.println(String.format("op=%s", inst.op));
        int opVal = Integer.valueOf(inst.op);
        System.out.println(String.format("For inst=%s", inst));
        
        // TODO: hotfix
        // When STORE and READ say NUM_DIRECT, they really mean NUM_LITERAL...
        if (insType == InsType.STORE || insType == InsType.READ) {
            if (opType == OpType.NUM_DIRECT) opType = OpType.NUM_LITERAL;
            System.out.println("When STORE and READ say NUM_DIRECT, they really mean NUM_LITERAL...");
            System.out.println(String.format("New inst=%s", inst));
        }
                
        Integer opVal2 = null;
        switch (opType){
        case NUM_LITERAL:  opVal2 = opVal; break;
        case NUM_DIRECT: opVal2 = getFrom(regs, opVal); break;
        case NUM_INDIRECT: opVal2 = getFrom(regs, getFrom(regs, opVal)); break;
        default: assert(inst.op.matches("\\d+")); System.out.println("UNKNOWN REFERENCE TYPE??"); break;
        }
        
        System.out.println(String.format("opVal2=%s", opVal2));
        return opVal2;
    }
    
    /**
     * Get from infinite list.
     * @param infList Infinite list.
     * @param ind Index.
     */
    private Integer getFrom(HashMap<Integer, Integer> infList, int key){
        int val = infList.containsKey(key) ? infList.get(key) : 0;
        return val;
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
        regs = new HashMap<Integer, Integer>(); // Wipe RAM registers.
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
        case LOAD: acc = getOperandValue(inst); pc++; break;
        case STORE: regs.put(getOperandValue(inst), acc); pc++; break;
        case READ: regs.put(getOperandValue(inst), tapeIn.remove(0)); pc++; break;
        case WRITE: tapeOut.add(getOperandValue(inst)); pc++; break;
        case ADD: acc += getOperandValue(inst); pc++; break;
        case SUB: acc -= getOperandValue(inst); pc++; break;
        case MUL: acc *= getOperandValue(inst); pc++; break;
        case DIV: acc /= getOperandValue(inst); pc++; break;
        case HALT: simState = SimState.FINAL; break;
        case JUMP: pc = gotos.get(inst.op); break;
        case JZERO: if(acc == 0) pc = gotos.get(inst.op); else pc++; break;
        case JGTZ: if(acc != 0) pc = gotos.get(inst.op); else pc++; break;
        }
        if (simState == SimState.INITIAL) simState = SimState.ONGOING;
    }

    @Override
    public String showStatus() {
        String str = "";
        if (simState == SimState.FINAL)
            str += String.format("Machine has no more moves available. %n");
        str += String.format("inputTape: %s%n", tapeIn);
        str += String.format("pc: %s ", pc);
        if (simState != SimState.FINAL)
            str += String.format("(Next: %s)", instructions.get(pc));
        str += String.format("%n");
        str += String.format("acc: %s%n", acc);
        str += String.format("regs: %s%n", regs);
        str += String.format("outputTape: %s%n", tapeOut);
        return str;
    }

    @Override
    public SimState showSimState() {
        return simState;
    }
    
    public String toString(){
        String str = "";
        str += String.format("Machine Definition:%n");
        str += String.format("instructions: (...)%n");
        str += String.format("gotos: %s%n", gotos);
        return str;
    }

    @Override
    public void setInput(String tapeIn) {
        this.tapeIn = new ArrayList<Integer>();
        String[] tapeIn2 = tapeIn.replaceAll("\\s+", "").split(",");
        for(int i = 0; i < tapeIn2.length; i++)
            this.tapeIn.add(Integer.valueOf(tapeIn2[i]));
        
        System.out.println("tape is " + this.tapeIn.toString());
        readyForTakeoff(); // simState = INITIAL if nothing else is missing.
    }
}
