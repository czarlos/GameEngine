package view.player;

import grid.Coordinate;
import grid.Grid;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent.KeyBinding;
import controller.editor.GridController;
import view.canvas.GridCanvas;
import view.canvas.GridMouseListener;


public class StagePlayerPanel extends JPanel {
    private JPanel mySidePanel;
    private GridController myController;
    
    public StagePlayerPanel (String stageName, Grid g) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridCanvas gc = new GridCanvas(g);
        GridController gcontrol = new GridController(g);
        gc.addGridMouseListener(gcontrol);
        add(gc);
        mySidePanel = new PlayerControlPanel(gcontrol);
        mySidePanel.setMaximumSize(new Dimension(200, 500));
        add(mySidePanel);
        repaint();
    }

}
