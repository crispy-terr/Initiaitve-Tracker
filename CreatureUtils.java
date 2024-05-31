import java.io.*;
import java.util.*;
import java.util.concurrent.Flow;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.nio.*;
import java.nio.file.*;

public class CreatureUtils {

    static final File NEXT_ENCOUNTER_FOLDER;
    static final File CREATURES_FOLDER;
    static final int PLAYER;
    static final int ENEMY;
    static final int BOSS;

    static {
        NEXT_ENCOUNTER_FOLDER = new File("NextEncounter");
        CREATURES_FOLDER = new File("Creatures");
        PLAYER = 0;
        ENEMY = 1;
        BOSS = 2;
    }

    private CreatureUtils() {
        throw new AssertionError();
    }

    public static void display(Creature creature, int xPos, int yPos, Graphics g) {
        String x = creature.toString();
        String[] xArr = x.split("\n");

        for (int i = 0; i < xArr.length; i++) {
            g.drawString(xArr[i], xPos, yPos + ((20 * i)));
        }
    }

    public static void printCreature(Creature creature) {
        System.out.println(creature);
    }

    public static Creature nextCreature(ArrayList<Creature> creatures) {
        Creature temp = creatures.get(0);
        creatures.remove(0);
        creatures.add(creatures.size() - 1, temp);
        return temp;
    }

    // Goes through all player files in directory and adds them to a CreatureList
    public static void createEncounter(ArrayList<Creature> creatures) throws Exception {
        File dir = new File(NEXT_ENCOUNTER_FOLDER.getPath());
        File[] directoryListing = dir.listFiles();

        int creatureNum = 0;
        for (File n : directoryListing) {
            creatureNum++;
        }

        Creature[] ctrArr = new Creature[creatureNum];
        for (int i = 0; i < creatureNum; i++) {
            File file = new File(directoryListing[i].toString());
            Scanner sc = new Scanner(file);
            String nextLine = sc.nextLine();
            if (nextLine.equals("[PLAYER]")) {
                Player temp = new Player(file);
                parsePlayerFile(temp);
                ctrArr[i] = temp;
            } else if (nextLine.equals("[ENEMY]")) {
                Enemy temp = new Enemy(file);
                parseEnemyFile(temp);
                ctrArr[i] = temp;
            } else if (nextLine.equals("[BOSS]")) {
                Boss temp = new Boss(file);
                parseBossFile(temp);
                ctrArr[i] = temp;
            } else {
                System.out.println("Error: Not a compatible file type; Failed to create a creature.");
            }
            sc.close();
        }
        for (Creature creature : ctrArr) {
            creatures.add(creature);
        }
    }

    public static Creature[] createEncounter() throws Exception {
        File dir = new File(NEXT_ENCOUNTER_FOLDER.getPath());
        File[] directoryListing = dir.listFiles();

        int creatureNum = 0;
        for (File n : directoryListing) {
            creatureNum++;
        }

        Creature[] ctrArr = new Creature[creatureNum];
        for (int i = 0; i < creatureNum; i++) {
            File file = new File(directoryListing[i].toString());
            Scanner sc = new Scanner(file);
            if (sc.nextLine().equals("[PLAYER]")) {
                Player temp = new Player(file);
                parsePlayerFile(temp);
                ctrArr[i] = temp;
            } else if (sc.nextLine().equals("[ENEMY]")) {
                Enemy temp = new Enemy(file);
                parseEnemyFile(temp);
                ctrArr[i] = temp;
            } else if (sc.nextLine().equals("[BOSS]")) {
                Boss temp = new Boss(file);
                parseBossFile(temp);
                ctrArr[i] = temp;
            } else {
                System.out.println("ERROR: Not a compatible file type; Failed to create a creature.");
            }
            sc.close();
        }
        return ctrArr;
    }

