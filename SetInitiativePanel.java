import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.MatteBorder;

public class SetInitiativePanel extends JPanel implements ActionListener {

    //GUI components
    private JTextField jtfRoll = new JTextField(10);
    private JLabel errorLabel = new JLabel();

    //Logic components
    private static File dir = new File(CreatureUtils.NEXT_ENCOUNTER_FOLDER.getPath());
    static File[] dirList = dir.listFiles();
    private static ArrayList<File> creatureFiles;
    private static int indexPointer = 0;
    private Creature creature;
    private int initiativeRoll = 0;

    static {
        creatureFiles = new ArrayList<File>();
        for (File n : dirList) {
            creatureFiles.add(n);
        }
    }

    public SetInitiativePanel() throws Exception{

        //Set layout and add glue
        setLayout(new GridLayout(2, 2));

        //Set the size of the panel
        setMaximumSize(new Dimension(1000, 50));
        setPreferredSize(new Dimension(200, 50));

        //Initialize the creature and increment the index
        creature = new Creature(creatureFiles.get(indexPointer));
        indexPointer++;
        creature = CreatureUtils.parseCTRFile(creature);

        //Give TextField action listener
        jtfRoll.addActionListener(this);
        jtfRoll.setText("<Init Roll>");

        //Set size for jtfRoll
        jtfRoll.setPreferredSize(new Dimension(100,25));

        //Set up error label
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        //Add the creature's name and a checkbox to the panel
        add(new JLabel(" " + creature.getName()));
        add(jtfRoll);
        add(errorLabel);
        setBorder(new MatteBorder(0,0,1,0, Color.GRAY));

        //Let's hope this will prevent index out of bounds errors :)
        if(indexPointer > creatureFiles.size()){
            indexPointer = 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            initiativeRoll = Integer.parseInt(jtfRoll.getText());

            if(errorLabel.isVisible()){
                errorLabel.setVisible(false);
            }

            CreatureUtils.writeRoll(creature, initiativeRoll);

        } catch (Exception ee){
            errorLabel.setText("Enter an integer value");
            errorLabel.setVisible(true);
        }
    }

    public static File[] getDirList(){
        return dirList;
    }

    public Creature getCreature(){
        return creature;
    }

    public JTextField getJTFRoll(){
        return jtfRoll;
    }

    public JLabel getErrorLabel(){
        return errorLabel;
    }
    
}
