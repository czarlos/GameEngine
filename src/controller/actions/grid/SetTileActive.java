package controller.actions.grid;

import java.util.List;
import controllers.GameManager;
import grid.Coordinate;


public abstract class SetTileActive extends AbstractGridCommand {

    public SetTileActive (GameManager manager, List<Coordinate> selectedCoordinates) {
        super(manager, selectedCoordinates);
    }

    @Override
    public void undo () {
    }

    @Override
    public void execute () {
    }

}
