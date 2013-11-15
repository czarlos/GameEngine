package view.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import grid.Grid;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import controllers.WorldManager;
import stage.Stage;
import view.canvas.GridCanvas;

public class StagePanel extends JPanel{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1534023398376725167L;
    private final String[] defaultTypes = {"Tile", "GameUnit", "GameObject"};
            
    public StagePanel(String stageName, Grid g, WorldManager wm){
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridCanvas gc = new GridCanvas(g);
        add(gc);
        StageEditorPanel panel = new StageEditorPanel(wm, defaultTypes);
        panel.setMaximumSize(new Dimension(200,500));
        add(panel);
    }
   
}
