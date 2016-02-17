package turing.structs;

/**
 * @author jose
 *
 *  Represents a Turing Machine movement.
 */
public class Movement {
    MovementEnum value;
    
    public Movement(Character ch) throws RuntimeException{
        switch(Character.toUpperCase(ch)){
        case 'L': value = MovementEnum.LEFT; break;
        case 'R': value = MovementEnum.RIGHT; break;
        case 'S': value = MovementEnum.STOP; break;
        default:
            throw new RuntimeException("Invalid Turing movement character.");
        }
    }
    
    public Movement(MovementEnum value){
        this.value = value;
    }
    
    public String toString(){
        switch(value){
        case LEFT: return "L";
        case RIGHT: return "R";
        case STOP: return "S";
        }
        return null;
    }
    
    /*
     * Equals and hashCode.
     */
    
    public boolean equals(Object ob){
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Movement other = (Movement)ob;
        if (value != other.value) return false;
        return true;
    }
    
    public int hashCode() {
        return value.hashCode();
    }
    
    /*
     * Getters and setters.
     */
    
    public MovementEnum getValue() {
        return value;
    }
}
