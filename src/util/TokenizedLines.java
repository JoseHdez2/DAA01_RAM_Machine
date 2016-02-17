package util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jose
 *
 *	ArrayList<ArrayList<String>> class renaming.
 */

@SuppressWarnings("serial")
public class TokenizedLines extends ArrayList<ArrayList<String>>{

    public TokenizedLines(){
        super();
    }
    
    public TokenizedLines(List<ArrayList<String>> subList) {
        super(subList);
    }
}
