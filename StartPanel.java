import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;
import javax.swing.*;

public class StartPanel extends JPanel implements ActionListener {
    private JButton jbStart = new JButton("Start");
    private JButton jbCreateFiles = new JButton("Create Files");
    private JPanel titlePanel = new JPanel();
    private JLabel titleLabel = new JLabel("Initiative Tracker");
    private JPanel buttonPanel = new JPanel();
    private Font titleFont;

    public StartPanel(){
        boolean enoughCreatures = CreatureUtils.checkCreaturesFolderNumFiles();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        titleFont = new Font("Arial", Font.PLAIN, 50);
        titleLabel.setFont(titleFont);

        titlePanel.add(titleLabel);

        buttonPanel.add(jbStart);
        jbStart.addActionListener(this);
        buttonPanel.add(jbCreateFiles);
        jbCreateFiles.addActionListener(this);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        add(titlePanel);
        add(buttonPanel);
        
        //If not enough creature files, set start to disabled
        if(!enoughCreatures){
            jbStart.setEnabled(false);
        }

        
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        StartPanel ctrmp = new StartPanel();
        frame.add(ctrmp);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("jbStart")){
            setAllInvisible();
            revalidate();

        } else if (e.getActionCommand().equalsIgnoreCase("jbCreateFiles")){
            setAllInvisible();
        }
    }

    public void setAllInvisible(){
        for (Component c : getComponents()) {
            c.setVisible(false);
        }
    }
}
