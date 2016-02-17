package turing.structs;

import automaton.structs.Symbol;
import pushdown.structs.SymbolList;
import turing.main.TuringIOConst;
import util.Debug;

@SuppressWarnings("serial")
public class Tape implements TuringIOConst{
    private SymbolList tape = new SymbolList();
    private Integer headPos = 0;
    private Symbol blankSymbol = DEFAULT_BLANK;
    
    public Tape(String str){
        this(str, null, null);
    }
    
    public Tape(String str, Symbol blankSymbol){
        this(str, blankSymbol, null);
    }
    
    public Tape(String str, Symbol blankSymbol, Integer headPos){
        tape = new SymbolList(str,"");
        if(blankSymbol != null) this.blankSymbol = blankSymbol;
        if(headPos != null) this.headPos = headPos;
    }
    
    public Tape moveHead(Movement mov){
        Debug deb = new Debug(true);
//        deb.out("before:%s", this.tape);
        
        switch(mov.value){
        case LEFT: this.moveHeadLeft(); break;
        case RIGHT: this.moveHeadRight(); break;
        case STOP: break;
        }
        
//        deb.out("after:%s", this.tape);
        deb.out("%d",headPos);
        return this;
    }
    
    public Symbol readSymbolAtHead(){
        return tape.get(headPos);
    }
    
    public Tape writeSymbolAtHead(Symbol sym){
        tape.set(headPos, sym);
        return this;
    }
    
    private void moveHeadLeft(){
        headPos = headPos-1;
//        if(headPos < 0){
//            tape.add(0, blankSymbol);
//            headPos = 0;
//        }
    }
    
    private void moveHeadRight(){
        headPos = headPos + 1;
//        if(headPos >= tape.size()-1){
//            tape.add(blankSymbol);
//            headPos = tape.size()-1; // TODO: redundant.
//        }
    }
    
    public Tape deepEnoughCopy(){
        Tape copyTape = new Tape("", blankSymbol, headPos);
        copyTape.tape.addAll(this.tape);
        return copyTape;
    }
    
    public String toString(){
        String str = "";
        for (int i = 0; i < tape.size(); i++){
            if (i == headPos)
                str += String.format("[%s]", tape.get(i));
            else
                str += String.format("%s", tape.get(i));
        }
        return str;
    }
    
    /*
     * Equals and hashCode.
     */
    
    public boolean equals(Object ob){
        if (!super.equals(ob)) return false; // TODO: Hope this is ok.
        if (ob == null) return false;
        if (ob.getClass() != getClass()) return false;
        Tape other = (Tape)ob;
        if (!headPos.equals(other.headPos)) return false;
        if (!blankSymbol.equals(other.blankSymbol)) return false;

        return true;
    }
    
    public int hashCode() {
        return  super.hashCode() ^
                headPos.hashCode() ^
                blankSymbol.hashCode();
    }
}
