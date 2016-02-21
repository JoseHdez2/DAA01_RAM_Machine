package old.pushdown.main;

import java.io.IOException;

import javax.swing.JTable;

import old.automaton.main.FrameAutomatonLoad;
import old.automaton.main.FrameAutomatonTrace;
import old.automaton.structs.AutomatonData;
import old.pushdown.structs.PushdownData;
import old.util.TokenizedLines;

@SuppressWarnings("serial")
public class FramePushdownLoad extends FrameAutomatonLoad{
    
    @Override
    protected FrameAutomatonTrace createAutomatonFrameTrace(AutomatonData automatonData, String inputString) {
        // TODO: Nasty typecast, for the sake of inheritance.
        return new FramePushdownTrace((PushdownData)automatonData, inputString);
    }

    @Override
    protected AutomatonData readDataFromFile(String fullFilePath) {
        try {
            return new PushdownData(fullFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            return new PushdownData();
            return null;
        }
    }

    @Override
    protected AutomatonData initializeAutomatonData() {
        return new PushdownData();
    }

    @Override
    protected JTable initializeTransitionTable() {
        return new JTable();
    }

    @Override
    protected TokenizedLines getTransitionLines() {
        return new TokenizedLines(); // TODO not right
    }

    @Override
    protected String str(String strId) {
        // TODO Auto-generated method stub
        return null;
    }
}
