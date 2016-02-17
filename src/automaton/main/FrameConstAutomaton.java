package automaton.main;

import i18n.GUIStr;
import i18n.I18n;

public interface FrameConstAutomaton {

    //
    // Strings.
    //
    
    final String STR_WINDOW_TITLE = I18n.getString(GUIStr.PUSHDOWN_WINDOW_TITLE);
    final String STR_WINDOW_LOAD = I18n.getString(GUIStr.PUSHDOWN_WINDOW_LOAD);
    
    final String STR_LOAD = I18n.getString(GUIStr.LOAD);
    final String STR_RUN = I18n.getString(GUIStr.RUN);
    final String STR_TRACE = I18n.getString(GUIStr.TRACE);
    final String STR_TRANS = I18n.getString(GUIStr.TRANSITIONS);
    
    //
    // Window integers.
    //
    
    final int FRAME_X = 100;
    final int FRAME_Y = 100;
    final int FRAME_WIDTH = 450;
    final int FRAME_HEIGHT = 300;
    
    final int FIELD_INPUT_STR_SIZE = 10;
    
    // TODO this doesn't work
    // change to true if you want an English interface.
    boolean englishGUI = false;
    int lang = englishGUI ? 0 : 1;
    


    String[][] tableTransDummyData =
        {
            {"q1", "q2", "a1", "A1", "A2"},
        };
    String[][] tableTransColumns = 
        {
            {"State 1", "State 2", "Input", "Stack Pop", "Stack Push"},
            {"Estado 1", "Estado 2", "Entrada", "De pila", "A pila"},
        };
    
    String[][] tableTraceDummyData =
        {
            {"q1", "a1", "A1"},
        };
    
    String[][] tableTraceColumns = 
        {
            {"Current State", "Remaining String", "Stack Content"},
            {"Estado Actual", "Cadena Restante", "Contenido de Pila"},
        };
}
