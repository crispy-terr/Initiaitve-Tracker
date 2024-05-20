import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.net.URI;
import java.security.spec.ECFieldF2m;

public class GUIUtils {


    private static ArrayList<ImageIcon> graphicsList; 
    private static final File GRAPHICS_DIRECTORY = new File(GUIUtils.class.getResource("/Graphics").getPath());

    static{
        graphicsList = new ArrayList<ImageIcon>();
    }


    private GUIUtils(){
        throw new AssertionError();
    }

    public static InitiativeTrackerPanel createGameMenu() throws Exception{

        //Declare important variables
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        CreatureList ctrList;
        InitiativeTrackerPanel frame;

        //Sort creature list
        CreatureUtils.createEncounter(creatures);
        CreatureUtils.sort(creatures);
        ctrList = new CreatureList(creatures);

        //Set up Frame
        frame = new InitiativeTrackerPanel(ctrList);
        frame.setSize(500, 500);
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

        return frame;
    }

    public static InitiativeTrackerPanel createGameMenu(String name) throws Exception{

        //Parse graphics folder
        parseGraphics();

        //Declare important variables
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        CreatureList ctrList;
        InitiativeTrackerPanel frame;

        //Sort creature list
        CreatureUtils.createEncounter(creatures);
        CreatureUtils.sort(creatures);
        ctrList = new CreatureList(creatures);

        //Set up Frame
        frame = new InitiativeTrackerPanel(ctrList);
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

        return frame;
    }

    public static JFrame createStartMenuOld(Dimension dimension, String name){

        //frame that everything is placed on
        JFrame frame = new JFrame(name);
        frame.setSize(dimension);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Panel that everything is placed on
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setBorder(new EmptyBorder(new Insets(50, 50, 50, 50)));

        //Start Encounter button
        JButton jbStartEncounter = new JButton("Start Encounter");
        jbStartEncounter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae){
                ArrayList<Creature> creatures = new ArrayList<Creature>();

                try{
                    CreatureUtils.createEncounter(creatures);
                }catch (Exception e){
                    System.out.println("Something went wrong. Error 0001.");
                }

                InitiativeTrackerPanel cFrame = new InitiativeTrackerPanel(new CreatureList(creatures));
                cFrame.setSize(dimension);
                frame.setVisible(false);
                cFrame.setVisible(true);
            }
        });
        contentPanel.add(jbStartEncounter);
        contentPanel.add(Box.createRigidArea(new Dimension(5,0)));

        //Create Encounter button
        JButton jbCreateEncounter = new JButton("Create Encounter");
        contentPanel.add(jbCreateEncounter);
        contentPanel.add(Box.createRigidArea(new Dimension(5,0)));

        //Character settings button
        

        frame.add(contentPanel);

        frame.setVisible(true);


        return frame;
    }

    public static JTabbedPane createTabbedPane() throws Exception{

        GUIUtils.parseGraphics();
        
        JTabbedPane jtp = new JTabbedPane();
        jtp.setBorder(new EmptyBorder(10,0,10,0));

        //Create Encounter page
        JPanel page1 = new JPanel();
        page1.add(new JLabel("Create Encounter"));

        //Dice Roller page
        JPanel page2 = new JPanel();
        page2.setLayout(new BoxLayout(page2, BoxLayout.PAGE_AXIS));

        GridLayout gl = new GridLayout(4, 2);
        gl.setVgap(10);
        gl.setHgap(30);

        DiceRollerPanel drp = new DiceRollerPanel(gl);

        JPanel rollPanel = drp.getRollPanel();

        page2.setBorder(new EmptyBorder(10, 50, 10, 50));

        page2.add(drp);
        page2.add(rollPanel);
        page2.add(drp.getBonusPanel());
        page2.add(drp.getErrorPanel());

        //Initiative Tracker page
        JPanel page3 = GUIUtils.createGameMenu();

        jtp.add("Create Encounter", page1);
        jtp.add("Dice Roller", page2);
        jtp.add("Initiative Tracker", page3);

        return jtp;
    }

    public static void parseGraphics(){
        try{
            URI uri = GUIUtils.class.getResource("/Graphics").toURI();
            File dir = new File(uri);
            for(File f : dir.listFiles()){
                graphicsList.add(new ImageIcon(f.getPath()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageIcon> getGraphicsList(){
        return graphicsList;
    }

    public static String getGraphicsDirectory(){
        return GRAPHICS_DIRECTORY.getPath();
    }



}
