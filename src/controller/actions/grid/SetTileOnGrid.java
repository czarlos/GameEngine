package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.WorldManager;

public class SetTileOnGrid extends AbstractGridCommand {

    
    public SetTileOnGrid (WorldManager wm, List<Coordinate> selectedCoordinates) {
        super(wm, selectedCoordinates);
    }

    @Override
    public void undo () {

    }

    @Override
    public void execute () {
        myWM.setTile(myWM.getActiveID(), mySelectedCoordinates.get(0).getX(), mySelectedCoordinates.get(0).getY());

    }

}
