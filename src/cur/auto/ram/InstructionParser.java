package cur.auto.ram;

import cur.auto.ram.Instruction.InsType;
import cur.auto.ram.Instruction.OpType;

/**
 * Instruction knows what an instruction can be.
 * RAM_Auto knows how each instruction behaves.
 * InstructionParser knows what each instruction looks like.
 * 
 * @author jose
 *
 */
public class InstructionParser {
    
    
    /**
     * @param str
     * @return
     */
    private static InsType instructionType(String str){
        
    }
    
    /**
     * Given a string that represents an operand, return the operand type.
     * @param str String that represents an operand.
     * @return OpType, null if no matching type was found.
     */
    private static OpType operandType(String str){
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
     * Main purpose of this class.
     * Parse, create and return the instruction represented by the provided line.
     * @param line String that contains an instruction.
     * @return Instruction.
     */
    public Instruction parseLineAsInstruction(String line){
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

            // throws IllegalArgumentException if instruction is not defined in Instruction.InsType.
            Instruction.InsType insType = InsType.valueOf(thisLine[0].toUpperCase());
            
            // WOW, I AM SO SMART! THAT WAS SMART. WHAT I DID BELOW AND WAS THE SAME AS LINE 70.
            /*
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
            */
            if (thisLine.length == 2){
                assert(operandType(thisLine[1]) != null); // Check for valid operand syntax.
                return new Instruction(insType, thisLine[1]);
            }
            else return new Instruction(insType);
        }
        
        return new Instruction(InsType.HALT);
    }
}
