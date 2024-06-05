import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BackButton extends JButton implements ActionListener {

    JPanel prevPanel;
    JPanel currentPanel;

    public BackButton(){
        setText("Back");
    }

    public BackButton(JPanel prevPanel, JPanel currentPanel){
        setText("Back");
        addActionListener(this);
        addActionListener(new StartPanel());

        this.prevPanel = prevPanel;
        this.currentPanel = currentPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(Component c : currentPanel.getComponents()){
            c.setVisible(false);
        }

        for(Component c : prevPanel.getComponents()){
            c.setVisible(true);
            if(prevPanel instanceof StartPanel){
                ((StartPanel)prevPanel).checkEnoughCreatures();
            }
        }
    }
    
}
