import javax.swing.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        
        try{
            String[] dirs = new String[4];
            dirs[0] = CreatureUtils.CREATURES_FOLDER.getPath();
            dirs[1] = CreatureUtils.NEXT_ENCOUNTER_FOLDER.getPath();
            dirs[2] = GUIUtils.getGraphicsDirectory();
            dirs[3] = new File("docs").getPath();
            checkAndCreateDirectories(dirs);


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
            StartPanel sp = new StartPanel();

            JFrame frame = new JFrame("Initiative Tracker");
            frame.setIconImage(new ImageIcon(GUIUtils.getGraphicsDirectory() + "/Logo.png").getImage());
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(sp);
            frame.setVisible(true);
            
        } catch (Exception e){
            JFrame frame = new JFrame("Initiative Tracker");
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER));
            
            JLabel error = new JLabel("Something went wrong.");
            error.setForeground(Color.RED);
            frame.add(error);

            frame.setVisible(true);
        }

    }

    private static void moveFilesOnShutdown() throws Exception {
        CreatureUtils.moveToCreaturesFolder();
    }

    private static void checkAndCreateDirectories(String[] dirs){
        for(String dirPath : dirs){
            File dir = new File(dirPath);
            if(!dir.exists()){
                if(dir.mkdirs()){
                    System.out.println("Directory created: " + dirPath);
                } else {
                    System.err.println("Failed to create directory: " + dirPath);
                }
            } else {
                System.out.println("Directory already exists: " + dirPath);
            }
        }
    }
}