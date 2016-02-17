package pushdown.structs;

import java.util.ArrayList;

import automaton.structs.Symbol;

@SuppressWarnings("serial")
public class SymbolList extends ArrayList<Symbol> {
    
    public SymbolList(){}
    
    public SymbolList(String symbols, String regex){
        String[] charList = symbols.split(regex);
        
        for (String str : charList){
            this.add(new Symbol(str));
        }
    }
}
