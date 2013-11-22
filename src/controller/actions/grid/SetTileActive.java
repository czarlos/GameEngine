package controller.actions.grid;

import java.util.ArrayList;
import java.util.List;
import grid.Coordinate;
import grid.Grid;
import grid.Tile;


public abstract class SetTileActive extends AbstractGridCommand {



    public SetTileActive (Grid grid, List<Coordinate> selectedCoordinates) {
        super(grid, (ArrayList<Coordinate>) selectedCoordinates);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void undo () {
    }

    @Override
    public void execute () {
    }

}
