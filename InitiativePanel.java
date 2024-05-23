import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class InitiativePanel extends JPanel {
    private JPanel scrollPanel;
    private JList creaturesList;
    private JButton createEncounterButton;

    
    
    //Testing some stuff with scroll panel
    public static void main(String[] args){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
        p.add(new JLabel("ggg"));
        JPanel[] panelArr = new JPanel[]{p,p,p,p,p,p};
        JList paneList = new JList<>(panelArr);

        String[] testList = new String[]{"a", "b", "c", "d", "e", "f", "g"};
        JList list = new JList<>(testList);
        JScrollPane jsp = new JScrollPane(paneList);
        jsp.setMaximumSize(new Dimension(10, 50));
        


        JFrame frame = new JFrame();
        frame.add(jsp);
        frame.setLayout(new GridLayout(1,1));
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

 
