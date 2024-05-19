import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class MakeTimerPanel extends JPanel implements ActionListener{

   private JLabel jlName = new JLabel("Timer Name: "); //Handles timer name
   private JLabel jlLength = new JLabel("Timer Length (rounds): "); //Handles timer length
   private JTextField jtfName = new JTextField(10);
   private JTextField jtfLength = new JTextField(10);
   private JLabel errorLabel = new JLabel();

   private Creature currentCreature;
   private RoundTimer nextTimer;
   private int numCreatures;

   public MakeTimerPanel(Creature currentCreature, int numCreatures){
      errorLabel.setVisible(false);
      this.numCreatures = numCreatures;
      this.currentCreature = currentCreature;

      //Give textfields actionListener
      jtfName.addActionListener(this);
      jtfLength.addActionListener(this);

      this.add(jlName);
      this.add(jtfName);
      this.add(jlLength);
      this.add(jtfLength);
      this.add(errorLabel);
   }
   
   public void actionPerformed(ActionEvent ae){

      try{
         //If there is text in jtfName and jtfLength
         if(jtfName.getText().length()>0 && jtfLength.getText().length()>0 && Integer.parseInt(jtfLength.getText())>0){
            //Make a timer with parameters specified in jtfName and jtfLength
            nextTimer = CreatureFrame.makeTimer(numCreatures);
            currentCreature.getTimers().add(nextTimer);
            currentCreature.createShowTimerPanel();

            //Set text fields to blank if successful
            jtfLength.setText("");
            jtfName.setText("");

            //make the errorLabel invisible if it is visible
            errorLabel.setVisible(false);

            //Popup to that notifies the user that a timer has been created
            JFrame frame = new JFrame("Timer Created");
            frame.setSize(300,100);
            frame.setVisible(true);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER));
            frame.setLocationRelativeTo(null);
            frame.add(new JLabel("Timer: \"" + nextTimer.getName() + "\" has been created"), BorderLayout.CENTER);
            frame.add(new JLabel("for " + currentCreature.getName() + "!"));
            frame.setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/D20_icon.png")).getImage());
         }
      }catch(java.lang.NumberFormatException e){
         errorLabel.setText("Enter a positive integer in the length field.");
         errorLabel.setForeground(Color.RED);
         errorLabel.setVisible(true);
         revalidate();
      }
   }

   public String getName(){
      return jtfName.getText(); 
   }

   public int getLength(){
      return Integer.parseInt(jtfLength.getText());
   }

   public String getLengthString(){
      return jtfLength.getText();
   }

   public void setJTFNameText(String text){
      jtfName.setText(text);
   }

   public void setJTFLengthText(String text){
      jtfLength.setText(text);
   }

   public void setCurrentCreature(Creature currentCreature){
      this.currentCreature = currentCreature;
   }
}