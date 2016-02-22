package cur.auto.ram;

import java.util.ArrayList;

public class Instruction {
    // Instruction type.
    enum InsType {
        LOAD,   // Put value from specified register (or literal) into accumulator.
        STORE,  // Store value from accumulator into specified register.
        READ,   // Read from input tape and write into specified register.
        WRITE,  // Write to output tape from specified register.
        ADD,
        SUB,
        MUL,
        DIV,
        HALT,
        JUMP,
        JZERO,
        JGTZ,
    }
    
    enum OpType {
        NONE,       // No operand.
        LITERAL,    // Literal operand.
        DIRECT,     // Direct operand.
        INDIRECT,   // Indirect operand.
    }
    
    InsType insType;    // Instruction type. LOAD, STORE, READ...
    OpType opType;  // Operand type.
    String op;  // Instruction operand. Either one or none.

    /**
     * Check validity of operand, relative to the instruction type.
     */
    private void operandValidityCheck(){
        String insName = insType.toString();
        if (insType == InsType.HALT){
            if (opType != OpType.NONE) System.err.println(insName + " does not take operands (error).");
        } else
        if (insType != InsType.HALT){
            if (opType == OpType.NONE) System.err.println(insName + " needs an operand (error).");
            if (insType == InsType.STORE || insType == InsType.READ || insType == InsType.WRITE){
                if (opType == OpType.LITERAL)
                    System.err.println(insName + " does not take a literal operand.");
            }
        }
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
    public Instruction(InsType insType, String op) {
        this.insType = insType;
        this.op = op;
        operandValidityCheck();
    }
}
