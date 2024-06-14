import java.awt.event.ActionEvent;

import javax.swing.JPanel;

public class CTRMakerBackButton extends BackButton {
    private static boolean ctrMade = false;

    public CTRMakerBackButton(JPanel previousPanel, JPanel currentPanel){
        super(previousPanel, currentPanel);
        setActionCommand("CTRBack");
    }

    public static boolean isCtrMade() {
        return ctrMade;
    }
    
    public static void setCtrMade(boolean ctrMade) {
        CTRMakerBackButton.ctrMade = ctrMade;
    }

    public void actionPerformed(ActionEvent ae){
        super.actionPerformed(ae);
    }
}
