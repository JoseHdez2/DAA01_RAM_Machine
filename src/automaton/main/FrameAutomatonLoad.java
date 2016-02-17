package automaton.main;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import automaton.structs.AutomatonData;
import i18n.GUIStr;
import i18n.I18n;
import util.MyTableModel;
import util.TokenizedLines;

@SuppressWarnings("serial")
public abstract class FrameAutomatonLoad extends JFrame implements FrameConstAutomaton {
//    TODO: be able to consider acceptance states to accept string (in pushdown)
//    TODO: have the tables in the GUI be scrollable (somehow they aren't)
    
    protected String chosenFileFullPath = null;
    protected AutomatonData automatonData = initializeAutomatonData();
    protected JTextField inputStringField = null;
    
    protected FrameAutomatonTrace frameAutomatonTrace;
    protected JLabel labelFilename = new JLabel("---");
    protected JTable tableTrans = new JTable();
    protected JScrollPane scrollPane = new JScrollPane();
    
    protected JButton buttonRun = new JButton(I18n.getString(GUIStr.RUN));
	
	/**
	 * Create the application.
	 */
	public FrameAutomatonLoad() {
      setVisible(true);
      initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		setTitle(str("STR_WINDOW_TITLE"));
		setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panelNorth = buildLoadPanel();
        this.getContentPane().add(panelNorth, BorderLayout.NORTH);
		
        JPanel panelSouth = buildRunPanel();
        this.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		
		JPanel panelTrans = new JPanel();
		this.getContentPane().add(panelTrans, BorderLayout.CENTER);
		
//		tableTrans = new JTable(tableTransDummyData, tableTransColumns[lang]);
		tableTrans = initializeTransitionTable();

		tableTrans.setEnabled(false);
		scrollPane = new JScrollPane(tableTrans);
		
		panelTrans.add(scrollPane, BorderLayout.SOUTH);
		
		panelNorth.setMinimumSize(panelNorth.getPreferredSize());
		
		lockTraceGUI(true);
	}
	
	/**
	 * @return JPanel with GUI for automaton file loading.
	 */
	protected JPanel buildLoadPanel(){
        
        JPanel panelNorth = new JPanel();
        
        JButton btnLoad = new JButton(STR_LOAD);
        panelNorth.add(btnLoad, BorderLayout.NORTH);

        btnLoad.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog openFile = new FileDialog((JFrame)SwingUtilities.getWindowAncestor(btnLoad), 
                        STR_WINDOW_LOAD, FileDialog.LOAD);
                openFile.setDirectory(System.getProperty("user.dir"));
                openFile.setVisible(true);
                
                if(!(openFile.getFile() == null)){
                    chosenFileFullPath = openFile.getDirectory() + File.separator + openFile.getFile();
                    System.out.println(chosenFileFullPath);
                    labelFilename.setText(openFile.getFile());
                    labelFilename.setFont(labelFilename.getFont().deriveFont(Font.BOLD));
                    updateLoadedAutomaton();
                    lockTraceGUI(false);
                }
            }
            
        });
        
        panelNorth.add(labelFilename);
        
        return panelNorth;
	}
	
	/**
	 * @return JPanel with GUI for entering an input string and running a trace.
	 */
	protected JPanel buildRunPanel(){
	    
	    JPanel runPanel = new JPanel();
        
        inputStringField = new JTextField(FIELD_INPUT_STR_SIZE);
        runPanel.add(inputStringField);
        
        buttonRun = new JButton(STR_RUN);
        runPanel.add(buttonRun);
        
        buttonRun.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String inputString = inputStringField.getText();
                frameAutomatonTrace = createAutomatonFrameTrace(automatonData, inputString);
                frameAutomatonTrace.setVisible(true);
            }
            
        });
	    
	    return runPanel;
	}
	
	

	protected void updateLoadedAutomaton(){
        automatonData = readDataFromFile(chosenFileFullPath);
        
//        TokenizedLines transitions = automatonData.getTransitionRules().asStringMatrix();
        TokenizedLines transitions = getTransitionLines();
        
        tableTrans.setModel(new MyTableModel(transitions));
//      tableTrans.setColumnModel(tableTransColumns[lang]);
	}
	
	/**
	 * Disable/Enable GUI elements that lead to the trace window.
	 * @param lock If true, elements will be locked.
	 */
	protected void lockTraceGUI(boolean lock){
//	    tableTrans.setVisible(!lock);
	    buttonRun.setEnabled(!lock);
	    inputStringField.setEnabled(!lock);
	}
	
	/*
	 * ABSTRACT FUNCTIONS
	 * To be implemented by inheriting classes.
	 */
	
    protected abstract FrameAutomatonTrace createAutomatonFrameTrace(AutomatonData automatonData, String inputString);
    // automatonFrameTrace = new AutomatonFrameTrace(automatonData, inputString);
    
    // TODO: Since AutomatonData cannot be instantiated.
    /**
     * @return The corresponding (inheriting, non-abstract) AutomatonData object.
     */
    protected abstract AutomatonData readDataFromFile(String fullFilePath);
    
    protected abstract AutomatonData initializeAutomatonData();
    
    protected abstract JTable initializeTransitionTable();
    
    protected abstract TokenizedLines getTransitionLines();
    
    protected abstract String str(String strId);
}
