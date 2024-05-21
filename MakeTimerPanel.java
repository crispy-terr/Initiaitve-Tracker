import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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

      //Add objects to separate panels
      JPanel jlNamePanel = new JPanel();
      jlNamePanel.setMaximumSize(new Dimension(100,30));
      jlNamePanel.add(jlName);

      JPanel jtfNamePanel = new JPanel();
      jtfNamePanel.setMaximumSize(new Dimension(150,30));
      jtfNamePanel.add(jtfName);

      JPanel jlLengthPanel = new JPanel();
      jlLengthPanel.setMaximumSize(new Dimension(150,30));
      jlLengthPanel.add(jlLength);

      JPanel jtfLengthPanel = new JPanel();
      jtfLengthPanel.setMaximumSize(new Dimension(150,30));
      jtfLengthPanel.add(jtfLength);

      //Add objects to the panel
      this.add(jlNamePanel);
      this.add(jtfNamePanel);
      this.add(jlLengthPanel);
      this.add(jtfLengthPanel);
      this.add(errorLabel);
   }
   
   public void actionPerformed(ActionEvent ae){

      try{
         //If there is text in jtfName and jtfLength
         if(jtfName.getText().length()>0 && jtfLength.getText().length()>0 && Integer.parseInt(jtfLength.getText())>0){
            //Make a timer with parameters specified in jtfName and jtfLength
            nextTimer = InitiativeTrackerPanel.makeTimer(numCreatures);
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
            frame.setIconImage(new ImageIcon(CreatureUtils.class.getResource("/Graphics/Logo.png")).getImage());
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