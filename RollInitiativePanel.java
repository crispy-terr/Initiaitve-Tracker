import java.awt.event.*;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class RollInitiativePanel extends JPanel implements ActionListener{

    private JScrollPane jspNameDisplay;
    private JPanel mainPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JButton jbSetInitiative = new JButton("Set Initiative");
    private JButton jbRollInitiativeForAll = new JButton("Roll for all");

    public RollInitiativePanel() throws Exception{

        //Set layout for main panel and button banel
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        //Add action listener to buttons
        jbSetInitiative.addActionListener(this);
        jbRollInitiativeForAll.addActionListener(this);

        //Add all creatures to mainPanel as SetInitiativePanel
        for(File n : SetInitiativePanel.getDirList()){
            SetInitiativePanel sip = new SetInitiativePanel();
            mainPanel.add(sip);
        }

        //Add a border to mainPanel
        mainPanel.setBorder(new MatteBorder(0,0,1,0, Color.GRAY));

        jspNameDisplay = new JScrollPane(mainPanel);
        jspNameDisplay.setPreferredSize(new Dimension(300,400));
        jspNameDisplay.getVerticalScrollBar().setUnitIncrement(10);

        //Add everything
        buttonPanel.add(jbRollInitiativeForAll);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,5)));
        buttonPanel.add(jbSetInitiative);
        add(jspNameDisplay);
        add(buttonPanel);

    }

    public static void main(String[] args) throws Exception{
        RollInitiativePanel rip = new RollInitiativePanel();
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(rip);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Set Initiative")){
            for(Component n : mainPanel.getComponents()){
                if(n instanceof SetInitiativePanel){
                    ((SetInitiativePanel)n).actionPerformed(e);
                }
            }

            int numWithError = 0;
            for(Component n : mainPanel.getComponents()){
                if(n instanceof SetInitiativePanel){
                    if(((SetInitiativePanel)n).getErrorLabel().isVisible()){
                        numWithError++;
                    }
                }
            }

            if(numWithError == 0){
                setVisible(false);
            }
            

        } else if (e.getActionCommand().equals("Roll for all")){
            for(Component n : mainPanel.getComponents()){
                if(n instanceof SetInitiativePanel){
                    int rand = (int)(Math.random()*20 + 1);
                    ((SetInitiativePanel)n).getJTFRoll().setText(rand + "");
                }
            }
        }
    }
    
}
