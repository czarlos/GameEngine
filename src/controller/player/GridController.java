package controller.player;

import controllers.GameManager;
import grid.Coordinate;
import view.canvas.GridMouseListener;
import view.player.StagePlayerPanel;

/**
 * This class triggers program actions caused by users interacting with the grid.
 * This class listens for clicks on the grid. After a certain number of clicks, this 
 * class triggers a specific action (method call). These actions are encapsulated by
 * N-Click Action. This class also forces the grid to redraw itself after 
 * an action is triggered to make sure the most current state of the grid is being 
 * painted for the user. Class also updates the side panel of the view
 * that displays information on the currently selected (most recently clicked) coordinate.
 *
 */
public class GridController implements GridMouseListener {
    private NClickAction myCurrentAction;
    private GameManager myManager;
    private Coordinate mySelectedCoordinate;
    private StagePlayerPanel myView;

    /**
     * @param manager GridManager to hand into N-Click Actions 
     * (used to perform abstract grid commands).
     * 
     * @param view StagePlayerPanel to update with currently selected coordinate
     * and to refresh after N-Click Actions are performed.
     */
    public GridController (GameManager manager, StagePlayerPanel view) {
        myManager = manager;
        mySelectedCoordinate = new Coordinate(0, 0);
        myView = view;
    }

    /**
     * Tell grid to execute a certain AbstractGridCommmand after a certain number of clicks.
     * Please note that calling doCommand will treat the last click on the grid as the first click
     * toward your command, not the first click after your command is created. This is to enable 
     * BeginDoAction and DoAction to be created when their respective buttons are clicked in the GUI, not
     * when the grid is clicked. (If I am performing an action, the program can't create a DoAction instance until
     * I click the button representing the action I want to perform. But at that point, I've already selected a coordinate
     * from the grid. That is why doCommand performs a click on the last selected coordinate)
     * @param commandName
     * @param numClicks
     * @param args
     */
    public void doCommand (String commandName, int numClicks, Object ... args) {
        myCurrentAction = new NClickAction(numClicks, commandName, myManager,
                                           args);
        myCurrentAction.click(mySelectedCoordinate);
    }

    /**
     * Overload of doAction that takes in an already made NClickAction. This method 
     * feeds in GameManager and selected coordinates into this pre-existing NClickAciton.
     * Click is immediately performed when setting this action. Please see explanation above.
     * @param action NClickAction to be performed.
     */
    public void doCommand (NClickAction action) {
        myCurrentAction = action;
        Object[] args = { myManager, action.getCurrentArgs() };
        action.setArgs(args);
        myCurrentAction.click(mySelectedCoordinate);
        myView.revalidate();
    }

    /**
     * When the grid is clicked, click current NClickAction and update SelectedInfoPanel with current coordinate.
     */
    @Override
    public void gridClicked (Coordinate c) {

        mySelectedCoordinate = c;

        if (myCurrentAction != null)
            myCurrentAction.click(c);

        myView.updatedSelectedInfoPanel(c);
        myView.revalidate();
    }

    /**
     * Set current action being counted towards to null.
     */
    public void clearCurrentCommand () {
        myCurrentAction = null;
    }

    /**
     * Retrieve the most recently selected coordinate on the grid.
     * @return
     */
    public Coordinate getCurrentCoordinate () {
        return mySelectedCoordinate;
    }

}
