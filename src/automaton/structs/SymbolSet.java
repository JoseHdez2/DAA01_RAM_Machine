package automaton.structs;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressWarnings("serial")
public class SymbolSet extends HashSet<Symbol> {
    SymbolSet(){};
    
    /**
     * Interprets each String as a unique element.
     * @param states
     */
    public SymbolSet(ArrayList<String> states){
        for (String token : states) {
            this.add(new Symbol(token));
        }
    }
}
