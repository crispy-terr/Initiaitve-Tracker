import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StatsDisplay extends JPanel {
    private JTextField jtfStatInput = new JTextField(10);
    private String statName;

    public StatsDisplay(){
        //setLayout(new GridLayout(2,1));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        statName = "blank";
        add(new JLabel(statName + ":"));
        add(jtfStatInput);
    }

    public StatsDisplay(String statName){
        //setLayout(new GridLayout(2,1));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        jtfStatInput.setMaximumSize(new Dimension(250, 20));
        this.statName = statName;
        setBorder(BorderFactory.createEmptyBorder(10,10,0,0));
        add(new JLabel(statName + ":"));
        add(jtfStatInput);
    }

    public JTextField getStatInput(){
        return jtfStatInput;
    }
}
