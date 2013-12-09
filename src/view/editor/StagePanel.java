package view.editor;

import controller.editor.GridEditorController;
import controllers.WorldManager;
import view.GridWithSide;
import view.canvas.GridCanvas;

/**
 * 
 */
@SuppressWarnings("serial")
public class StagePanel extends GridWithSide {

    private GridEditorController myController;

    public StagePanel (WorldManager wm,
                       GridEditorController gridcontrol, int stageId) {
        super(wm);
        myController = gridcontrol;
        initStagePanel(wm, stageId);
    }

    private void initStagePanel (WorldManager wm, int stageId) {

        StageSidePanel panel = new StageSidePanel(wm, myController);
        addToSideColumn(panel);
        myController.addStageSidePanel(stageId, panel);
        myGrid.addGridMouseListener(myController);

    }

    public GridCanvas getGridCanvas () {
        return myGrid;
    }
}
