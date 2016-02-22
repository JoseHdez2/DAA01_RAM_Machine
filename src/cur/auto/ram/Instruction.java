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
    
    // Static, semantic classifications of instructions. Simplifies operand validity check.
    
    // TODO: would a HashMap<insType, opTypesItTakes> be better? Probably not.
    
    // Much needed syntactic sugar. Not sure this static hash thing was a good idea.
    private static <T> ArrayList<T> newArrayList(Object... objects){
        ArrayList<T> al = new ArrayList<T>();
        for (int i = 0; i < objects.length; i++) al.add((T)objects[i]);
        return al;
    }
    
    private static HashMap<InsType, ArrayList<OpType>> validOperands;
    static
    {
        validOperands = new HashMap<InsType, ArrayList<OpType>>();
        validOperands.put(InsType.LOAD, newArrayList(OpType.NUM_LITERAL, OpType.NUM_DIRECT, OpType.NUM_INDIRECT));
        validOperands.put(InsType.HALT, new ArrayList<OpType>(Arrays.asList(OpType.NONE)));
    }
            
    
    // Instructions that can take no operands.
    static ArrayList<InsType> INST_TAKES_NONE = 
            new ArrayList<InsType>(Arrays.asList(InsType.HALT));
    
    // Instructions that can take a direct or indirect (numeric) operand.
    static ArrayList<InsType> INST_TAKES_NUM_DIR_IND = 
            new ArrayList<InsType>(Arrays.asList(InsType.LOAD, InsType.STORE, InsType.READ, 
                    InsType.WRITE, InsType.ADD, InsType.SUB, InsType.MUL, InsType.DIV));
    
    // Instructions that can take a numeric literal operand.
    static ArrayList<InsType> INST_TAKES_NUM_LIT = 
            new ArrayList<InsType>(Arrays.asList(InsType.LOAD,
                    InsType.WRITE, InsType.ADD, InsType.SUB, InsType.MUL, InsType.DIV));
    
    // Instructions that can take a string (name) operand.
    static ArrayList<InsType> INST_TAKES_NAME = 
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
     * Given a string that represents an operand, return the operand type.
     * @param str String that represents an operand.
     * @return OpType, null if no matching type was found.
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
    private boolean operandValidityCheck(){
        String insName = insType.toString();
        
        // We assume Instruction#operandType(op) == opType. InsParser did its job.
        switch(opType){
        case NAME:
            if (!INST_TAKES_NAME.contains(insType)){
            System.err.println(insName + ": invalid name operand."); return false; }
            else return true;
        case NONE:
            if (!INST_TAKES_NONE.contains(insType)){
            System.err.println(insName + ": needs an operand."); return false; }
            else return true;
        case NUM_DIRECT:
            if (!INST_TAKES_NUM_DIR_IND.contains(insType)){ 
            System.err.println(insName + ": needs an operand."); return false; }
            else return true;
        case NUM_INDIRECT:
            if (!INST_TAKES_NUM_DIR_IND.contains(insType)){
            System.err.println(insName + ": needs an operand."); return false; }
            else return true;
        case NUM_LITERAL:
            if (!INST_TAKES_NONE.contains(insType)){
            System.err.println(insName + ": needs an operand."); return false; }
            else return true;
        }
        System.err.println("Null opType or something? error.");
        return false;
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
