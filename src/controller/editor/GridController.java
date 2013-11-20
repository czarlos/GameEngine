package controller.editor;

import controllers.WorldManager;
import grid.Coordinate;
import view.canvas.GridMouseListener;


public class GridController implements GridMouseListener {
    private NClickAction myCurrentAction;
    private WorldManager myWM;
    private Coordinate mySelectedCoordinate;

    public GridController (WorldManager wm) {
        myWM = wm;
        mySelectedCoordinate = new Coordinate(0, 0);
    }

    public void doCommand (String commandName, int numClicks, Object ... args) {
        myCurrentAction = new NClickAction(numClicks, commandName, myWM, args);
        myCurrentAction.click(mySelectedCoordinate);
    }

    @Override
    public void gridClicked (Coordinate c) {

        mySelectedCoordinate = c;
        System.out.println(c.getX() + " " + c.getY());
        if (myCurrentAction != null)
            myCurrentAction.click(c);
    }

    public void clearCurrentCommand () {
        myCurrentAction = null;
    }
}
