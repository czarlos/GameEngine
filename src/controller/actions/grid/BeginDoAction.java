package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.GameManager;


public class BeginDoAction extends AbstractGridCommand {

    protected int myActionId;

    public BeginDoAction (GameManager manager, int actionId,
                          List<Coordinate> selectedCoordinates) {
        super(selectedCoordinates, manager);
        myActionId = actionId;
    }

    @Override
    public void undo () {
    }

    @Override
    public void execute () {
        myManager.beginAction(mySelectedCoordinates.get(0), myActionId);
    }
}
