import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.net.URI;

public class GUIUtils {


    private static ArrayList<ImageIcon> graphicsList; 
    private static final File GRAPHICS_DIRECTORY = new File(GUIUtils.class.getResource("/Graphics").getPath());

    static{
        graphicsList = new ArrayList<ImageIcon>();
    }


    private GUIUtils(){
        throw new AssertionError();
    }

    public static CreatureFrame createGameMenu() throws Exception{

        //Declare important variables
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        CreatureList ctrList;
        CreatureFrame frame;

        //Sort creature list
        CreatureUtils.createEncounter(creatures);
        CreatureUtils.sort(creatures);
        ctrList = new CreatureList(creatures);

        //Set up Frame
        frame = new CreatureFrame(ctrList);
        frame.setTitle("Initiative Calculator");
        frame.setSize(500, 500);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    public static CreatureFrame createGameMenu(Dimension dimension, String name) throws Exception{

        //Parse graphics folder
        parseGraphics();

        //Declare important variables
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        CreatureList ctrList;
        CreatureFrame frame;

        //Sort creature list
        CreatureUtils.createEncounter(creatures);
        CreatureUtils.sort(creatures);
        ctrList = new CreatureList(creatures);

        //Set up Frame
        frame = new CreatureFrame(ctrList);
        frame.setTitle(name);
        frame.setSize(dimension);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return frame;
    }

    public static JFrame createStartMenu(Dimension dimension, String name){

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

                CreatureFrame cFrame = new CreatureFrame(new CreatureList(creatures));
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
