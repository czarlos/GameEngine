package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.GameManager;
import controllers.WorldManager;

public class BeginDoAction extends AbstractGridCommand {

    protected String myActionName;
    public BeginDoAction (GameManager manager, List<Coordinate> selectedCoordinates, String actionName) {
        super(manager, selectedCoordinates);
        
    }
    @Override
    public void undo () {
    }
    @Override
    public void execute () {
        //myManager.beginAction(mySelectedCoordinates.get(0),myActionName);
    }

}
