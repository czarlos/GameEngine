package controller.actions.grid;

import grid.Coordinate;
import grid.Grid;
import controller.actions.AbstractUndoableCommand;


public abstract class AbstractGridCommand extends AbstractUndoableCommand {
    protected Grid myGrid;
    protected Coordinate mySelectedCoordinate;

    public AbstractGridCommand (Grid grid, Coordinate selectedCoordinate) {
        myGrid = grid;
        mySelectedCoordinate = selectedCoordinate;
    }
}
