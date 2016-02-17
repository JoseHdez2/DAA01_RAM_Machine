package i18n;

import java.util.HashMap;
import java.util.Map;

public abstract class GUIStrHash { 
    
    private static final Map<GUIStr, String> hash;
    static
    {
        hash = new HashMap<GUIStr, String>();
        hash.put(GUIStr.MAIN_TITLE, "MainTitle");
        hash.put(GUIStr.LOAD, "Load");
        hash.put(GUIStr.RUN, "Run");
        hash.put(GUIStr.TRACE, "Trace");
        hash.put(GUIStr.TRANSITIONS, "Transitions");
        hash.put(GUIStr.PUSHDOWN_WINDOW_TITLE, "Pushdown.WindowTitle");
        hash.put(GUIStr.PUSHDOWN_WINDOW_LOAD, "Pushdown.WindowLoad");
        hash.put(GUIStr.TURING_WINDOW_TITLE, "Turing.WindowTitle");
        hash.put(GUIStr.TURING_WINDOW_LOAD, "Turing.WindowLoad");
    }
    
    public static String get(GUIStr stringId){
        return hash.get(stringId);
    }
}

