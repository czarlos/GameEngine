package view.editor;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;


/**
 * 
 */

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

    Image myImage;

    public ImagePanel (Image image) {
        myImage = image;
    }

    @Override
    public void paintComponent (Graphics g) {
        g.drawImage(myImage, 15, 5, 35, 35, null);
    }
}
