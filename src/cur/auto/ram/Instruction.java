package cur.auto.ram;

import java.util.ArrayList;

public class Instruction {
    // Instruction type.
    enum InsType {
        LOAD,   
        STORE,  // Write ''
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
    
    // Reference type.
    enum RefType {
        LITERAL,    // The operand is the actual value.
        DIRECT,     // Operand is register index which holds value.
        INDIRECT,   // Operand is register index which holds register index which holds value.
    }
    
    InsType insType;    // Instruction type. LOAD, STORE, READ...
    RefType refType;    // Operand reference type. LITERAL, DIRECT, INDIRECT...
//    ArrayList<Integer> op;  // Instruction operands. Up to three allowed.
    String op;  // Instruction operand. Either one or none.
    
    public Instruction(InsType insType, RefType refType, String op) {
        this.insType = insType;
        this.refType = refType;
        this.op = op;
    }
}
