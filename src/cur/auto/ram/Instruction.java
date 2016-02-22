package cur.auto.ram;

import java.util.ArrayList;
import java.util.Arrays;

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
    
    // Instructions that take a numeric operand. Simplifies operand validity check.
    // Note: Some of these don't just need the operand to be numeric (e.g. no literals allowed).
    static ArrayList<InsType> INST_TYPE_NUMERIC = 
            new ArrayList<InsType>(Arrays.asList(InsType.LOAD, InsType.STORE, InsType.READ, 
                    InsType.WRITE, InsType.ADD, InsType.SUB, InsType.MUL, InsType.DIV));
    
    // Instructions that take a string (name) operand.
    static ArrayList<InsType> INST_TYPE_NAME = 
        new ArrayList<InsType>(Arrays.asList(InsType.JUMP, InsType.JZERO, InsType.JGTZ));
    
    enum OpType {
        NONE,       // No operand.
        LITERAL,    // Literal operand.
        DIRECT,     // Direct operand.
        INDIRECT,   // Indirect operand.
    }
    
    InsType insType;    // Instruction type. LOAD, STORE, READ...
    OpType opType;  // Operand type.
    String op;  // Instruction operand. Either one or none.
    
    public static boolean isNumericOperand(String p){
        return p.matches("[=*]?\\d+");
    }
    
    public static boolean isNumericLiteralOperand(String p){
        return p.matches("[=*]?\\d");
    }
    
    public static boolean isNumericDirectOperand(String p){
        return p.matches("[=*]?\\d+");
    }
    
    public static boolean isNumericIndirectOperand(String p){
        return p.matches("[=*]?\\d+");
    }
    
    public static boolean isNameOperand(String p){
        return p.matches("[A-z_]+");
    }
    
    /**
     * Check validity of operand, relative to the instruction type.
     */
    private void operandValidityCheck(){
        String insName = insType.toString();
        
        // Check that operand type agrees with instruction type.
        if(isNumericOperand(op)){
            if (!INST_TYPE_NUMERIC.contains(insType))
                System.err.println(insName + ": Invalid numeric operand.");
        } else if(isNameOperand(op)){
            if (!INST_TYPE_NAME.contains(insType))
                System.err.println(insName + ": Invalid name operand.");
        } else {
            System.err.println(insName + ": Invalid, unknown type operand.");
        }
        
        if (insType == InsType.HALT && opType != OpType.NONE)
            System.err.println(insName + " does not take operands (error).");
        switch(opType){
        case DIRECT:
            break;
        case INDIRECT:
            break;
        case LITERAL:
            break;
        case NONE:
            break;
        }
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
