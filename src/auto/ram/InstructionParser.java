package auto.ram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Predicate;

import auto.ram.Instruction.InsType;
import auto.ram.Instruction.OpType;

/**
 * Instruction knows what an instruction can be.
 * RAM_Auto knows how each instruction behaves.
 * InstructionParser knows what each instruction looks like.
 * 
 * @author jose
 *
 */
public class InstructionParser {
    
    static String SEP = "#";    // Token separator, for visibly reducing whitespace, etc. TODO: weird phrasing
    
    /**
     * Given a string that represents an operand, return the operand type.
     * @param str String that represents an operand.
     * @return OpType, null if no matching type was found.
     */
    private static OpType operandType(String str){
//        if (str.matches("[=*]?\\d+"));
        if (str.matches("\\d+")) return OpType.NUM_DIRECT;
        if (str.matches("=\\d+")) return OpType.NUM_LITERAL;
        if (str.matches("\\*\\d+")) return OpType.NUM_INDIRECT;
        if (str.matches("[A-z_]+")) return OpType.NAME;
        if (str.matches("") || str == null) return OpType.NONE;
        else {
            System.err.println(str + ": Unknown operand type.");
            return null;    // return NONE?
        }
    }

    private static Predicate<String> emptyLine(){
        return p -> p.isEmpty();
    }
    
    /**
     * Prepare lines for parsing: remove comments and empty lines, and reduce whitespace.
     * @param programFileContent
     * @return
     */
    private static ArrayList<String> prepareLines(String programFileContent){
        String[] lines = programFileContent.split(System.getProperty("line.separator")); // http://stackoverflow.com/a/1096633/3399416
        
        for (int i = 0; i < lines.length; i++){
            // Remove comments. Only keep what's before the ';' (comment separator).
            lines[i] = lines[i].split(";")[0];
            // Replace whitespace with separators.
            lines[i] = lines[i].trim().replaceAll("\\s+", SEP);
        }
        ArrayList<String> lines2 = new ArrayList<String>(Arrays.asList(lines));
        
        // Only keep the lines with code.
        lines2.removeIf(emptyLine());
        
        System.out.println(lines2);
        return lines2;
    }
    
    // TODO: two calls? 1st for gotos, then 2nd for instructions?
    
    public static HashMap<String,Integer> parseGotos(String programFileContent){
        HashMap<String,Integer> gotos = new HashMap<String,Integer>();
        // Remove comments and empty lines, and reduce whitespace.
        ArrayList<String> lines2 = prepareLines(programFileContent);

        // Do actual parsing
        for (int i = 0; i < lines2.size(); i++){
            String dummy = lines2.get(i); // split() is destructive.
            String[] thisLine = dummy.split(SEP);
            // Detect tags/"goto"s and add them
            if (thisLine.length > 1 && thisLine[1].equals(":")){
//                System.out.println("doopy woop!");
                gotos.put(thisLine[0], i);  // Add goto with key= token 0 and value= this line's index.
                dummy = lines2.get(i); // split() is destructive.
                thisLine = dummy.substring(dummy.indexOf(':')+2).split(SEP);
            }
        } 
        System.out.println(gotos);
        return gotos;
    }
    
    // TODO: this is a temp debug function.
    /**
     * Print a String[] correctly to standard output.
     * @param strArr String[] to print.
     */
    private static void print(String[] strArr){
        System.out.print("[");
        for(int i = 0; i < strArr.length; i++){
            System.out.print(strArr[i]);
            if (i != strArr.length-1) System.out.print(", ");
        }
        System.out.print("]\n");
    }
    
    public static ArrayList<Instruction> parseInstructions(String programFileContent){
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        
        // Remove comments and empty lines, and reduce whitespace.
        ArrayList<String> lines2 = prepareLines(programFileContent);

        // Do actual parsing
        for (int i = 0; i < lines2.size(); i++){
            String dummy = lines2.get(i); // split() is destructive.
            String[] thisLine = dummy.split(SEP);
//            System.out.println(lines2.get(i));
//            System.out.println(String.format("thisLine[1]: '%s'", thisLine[1]));
            // Detect tags/"goto"s and add them
            if (thisLine.length > 1 && thisLine[1].equals(":")){
                System.out.println("hey");
                print(thisLine);
                dummy = lines2.get(i); // split() is destructive.
                print(thisLine);
                thisLine = dummy.substring(dummy.indexOf(':')+2).split(SEP);
                print(thisLine);
                print(thisLine);
            }
            instructions.add(parseLineAsInstruction(thisLine));
        }          
        return instructions;
    }
    
    /**
     * Main purpose of this class.
     * Parse, create and return the instruction represented by the provided line.
     * Line must have been stripped of the tag (if it had any) otherwise it will fail.
     * @param line String[] that contains a tokenized line representing an instruction.
     * @return Instruction.
     */
    public static Instruction parseLineAsInstruction(String[] line){
//        String[] line = splitLine.split(SEP);
        assert(line.length <= 2);

        // 'Parse' instruction type.
        // Throws IllegalArgumentException if instruction is not defined in Instruction.InsType.
        Instruction.InsType insType = InsType.valueOf(line[0].toUpperCase());
        
        if (line.length == 2){
            assert(operandType(line[1]) != null); // Check for valid operand syntax.
            return new Instruction(insType, operandType(line[1]), line[1]);
        }
        else return new Instruction(insType);
    }
}
