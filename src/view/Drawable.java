package view;

import java.awt.Graphics;
import java.awt.Image;


public interface Drawable {
    public abstract void draw (Graphics g, int x, int y, int height, int width);

    public abstract String getName ();

    public abstract Image getImage ();
}
