package cur.auto.ram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Instruction {
    // Instruction type.
    enum InsType {
        LOAD,   // Put value from specified register (or literal) into accumulator.
        STORE,  // Store value from accumulator into specified register.
        READ,   // Read from input tape and write into specified register.
        WRITE,  // Write to output tape from specified register.
        ADD,    // Add value into accumulator.
        SUB,    // Subtract value from accumulator.
        MUL,    // Multiply accumulator by referenced (or literal) value.
        DIV,    // Divide accumulator by referenced (or literal) value.
        HALT,   // Stop program execution.
        JUMP,   // Jump unconditionally to line specified by tag.
        JZERO,  // Jump to line specified by tag if referenced value is zero.
        JGTZ,   // Jump to line specified by tag if referenced value is greater than zero.
    }
    
    // HashMap with key= the instruction type and values= the operand types it takes.
    private static HashMap<InsType, ArrayList<OpType>> validOperands;
    static
    {
        validOperands = new HashMap<InsType, ArrayList<OpType>>();
        validOperands.put(InsType.LOAD, 
                new ArrayList<OpType>(Arrays.asList(OpType.NUM_LITERAL, OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.STORE, 
                new ArrayList<OpType>(Arrays.asList(                    OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.READ, 
                new ArrayList<OpType>(Arrays.asList(                    OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.WRITE, 
                new ArrayList<OpType>(Arrays.asList(OpType.NUM_LITERAL, OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.ADD, 
                new ArrayList<OpType>(Arrays.asList(OpType.NUM_LITERAL, OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.SUB, 
                new ArrayList<OpType>(Arrays.asList(OpType.NUM_LITERAL, OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.MUL, 
                new ArrayList<OpType>(Arrays.asList(OpType.NUM_LITERAL, OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.DIV, 
                new ArrayList<OpType>(Arrays.asList(OpType.NUM_LITERAL, OpType.NUM_DIRECT, OpType.NUM_INDIRECT)));
        validOperands.put(InsType.JUMP, 
                new ArrayList<OpType>(Arrays.asList(OpType.NAME)));
        validOperands.put(InsType.JZERO, 
                new ArrayList<OpType>(Arrays.asList(OpType.NAME)));
        validOperands.put(InsType.JGTZ, 
                new ArrayList<OpType>(Arrays.asList(OpType.NAME)));
        validOperands.put(InsType.HALT, 
                new ArrayList<OpType>(Arrays.asList(OpType.NONE)));
    }
    
    enum OpType {
        NONE,           // No operand.
        NUM_LITERAL,    // Numeric, literal operand.
        NUM_DIRECT,     // Numeric, direct operand.
        NUM_INDIRECT,   // Numeric, indirect operand.
        NAME,           // Name (String) operand.
    }
    
    InsType insType;    // Instruction type. LOAD, STORE, READ...
    OpType opType;  // Operand type.
    String op;  // Instruction operand. Either one or none.
    
    /**
     * Check validity of operand, relative to the type of this instruction.
     * ("Is my operand compatible with me?")
     */
    private boolean operandValidityCheck(){
        assert(opType != null);
        
        // "If the operand type is not within the valid types for this instruction type..."
        if (!validOperands.get(insType).contains(opType)){
            System.err.println(String.format("%s: invalid %s operand.", insType, opType));
            return false;
        } else return true;
    }
    
    /**
     * Constructor for instructions with no operand.
     * @param insType
     */
    public Instruction(InsType insType) {
        this.insType = insType;
        this.opType = OpType.NONE;
        operandValidityCheck();
    }
    
    /**
     * Constructor for instructions with a single operand.
     * @param insType
     * @param op
     */
    public Instruction(InsType insType, OpType opType, String op) {
        this.insType = insType;
        this.opType = opType;
        this.op = op;
        operandValidityCheck();
    }
}
