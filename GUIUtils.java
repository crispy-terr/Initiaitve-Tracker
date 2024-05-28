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

    static {
        graphicsList = new ArrayList<ImageIcon>();
    }

    private GUIUtils() {
        throw new AssertionError();
    }

    public static InitiativeTrackerPanel createGameMenu() throws Exception {

        // Declare important variables
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        CreatureList ctrList;
        InitiativeTrackerPanel frame;

        // Sort creature list
        CreatureUtils.createEncounter(creatures);
        CreatureUtils.sort(creatures);
        ctrList = new CreatureList(creatures);

        // Set up Frame
        frame = new InitiativeTrackerPanel(ctrList);
        frame.setSize(500, 500);
        frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));

        return frame;
    }

    public static InitiativeTrackerPanel createGameMenu(String name) throws Exception {

        // Parse graphics folder
        parseGraphics();

        // Declare important variables
        ArrayList<Creature> creatures = new ArrayList<Creature>();
        CreatureList ctrList;
        InitiativeTrackerPanel panel;

        // Sort creature list
        CreatureUtils.createEncounter(creatures);
        CreatureUtils.sort(creatures);
        ctrList = new CreatureList(creatures);

        // Set up Frame
        panel = new InitiativeTrackerPanel(ctrList);
        panel.setLayout(new GridLayout(1, 4));

        return panel;
    }

    public static JTabbedPane createTabbedPane() throws Exception {

        GUIUtils.parseGraphics();

        JTabbedPane jtp = new JTabbedPane();
        jtp.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Create Encounter page
        JPanel page1 = new JPanel();
        // page1.add(new CreateEncounterPanel());

        // Roll Initiative Page
        JPanel page4 = new JPanel();
        // page4.add(new RollInitiativePanel());

        // Dice Roller page
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

        // Initiative Tracker page
        JPanel page3 = GUIUtils.createGameMenu();

        jtp.add("Create Encounter", page1);
        jtp.add("Roll Initiative", page4);
        jtp.add("Dice Roller", page2);
        jtp.add("Initiative Tracker", page3);

        return jtp;
    }

    public static JPanel createDiceRollPanel() {
        JPanel page = new JPanel();
        page.setLayout(new BoxLayout(page, BoxLayout.PAGE_AXIS));
        page.setBorder(new EmptyBorder(10, 50, 10, 50));

        GridLayout gl = new GridLayout(4, 2);
        gl.setVgap(10);
        gl.setHgap(30);

        DiceRollerPanel drp = new DiceRollerPanel(gl);

        page.add(drp);
        page.add(drp.getRollPanel());
        page.add(drp.getBonusPanel());
        page.add(drp.getErrorPanel());

        return page;
    }

    public static void parseGraphics() {
        try {
            URI uri = GUIUtils.class.getResource("/Graphics").toURI();
            File dir = new File(uri);
            for (File f : dir.listFiles()) {
                graphicsList.add(new ImageIcon(f.getPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JFrame makeErrorMessage(String errorString, String errorTitle){
        JFrame frame = new JFrame(errorTitle);
        frame.setSize(300,100);
        JLabel errorLabel = new JLabel(errorString);
        frame.add(errorLabel);
        frame.setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/Logo.png")).getImage());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    public static ArrayList<ImageIcon> getGraphicsList() {
        return graphicsList;
    }

    public static String getGraphicsDirectory() {
        return GRAPHICS_DIRECTORY.getPath();
    }

}
