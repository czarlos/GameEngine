package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.GameManager;

/**
 * Calls BeginDoAction in GameManager. This class is very specific
 * because it instantiates a method call to be used in a command 
 * pattern. 
 *
 */
public class BeginDoAction extends AbstractGridCommand {

    protected int myActionId;

    /**
     * Instantiate BeginDoActions 
     * @param manager GameManager to call beginAction on.
     * @param actionId Id of action to be called. Actions are implemented in
     * Manager and effect a users stats. They only difference for the purposes of this class
     * is what action is being called.
     * @param selectedCoordinates
     */
    public BeginDoAction (GameManager manager, int actionId,
                          List<Coordinate> selectedCoordinates) {
        super(selectedCoordinates, manager);
        myActionId = actionId;

    }

    @Override
    public void undo () {
    }

    /**
     * Call GameManager.doAction using the first coordinate in the coordinate list.
     */
    @Override
    public void execute () {
        myManager.beginAction(mySelectedCoordinates.get(0), myActionId);
    }

}
