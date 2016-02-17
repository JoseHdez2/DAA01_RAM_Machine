package turing.main;

import automaton.main.Automaton;
import automaton.structs.AutomatonData;
import automaton.structs.AutomatonStatus;
import automaton.structs.AutomatonTransition;
import automaton.structs.State;
import turing.structs.Tape;
import turing.structs.TuringData;
import turing.structs.TuringStatus;
import turing.structs.TuringTransition;
import turing.structs.TuringTransitionSet;

public class TuringAutomaton extends Automaton {
    
    protected TuringData data;
    
    public TuringAutomaton(AutomatonData data) {
        super(data);
        this.data = (TuringData)data;
        // TODO Constructor raro.
    }

    @Override
    protected boolean correctMachineDefinition(AutomatonData data) {
        // TODO Implement
        return true;
    }

    @Override
    protected AutomatonStatus createInitialStatus(String inputString) {
        State newState = data.getInitialState();
        Tape newTape = new Tape(inputString, ((TuringData)data).getBlankSymbol());
        
        return new TuringStatus(newState, newTape);
    }

    @Override
    protected TuringTransitionSet findApplicableTransitionRules(AutomatonStatus as) {
        
        TuringStatus ts = (TuringStatus)as;
        
        TuringTransitionSet applicableTransitions = new TuringTransitionSet();
        
        for (AutomatonTransition tr : data.getTransitionRules()){
            TuringTransition ttr = (TuringTransition)tr;
            if (!tr.getPrevState().equals(ts.getCurrentState())) continue;
            if (!tr.getInputCharacter().equals(ts.getTape().readSymbolAtHead())) continue;
            
            applicableTransitions.add(ttr);
        }
        return applicableTransitions;
    }

    @Override
    protected boolean acceptanceStatus(AutomatonStatus as) {
        TuringStatus ts = (TuringStatus)as;
        
        if (!data.getAcceptStates().contains(ts.getCurrentState())) return false;
        if (!findApplicableTransitionRules(as).isEmpty()) return false;
        return true;
    }

    @Override
    public TuringStatus applyTransition(AutomatonStatus as, AutomatonTransition tr) {
        // Type-casting, to 'specify' from the general inherited algorithm.
        TuringStatus ts = (TuringStatus)as;
        TuringTransition ttr = (TuringTransition)tr;
        
        // Construct the new status:
        State newState = ttr.getNextState();    // the state will be the transition's output state.
        Tape newTape = ts.getTape().deepEnoughCopy(); // the tape will be the same, except we...
        newTape = newTape.writeSymbolAtHead(ttr.getOutputCharacter()); // ...write transition symbol.
        newTape = newTape.moveHead(ttr.getMovement()); // ...move tape head.
        
        return new TuringStatus(newState, newTape);
    }

}
