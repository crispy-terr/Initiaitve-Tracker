import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CheckBoxTest implements ActionListener{
    static JCheckBox jcb = new JCheckBox();
    static JLabel jcbResponse = new JLabel("Unchecked");

    public CheckBoxTest(){
        JFrame frame = new JFrame("Check Box Test");
        frame.setSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        jcb.addActionListener(this);

        p.add(jcb);
        p.add(jcbResponse);
        frame.add(p);
        frame.setVisible(true); 
    }

    public static void main(String[] args) throws Exception{
        new CheckBoxTest();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(jcb.isSelected()){
            jcbResponse.setText("Selected");
        }
        else{
            jcbResponse.setText("Not Selected");
        }
    }
}
