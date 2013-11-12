package controller.actions.grid;

import grid.Coordinate;
import grid.Grid;
import grid.Tile;


public class SetTileActive extends AbstractGridCommand {

    public SetTileActive (Grid grid, Coordinate selectedCoordinate) {
        super(grid, selectedCoordinate);
    }

    @Override
    public void undo () {
        Tile tile = myGrid.getTile(mySelectedCoordinate.getX(), mySelectedCoordinate.getY());
        tile.setActive(false);
    }

    @Override
    public void execute () {
        Tile tile = myGrid.getTile(mySelectedCoordinate.getX(), mySelectedCoordinate.getY());
        tile.setActive(!tile.isActive());
    }

}
