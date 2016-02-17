package pushdown.main;

/**
 * @author jose
 *
 *  Collection of constants.
 *  Models the arbitrary positions of data
 *  that were chosen as the input/output standard.
 *  It helps give semantics to the base string data.
 */
public interface FrameConstPushdown {
    
    /*
     * Input constants.
     */
    
    // Data positions in processed file.
    final static int IN_FILE_STATE_SET = 0;
    final static int IN_FILE_INPUT_ALPH = 1;
    final static int IN_FILE_STACK_ALPH = 2;
    final static int IN_FILE_INIT_STATE = 3;
    final static int IN_FILE_INIT_STACK = 4;
    final static int IN_FILE_ACCEPT_STATES = 5;
    final static int IN_FILE_TRANS_FUNCT = 6; // Where the transition lines BEGIN.
    
    // Data positions in each processed transition line. 
    final static int IN_TRAN_PREV_STATE = 0;
    final static int IN_TRAN_NEXT_STATE = 3;
    final static int IN_TRAN_REQ_INP_CHAR = 1;
    final static int IN_TRAN_REQ_STACK_SYM = 2;
    final static int IN_TRAN_STACK_SYM_TO_PUSH = 4;
    
    /*
     * Output constants.
     */
    
    // Data positions in each transition line to output.
    final static int OUT_TRAN_PREV_STATE = 0;
    final static int OUT_TRAN_NEXT_STATE = 3;
    final static int OUT_TRAN_REQ_INP_CHAR = 1;
    final static int OUT_TRAN_REQ_STACK_SYM = 2;
    final static int OUT_TRAN_STACK_SYM_TO_PUSH = 4;
    
    // Token positions in each automata status to output.
    final static int OUT_STAT_CUR_STATE = 0;
    final static int OUT_STAT_REMAIN_STR = 1;
    final static int OUT_STAT_STACK_STATUS = 2;
}
