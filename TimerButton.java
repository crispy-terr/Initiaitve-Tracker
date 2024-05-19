import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//How will I return the timerPanel to the creature or jframe???

public class TimerButton extends JButton implements ActionListener{

    private Creature curCreature;
    private JPanel timerPanel;

    public TimerButton(){
        super("Timer");
        curCreature = new Creature();
    }

    public TimerButton(Creature curCreature){
        super("Timer");
        this.curCreature = curCreature;
    }

    public Creature getCurCreature(){
        return curCreature;
    }

    public JPanel getTimerPanel(){
        return timerPanel;
    }

    public void setCurCreature(Creature creature){
        curCreature = creature;
    }

    public void setTimerPanel(JPanel timerPanel){
        this.timerPanel = timerPanel;
    }

    public void actionPerformed(ActionEvent e) {
        
    }
    
}
