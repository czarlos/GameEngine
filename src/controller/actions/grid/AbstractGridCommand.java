package controller.actions.grid;

import java.util.List;
import grid.Coordinate;
import controller.actions.AbstractUndoableCommand;
import controllers.GameManager;

/**
 * This class encapsulates actions that can be performed on the grid. All commands
 * inheriting from AbstractGridCommand require GameManager to perform actions on the
 * grid and a list of coordinates representing where the grid has been clicked. 
 *
 */
public abstract class AbstractGridCommand extends AbstractUndoableCommand {
    protected GameManager myManager;
    protected List<Coordinate> mySelectedCoordinates;

    public AbstractGridCommand (List<Coordinate> selectedCoordinates,
                                GameManager manager) {
        myManager = manager;
        mySelectedCoordinates = selectedCoordinates;
    }
}
