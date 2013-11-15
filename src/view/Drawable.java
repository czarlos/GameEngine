package view;

import java.awt.Graphics;


public abstract class Drawable extends Customizable {

    public void draw (Graphics g, int x, int y, int width, int height) {
        // set ImageObserver null. Not needed.
        g.drawImage(getImage(), x, y, width, height, null);
    }
}
