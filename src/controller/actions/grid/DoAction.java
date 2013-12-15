package controller.actions.grid;

import grid.Coordinate;
import java.util.List;
import controllers.GameManager;

/**
 * Class to encapsulate calling GameManager.doAction().
 *
 */
public class DoAction extends AbstractGridCommand {

    protected int myActionId;

    /**
     * Instantiate DoAction.
     * 
     * @param manager GameManager to call doAction on.
     * 
     * @param actionId Id of action to call. This id is simply sent to the manager,
     * which handles what action is actually called.
     * 
     * @param selectedCoordinates List of coordinates. Must be two.
     * These represent the coordinates performing the action and the 
     * one the action is being performed on.
     */
    public DoAction (GameManager manager, int actionId,
                     List<Coordinate> selectedCoordinates) {
        super(selectedCoordinates, manager);
        myActionId = actionId;
    }

    @Override
    public void undo () {
    }

    /**
     * Call GameManager.doAction() using first two coordinates in mySelectedcoordinates.
     */
    @Override
    public void execute () {
        myManager.doAction(mySelectedCoordinates.get(0),
                           mySelectedCoordinates.get(1), myActionId);
    }

}
