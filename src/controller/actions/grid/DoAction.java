package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.WorldManager;

public class DoAction extends AbstractGridCommand {

    protected String myActionName;
    public DoAction (WorldManager wm, List<Coordinate> selectedCoordinates, String actionName) {
        super(wm, selectedCoordinates);
        myActionName=actionName;
    }

    @Override
    public void undo () {
    }

    @Override
    public void execute () {
        myWM.doAction(mySelectedCoordinates.get(0),mySelectedCoordinates.get(1),myActionName);
    }

}
