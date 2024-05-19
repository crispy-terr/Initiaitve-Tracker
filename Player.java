import java.io.*;

public class Player extends Creature {
    private String playerName;

    public Player()
    {
        super();
        playerName = "Blank";
        super.setSpriteFileName("0000.png");
    }

    public Player(File file) throws Exception
    {
        super(file);
        playerName = "Blank";
        super.setSpriteFileName("0000.png");
    }

    public Player(int maxHp, int roll, int[] stats)
    {
        super(maxHp, roll, stats);
        playerName = "Blank";
        super.setSpriteFileName("0000.png");
    }

    public Player(int maxHp, int roll, int[] stats, String name, String playerName)
    {
        super(maxHp, roll, stats, name);
        this.playerName = playerName;
        super.setSpriteFileName("0000.png");
    }

    public String toString()
    {
        return super.toString() + "\nPlayer Name: " + playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    public String getPlayerName()
    {
        return playerName;
    }

}
