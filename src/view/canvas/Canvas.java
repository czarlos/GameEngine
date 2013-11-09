package view.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JPanel;


public class Canvas extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 5035575821683474890L;
    
    protected static final int STARTING_X = 0;
    protected static final int STARTING_Y = 0;

    public Canvas () {
        super();
    }

    public Canvas (LayoutManager arg0) {
        super(arg0);
    }

    public Canvas (boolean arg0) {
        super(arg0);
    }

    public Canvas (LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
    }

    public Dimension getPreferredSize () {
    	return new Dimension(250, 200);
    }

    /**
     * Method to define what gets painted to canvas.
     */
    public void paintComponent (Graphics g){
        super.paintComponent(g);
    }

}
