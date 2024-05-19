import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.util.Iterator;
import java.awt.*;

public class RoundTimer{
    private int numRounds;
    private int numTurns;
    private int numPlayers;
    private int turnCount = 0;
    private int roundCount = 0;
    private boolean running = true;
    private String name;
    private ArrayList<Creature> creatures = new ArrayList<Creature>();

    public RoundTimer(){
        numRounds = 0;
        numTurns = 0;
        name = "Blank Timer";
    }

    public RoundTimer(String name, int numRounds){
        this.name = name;
        this.numRounds = numRounds;
        numTurns = numRounds*numPlayers;
    }

    public RoundTimer(String name, int numRounds, int numPlayers){
        this.name = name;
        this.numRounds = numRounds;
        this.numPlayers = numPlayers;
        numTurns = numRounds*numPlayers;
    }

    public RoundTimer(String name, int numRounds, ArrayList<Creature> creatures){
        this.name = name;
        this.numRounds = numRounds;
        numPlayers = creatures.size();
        numTurns = numRounds*numPlayers;
        this.creatures = creatures;
    }

    public void increment(){
        turnCount++;
        if(turnCount % numPlayers == 0){
            roundCount++;
        }
        if(turnCount==numTurns-1){
            running = false;
        }
    }

    public String guiDisplay(){
        return name + ": " + roundCount + "/" + numRounds;
    }

    public String toString(){
        return name+"\n"+roundCount+"/"+numRounds;
    }

    public static void cleanTimerList(ArrayList<RoundTimer> timers){
        Iterator<RoundTimer> iterator = timers.iterator();
        while (iterator.hasNext()) {
            RoundTimer timer = iterator.next();
            if (!timer.getRunning()) {
                iterator.remove();
                
                JFrame frame = new JFrame("Time's Up!");
                frame.setSize(300,100);
                frame.setVisible(true);
                frame.setLayout(new FlowLayout(FlowLayout.CENTER));
                frame.setLocationRelativeTo(null);
                frame.add(new JLabel("Timer: " + timer.getName() + " has expired!"), BorderLayout.CENTER);
                frame.setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/D20_icon.png")).getImage());
                
            }
        }
    }

    public static void cleanAllTimerLists(ArrayList<Creature> creatures){
        for(Creature c : creatures){
            cleanTimerList(c.getTimers());
        }
    }

    public static void runTimerLogic(Creature creature, ArrayList<Creature> creatures){
        Scanner input = new Scanner(System.in);
        System.out.println("Name of Timer: ");
        String timerName = input.nextLine();
        System.out.println("Number of Rounds: ");
        int numRounds = input.nextInt();
        System.out.println();
        RoundTimer temp = new RoundTimer(timerName, numRounds, creatures);
        creature.getTimers().add(temp);
    }

    public int getNumRounds(){
        return numRounds;
    }

    public int getNumTurns(){
        return numTurns;
    }

    public String getName(){
        return name;
    }

    public boolean getRunning(){
        return running;
    }

    public void setNumRounds(int numRounds){
        this.numRounds = numRounds;
    }

    public void setNumTurns(int numTurns){
        this.numTurns = numTurns;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}