    public static void cycle(ArrayList<Creature> creatures, JFrame frame) {
        for (int i = 0; i < creatures.size(); i++) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Current Turn: " + creatures.get(i).getName());
            String x = sc.nextLine();

            if (x.equalsIgnoreCase("exit") || x.equalsIgnoreCase("quit")) {
                System.exit(0);
            } else if (x.equalsIgnoreCase("timer") || x.equalsIgnoreCase("timers")) {
                RoundTimer.runTimerLogic(creatures.get(i), creatures);
                if (i == 0)
                    i = creatures.size() - 1;
                else
                    i--;
            } else if (x.equalsIgnoreCase("show timers") || x.equalsIgnoreCase("show timer")) {
                JPanel p = creatures.get(i).getShowTimerPanel();
                frame.add(p);
                frame.revalidate();
            }
            sc.close();

            if (creatures.get(i).getTimers().size() > 0) {
                for (RoundTimer t : creatures.get(i).getTimers()) {
                    t.increment();
                }
            }
        }
    }

    // Sorts a list of creatures based on roll
    public static void sort(ArrayList<Creature> creatures) {
        for (int i = 0; i < creatures.size() - 1; i++) {
            for (int j = 0; j < (creatures.size() - 1 - i); j++) {
                if (creatures.get(j).getRoll() < creatures.get(j + 1).getRoll()) {
                    Creature temp = creatures.get(j);
                    creatures.set(j, creatures.get(j + 1));
                    creatures.set(j + 1, temp);
                }
            }
        }
    }

    public static void parseBossFile(Boss boss) throws Exception // ugh don't wanna do this rn
    {
        if (boss.getHasFile()) {
            File file = boss.getFile();
            String x = "";
            Scanner sc = new Scanner(file);
            boolean hasLooped = false;
            while (sc.hasNextLine()) {
                if (hasLooped) {
                    x += "\n" + sc.nextLine();
                } else {
                    x += sc.nextLine();
                    hasLooped = true;
                }
            }
            sc.close();

            String[] xArr = x.split("\n");
            ArrayList<String> xList = new ArrayList<String>();
            for (String n : xArr) {
                xList.add(n);
            }

            int[] newStats = new int[6];
            for (int i = 1; i < xList.size(); i++) {
                String nextLine = xList.get(i).substring(xList.get(i).indexOf(" ") + 1);
                if (i == 1) {
                    boss.setName(nextLine);
                } else if (i == 2) {
                    boss.setBossName(nextLine);
                } else if (i == 3) {
                    boss.setRoll(Integer.parseInt(nextLine));
                } else if (i == 4) {
                    boss.setMaxHp(Integer.parseInt(nextLine));
                    boss.setCurrentHp(Integer.parseInt(nextLine));
                } else if (i == 5) {
                    boss.setNumEnemies(Integer.parseInt(nextLine));
                } else if (i == 6) {
                    boss.setChallengeRating(Double.parseDouble(nextLine));
                } else if (i == 7) {
                    boss.setNumLegAct(Integer.parseInt(nextLine));
                } else if (i == 8) {
                    boss.setIsGeneric(Boolean.parseBoolean(nextLine));
                } else if (i == 9) {
                    boss.setArmorClass(Integer.parseInt(nextLine));
                } else {
                    newStats[i - 10] = Integer.parseInt(nextLine);
                }
            }
            boss.setStats(newStats);
            boss.setSprite(new CreatureSprite(boss));

        } else {
            System.out.println("Enemy does not have a file.");
        }
    }

    public static void parseEnemyFile(Enemy enemy) throws Exception {
        if (enemy.getHasFile()) {
            File file = enemy.getFile();
            String x = "";
            Scanner sc = new Scanner(file);
            boolean hasLooped = false;
            while (sc.hasNextLine()) {
                if (hasLooped) {
                    x += "\n" + sc.nextLine();
                } else {
                    x += sc.nextLine();
                    hasLooped = true;
                }
            }
            sc.close();

            String[] xArr = x.split("\n");
            ArrayList<String> xList = new ArrayList<String>();
            for (String n : xArr) {
                xList.add(n);
            }

            int[] newStats = new int[6];
            for (int i = 1; i < xList.size(); i++) {
                String nextLine = xList.get(i).substring(xList.get(i).indexOf(" ") + 1);
                if (i == 1) {
                    enemy.setName(nextLine);
                } else if (i == 2) {
                    enemy.setRoll(Integer.parseInt(nextLine));
                } else if (i == 3) {
                    enemy.setMaxHp(Integer.parseInt(nextLine));
                    enemy.setCurrentHp(Integer.parseInt(nextLine));
                } else if (i == 4) {
                    enemy.setNumEnemies(Integer.parseInt(nextLine));
                } else if (i == 5) {
                    enemy.setChallengeRating(Double.parseDouble(nextLine));
                } else if (i == 6) {
                    enemy.setArmorClass(Integer.parseInt(nextLine));
                } else {
                    newStats[i - 7] = Integer.parseInt(nextLine);
                }
            }
            enemy.setStats(newStats);
            enemy.setSprite(new CreatureSprite(enemy));

        } else {
            System.out.println("Enemy does not have a file.");
        }
    }

    public static Creature parseCTRFile(Creature creature) throws Exception {
        File ctrFile = creature.getFile();
        Scanner sc = new Scanner(ctrFile);
        String typeDecider = sc.nextLine();

        if (typeDecider.equals("[PLAYER]")) {
            creature = new Player(ctrFile);
            parsePlayerFile((Player) creature);
        } else if (typeDecider.equals("[ENEMY]")) {
            creature = new Enemy(ctrFile);
            parseEnemyFile((Enemy) creature);
        } else if (typeDecider.equals("[BOSS]")) {
            creature = new Boss(ctrFile);
            parseBossFile((Boss) creature);
        } else {
            System.err.println("Incorrect file format.");
        }
        sc.close();
        return creature;
    }

    public static void parsePlayerFile(Player player) throws Exception {
        if (player.getHasFile()) {
            File file = player.getFile();
            String x = "";
            Scanner sc = new Scanner(file);
            boolean hasLooped = false;
            while (sc.hasNextLine()) {
                if (hasLooped) {
                    x += "\n" + sc.nextLine();
                } else {
                    x += sc.nextLine();
                    hasLooped = true;
                }
            }
            sc.close();

            String[] xArr = x.split("\n");
            ArrayList<String> xList = new ArrayList<String>();
            for (String n : xArr) {
                xList.add(n);
            }

            int[] newStats = new int[6];
            for (int i = 1; i < xList.size()-1; i++) {
                String nextLine = xList.get(i).substring(xList.get(i).indexOf(" ") + 1);
                if (i == 1) {
                    player.setName(nextLine);
                } else if (i == 2) {
                    player.setPlayerName(nextLine);
                } else if (i == 3) {
                    player.setRoll(Integer.parseInt(nextLine));
                } else if (i == 4) {
                    player.setMaxHp(Integer.parseInt(nextLine));
                    player.setCurrentHp(Integer.parseInt(nextLine));
                } else if (i == 5) {
                    player.setArmorClass(Integer.parseInt(nextLine));
                } else {
                    newStats[i - 6] = Integer.parseInt(nextLine);
                }
            }
            player.setSprite(new CreatureSprite(player));
            player.setStats(newStats);
        } else {
            System.out.println("Player does not have a file.");
        }

    }

    public static void writeRoll(Creature creature, int roll) {
        if (creature.getHasFile()) {

            File creatureFile = creature.getFile();
            ArrayList<String> fileList = new ArrayList<>();

            // Convert file to string array
            try (Scanner sc = new Scanner(creatureFile)) {
                while (sc.hasNextLine()) {
                    fileList.add(sc.nextLine());
                }
            } catch (Exception e) {
                System.err.println("Something went wrong while reading the file");
                e.printStackTrace();
                return;
            }

            // Replace old roll with new roll
            for (int i = 0; i < fileList.size(); i++) {
                if (fileList.get(i).contains("Roll:")) {
                    fileList.set(i, "Roll: " + roll);
                }
            }

            // Write to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(creatureFile))) {
                for (String line : fileList) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println("Something went wrong while writing to the file");
                e.printStackTrace();
            }
        }
    }

    public static void moveToCreaturesFolder() {
        File dir = new File(NEXT_ENCOUNTER_FOLDER.getPath());
        File[] directoryListing = dir.listFiles();

        for (File file : directoryListing) {
            Path sourcePath = Paths.get(NEXT_ENCOUNTER_FOLDER.getPath(), file.getName());
            Path targetPath = Paths.get(CREATURES_FOLDER.getPath(), file.getName());

            try {
                Files.move(sourcePath, targetPath);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public static void moveToNextEncounterFolder(File file) {
        Path sourcePath = Paths.get(file.getPath());
        Path targetPath = Paths.get(NEXT_ENCOUNTER_FOLDER.getPath(), file.getName());

        try {
            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
    }

    public static void makeCTRFile(ArrayList<String> statsList, int type, JPanel errorPanel, JLabel errorLabel){
        String fileName = statsList.get(0).trim();

        //Removes spaces if the character name contains spaces
        if(fileName.contains(" ")){
            String[] fileNameArr = fileName.split(" ");
            fileName = "";
            for(String n : fileNameArr){
                fileName += n;
            }
        }
        fileName += ".ctr";

        Path newFilePath = Paths.get(CREATURES_FOLDER.getPath(), fileName);

        // Create the file
        try{
            Files.createFile(newFilePath);
        } catch (IOException ioe){
            JFrame frame = GUIUtils.makeErrorMessage("The file " + fileName + " Already Exists. Delete?", "File Already Exists");
            JButton delete = new JButton("Delete File");
            delete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    try{
                        Files.delete(newFilePath);
                        delete.setEnabled(false);
                    } catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }
            });
            frame.setLayout(new GridLayout(2,1));
            frame.add(delete);
        }

        writeCTRFile(new File(newFilePath.toString()), statsList, type);
    }

    public static void writeCTRFile(File file, ArrayList<String> statsList, int type){
        ArrayList<String> finList = new ArrayList<String>();

        //Deciding what type of ctr the file should be
        if(type == PLAYER){
            finList.add(0, "[PLAYER]");
            finList.add(1, "Name: " + statsList.get(0));
            finList.add(2, "PlayerName: " + statsList.get(statsList.size()-1));
            finList.add(3, "Roll: 1");
            finList.add(4, "maxHP: " + statsList.get(1));
            finList.add(5, "AC: " + statsList.get(2));
            for(int i = 0; i<6; i++){
                switch(i){
                    case 0:
                        finList.add(6+i, "Str: " + statsList.get(3+i));
                        break;
                    case 1:
                        finList.add(6+i, "Dex: " + statsList.get(3+i));
                        break;
                    case 2:
                        finList.add(6+i, "Con: " + statsList.get(3+i));
                        break;
                    case 3:
                        finList.add(6+i, "Int: " + statsList.get(3+i));
                        break;
                    case 4:
                        finList.add(6+i, "Wis: " + statsList.get(3+i));
                        break;
                    case 5:
                        finList.add(6+i, "Cha: " + statsList.get(3+i));
                        break;
                }
            }
            finList.add("Sprite: 0000");
        } else if(type == ENEMY){
            finList.add(0, "[ENEMY]");
            finList.add(1, "Name: " + statsList.get(0));
            finList.add(2, "Roll: 1");
            finList.add(3, "maxHP: " + statsList.get(1));
            finList.add(4, "numEnemies: 1");
            finList.add(5, "CR: 0.0");
            finList.add(6, "AC: " + statsList.get(2));
            finList.add(7, "Str: " + statsList.get(3));
            finList.add(8, "Dex: " + statsList.get(4));
            finList.add(9, "Con: " + statsList.get(5));
            finList.add(10, "Int: " + statsList.get(6));
            finList.add(11, "Wis: " + statsList.get(7));
            finList.add(12, "Cha: " + statsList.get(8));

        } else if(type == BOSS){
            finList.add(0, "[BOSS]");
            finList.add(1, "Name: " + statsList.get(0));
            finList.add(2, "Boss Name: "+ statsList.get(0));
            finList.add(3, "Roll: 1");
            finList.add(4, "maxHP: " + statsList.get(1));
            finList.add(5, "numEnemies: 1");
            finList.add(6, "CR: 0.0");
            finList.add(7, "numLegendaryActions: 3");
            finList.add(8, "generic: true");
            finList.add(9, "AC: " + statsList.get(2));
            finList.add(10, "Str: " + statsList.get(3));
            finList.add(11, "Dex: " + statsList.get(4));
            finList.add(12, "Con: " + statsList.get(5));
            finList.add(13, "Int: " + statsList.get(6));
            finList.add(14, "Wis: " + statsList.get(7));
            finList.add(15, "Cha: " + statsList.get(8));
        }

        //Write the list to the file
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for(String n : finList){
                writer.write(n);
                writer.newLine();
            }

            //Success message
            JFrame frame = new JFrame("File created!");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300,100);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER));
            frame.add(new JLabel(statsList.get(0) + " has been created!"));
            frame.setLocationRelativeTo(null);
            frame.setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/Logo.png")).getImage());
            frame.setVisible(true);

        } catch (IOException ioe){
            System.err.println("Something went wrong");
        }
    }

    //returns true if there are 2+ files in creatures folder
    public static boolean checkCreaturesFolderNumFiles(){
        boolean creaturesFolderIsGood = false;
        File dir = new File(CREATURES_FOLDER.getPath());
        File[] directoryListing = dir.listFiles();

        if(directoryListing.length >= 2){
            creaturesFolderIsGood = true;
        }

        return creaturesFolderIsGood;
    }
}