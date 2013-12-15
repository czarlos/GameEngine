package view.editor;

import controller.editor.GridEditorController;
import controllers.WorldManager;
import view.GridWithSide;
import view.canvas.GridCanvas;

/**
 * JPanel to represent a stage. Shows grid for a stage and editing options
 * for that stage.
 */
@SuppressWarnings("serial")
public class StagePanel extends GridWithSide {

    private GridEditorController myController;

    /**
     * Create new stage panel for a specified stage
     * @param wm WordManager containing stage data
     * @param gridcontrol GridEditorController to be used for this StagePanel
     * @param stageId Id of stage to make StagePanel for
     */
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
