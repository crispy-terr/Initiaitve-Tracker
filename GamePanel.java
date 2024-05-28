import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JTabbedPane{
    private JPanel createEncounterPanel;
    private JPanel rollInitiativePanel;
    private JPanel diceRollerPanel;
    private JPanel initiativeTrackerPanel;

    public GamePanel() throws Exception{
        createEncounterPanel = new CreateEncounterPanel(this);
        add("Create Encounter", createEncounterPanel);
    }

    public JPanel getCreateEncounterPanel() {
        return createEncounterPanel;
    }

    public void setCreateEncounterPanel(JPanel createEncounterPanel) {
        this.createEncounterPanel = createEncounterPanel;
    }

    public JPanel getRollInitiativePanel() {
        return rollInitiativePanel;
    }

    public void setRollInitiativePanel(JPanel rollInitiativePanel) {
        this.rollInitiativePanel = rollInitiativePanel;
    }

    public JPanel getDiceRollerPanel() {
        return diceRollerPanel;
    }

    public void setDiceRollerPanel(JPanel diceRollerPanel) {
        this.diceRollerPanel = diceRollerPanel;
    }

    public JPanel getInitiativeTrackerPanel() {
        return initiativeTrackerPanel;
    }

    public void setInitiativeTrackerPanel(JPanel initiativeTrackerPanel) {
        this.initiativeTrackerPanel = initiativeTrackerPanel;
    }
}
