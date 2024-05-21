import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DiceRollerPanel extends JPanel implements ActionListener {

    // One button for each of the dice
    private JButton d20 = new JButton("D20", GUIUtils.getGraphicsList().get(7));
    private JButton d12 = new JButton("D12", GUIUtils.getGraphicsList().get(5));
    private JButton d10 = new JButton("D10", GUIUtils.getGraphicsList().get(4));
    private JButton d8 = new JButton("D8", GUIUtils.getGraphicsList().get(10));
    private JButton d6 = new JButton("D6", GUIUtils.getGraphicsList().get(9));
    private JButton d4 = new JButton("D4", GUIUtils.getGraphicsList().get(8));
    private JButton d2 = new JButton("D2", GUIUtils.getGraphicsList().get(6));
    private JButton percentile = new JButton("Percentile");

    // Roll panel
    private JPanel rollPanel = new JPanel();
    private JLabel rollLabel = new JLabel("Nothing has been rolled yet.");
    private int roll = 0;

    // Handling bonus
    private JPanel bonusPanel = new JPanel();
    private JLabel bonusLabel = new JLabel("Bonus:");
    private JTextField bonusTextField = new JTextField(10);
    private int bonus = 0;
    private String plusMinus = " + ";
    private int total;

    //Error Panel
    private JLabel errorLabel = new JLabel("Please enter an integer value.");
    private JPanel errorPanel = new JPanel();


    public DiceRollerPanel(LayoutManager layout) {
        // Set up panel (construct super, add buttons)
        super(layout);
        add(d20);
        add(d12);
        add(d10);
        add(d8);
        add(d6);
        add(d4);
        add(d2);
        add(percentile);
        add(rollPanel);

        // Add action listeners to buttons
        for (Component c : getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).addActionListener(this);
                ((JButton) c).setLayout(new GridLayout(2, 1));
                ((JButton) c).revalidate();
            }
        }

        // Set up rollPanel
        GridLayout gl = new GridLayout(2, 1);
        gl.setVgap(5);
        rollPanel.setLayout(gl);

        JPanel textPanel = new JPanel();
        textPanel.add(new JLabel("Roll + Bonus = Total"));

        JPanel rollBonusTotalPanel = new JPanel();
        rollBonusTotalPanel.add(rollLabel);

        rollPanel.add(textPanel);
        rollPanel.add(rollBonusTotalPanel);

        // Set up bonus panel
        bonusPanel.add(bonusLabel);
        bonusPanel.add(bonusTextField);
        bonusTextField.addActionListener(this);
        bonusTextField.setActionCommand("jtfbonus");
        errorLabel.setVisible(false);

        // Set up error panel
        errorLabel.setVisible(false);
        errorPanel.add(errorLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        evaluateBonus();

        if (e.getActionCommand().equals("D20")) {
            roll = (int) ((Math.random() * 20) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("D10")) {
            roll = (int) ((Math.random() * 10) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("D12")) {
            roll = (int) ((Math.random() * 12) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("D8")) {
            roll = (int) ((Math.random() * 8) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("D4")) {
            roll = (int) ((Math.random() * 4) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("D2")) {
            roll = (int) ((Math.random() * 2) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("Percentile")) {
            roll = (int) ((Math.random() * 100) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("D6")) {
            roll = (int) ((Math.random() * 6) + 1);
            calculateTotal();
            rollLabel.setText(roll + plusMinus + Math.abs(bonus) + " = " + total);
        } else if (e.getActionCommand().equals("jtfbonus")) {
            evaluateBonus();
            calculateTotal();
        }
    }

    public void calculateTotal(){
       total = bonus + roll;
    }

    public void evaluateBonus(){
        if(!(bonusTextField.getText().length() == 0)){
            try{
                bonus = Integer.parseInt(bonusTextField.getText());
                if(errorPanel.isVisible()){
                    errorPanel.setVisible(false);
                }

                if(bonus>=0){
                    plusMinus = " + ";
                }
                else{
                    plusMinus = " - ";
                }
    
            }catch(Exception ee){
                errorLabel.setForeground(Color.RED);
                errorPanel.setVisible(true);
                errorLabel.setVisible(true);
            }
        }
    }

    public int getRoll() {
        return roll;
    }

    public JPanel getRollPanel() {
        return rollPanel;
    }

    public JPanel getBonusPanel(){
        return bonusPanel;
    }

    public JPanel getErrorPanel(){
        return errorPanel;
    }

}
