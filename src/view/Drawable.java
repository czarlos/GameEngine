package view;

import java.awt.Graphics;

public interface Drawable {
	abstract public void draw(Graphics g, int x, int y, int width, int height);
}
