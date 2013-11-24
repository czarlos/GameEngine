package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.WorldManager;

public class BeginDoAction extends AbstractGridCommand {

    protected String myActionName;
    public BeginDoAction (WorldManager wm, List<Coordinate> selectedCoordinates, String actionName) {
        super(wm, selectedCoordinates);
        
    }
    @Override
    public void undo () {
    }
    @Override
    public void execute () {
        myWM.beginAction(mySelectedCoordinates.get(0),myActionName);
    }

}
