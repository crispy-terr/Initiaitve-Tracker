import java.io.File;

public class Enemy extends Creature {
    private int numEnemies;
    private double challengeRating;

    public Enemy()
    {
        super();
        numEnemies = 1;
        challengeRating = 0;
        super.setSpriteFileName("0001.png");
    }

    public Enemy(File file) throws Exception
    {
        super(file);
        super.setSpriteFileName("0001.png");
    }

    public int getNumEnemies() {
        return numEnemies;
    }

    public double getChallengeRating() {
        return challengeRating;
    }

    public void setNumEnemies(int numEnemies) {
        this.numEnemies = numEnemies;
    }

    public void setChallengeRating(double challengeRating) {
        this.challengeRating = challengeRating;
    }
}
