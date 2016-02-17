package automaton.main;

/**
 * @author jose
 *
 *  Collection of constants.
 *  Models the arbitrary positions of data
 *  that were chosen as the input/output standard.
 *  It helps give semantics to the base string data.
 */
public interface AutomatonIOConst {
    // TODO: Nothing if this remains abstract.
    // Something if I make a new layer of abstraction over this one...
    // And this becomes NFA/DFA.


    
    /*
     * Output constants.
     */
    
    final int OUT_TRAN_TOK_NUM = 3;
    // Data positions in each transition line to output.
    final static int OUT_TRAN_INPUT_STATE = 0;
    final static int OUT_TRAN_INPUT_CHAR = 1;
    final static int OUT_TRAN_OUTPUT_STATE = 2;
}
