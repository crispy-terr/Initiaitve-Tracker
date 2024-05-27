import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CTRMakerPanel extends JPanel {
    private JCheckBox jcbCreature = new JCheckBox("Creature");
    private JCheckBox jcbEnemy = new JCheckBox("Enemy");
    private JCheckBox jcbBoss = new JCheckBox("Boss");
    private JPanel checkBoxPanel = new JPanel();

    private JTextField jtfCharacterName = new JTextField();
    private JTextField jtfPlayerName = new JTextField();
    private JTextField jtfStrength = new JTextField();
    private JTextField jtfDexterity = new JTextField();
    private JTextField jtfConstitution = new JTextField();
    private JTextField jtfIntelligence = new JTextField();
    private JTextField jtfWisdom = new JTextField();
    private JTextField jtfCharisma = new JTextField();

}
