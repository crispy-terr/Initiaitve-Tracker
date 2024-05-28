import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CTRMakerPanel extends JPanel implements ActionListener {
    private JRadioButton jrbPlayer = new JRadioButton("Player");
    private JRadioButton jrbEnemy = new JRadioButton("Enemy");
    private JRadioButton jrbBoss = new JRadioButton("Boss");
    private ButtonGroup typeButtonGroup = new ButtonGroup();
    private JPanel radioButtonPanel = new JPanel();
    private JPanel mainPanel = new JPanel();

    // Universal stats
    private StatsDisplay characterName = new StatsDisplay("Character Name");
    private StatsDisplay strength = new StatsDisplay("Strength");
    private StatsDisplay dexterity = new StatsDisplay("Dexterity");
    private StatsDisplay constitution = new StatsDisplay("Constitution");
    private StatsDisplay intelligence = new StatsDisplay("Intelligence");
    private StatsDisplay wisdom = new StatsDisplay("Wisdom");
    private StatsDisplay charisma = new StatsDisplay("Charisma");
    private StatsDisplay maxHP = new StatsDisplay("Max HP");
    private StatsDisplay armorClass = new StatsDisplay("Armor Class");
    private JPanel statsPanel = new JPanel();

    // Player specific stats
    private StatsDisplay playerName = new StatsDisplay("Player Name");

    // Errors
    private JPanel errorPanel = new JPanel();
    private JLabel errorLabel = new JLabel();

    private JButton jbCreateCreature = new JButton("Create Creature");

    public CTRMakerPanel() {
        // Set layout of the CTRMakerPanel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Set up typeButtonGroup
        typeButtonGroup.add(jrbPlayer);
        typeButtonGroup.add(jrbEnemy);
        typeButtonGroup.add(jrbBoss);

        // Give buttons action listener
        jrbPlayer.addActionListener(this);
        jrbEnemy.addActionListener(this);
        jrbBoss.addActionListener(this);

        // Set up the radioButtonPanel
        radioButtonPanel.setMaximumSize(new Dimension(300, 50));
        radioButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        radioButtonPanel.add(jrbPlayer);
        radioButtonPanel.add(jrbEnemy);
        radioButtonPanel.add(jrbBoss);
        jrbPlayer.setSelected(true);

        // Set up statsPanel
        statsPanel.setLayout(new GridLayout(5, 2));
        statsPanel.add(characterName);
        statsPanel.add(maxHP);
        statsPanel.add(armorClass);
        statsPanel.add(strength);
        statsPanel.add(dexterity);
        statsPanel.add(constitution);
        statsPanel.add(intelligence);
        statsPanel.add(wisdom);
        statsPanel.add(charisma);
        statsPanel.add(playerName);
        statsPanel.setMaximumSize(new Dimension(500,300));
        mainPanel.add(statsPanel);

        // Add create creature button and give it action listener
        jbCreateCreature.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){

                //Add all text fields to a list and check if they're filled in
                int filledInPointer = 0;
                ArrayList<String> statsList = new ArrayList<String>();
                for(Component c : statsPanel.getComponents()){
                    if(c instanceof StatsDisplay){
                        StatsDisplay sd = (StatsDisplay)c;
                        statsList.add(sd.getStatInput().getText());
                        if(!sd.getStatInput().getText().equals("")){
                            filledInPointer++;
                        }
                    }
                }
                System.out.println(statsList);
                int minFilledInFields = statsList.size();

                //if boss or enemy is selected, subtract 1 from the amount of filled in fields needed
                if(jrbBoss.isSelected() || jrbEnemy.isSelected()){
                    minFilledInFields--;
                }

                if(filledInPointer == minFilledInFields){
                    if(jrbPlayer.isSelected()){
                        System.out.println("Player created");
                        CreatureUtils.makeCTRFile(statsList, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }
                    else if(jrbEnemy.isSelected()){
                        System.out.println("Enemy Created");
                        CreatureUtils.makeCTRFile(statsList, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }
                    else{
                        System.out.println("Boss Created");
                        CreatureUtils.makeCTRFile(statsList, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }

                } else {
                    errorLabel.setText("Please fill out all fields.");
                    errorPanel.setVisible(true);
                }
            }
        });
        mainPanel.add(jbCreateCreature);

        // Set up errorPanel
        errorLabel.setText("Creature could not be created.");
        errorLabel.setForeground(Color.RED);
        errorPanel.add(errorLabel);
        errorPanel.setVisible(false);

        // Add everything to this
        add(radioButtonPanel);
        add(mainPanel);
        add(errorPanel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        CTRMakerPanel ctrmp = new CTRMakerPanel();
        frame.add(ctrmp);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jrbPlayer.isSelected()) {
            playerName.setVisible(true);
        } else if (jrbEnemy.isSelected()) {
            playerName.setVisible(false);
        } else if (jrbBoss.isSelected()) {
            playerName.setVisible(false);
        }
    }

}
