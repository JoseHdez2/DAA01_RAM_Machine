package cur.auto.ram;

import java.util.ArrayList;

public class Instruction {
    // Instruction type.
    enum InsType {
        LOAD,   // Put value from specified register (or literal) into accumulator.
        STORE,  // Store value from accumulator into specified register.
        READ,   // Read from tape and write into 'op' registry.
        WRITE,  // Write 'op' into output registry.
        ADD,
        SUB,
        MUL,
        DIV,
        HALT,
        JUMP,
        JZERO,
        JGTZ,
    }
    
    InsType insType;    // Instruction type. LOAD, STORE, READ...
//    ArrayList<Integer> op;  // Instruction operands. Up to three allowed.
    String op;  // Instruction operand. Either one or none.
    
    /**
     * Constructor for instructions with no operand.
     * @param insType
     */
    public Instruction(InsType insType) {
        this.insType = insType;
        if(insType != InsType.HALT)
            System.err.println("Error: Instruction needs an operand.");
    }
    
    /**
     * Constructor for instructions with a single operand.
     * @param insType
     * @param op
     */
    public Instruction(InsType insType, String op) {
        this.insType = insType;
        // Check validity of operand
        
        this.op = op;
    }
}
