import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class CreateEncounterFrame extends JFrame {

    private ArrayList<JLabel> selectionList = new ArrayList<JLabel>();
    private JPanel mainPanel = new JPanel();
    private CreatureList creatureList;
    
    public CreateEncounterFrame(){
        creatureList = new CreatureList(null);
    }

    public CreateEncounterFrame(CreatureList creatureList){
        
    }
}
