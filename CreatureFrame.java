import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CreatureFrame extends JFrame{

    private CreatureList creatureList;
    private static MakeTimerPanel mtp;
    private JPanel showTimerPanel = new JPanel();
    private JPanel spritePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel infoPanel;
    private boolean pointer = false; //Keeps track of if the start button has been pressed
    private int numCreatures;

    public CreatureFrame(CreatureList creatureList){

        ArrayList<JComponent> compList = new ArrayList<JComponent>();
        numCreatures = creatureList.getCreatures().size();

        showTimerPanel.add(new JLabel("No Timers Shown"));
        this.creatureList = creatureList;
        showTimerPanel.setVisible(false);

        spritePanel.setVisible(false);

        //initiative display declaration
        JLabel intiativeDisplay = new JLabel();
        intiativeDisplay.setVisible(false);

        mtp = new MakeTimerPanel(creatureList.getCurrentCreature(), creatureList.getCreatures().size());
        mtp.setPreferredSize(new Dimension(100,200));;
        mtp.setSize(new Dimension(100,200));
        mtp.setVisible(false);

        //Start JLabel
        JLabel jlCreature = new JLabel("Press Start!");
        jlCreature.setForeground(Color.BLACK);

        //Set up the information panel. Contains creature name, round count, buttons.
        infoPanel = new JPanel();
        infoPanel.setSize(150,100);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(jlCreature);

        //Declare MakeTimer button
        JButton jbMakeTimer = new JButton("Make Timer");
        jbMakeTimer.setVisible(false);

        //ShowTimer button
        JButton jbShowTimer = new JButton("Show Timers");
        jbShowTimer.setVisible(false);
        jbShowTimer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                showTimerPanel = creatureList.getCurrentCreature().getShowTimerPanel();
                showTimerPanel.setVisible(true);
                add(showTimerPanel);
                revalidate();
            }
        });

        //Next button
        JButton next = new JButton("Start");
        next.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

               //true if it isn't the first press of the next button
               pointer = true;

               //Frame logic
               creatureList.cycle();
               jlCreature.setText("It is " + creatureList.getCurrentCreature().getName() + "'s Turn.");
               mtp.setVisible(false);
               jbMakeTimer.setVisible(true);
               //spritePanel.setVisible(true);

               //Timer Logic, Timers are incremented if start has been pressed.
               RoundTimer.cleanAllTimerLists(creatureList.getCreatures());

                if(pointer){
                    mtp.setCurrentCreature(creatureList.getCurrentCreature());

                    creatureList.incrementTimers();
                    intiativeDisplay.setText("Turn: " + (creatureList.getTurnCount()) + ", Round: " + (creatureList.getRoundCount()+1));

                    //Sprite Panel things
                    spritePanel.removeAll();
                    spritePanel.add(new JLabel(creatureList.getCurrentCreature().getSprite().getSpriteImage()),0);
                }

                //Timer Panel Logic
                if(showTimerPanel.isVisible()){
                    showTimerPanel.setVisible(false);
                }

                //Visible Components Logic
               if(next.getText().equals("Start")){
                  next.setText("Next");
                  jbMakeTimer.setVisible(true);
                  jbShowTimer.setVisible(true);
                  intiativeDisplay.setVisible(true);
               }
            }
        });

        //Make Timer button
        jbMakeTimer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                mtp.setVisible(true);
                jbMakeTimer.setVisible(false);
            }
        });

        //Add things to infoPanel
        infoPanel.add(intiativeDisplay);
        infoPanel.add(next);
        infoPanel.add(Box.createRigidArea(new Dimension(0,5)));
        infoPanel.add(jbMakeTimer);
        infoPanel.add(Box.createRigidArea(new Dimension(0,5)));
        infoPanel.add(jbShowTimer);
        infoPanel.add(Box.createRigidArea(new Dimension(0,5)));

        //Make next button default
        getRootPane().setDefaultButton(next);

        //Set image
        setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/D20_icon.png")).getImage());

        //Add infoPanel to CreatureFrame
        this.add(infoPanel);
        this.add(mtp);
        this.add(showTimerPanel);
        this.add(spritePanel);
    }

    public static RoundTimer makeTimer(int numCreatures){
        String timerName = mtp.getName();
        int timerLength = mtp.getLength();
        RoundTimer temp = new RoundTimer(timerName, timerLength, numCreatures);
        return temp;
    }

    public CreatureList getCreatureList() {
        return creatureList;
    }

    public void setCreatureList(CreatureList creatureList) {
        this.creatureList = creatureList;
    }

    public static MakeTimerPanel getMtp() {
        return mtp;
    }

    public static void setMtp(MakeTimerPanel mtp) {
        CreatureFrame.mtp = mtp;
    }

    public JPanel getShowTimerPanel() {
        return showTimerPanel;
    }

    public void setShowTimerPanel(JPanel showTimerPanel) {
        this.showTimerPanel = showTimerPanel;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(JPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public boolean isPointer() {
        return pointer;
    }

    public void setPointer(boolean pointer) {
        this.pointer = pointer;
    }

    public int getNumCreatures() {
        return numCreatures;
    }

    public void setNumCreatures(int numCreatures) {
        this.numCreatures = numCreatures;
    }
}
