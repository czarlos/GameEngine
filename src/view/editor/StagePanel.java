package view.editor;

import grid.GridConstants;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import controller.editor.GridEditorController;
import controllers.WorldManager;
import view.canvas.GridCanvas;
import view.editor.StageEditorPanel;


public class StagePanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1534023398376725167L;
    private final String[] defaultTypes = GridConstants.DEFAULTTABTYPES;
    private GridCanvas myCanvas;
    private WorldManager myWorldManager;
    private int myID;
    private GridEditorController myController;
    

    public StagePanel (String stageName, WorldManager wm, int stageID,
                       GridEditorController gridcontrol) {
        myID = stageID;
        myWorldManager = wm;
        myController = gridcontrol;
        myCanvas = new GridCanvas(myWorldManager);
        initStagePanel();
    }

    private void initStagePanel () {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        JScrollPane scrollGrid =
                new JScrollPane(myCanvas,
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollGrid.setLayout(new ScrollPaneLayout());
        add(scrollGrid);
        //StageEditorPanel panel = new StageEditorPanel(myWorldManager,
        //                                          defaultTypes, myID);
        StageSidePanel panel = new StageSidePanel(myWorldManager,
                                                      defaultTypes, myID);
        panel.setSize(new Dimension(200, 500));
        add(panel);
        myController.addStageSidePanel(panel);
        repaint();
        myCanvas.addGridMouseListener(myController);
    }
}
