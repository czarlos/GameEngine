package view.canvas;

import grid.Coordinate;
import grid.Grid;
import grid.Tile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import controller.actions.grid.SetTileActive;
import controller.editor.GridController;


public class GridCanvas extends Canvas {

    Grid myGrid;
    Collection<GridMouseListener> myClickSubscribers;
    Tile t;

    private static final long serialVersionUID = -3908147776463294489L;

    public GridCanvas (Grid grid) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        myGrid = grid;
        myClickSubscribers = new ArrayList<>();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
//                Coordinate c = mapPixelsToGrid(e.getX(), e.getY());
//                t = myGrid.getTile(c.getX(), c.getY());
//                System.out.println(c.getX());
                notifySubscribersOfClick(e);
            }
        });
    }

    public void notifySubscribersOfClick (MouseEvent e) {
        Coordinate clickGridCoordinate = mapPixelsToGrid(e.getX(), e.getY());

        for (GridMouseListener subscriber : myClickSubscribers) {
            subscriber.gridClicked(clickGridCoordinate);
        }
        repaint();
    }
    
    public void addGridMouseListener (GridMouseListener l) {
        myClickSubscribers.add(l);
    }

    public Coordinate mapPixelsToGrid (int x, int y) {
        // cast x and y to double to enable non-integer values when evaluating division
        double doubleX = x;
        double doubleY = y;

        int gridX = (int) (doubleX / getSize().width * myGrid.getWidth());
        int gridY = (int) (doubleY / getSize().height * myGrid.getHeight());

        return new Coordinate((int) gridX, (int) gridY);
    }

    public static void main (String args[]) {
        GridCanvas myCanvas = new GridCanvas(new Grid(10, 10, 0));
        GridController control = new GridController(myCanvas.myGrid);
        myCanvas.addGridMouseListener(control);
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
    
    public int getHeight() {
        return getSize().height;
    }
    
    public int getWidth() {
        return getSize().width;
    }

}
