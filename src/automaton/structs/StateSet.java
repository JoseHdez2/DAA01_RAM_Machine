package automaton.structs;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressWarnings("serial")
public class StateSet extends HashSet<State>{
    StateSet(){};
    
    /**
     * Interprets each String as a unique element.
     * @param states
     */
    public StateSet(ArrayList<String> states){
        for (String token : states) {
            this.add(new State(token));
        }
    }
}
