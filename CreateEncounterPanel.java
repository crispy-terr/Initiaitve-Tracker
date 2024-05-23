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
    private JButton jbCreateEncounter = new JButton("Create Encounter");
    private JScrollPane jspCreatures;

    public CreateEncounterPanel() throws Exception {
        // Set layout for instance and mainList
        setLayout(new GridLayout(1, 2));
        mainList.setLayout(new BoxLayout(mainList, BoxLayout.Y_AXIS));

        // add action listener to button
        jbCreateEncounter.addActionListener(this);

        // Add all creatures to the mainList panel as ChooseCreaturePanels
        for (File n : ChooseCreaturePanel.dirList) {
            ChooseCreaturePanel ccp = new ChooseCreaturePanel();
            mainList.add(ccp);
        }

        // Make the mainList panel look nice
        mainList.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        jspCreatures = new JScrollPane(mainList);

        add(jspCreatures);
        add(jbCreateEncounter);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<File> creaturesInEncounter = new ArrayList<File>();

        for (int i = 0; i < ChooseCreaturePanel.dirList.length; i++) {
            if (mainList.getComponent(i) instanceof ChooseCreaturePanel) {
                if (((ChooseCreaturePanel) (mainList.getComponent(i))).isInEncounter()) {

                    File creatureFile = ((ChooseCreaturePanel) (mainList.getComponent(i))).getCreature().getFile();

                    // Move the file to the NextEncounter folder

                    sourcePath = creatureFile.toPath();
                    Path targetPath = Paths.get(nextEncounterFolderPath.toString(), creatureFile.getName());
                    try {
                        Files.move(sourcePath, targetPath);
                        System.out.println("File moved successfully");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }

                }
            }
        }

        setVisible(false);
    }

    public static void main(String[] args) throws Exception {
        CreateEncounterPanel cep = new CreateEncounterPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(1, 2));
        frame.add(cep);

        frame.setVisible(true);
    }

}
