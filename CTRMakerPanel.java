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
    private JPanel buttonPanel = new JPanel();

    // Universal stats
    private StatsDisplay characterName = new StatsDisplay("Character Name");
    private StatsDisplay strength = new StatsDisplay("Strength", true);
    private StatsDisplay dexterity = new StatsDisplay("Dexterity", true);
    private StatsDisplay constitution = new StatsDisplay("Constitution", true);
    private StatsDisplay intelligence = new StatsDisplay("Intelligence", true);
    private StatsDisplay wisdom = new StatsDisplay("Wisdom", true);
    private StatsDisplay charisma = new StatsDisplay("Charisma", true);
    private StatsDisplay maxHP = new StatsDisplay("Max HP", true);
    private StatsDisplay armorClass = new StatsDisplay("Armor Class", true);
    private JPanel statsPanel = new JPanel();

    // Player specific stats
    private StatsDisplay playerName = new StatsDisplay("Player Name");

    // Errors
    private JPanel errorPanel = new JPanel();
    private JLabel errorLabel = new JLabel();

    //Buttons
    private JButton jbCreateCreature = new JButton("Create Creature");
    private CTRMakerBackButton bbBackToStart;

    private boolean filesCreated = false;

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
                ArrayList<String> statsList = new ArrayList<String>();
                for(Component c : statsPanel.getComponents()){
                    if(c instanceof StatsDisplay){
                        StatsDisplay sd = (StatsDisplay)c;
                        sd.checkInputForValidity();
                        boolean validInput = sd.isValid();
                        if(sd.getStatInput().getText().length() > 0 && validInput)
                            statsList.add(sd.getStatInput().getText());
                    }
                }

                int lengthTarget = 0;
                if(jrbBoss.isSelected() || jrbEnemy.isSelected()){
                    if(playerName.getStatInput().getText().length() > 0){
                        statsList.remove(statsList.size()-1);
                    }
                    
                    lengthTarget = 9;
                } else {
                    lengthTarget = 10;
                }
                    System.out.println(lengthTarget);
                    System.out.println(statsList);

                if(lengthTarget == statsList.size()){
                    if(jrbPlayer.isSelected()){
                        CreatureUtils.makeCTRFile(statsList, CreatureUtils.PLAYER, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }
                    else if(jrbEnemy.isSelected()){
                        CreatureUtils.makeCTRFile(statsList, CreatureUtils.ENEMY, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }
                    else{
                        CreatureUtils.makeCTRFile(statsList, CreatureUtils.BOSS, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }
                    filesCreated = true;

                } else {
                    errorLabel.setText("Please fill out all fields with correct values.");
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

    public CTRMakerPanel(JPanel previousPanel) {
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
                ArrayList<String> statsList = new ArrayList<String>();
                for(Component c : statsPanel.getComponents()){
                    if(c instanceof StatsDisplay){
                        StatsDisplay sd = (StatsDisplay)c;
                        sd.checkInputForValidity();
                        boolean validInput = sd.isValid();
                        if(sd.getStatInput().getText().length() > 0 && validInput)
                            statsList.add(sd.getStatInput().getText());
                    }
                }

                int lengthTarget = 0;
                if(jrbBoss.isSelected() || jrbEnemy.isSelected()){
                    if(playerName.getStatInput().getText().length() > 0){
                        statsList.remove(statsList.size()-1);
                    }
                    lengthTarget = 9;
                } else {
                    lengthTarget = 10;
                }

                if(lengthTarget == statsList.size()){
                    if(jrbPlayer.isSelected()){
                        CreatureUtils.makeCTRFile(statsList, CreatureUtils.PLAYER, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }
                    else if(jrbEnemy.isSelected()){
                        CreatureUtils.makeCTRFile(statsList, CreatureUtils.ENEMY, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }
                    else{
                        CreatureUtils.makeCTRFile(statsList, CreatureUtils.BOSS, errorPanel, errorLabel);
                        errorPanel.setVisible(false);
                    }

                } else {
                    errorLabel.setText("Please fill out all fields with correct values.");
                    errorPanel.setVisible(true);
                }
            }
        });
        bbBackToStart = new CTRMakerBackButton(previousPanel, this);
        buttonPanel.add(bbBackToStart);
        buttonPanel.add(jbCreateCreature);

        // Set up errorPanel
        errorLabel.setText("Creature could not be created.");
        errorLabel.setForeground(Color.RED);
        errorPanel.add(errorLabel);
        errorPanel.setVisible(false);

        // Add everything to this
        add(radioButtonPanel);
        add(mainPanel);
        add(buttonPanel);
        add(errorPanel);
    }

    public boolean isFilesCreated() {
        return filesCreated;
    }

    public void setFilesCreated(boolean filesCreated) {
        this.filesCreated = filesCreated;
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

    public JRadioButton getJrbPlayer() {
        return jrbPlayer;
    }

    public void setJrbPlayer(JRadioButton jrbPlayer) {
        this.jrbPlayer = jrbPlayer;
    }

    public JRadioButton getJrbEnemy() {
        return jrbEnemy;
    }

    public void setJrbEnemy(JRadioButton jrbEnemy) {
        this.jrbEnemy = jrbEnemy;
    }

    public JRadioButton getJrbBoss() {
        return jrbBoss;
    }

    public void setJrbBoss(JRadioButton jrbBoss) {
        this.jrbBoss = jrbBoss;
    }

    public ButtonGroup getTypeButtonGroup() {
        return typeButtonGroup;
    }

    public void setTypeButtonGroup(ButtonGroup typeButtonGroup) {
        this.typeButtonGroup = typeButtonGroup;
    }

    public JPanel getRadioButtonPanel() {
        return radioButtonPanel;
    }

    public void setRadioButtonPanel(JPanel radioButtonPanel) {
        this.radioButtonPanel = radioButtonPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public StatsDisplay getCharacterName() {
        return characterName;
    }

    public void setCharacterName(StatsDisplay characterName) {
        this.characterName = characterName;
    }

    public StatsDisplay getStrength() {
        return strength;
    }

    public void setStrength(StatsDisplay strength) {
        this.strength = strength;
    }

    public StatsDisplay getDexterity() {
        return dexterity;
    }

    public void setDexterity(StatsDisplay dexterity) {
        this.dexterity = dexterity;
    }

    public StatsDisplay getConstitution() {
        return constitution;
    }

    public void setConstitution(StatsDisplay constitution) {
        this.constitution = constitution;
    }

    public StatsDisplay getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(StatsDisplay intelligence) {
        this.intelligence = intelligence;
    }

    public StatsDisplay getWisdom() {
        return wisdom;
    }

    public void setWisdom(StatsDisplay wisdom) {
        this.wisdom = wisdom;
    }

    public StatsDisplay getCharisma() {
        return charisma;
    }

    public void setCharisma(StatsDisplay charisma) {
        this.charisma = charisma;
    }

    public StatsDisplay getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(StatsDisplay maxHP) {
        this.maxHP = maxHP;
    }

    public StatsDisplay getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(StatsDisplay armorClass) {
        this.armorClass = armorClass;
    }

    public JPanel getStatsPanel() {
        return statsPanel;
    }

    public void setStatsPanel(JPanel statsPanel) {
        this.statsPanel = statsPanel;
    }

    public StatsDisplay getPlayerName() {
        return playerName;
    }

    public void setPlayerName(StatsDisplay playerName) {
        this.playerName = playerName;
    }

    public JPanel getErrorPanel() {
        return errorPanel;
    }

    public void setErrorPanel(JPanel errorPanel) {
        this.errorPanel = errorPanel;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public void setErrorLabel(JLabel errorLabel) {
        this.errorLabel = errorLabel;
    }

    public JButton getJbCreateCreature() {
        return jbCreateCreature;
    }

    public void setJbCreateCreature(JButton jbCreateCreature) {
        this.jbCreateCreature = jbCreateCreature;
    }

    public CTRMakerBackButton getBbBackToStart() {
        return bbBackToStart;
    }

    public void setBbBackToStart(CTRMakerBackButton bbBackToStart) {
        this.bbBackToStart = bbBackToStart;
    }

}
