package controller.actions.grid;

import java.util.List;
import grid.Coordinate;
import grid.Grid;
import controller.actions.AbstractUndoableCommand;


public abstract class AbstractGridCommand extends AbstractUndoableCommand {
    protected Grid myGrid;
    protected List<Coordinate> mySelectedCoordinates;

    public AbstractGridCommand (Grid grid, List<Coordinate> selectedCoordinates) {
        myGrid = grid;
        mySelectedCoordinates = selectedCoordinates;
    }
}
