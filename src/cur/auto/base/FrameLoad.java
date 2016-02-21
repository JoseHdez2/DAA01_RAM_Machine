package cur.auto.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class FrameLoad extends JFrame{
    
    Automaton automaton;    // Automaton instance which we simulate.
    JButton btnLoad;    // Load automaton data from file (and initialize simulation).
    JButton btnStep;   // Perform a step of the simulation.
    JButton btnRun;    // Step until the simulation ends.
    Timer runTimer;     // Determines step frequency when running simulation.
    String dataContent;
    
    public FrameLoad(Automaton automaton){
        setSize(1200, 600);
        setLocationRelativeTo(null);    // Center frame on screen.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.automaton = automaton;
        
        btnLoad = new JButton("Load");
        btnLoad.addActionListener(listLoad);
        
        setVisible(true);
    }
    
    ActionListener listLoad = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Load action.");
            String dataContent = "happy meal";
            automaton.loadTransitions(dataContent);
        }
        
    };
    
    ActionListener listStep = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Step action.");
            automaton.step();
            // TODO: update on screen.
        }
        
    };
    
    ActionListener runStep = new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Run action.");
            while (automaton.showSimState() != Automaton.SimState.FINAL){
                listStep.actionPerformed(e);
            }
        }
        
    };
}
