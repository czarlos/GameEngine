package controller.actions.grid;

import java.util.List;
import controllers.WorldManager;
import grid.Coordinate;


public class MoveCharacter extends AbstractGridCommand {

    public MoveCharacter (WorldManager wm, List<Coordinate> selectedCoordinates) {
        super(wm, selectedCoordinates);

    }

    @Override
    public void undo () {
        myWM.doMove(mySelectedCoordinates.get(1), mySelectedCoordinates.get(0));
    }

    @Override
    public void execute () {
        myWM.doMove(mySelectedCoordinates.get(0), mySelectedCoordinates.get(1));
    }

}
