package auto.base;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class RAM_Frame extends JFrame{
    
    Automaton automaton;    // Automaton instance which we simulate.
    JTextField fieldLoad;    // Filepath to load with btnLoad.
    JButton btnLoad;    // Load automaton data from file (and initialize simulation).
    JButton btnStep;   // Perform a step of the simulation.
    JButton btnRun;    // Step until the simulation ends.
    Timer runTimer;     // Determines step frequency when running simulation.
    String dataContent;
    JTextArea areaCode;
    
    JPanel paneLeft, paneRight;
    
    static String INIT_FILE = "example_files/ram/test1.ram";
    
    public RAM_Frame(Automaton automaton){
        setTitle("RAM Machine");
        setSize(1200, 600);
        setLocationRelativeTo(null);    // Center frame on screen.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 2));
        paneLeft = new JPanel();
        paneRight = new JPanel();
        add(paneLeft);
        add(paneRight);
        
        this.automaton = automaton;
        
        
        fieldLoad = new JTextField(INIT_FILE, INIT_FILE.length());
        paneLeft.add(fieldLoad);
        
        btnLoad = new JButton("Load");
        btnLoad.addActionListener(listLoad);
        paneLeft.add(btnLoad);
        
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
        
        // http://stackoverflow.com/a/3549341/3399416
//        JTable table = new JTable(new DefaultTableModel(new Object[]{"Column1", "Column2"}, 2));
//        DefaultTableModel model = (DefaultTableModel) table.getModel();
//        model.addRow(new Object[]{"Column 1", "Column 2", "Column 3"});
        
//        paneRight.add(table);
        
        setVisible(true);
    }
    
    // TODO: this would def be public if I expanded the codebase enough.
    private void updateTraceScreen(){
        System.out.println(automaton.showStatus());
        JTextArea jta = new JTextArea(automaton.showStatus(),0,0);
//        paneRight.add(new JTextArea(automaton.showStatus(),0,0));
        paneRight.add(jta);
        paneRight.repaint();
        this.repaint();
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
        }
        
    };
    
    ActionListener listStep = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Step action.");
            automaton.step();
            // TODO: update on screen.
            updateTraceScreen();
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
}
