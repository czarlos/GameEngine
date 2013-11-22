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
    
    public StagePanel (String stageName, WorldManager wm) {
        activeObjectInfo = new String[2];
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridCanvas gc = new GridCanvas(wm);
        add(gc);
        StageEditorPanel panel = new StageEditorPanel(wm, defaultTypes);
        panel.setMaximumSize(new Dimension(200, 500));
        add(panel);
    }
    
    public void setActiveObject(String type, String name){
        activeObjectInfo[0] = type;
        activeObjectInfo[1] = name;
    }
    
    

}
