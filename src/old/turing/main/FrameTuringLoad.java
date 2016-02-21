package old.turing.main;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.JTable;

import old.automaton.main.FrameAutomatonLoad;
import old.automaton.main.FrameAutomatonTrace;
import old.automaton.structs.AutomatonData;
import old.i18n.GUIStr;
import old.i18n.I18n;
import old.turing.structs.TuringData;
import old.turing.structs.TuringTransitionSet;
import old.util.TokenizedLines;

@SuppressWarnings("serial")
public class FrameTuringLoad extends FrameAutomatonLoad implements FrameConstTuring{

    @Override
    protected FrameAutomatonTrace createAutomatonFrameTrace(AutomatonData automatonData, String inputString) {
        // TODO Auto-generated method stub
        return new FrameTuringTrace(automatonData, inputString);
    }

    @Override
    protected AutomatonData readDataFromFile(String fullFilePath) {
        // TODO Auto-generated method stub
        try {
            return new TuringData(fullFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            return new TuringData();
            return null;
        }
    }

    @Override
    protected AutomatonData initializeAutomatonData() {
        // TODO Auto-generated method stub
        return new TuringData();
    }

    @Override
    protected JTable initializeTransitionTable() {
        return new JTable(FrameConstTuring.tableTransDummyData, FrameConstTuring.tableTransColumns[0]);
    }

    @Override
    protected TokenizedLines getTransitionLines() {
        TuringData td = (TuringData)automatonData;
        TuringTransitionSet tts = (TuringTransitionSet)td.getTransitionRules();
        return tts.asStringMatrix();
    }

    @Override
    protected String str(String strId) {
        HashMap<String, String> myMap = new HashMap<String, String>();
        myMap.put("STR_WINDOW_TITLE", I18n.getString(GUIStr.TURING_WINDOW_TITLE));
        myMap.put("STR_WINDOW_LOAD", I18n.getString(GUIStr.TURING_WINDOW_LOAD));
        
        try {
            if (!myMap.containsKey(strId))
                throw new Exception("Hard-coded call to non-existent GUI string.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return myMap.get(strId);
    }

}
