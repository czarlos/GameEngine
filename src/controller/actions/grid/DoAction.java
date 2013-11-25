package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.GameManager;


public class DoAction extends AbstractGridCommand {

    protected int myActionId;

    public DoAction (GameManager manager, int actionId, List<Coordinate> selectedCoordinates) {
        super(selectedCoordinates,manager);
        myActionId = actionId;
    }

    @Override
    public void undo () {
    }

    @Override
    public void execute () {
        myManager.doAction(mySelectedCoordinates.get(0),mySelectedCoordinates.get(1),myActionId);
    }

}
