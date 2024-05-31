import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.io.File;
import java.util.*;

public class ChooseCreaturePanel extends JPanel implements ActionListener {

    // Setting up the directory, making an ArrayList to store it
    private static File dir = new File(CreatureUtils.CREATURES_FOLDER.getPath());
    static File[] dirList = dir.listFiles();
    private static ArrayList<File> creatureFiles;
    private static int indexPointer = 0;
    private static int numInEncounter = 0;

    // Gui components
    private JCheckBox jcbInEncounter = new JCheckBox();

    // Creature that this box represents
    private Creature creature;

    //True if check box is checked
    private boolean inEncounter = false;

    //True if deleted in a delete panel
    private boolean deleted = false;

    static {
        creatureFiles = new ArrayList<File>();
        for (File n : dirList) {
            creatureFiles.add(n);
        }
    }

    public ChooseCreaturePanel() throws Exception {

        //Set the layout and size of the panel and add glue to the right edge of the panel
        setLayout(new GridLayout(1,2));
        setPreferredSize(new Dimension(200, 50));
        setMaximumSize(new Dimension(1000,100));
        setMinimumSize(new Dimension(1000, 100));

        //Initialize the creature and increment the index
        creature = new Creature(creatureFiles.get(indexPointer));
        indexPointer++;
        creature = CreatureUtils.parseCTRFile(creature);

        //Give the checkbox an action listener
        jcbInEncounter.addActionListener(this);

        //Add the creature's name and a checkbox to the panel
        add(new JLabel(" " + creature.getName()));
        add(jcbInEncounter);
        setBorder(new MatteBorder(0,0,1,0, Color.GRAY));

        //Let's hope this will prevent index out of bounds errors :)
        if(indexPointer > creatureFiles.size()){
            indexPointer = 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(jcbInEncounter.isSelected()){
            inEncounter = true;
            numInEncounter++;
        }
        else{
            inEncounter = false;
            numInEncounter--;
        }
    }

    public boolean isInEncounter(){
        return inEncounter;
    }

    public Creature getCreature(){
        return creature;
    }

    public static int getNumInEncounter(){
        return numInEncounter;
    }

    public JCheckBox getCheckBox(){
        return jcbInEncounter;
    }

    public boolean isDeleted(){
        return deleted;
    }

    public void setDeleted(boolean isDeleted){
        deleted = isDeleted;
    }

    public static void resetIndexPointer(){
        indexPointer = 0;
    }

}
