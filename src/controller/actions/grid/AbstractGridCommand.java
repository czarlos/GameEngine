package controller.actions.grid;

import java.util.List;
import grid.Coordinate;
import controller.actions.AbstractUndoableCommand;
import controllers.GameManager;


public abstract class AbstractGridCommand extends AbstractUndoableCommand {
    protected GameManager myManager;
    protected List<Coordinate> mySelectedCoordinates;

    public AbstractGridCommand (GameManager manager, List<Coordinate> selectedCoordinates) {
        myManager = manager;
        mySelectedCoordinates = selectedCoordinates;
    }
}
