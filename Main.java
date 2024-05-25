import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                moveFilesOnShutdown();
            } catch (IOException e) {
                System.err.println("Failed to move files on shutdown: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        GUIUtils.parseGraphics();
        GamePanel gp = new GamePanel();

        JFrame frame = new JFrame("Initiative Tracker");
        frame.setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/Logo.png")).getImage());
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(gp);
        frame.setVisible(true);

    }

    private static void moveFilesOnShutdown() throws Exception {
        CreatureUtils.moveToCreaturesFolder();
    }
}
