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
    
    // Static, semantic classifications of instructions. Simplifies operand validity check.
    
    // Instructions that can take no operands.
    static ArrayList<InsType> INST_TAKES_NONE = 
            new ArrayList<InsType>(Arrays.asList(InsType.HALT));
    
    // Instructions that can take a numeric operand (may not take all types: see below).
    static ArrayList<InsType> INST_TYPE_NUM = 
            new ArrayList<InsType>(Arrays.asList(InsType.LOAD, InsType.STORE, InsType.READ, 
                    InsType.WRITE, InsType.ADD, InsType.SUB, InsType.MUL, InsType.DIV));
    
    // Instructions that DO take a numeric operand, as long as it's NOT literal.
    static ArrayList<InsType> INST_TYPE_NOT_LITERAL = 
            new ArrayList<InsType>(Arrays.asList(InsType.LOAD, InsType.STORE, InsType.READ, 
                    InsType.WRITE, InsType.ADD, InsType.SUB, InsType.MUL, InsType.DIV));
    
    // Instructions that take a string (name) operand.
    static ArrayList<InsType> INST_TYPE_NAME = 
        new ArrayList<InsType>(Arrays.asList(InsType.JUMP, InsType.JZERO, InsType.JGTZ));
    
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
     * Given a string that represents an operand, deduce the operand type.
     * @param str
     * @return
     */
    public static OpType operandType(String str){
//        if (str.matches("[=*]?\\d+"));
        if (str.matches("\\d+")) return OpType.NUM_DIRECT;
        if (str.matches("=\\d+")) return OpType.NUM_LITERAL;
        if (str.matches("*\\d+")) return OpType.NUM_INDIRECT;
        if (str.matches("[A-z_]+")) return OpType.NAME;
        if (str.matches("") || str == null) return OpType.NONE;
        else {
            System.err.println(str + ": Unknown operand type.");
            return null;    // return NONE?
        }
    }
    
    /**
     * Check validity of operand, relative to the type of this instruction.
     * ("Is my operand compatible with me?")
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
