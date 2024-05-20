import java.io.File;

public class Boss extends Enemy {
    private String bossName;
    private int numLegAct;
    private boolean isGeneric; //Bosses tagged as generic will be referred to as if they're not unique; A Goblin (Generic) vs. Akra, Goblin King (Not Generic)

    //Github test comment???
    public Boss()
    {
        super();
        bossName = "Blank"; 
        numLegAct = 0;
        super.setSpriteFileName("0004.png");
    }

    public Boss(File file) throws Exception
    {
        super(file);
        bossName = "Blank";
        numLegAct = 0;
        super.setSpriteFileName("0004.png");
    }

    public void useLegAct()
    {
        numLegAct--;
    }

    public String toString()
    {
        return super.toString() + "\nBoss Name: " + bossName + "\nLegendary Actions: " + numLegAct;
    }

    public String getBossName()
    {
        return bossName;
    }

    public int getNumLegAct()
    {
        return numLegAct;
    }

    public boolean getIsGeneric()
    {
        return isGeneric;
    }

    public void setBossName(String bossName)
    {
        this.bossName = bossName;
    }

    public void setNumLegAct(int numLegAct)
    {
        this.numLegAct = numLegAct;
    }

    public void setIsGeneric(boolean isGeneric)
    {
        this.isGeneric = isGeneric;
    }

}
