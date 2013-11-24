package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.GameManager;


public class DoAction extends AbstractGridCommand {

    protected String myActionName;

    public DoAction (GameManager manager, List<Coordinate> selectedCoordinates, String actionName) {
        super(manager, selectedCoordinates);
        myActionName = actionName;
    }

    @Override
    public void undo () {
    }

    @Override
    public void execute () {
        // myWM.doAction(mySelectedCoordinates.get(0),mySelectedCoordinates.get(1),myActionName);
    }

}
