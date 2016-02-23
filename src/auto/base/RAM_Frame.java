package auto.base;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import auto.base.Automaton.SimState;

public class RAM_Frame extends JFrame{
    
    Automaton automaton;    // Automaton instance which we simulate.
    JTextField fieldLoad;    // Filepath to load with btnLoad.
    JButton btnLoad;    // Load automaton data from file (and initialize simulation).
    JTextField fieldInput; // Input tape.
    JButton btnInput;   // Load input tape.
    JButton btnStep;   // Perform a step of the simulation.
    JButton btnRun;    // Step until the simulation ends.
    Timer runTimer;     // Determines step frequency when running simulation.
//    String dataContent;
    JTextArea areaCode;
    JTextArea areaDefinition;
    JTextArea areaTrace; 
    
    JPanel paneLeft, paneRight;
    JScrollPane paneRightScroll;
    
    static String INIT_FILE = "example_files/ram/test5.ram";
    static String INIT_INP = "1,2,3,0,-1";
    
    public RAM_Frame(Automaton automaton){
        setTitle("RAM Machine");
        setSize(1200, 600);
        setLocationRelativeTo(null);    // Center frame on screen.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        paneLeft = new JPanel();
        paneRight = new JPanel();
        add(paneLeft);
//        add(paneRight);
//        paneRightScroll = new JScrollPane(paneRight);
        areaTrace = new JTextArea("No trace.",0,0);
        areaTrace.setEditable(false);
        
        paneRightScroll = new JScrollPane(areaTrace);
//        paneRightScroll.add(paneRight);
        add(paneRightScroll);
        
        this.automaton = automaton;
        
        fieldLoad = new JTextField(INIT_FILE, INIT_FILE.length());
        paneLeft.add(fieldLoad);
        
        btnLoad = new JButton("Load Machine");
        btnLoad.addActionListener(listLoad);
        paneLeft.add(btnLoad);
        
        fieldInput = new JTextField(INIT_INP, 15);
        paneLeft.add(fieldInput);
        
        btnInput = new JButton("Load Input");
        btnInput.addActionListener(listInput);
        paneLeft.add(btnInput);
        
        btnStep = new JButton("Step");
        btnStep.addActionListener(listStep);
        paneLeft.add(btnStep);
        
        btnRun = new JButton("Run");
        btnRun.addActionListener(listRun);
        paneLeft.add(btnRun);
        
        runTimer = new Timer(1000, listStep);
        
        areaCode = new JTextArea("---",0,0);
        areaCode.setEditable(false);
        paneLeft.add(areaCode);
        
        areaDefinition = new JTextArea("No machine loaded.",0,0);
        areaDefinition.setEditable(false);
        paneLeft.add(areaDefinition);
        
        // http://stackoverflow.com/a/3549341/3399416
//        JTable table = new JTable(new DefaultTableModel(new Object[]{"Column1", "Column2"}, 2));
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        
//        paneRight.add(table);
        areaTrace = new JTextArea("No trace.",0,0);
        areaTrace.setEditable(false);
//        paneRightScroll.add(areaTrace);
        areaTrace.setText("lets eat some cake");
        setVisible(true);
    }
    
    // TODO: this would def be public if I expanded the codebase enough.
    private void updateTraceScreen(){
        System.out.println(automaton.showStatus());
//        JTextArea jta = new JTextArea(automaton.showStatus(),0,0);
//        paneRight.add(new JTextArea(automaton.showStatus(),0,0));
//        paneRight.add(jta);
//        paneRight.repaint();
        areaTrace.setText(areaTrace.getText() + "\n\n" + automaton.showStatus());
        paneRightScroll.repaint();
        paneRightScroll.revalidate();
        this.repaint();
        this.revalidate();
    }
    
    ActionListener listLoad = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Load action.");
            String dataContent = "happy meal";
            try {
                dataContent = new Scanner(new File(fieldLoad.getText())).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            automaton.loadTransitions(dataContent);
            areaCode.setText(dataContent);
            areaDefinition.setText(automaton.toString());
        }
        
    };
    
    ActionListener listInput = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Load input action.");
            automaton.setInput(fieldInput.getText());
        }
        
    };
    
    ActionListener listStep = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Step action.");
            // Show initial state.
            if (automaton.showSimState() == SimState.INITIAL)
                updateTraceScreen();
            if (automaton.showSimState() != SimState.FINAL){
                automaton.step();
                updateTraceScreen();
            } else {
//                System.out.println("Machine is in final state."); commented out so it doesn't keep "running".
            }
        }
        
    };
    
    ActionListener listRun = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Run action.");
            if (runTimer.isRunning()){
                runTimer.stop();
                btnRun.setText("Run");
            }
            else {
                runTimer.start();
                btnRun.setText("Stop");
            }
        }
        
    };
    /*
    // since repaint() doesn't seem to work...
    public void paint(Graphics g){
        super.paint(g);
        for (int i = 0; i < this.getComponents().length - 1; i++){
            this.getComponents()[i].paint(g);;
        }
    }*/
}
