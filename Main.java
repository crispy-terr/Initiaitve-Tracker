import javax.swing.*;

public class Main {
    static InitiativeTrackerPanel panel;

    public static void main(String[] args) throws Exception {
        JTabbedPane jtp = GUIUtils.createTabbedPane();
        jtp.setVisible(true);

        JFrame frame = new JFrame("Initiative Tracker");
        frame.setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/Logo.png")).getImage());
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(jtp);
        frame.setVisible(true);
    }
}
