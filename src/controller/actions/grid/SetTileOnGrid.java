package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.GameManager;


public class SetTileOnGrid extends AbstractGridCommand {

    public SetTileOnGrid (GameManager manager, List<Coordinate> selectedCoordinates) {
        super(manager, selectedCoordinates);
    }

    @Override
    public void undo () {

    }

    @Override
    public void execute () {
        //myManager.setTile(myManager.getActiveID(), mySelectedCoordinates.get(0).getX(), mySelectedCoordinates
        //        .get(0).getY());

    }

}
