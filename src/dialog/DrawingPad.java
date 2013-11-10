package dialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;

public class DrawingPad extends JComponent{
    Image image;
    Graphics2D graphics2D;
    //this is what we'll be using to draw on
    int currentX, currentY, oldX, oldY;

    public DrawingPad(){
        
            setDoubleBuffered(false);
            addMouseListener(new MouseAdapter(){
                    public void mousePressed(MouseEvent e){
                            oldX = e.getX();
                            oldY = e.getY();
                    }
            });
            addMouseMotionListener(new MouseMotionAdapter(){
                    public void mouseDragged(MouseEvent e){
                            currentX = e.getX();
                            currentY = e.getY();
                            if(graphics2D != null)
                            graphics2D.drawLine(oldX, oldY, currentX, currentY);
                            repaint();
                            oldX = currentX;
                            oldY = currentY;
                    }

            });

    }

    public void paintComponent(Graphics g){
            if(image == null){
                    image = createImage(getSize().width, getSize().height);
                    graphics2D = (Graphics2D)image.getGraphics();
                    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    clear();

            }
            g.drawImage(image, 0, 0, null);
    }


    public void clear(){
            graphics2D.setPaint(Color.white);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            graphics2D.setPaint(Color.black);
            repaint();
    }

}
