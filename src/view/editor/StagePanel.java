package view.editor;

import controller.editor.GridEditorController;
import controllers.WorldManager;
import view.GridWithSide;
import view.canvas.GridCanvas;


public class StagePanel extends GridWithSide {

    /**
     * 
     */
    private static final long serialVersionUID = 1534023398376725167L;
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
