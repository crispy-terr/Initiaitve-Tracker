import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Creature{
    private int maxHp;
    private int currentHp;
    private int tempHp = 0;
    private int roll = 0;
    private int armorClass = 0;
    private int[] stats = new int[6];
    private File file;
    private boolean hasFile;
    private String name;
    private ArrayList<RoundTimer> timers = new ArrayList<RoundTimer>();

    //Google GUI
    private JPanel showTimerPanel = new JPanel();
    private CreatureSprite sprite;
    private CreatureSprite miniSprite;
    private JLabel guiDisplay = new JLabel();
    private String spriteFileName;

    public Creature()
    {
        maxHp = 0;
        currentHp = 0;
        tempHp = 0;
        roll = -1;
        stats = new int[]{0,0,0,0,0,0};
        hasFile = false;
        name = "Blank";
        sprite = new CreatureSprite();
    }

    public Creature(File file) throws Exception
    {
        this.file = file;
        hasFile = true;
        showTimerPanel.setLayout(new BoxLayout(showTimerPanel, BoxLayout.Y_AXIS));
        showTimerPanel.setSize(new Dimension(100,200));
        sprite = new CreatureSprite();
    }

    public Creature(int maxHp, int roll, int[] stats)
    {
        this.maxHp = maxHp;
        currentHp = maxHp;
        this.roll = roll;
        this.stats = stats;
        hasFile = false;
        sprite = new CreatureSprite();
    }

    public Creature(int maxHp, int roll, int[] stats, String name)
    {
        this.maxHp = maxHp;
        currentHp = maxHp;
        this.roll = roll;
        this.stats = stats;
        this.name = name;
        hasFile = false;
        sprite = new CreatureSprite();
    }

    public void createShowTimerPanel(){
        
        for (RoundTimer t : timers) {
            showTimerPanel.add(new JLabel(t.guiDisplay()));
        }
    }

    public void printTimers(){
        for(RoundTimer t : timers){
            System.out.println(t+"\n");
        }
    }

    public void addTimer(RoundTimer t){
        timers.add(t);
    }

    public void addTimerArray(RoundTimer[] timerArr){
        for(RoundTimer t : timerArr){
            timers.add(t);
        }
    }

    public void addTimerArray(ArrayList<RoundTimer> timerList){
        for(RoundTimer t : timerList){
            timers.add(t);
        }
    }

    public void addTempHp(int tempHp)
    {
        this.tempHp+=tempHp;
    }

    public void heal(int heal)
    {
        currentHp+=heal;
    }

    public void takeDamage(int damage)
    {
        currentHp-=damage;
    }

    public String toString()
    {
        String x = "";
        for (int i = 0; i<stats.length; i++) {
            if(i==stats.length-1)
            {
                x+=stats[i];
            }
            else{
                x+=stats[i]+", ";
            }
        }

        return "Name: " + name + "\nHP: "+ currentHp + " / " + maxHp +"\nAC: " + armorClass + "\nRoll: " + roll + "\nStats: " + x;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getTempHp() {
        return tempHp;
    }

    public int getRoll() {
        return roll;
    }

    public int[] getStats() {
        return stats;
    }

    public File getFile() {
        return file;
    }

    public boolean getHasFile() {
        return hasFile;
    }

    public String getName() {
        return name;
    }

    public int getArmorClass(){
        return armorClass;
    }

    public ArrayList<RoundTimer> getTimers(){
        return timers;
    }

    public JPanel getShowTimerPanel(){
        showTimerPanel.removeAll();
        createShowTimerPanel();
        return showTimerPanel;
    }

    public CreatureSprite getSprite(){
        return sprite;
    }

    public CreatureSprite getMiniSprite(){
        return miniSprite;
    }

    public JLabel getGUIDisplay(){
        return guiDisplay;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public void setTempHp(int tempHp) {
        this.tempHp = tempHp;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

    public void setName(String name) {
        this.name = name;
    }  

    public void setArmorClass(int armorClass){
        this.armorClass = armorClass;
    }

    public void setTimers(ArrayList<RoundTimer> timers){
        this.timers = timers;
    }

    public void setSprite(CreatureSprite sprite){
        this.sprite = sprite;
    }

    public void setMiniSprite(CreatureSprite miniSprite){
        this.miniSprite = miniSprite;
    }

    public String getSpriteFileName() {
        return spriteFileName;
    }

    public void setSpriteFileName(String spriteFileName) {
        this.spriteFileName = spriteFileName;
    }
}