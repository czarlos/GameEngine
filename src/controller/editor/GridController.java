package controller.editor;

import controllers.GameManager;
import grid.Coordinate;
import view.canvas.GridMouseListener;
import view.player.StagePlayerPanel;


public class GridController implements GridMouseListener {
    private NClickAction myCurrentAction;
    private GameManager myManager;
    private Coordinate mySelectedCoordinate;
    private StagePlayerPanel myView;

    public GridController (GameManager manager, StagePlayerPanel view) {
        myManager = manager;
        mySelectedCoordinate = new Coordinate(0, 0);
        myView = view;
    }

    public void doCommand (String commandName, int numClicks, Object ... args) {
        myCurrentAction = new NClickAction(numClicks, commandName, myManager,
                                           args);
        myCurrentAction.click(mySelectedCoordinate);
    }

    public void doCommand (NClickAction action) {
        myCurrentAction = action;
        Object[] args = { myManager, action.getCurrentArgs() };
        action.setArgs(args);
        myCurrentAction.click(mySelectedCoordinate);
        myView.revalidate();
    }

    @Override
    public void gridClicked (Coordinate c) {

        mySelectedCoordinate = c;

        if (myCurrentAction != null)
            myCurrentAction.click(c);

        myView.updatedSelectedInfoPanel(c);
        myView.revalidate();
    }

    public void clearCurrentCommand () {
        myCurrentAction = null;
    }

    public Coordinate getCurrentCoordinate () {
        return mySelectedCoordinate;
    }
}
