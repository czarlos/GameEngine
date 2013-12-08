package view.editor;

import controller.editor.GridEditorController;
import controllers.WorldManager;
import view.GridWithSide;


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

        StageSidePanel panel = new StageSidePanel(wm,
                                                   stageId);
        addToSideColumn(panel);
        myController.addStageSidePanel(panel);
        myGrid.addGridMouseListener(myController);
    }
}
