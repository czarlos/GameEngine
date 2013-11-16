package view.editor;

import grid.Grid;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import controllers.WorldManager;
import view.canvas.GridCanvas;


public class StagePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1534023398376725167L;

    public StagePanel (String stageName, Grid g, WorldManager wm) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridCanvas gc = new GridCanvas(g);
        // gc.setSize(400,400);
        add(gc);
        StageEditorPanel panel = new StageEditorPanel(wm);
        // panel.setSize(180,400);
        add(panel);
    }

}
