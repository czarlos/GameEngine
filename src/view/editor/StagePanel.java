package view.editor;

import java.awt.Dimension;
import grid.Grid;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import controllers.WorldManager;
import view.canvas.GridCanvas;
import view.editor.StageEditorPanel;


public class StagePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1534023398376725167L;
    private final String[] defaultTypes = { "Tile", "GameUnit", "GameObject" };
    private String[] activeObjectInfo;
    private GridCanvas myCanvas;
    private WorldManager myWorldManager;

    public StagePanel (String stageName, WorldManager wm) {
        activeObjectInfo = new String[2];

        myCanvas = new GridCanvas(wm);
        myWorldManager = wm;

        initStagePanel();
    }

    private void initStagePanel () {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        add(myCanvas);
        StageEditorPanel panel = new StageEditorPanel(myWorldManager, defaultTypes);
        panel.setMaximumSize(new Dimension(200, 500));
        add(panel);

    }

    public void setActiveObject (String type, String name) {
        activeObjectInfo[0] = type;
        activeObjectInfo[1] = name;
    }

}
