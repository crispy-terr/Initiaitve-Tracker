import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StatsDisplay extends JPanel {
    private JTextField jtfStatInput = new JTextField(10);
    private String statName;
    private boolean integerInput = false;
    private boolean validInput;

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

    public StatsDisplay(String statName, boolean integerInput){
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

    public void checkInputForValidity(){
        boolean isValid;
        if(integerInput){
            try{
                Integer.parseInt(jtfStatInput.getText());
                isValid = true;
            } catch (NumberFormatException nfe){
                isValid = false;
            }
        } else {
            if(jtfStatInput.getText().length() > 0){
                isValid = true;
            } else {
                isValid = false;
            }
        }
        validInput = isValid;
    }

    public boolean isValid(){
        return validInput;
    }
}
