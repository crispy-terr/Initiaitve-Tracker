import java.awt.Dimension;

public class Main {
    static CreatureFrame frame;

    public static void main(String[] args) throws Exception {
        frame = GUIUtils.createGameMenu(new Dimension(500, 500), "Initiative Tracker");
        frame.setVisible(true);
    }
}
