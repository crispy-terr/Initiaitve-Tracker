import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.nio.*;
import java.nio.file.*;

public class CreatureUtils {

    static final File NEXT_ENCOUNTER_FOLDER;
    static final File CREATURES_FOLDER;

    static {
        NEXT_ENCOUNTER_FOLDER = new File("NextEncounter");
        CREATURES_FOLDER = new File("Creatures");
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
        // ArrayList<Creature> creatures = new ArrayList<Creature>();
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
            for (int i = 1; i < xList.size(); i++) {
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
}