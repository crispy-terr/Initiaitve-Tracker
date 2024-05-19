import javax.swing.ImageIcon;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;


public class CreatureSprite {
    
    private ImageIcon spriteImage;
    private String spriteName;
    private ArrayList<ImageIcon> graphicsList = GUIUtils.getGraphicsList();
    private Dimension imageDimension;
    private String graphicsDirectory = GUIUtils.getGraphicsDirectory();

    public CreatureSprite(){
        spriteImage = new ImageIcon(graphicsDirectory + "/Null_Graphic.png");
        spriteName = "Blank";
        imageDimension = new Dimension(spriteImage.getIconWidth(), spriteImage.getIconHeight());
    }

    public CreatureSprite(Player player){
        spriteImage = new ImageIcon(graphicsDirectory + "/"+ player.getSpriteFileName());
        spriteName = player.getName() + " Sprite";
        imageDimension = new Dimension(spriteImage.getIconWidth(), spriteImage.getIconHeight());
    }

    public CreatureSprite(Enemy enemy){
        spriteImage = new ImageIcon(graphicsDirectory + "/"+ enemy.getSpriteFileName());
        spriteName = enemy.getName() + " Sprite";
        imageDimension = new Dimension(spriteImage.getIconWidth(), spriteImage.getIconHeight());
    }

    public CreatureSprite(Boss boss){
        spriteImage = new ImageIcon(graphicsDirectory + "/" + boss.getSpriteFileName());
        spriteName = boss.getName() + " Sprite";
        imageDimension = new Dimension(spriteImage.getIconWidth(), spriteImage.getIconHeight());
    }

    public void showSprite(){
        JFrame frame = new JFrame(spriteName);
        frame.setSize(new Dimension((int)imageDimension.getWidth(), (int)imageDimension.getHeight()+50));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new JLabel(spriteImage));
        frame.setVisible(true);
    }

    public String toString(){
        return spriteName;
    }

    public ImageIcon getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(ImageIcon spriteImage) {
        this.spriteImage = spriteImage;
    }

    public ArrayList<ImageIcon> getGraphicsList() {
        return graphicsList;
    }

    public void setGraphicsList(ArrayList<ImageIcon> graphicsList) {
        this.graphicsList = graphicsList;
    }

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }

}

