package controller.actions.grid;

import java.util.List;
import grid.Coordinate;
import controller.actions.AbstractUndoableCommand;
import controllers.WorldManager;


public abstract class AbstractGridCommand extends AbstractUndoableCommand {
    protected WorldManager myWM;
    protected List<Coordinate> mySelectedCoordinates;

    public AbstractGridCommand (WorldManager wm, List<Coordinate> selectedCoordinates) {
        myWM = wm;
        mySelectedCoordinates = selectedCoordinates;
    }
}
