import java.util.ArrayList;

public class CreatureList {
    private ArrayList<Creature> creatures;
    private Creature currentCreature;
    private int totalTurns;
    private int turnPointer = 0;
    private int roundPointer = 0;

    public CreatureList(ArrayList<Creature> creatures){
        this.creatures = creatures;
        totalTurns = creatures.size();
    }

    public void cycle(){
        Creature temp = creatures.get(0);
        creatures.remove(0);
        creatures.add(temp);
        currentCreature = temp;
    }

    public void incrementTimers(){
        //Increment CreatureList
        turnPointer++;
        if(turnPointer>totalTurns){
            turnPointer=1;
            roundPointer++;
        }
        //Increment all timers
        for (Creature creature : creatures) {
            for (RoundTimer t : creature.getTimers()) {
                t.increment();
            }
        }
    }

    public String toString(){
        String x = "";
        for(Creature n : creatures){
            x+=n.getName();
        }
        return x;
    }

    public Creature getNext(){
        cycle();
        return creatures.get(0);
    }

    //Sorts a list of creatures from highest to lowest based on roll
    public void sort()
    {
        for(int i = 0; i<creatures.size()-1; i++)
        {
            for(int j = 0; j<(creatures.size()-1-i); j++)
            {
                if(creatures.get(j).getRoll()<creatures.get(j+1).getRoll())
                {
                    Creature temp = creatures.get(j);
                    creatures.set(j, creatures.get(j+1));
                    creatures.set(j+1, temp);
                }
            }
        }
    }

    public Creature getCurrentCreature(){
        return currentCreature;
    }

    public ArrayList<Creature> getCreatures(){
        return creatures;
    }

    public int getRoundCount(){
        return roundPointer;
    }

    public int getTurnCount(){
        return turnPointer;
    }

    public int getNumTurns(){
        return totalTurns;
    }

    public void setCreatures(ArrayList<Creature> creatures){
        this.creatures = creatures;
    }
}
