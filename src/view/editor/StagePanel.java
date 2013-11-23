package view.editor;

import java.awt.Dimension;
import grid.Grid;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import controller.editor.GridController;
import controller.editor.GridEditorController;
import controllers.WorldManager;
import view.canvas.GridCanvas;
import view.editor.StageEditorPanel;


public class StagePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1534023398376725167L;
    private final String[] defaultTypes = { "Tile", "GameUnit", "GameObject" };
    private GridCanvas myCanvas;
    private WorldManager myWorldManager;
    
    
    public StagePanel (String stageName, WorldManager wm) {
        GridEditorController gridcontrol = new GridEditorController(wm);
        myCanvas = new GridCanvas(wm);
        myCanvas.addGridMouseListener(gridcontrol);
        myWorldManager = wm;

        initStagePanel();
    }
    
    private void initStagePanel(){
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        JScrollPane scrollGrid = new JScrollPane(myCanvas, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollGrid.setLayout(new ScrollPaneLayout());
        add(scrollGrid);
        StageEditorPanel panel = new StageEditorPanel(myWorldManager, defaultTypes);
        panel.setMaximumSize(new Dimension(200, 500));
        add(panel);
        repaint();
    }
    

    
    
    

}
