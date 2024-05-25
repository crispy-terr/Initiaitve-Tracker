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
    private GamePanel gp;

    public RollInitiativePanel(GamePanel gp) throws Exception{
        this.gp = gp;

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

            //Once initiative has been rolled, add a dice roller and game panel
            try{
                gp.add("Initiative Tracker", GUIUtils.createGameMenu());
                gp.add("Dice Roller", GUIUtils.createDiceRollPanel());
                gp.remove(0);
            } catch (Exception ee){
                ee.printStackTrace();
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
