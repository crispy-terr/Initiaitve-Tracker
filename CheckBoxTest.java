import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckBoxTest {
    static JCheckBox jcb = new JCheckBox();
    static JLabel jcbResponse = new JLabel("Unchecked");

    public static void main(String[] args){
        JFrame frame = new JFrame("Check Box Test");
        frame.setSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();

        p.add(jcb);
        p.add(jcbResponse);
        frame.add(p);
        frame.setVisible(true);
    }

    public void itemStateChanged(ItemEvent e){
        Object source = e.getItemSelectable();
        
    }
}
