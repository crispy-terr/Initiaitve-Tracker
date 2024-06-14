import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.nio.*;
import java.nio.file.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DeletePanel extends JPanel implements ActionListener {

    private JPanel mainList = new JPanel();
    private JTextField jtfSearch = new JTextField("<Search>");
    private JButton jbDelete = new JButton("Delete Creatures");
    private JPanel scrollSearchPanel = new JPanel();
    private JScrollPane jspCreatures;
    private JPanel buttonPanel = new JPanel();

    public DeletePanel(JPanel prevPanel) throws Exception {
        ChooseCreaturePanel.resetIndexPointer();

        // Set layout for mainList and scrollSearchPanel
        mainList.setLayout(new BoxLayout(mainList, BoxLayout.Y_AXIS));
        scrollSearchPanel.setLayout(new BoxLayout(scrollSearchPanel, BoxLayout.PAGE_AXIS));

        // add action listener to button and scrollSearchPanel
        jbDelete.addActionListener(this);
        jtfSearch.addActionListener(this);
        jtfSearch.setActionCommand("Search");

        // Add all creatures to the mainList panel as ChooseCreaturePanels
        for (File n : ChooseCreaturePanel.getDirList()) {
            ChooseCreaturePanel ccp = new ChooseCreaturePanel();
            mainList.add(ccp);
        }
        ChooseCreaturePanel.resetIndexPointer();

        // Make the mainList panel look nice
        mainList.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        jspCreatures = new JScrollPane(mainList);
        jspCreatures.setPreferredSize(new Dimension(300, 350));

        // Change the scroll speed
        jspCreatures.getVerticalScrollBar().setUnitIncrement(10);

        // Add scroll panel stuff to scrollSearchPanel
        scrollSearchPanel.add(jtfSearch);
        scrollSearchPanel.add(jspCreatures);

        //Add to buttonPanel
        buttonPanel.add(new BackButton(prevPanel, this));
        buttonPanel.add(jbDelete);

        add(scrollSearchPanel);
        add(buttonPanel);
    }

    public DeletePanel() throws Exception {

        // Set layout for mainList and scrollSearchPanel
        mainList.setLayout(new BoxLayout(mainList, BoxLayout.Y_AXIS));
        scrollSearchPanel.setLayout(new BoxLayout(scrollSearchPanel, BoxLayout.PAGE_AXIS));

        // add action listener to button and scrollSearchPanel
        jbDelete.addActionListener(this);
        jtfSearch.addActionListener(this);
        jtfSearch.setActionCommand("Search");

        // Add all creatures to the mainList panel as ChooseCreaturePanels
        for (File n : ChooseCreaturePanel.getDirList()) {
            ChooseCreaturePanel ccp = new ChooseCreaturePanel();
            mainList.add(ccp);
        }

        // Make the mainList panel look nice
        mainList.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        jspCreatures = new JScrollPane(mainList);
        jspCreatures.setPreferredSize(new Dimension(300, 375));

        // Change the scroll speed
        jspCreatures.getVerticalScrollBar().setUnitIncrement(10);

        // Add scroll panel stuff to scrollSearchPanel
        scrollSearchPanel.add(jtfSearch);
        scrollSearchPanel.add(jspCreatures);

        add(scrollSearchPanel);
        add(jbDelete);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Search")) {
            if (jtfSearch.getText().length() > 0) {
                unsearch();
                search(jtfSearch.getText().toLowerCase());
            } else {
                unsearch();
            }
        } else if (e.getActionCommand().equalsIgnoreCase("Delete Creatures")) {
            for (Component c : mainList.getComponents()) {
                if (c instanceof ChooseCreaturePanel) {
                    ChooseCreaturePanel ccp = ((ChooseCreaturePanel) c);
                    if (ccp.isInEncounter()) {
                        Path creatureToBeDeletedPath = Paths.get(ccp.getCreature().getFile().getPath());
                        try {
                            Files.delete(creatureToBeDeletedPath);
                            ccp.getCheckBox().setSelected(false);
                            ccp.setVisible(false);
                            ccp.setDeleted(true);
                            ChooseCreaturePanel.getCreatureFiles().remove(ccp.getCreature().getFile());
                            ChooseCreaturePanel.setIndexPointer(ChooseCreaturePanel.getIndexPointer()-1);
                        } catch (Exception ee) {
                            System.err.println("Something went wrong.");
                        }
                    }
                }
            }

        }
    }

    public void search(String name) {
        for (Component c : mainList.getComponents()) {
            if (c instanceof ChooseCreaturePanel) {
                if (!((ChooseCreaturePanel) c).getCreature().getName().toLowerCase().contains(name)) {
                    c.setVisible(false);
                }
            }
        }
    }

    public void unsearch() {
        for (Component c : mainList.getComponents()) {
            if (c instanceof ChooseCreaturePanel) {
                ChooseCreaturePanel ccp = ((ChooseCreaturePanel) c);
                if (!ccp.isDeleted())
                    c.setVisible(true);
            }
        }
    }
}
