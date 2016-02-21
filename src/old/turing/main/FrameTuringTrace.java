package old.turing.main;

import old.automaton.main.FrameAutomatonTrace;
import old.automaton.structs.AutomatonData;
import old.turing.structs.TuringData;

@SuppressWarnings("serial")
public class FrameTuringTrace extends FrameAutomatonTrace{

    public FrameTuringTrace(AutomatonData automatonData, String inputString) {
        super(automatonData, inputString);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void updateAutomatonWithGUIData() {
        // TODO Auto-generated method stub
        TuringData turingData = (TuringData)automatonData;
        myAutomaton = new TuringAutomaton(turingData);
    }

}
