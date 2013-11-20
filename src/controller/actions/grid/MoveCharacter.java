package controller.actions.grid;

import java.util.List;
import grid.Coordinate;
import grid.Grid;


public class MoveCharacter extends AbstractGridCommand {

    public MoveCharacter (Grid grid,List<Coordinate> selectedCoordinates) {
        super(grid, selectedCoordinates);
    }

    @Override
    public void undo () {
        myGrid.doMove(mySelectedCoordinates.get(1), mySelectedCoordinates.get(0));
    }

    @Override
    public void execute () {
        myGrid.doMove(mySelectedCoordinates.get(0), mySelectedCoordinates.get(1));
    }

}
