package main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import automaton.main.FrameAutomatonLoad;
import i18n.GUIStr;
import i18n.I18n;
import pushdown.main.FramePushdownLoad;
import turing.main.FrameTuringLoad;

@SuppressWarnings("serial")
public class MainMenuFrame extends JFrame {

    @SuppressWarnings("unused")
    private FrameAutomatonLoad frameLoad = null;
    
    private JPanel contentPane;

    private String STR_TITLE = I18n.getString(GUIStr.MAIN_TITLE);
    private String STR_PUSHDOWN = I18n.getString(GUIStr.PUSHDOWN_WINDOW_TITLE);
    private String STR_TURING = I18n.getString(GUIStr.TURING_WINDOW_TITLE);

    /**
     * Create the frame.
     */
    public MainMenuFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        
        JLabel lblLogicalAutomata = new JLabel(STR_TITLE);
        panel.add(lblLogicalAutomata);
        lblLogicalAutomata.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel boxPanel = new JPanel();
        contentPane.add(boxPanel, BorderLayout.CENTER);
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS));
        
        Component verticalStrut_1 = Box.createVerticalStrut(20);
        boxPanel.add(verticalStrut_1);
        
        JButton btnPushdownAutomaton = new JButton(STR_PUSHDOWN);
        btnPushdownAutomaton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameLoad = new FramePushdownLoad();
            }
        });
        btnPushdownAutomaton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boxPanel.add(btnPushdownAutomaton);
        
        Component verticalStrut = Box.createVerticalStrut(20);
        boxPanel.add(verticalStrut);
        
        JButton btnTuringMachine = new JButton(STR_TURING);
        btnTuringMachine.setHorizontalAlignment(SwingConstants.RIGHT);
        btnTuringMachine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameLoad = new FrameTuringLoad();
            }
        });
        btnTuringMachine.setAlignmentX(Component.CENTER_ALIGNMENT);   
        boxPanel.add(btnTuringMachine);
    }

}
