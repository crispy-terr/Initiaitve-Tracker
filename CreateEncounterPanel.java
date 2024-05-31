import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.file.*;

public class CreateEncounterPanel extends JPanel implements ActionListener {

    // nio stuff
    private Path sourcePath;
    private Path nextEncounterFolderPath = Paths.get(CreatureUtils.NEXT_ENCOUNTER_FOLDER.getPath());

    // Gui Components
    private JPanel mainList = new JPanel();
    private JTextField jtfSearch = new JTextField("<Search>");
    private JButton jbCreateEncounter = new JButton("Create Encounter");
    private JPanel scrollSearchPanel = new JPanel();
    private JScrollPane jspCreatures;
    private GamePanel gp;
    private JLabel errorLabel = new JLabel("Please select more creatures for the battle.");

    // Logic
    private boolean actionPerformed;

    public CreateEncounterPanel(GamePanel gp) throws Exception {
        this.gp = gp;

        // Set layout for mainList and scrollSearchPanel
        mainList.setLayout(new BoxLayout(mainList, BoxLayout.Y_AXIS));
        scrollSearchPanel.setLayout(new BoxLayout(scrollSearchPanel, BoxLayout.PAGE_AXIS));

        // add action listener to button and scrollSearchPanel
        jbCreateEncounter.addActionListener(this);
        jtfSearch.addActionListener(this);
        jtfSearch.setActionCommand("Search");

        // Add all creatures to the mainList panel as ChooseCreaturePanels
        for (File n : ChooseCreaturePanel.dirList) {
            ChooseCreaturePanel ccp = new ChooseCreaturePanel();
            mainList.add(ccp);
        }
        ChooseCreaturePanel.resetIndexPointer();

        // Make the mainList panel look nice
        mainList.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        jspCreatures = new JScrollPane(mainList);
        jspCreatures.setPreferredSize(new Dimension(300, 375));

        // Change the scroll speed
        jspCreatures.getVerticalScrollBar().setUnitIncrement(10);

        // Add scroll panel stuff to scrollSearchPanel
        scrollSearchPanel.add(jtfSearch);
        scrollSearchPanel.add(jspCreatures);

        add(scrollSearchPanel);
        add(jbCreateEncounter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equalsIgnoreCase("Create Encounter")) {
            if (ChooseCreaturePanel.getNumInEncounter() > 1) {
                for (int i = 0; i < ChooseCreaturePanel.dirList.length; i++) {
                    if (mainList.getComponent(i) instanceof ChooseCreaturePanel) {
                        if (((ChooseCreaturePanel) (mainList.getComponent(i))).isInEncounter()) {

                            File creatureFile = ((ChooseCreaturePanel) (mainList.getComponent(i))).getCreature()
                                    .getFile();

                            // Move the file to the NextEncounter folder
                            sourcePath = creatureFile.toPath();
                            Path targetPath = Paths.get(nextEncounterFolderPath.toString(), creatureFile.getName());
                            try {
                                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }

                        }
                    }
                }
                setVisible(false);

                // Add a RollInitiativePanel once the creatures in the battle have been decided
                try {
                    gp.add("Roll Initiative", new RollInitiativePanel(gp));
                    gp.remove(0);
                    if (errorLabel.isVisible()) {
                        errorLabel.setVisible(false);
                    }
                } catch (Exception ee) {
                    System.err.println("Something went wrong");
                }
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Search")) {
            if (jtfSearch.getText().length() > 0) {
                unsearch();
                search(jtfSearch.getText().toLowerCase());
            } else {
                unsearch();
            }
        }
    }

    public boolean isActionPerformed() {
        return actionPerformed;
    }

    public void search(String name) {
        for (Component c : mainList.getComponents()) {
            if (c instanceof ChooseCreaturePanel) {
                if (!((ChooseCreaturePanel) c).getCreature().getName().toLowerCase().contains(name)) {
                    c.setVisible(false);
                }
            }
        }
    }

    public void unsearch() {
        for (Component c : mainList.getComponents()) {
            if (c instanceof ChooseCreaturePanel) {
                c.setVisible(true);
            }
        }
    }

}
