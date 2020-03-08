package View;

import javax.swing.*;
import java.awt.*;

public class JPanelBackground extends JPanel{
    private Image background;

    public void paintComponent(Graphics g){
        int width = this.getSize().width;
        int height = this.getSize().height;

        if(this.background != null){
            g.drawImage(this.background, 0, 0, width, height, null);
        }

        super.paintComponent(g);
    }

    public void setBackground(String imagePath){
        this.setOpaque(false);
        this.background = new ImageIcon(imagePath).getImage();
        repaint();
    }
}
