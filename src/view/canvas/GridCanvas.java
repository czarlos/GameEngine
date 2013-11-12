package view.canvas;

import grid.Grid;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import controllers.WorldManager;


public class GridCanvas extends Canvas {

    Grid myGrid;
    Collection<GridMouseListener> myClickSubscribers;

    private static final long serialVersionUID = -3908147776463294489L;

    public GridCanvas (Grid grid) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        myGrid = grid;
    }

    public static void main (String args[]) {
        GridCanvas myCanvas = new GridCanvas(new Grid(10, 10, 1));
        JFrame myFrame = new JFrame();
        myFrame.getContentPane().add(myCanvas);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    public static void main (String args[]) {
        GridCanvas myCanvas = new GridCanvas(new Grid(10, 10));

        JFrame myFrame = new JFrame();
        myFrame.getContentPane().add(myCanvas);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        int height = getSize().height;
        int width = getSize().width;
        myGrid.draw(g, STARTING_X, STARTING_Y, width, height);
    }

}
