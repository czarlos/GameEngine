package controller.actions.grid;

import grid.Coordinate;
import grid.Grid;
import java.util.List;
import controller.actions.Command;


public class BeginMoveCharacter implements Command {

    protected Grid myGrid;
    protected List<Coordinate> mySelectedCoordinates;

    public BeginMoveCharacter (Grid grid, List<Coordinate> selectedCoordinates) {
        myGrid = grid;
        mySelectedCoordinates = selectedCoordinates;
    }

    @Override
    public void execute () {
        System.out.println(mySelectedCoordinates.get(0));
        myGrid.beginMove(mySelectedCoordinates.get(0));
    }

}
