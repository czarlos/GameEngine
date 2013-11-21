package controller.actions.grid;

import java.util.ArrayList;
import java.util.List;
import controllers.WorldManager;
import grid.Coordinate;


public abstract class SetTileActive extends AbstractGridCommand {



    public SetTileActive (WorldManager wm, List<Coordinate> selectedCoordinates) {
        super(wm, (ArrayList<Coordinate>) selectedCoordinates);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void undo () {
    }

    @Override
    public void execute () {
    }

}
