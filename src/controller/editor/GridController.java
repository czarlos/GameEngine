package controller.editor;

import grid.Coordinate;
import grid.Grid;
import view.canvas.GridMouseListener;


public class GridController implements GridMouseListener {
    private NClickAction myCurrentAction;
    private Grid myGrid;
    private Coordinate mySelectedCoordinate;

    public GridController (Grid grid) {
        myGrid = grid;
        mySelectedCoordinate = new Coordinate(0, 0);
    }

    public void doCommand (String commandName, int numClicks, Object ... args) {
        myCurrentAction = new NClickAction(numClicks, commandName, myGrid, args);
        myCurrentAction.click(mySelectedCoordinate);
    }

    @Override
    public void gridClicked (Coordinate c) {
        mySelectedCoordinate = c;
        if (myCurrentAction != null)
            myCurrentAction.click(c);
    }

    public void clearCurrentCommand () {
        myCurrentAction = null;
    }
}
