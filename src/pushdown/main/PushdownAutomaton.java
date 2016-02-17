package pushdown.main;

import automaton.main.Automaton;
import automaton.structs.AutomatonData;
import automaton.structs.AutomatonStatus;
import automaton.structs.AutomatonTransition;
import pushdown.structs.PushdownData;
import pushdown.structs.PushdownTransitionSet;

/**
 * @author jose
 *
 *  The automaton itself. The only class that knows how to work with
 *  the AutomatonData it contains (e.g. apply transitions).
 */
public class PushdownAutomaton extends Automaton{

    public PushdownAutomaton(PushdownData data) {
        super(data);
    }
    
    @Override
    protected boolean correctMachineDefinition(AutomatonData data) {
        // TODO Implement
        return true;
    }

    @Override
    protected AutomatonStatus createInitialStatus(String inputString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected PushdownTransitionSet findApplicableTransitionRules(AutomatonStatus ms) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected boolean acceptanceStatus(AutomatonStatus ms) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public AutomatonStatus applyTransition(AutomatonStatus as, AutomatonTransition tr) {
        // TODO Auto-generated method stub
        return null;
    }



}