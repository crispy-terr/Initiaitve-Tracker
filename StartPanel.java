import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPanel extends JPanel implements ActionListener {
    private JButton jbStart = new JButton("Start");
    private JButton jbCreateFiles = new JButton("Create Files");
    private JButton jbDelete = new JButton("Delete Files");
    private JPanel titlePanel = new JPanel();
    private JLabel titleLabel = new JLabel("Initiative Tracker");
    private JPanel buttonPanel = new JPanel();
    private Font titleFont;

    private JPanel CTRMadePanel = new JPanel();
    private JLabel CTRMadeLabel = new JLabel("Files have been created. Please restart the program.");

    public StartPanel() {
        boolean enoughCreatures = CreatureUtils.checkCreaturesFolderNumFiles();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        titleFont = new Font("Arial", Font.PLAIN, 50);
        titleLabel.setFont(titleFont);

        titlePanel.add(titleLabel);
        
        buttonPanel.add(jbStart);
        jbStart.addActionListener(this);
        buttonPanel.add(jbCreateFiles);
        jbCreateFiles.addActionListener(this);
        buttonPanel.add(jbDelete);
        jbDelete.addActionListener(this);

        // Set up CTRMadePanel and add it
        CTRMadePanel.add(CTRMadeLabel);
        CTRMadeLabel.setForeground(Color.RED);
        CTRMadePanel.setVisible(false);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        add(titlePanel);
        add(buttonPanel);
        add(CTRMadePanel);

        // If not enough creature files, set start to disabled
        if (!enoughCreatures) {
            jbStart.setEnabled(false);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Start")) {
            setAllInvisible();
            try {
                add(new GamePanel());
            } catch (Exception ee) {
                System.err.println("An error occurred");
                ee.printStackTrace();
            }

        } else if (e.getActionCommand().equalsIgnoreCase("Create Files")) {
            setAllInvisible();
            add(new CTRMakerPanel(this));
        } else if (e.getActionCommand().equalsIgnoreCase("Delete Files")) {
            setAllInvisible();
            try {
                add(new DeletePanel(this));
            } catch (Exception ee) {
                System.err.println("Something went wrong.");
                ee.printStackTrace();
            }
        } else if (e.getActionCommand().equalsIgnoreCase("CTRBack")) {
            if (CTRMakerBackButton.isCtrMade()) {
                CTRMadePanel.setVisible(true);
                CTRMadePanel.revalidate();
                jbStart.setEnabled(false);
            }
        }
    }

    public void setAllInvisible() {
        for (Component c : getComponents()) {
            c.setVisible(false);
        }
    }

    public void checkEnoughCreatures() {
        if (CreatureUtils.checkCreaturesFolderNumFiles()) {
            jbStart.setEnabled(true);
        }
    }
}
