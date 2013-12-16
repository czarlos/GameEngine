package view;

import java.awt.Graphics;

/**
 * Interface that defines an element knowing how to draw itself to 
 * a graphics object.
 *
 */
public interface Drawable {
    
    /**
     * Tell component to draw itself
     * @param g Graphics object to draw to
     * @param x X coordinate to draw object at
     * @param y Y coordinate to draw object at
     * @param width Width of drawing
     * @param height Height of drawing
     */
    abstract public void draw (Graphics g, int x, int y, int width, int height);
}
