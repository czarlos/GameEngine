package controller.player;

import grid.Coordinate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import reflection.Reflection;
import controller.actions.Command;
import controller.actions.CommandShell;

/**
 * Class to encapsulate commands that take effect after multiple clicks on the grid.
 * Performing an action, for example, requires two clicks. One click selects the coordinate
 * performing the action and the second click selects where to perform the action. In addition,
 * these actions are not triggered by a button press, but by a click on the grid. They must therefore
 * be able to trigger themselves after a certain number of clicks.
 * 
 * This class also contains the notion of precursor commands. These commands can be performed at any interval
 * leading up to the main action performed by a given instance of NClickAction. This is how calling beginAction
 * is implemented since it is called after one click, but must precede doAcion, the main command an NClickAction would be 
 * performing in this case.
 * 
 * TL;DR;-Precursor commands called at given intervals leading up to N clicks.
 * At N Clicks, main command called.
 *
 */
public class NClickAction {
    protected List<Coordinate> myCoordinates;
    protected String myAction;
    protected int myNumClicks;
    protected Object[] myArgs;

    protected Map<Integer, CommandShell> precursorCommands;

    /**
     * Initialize new NClick Action
     * @param numClicks Clicks after which to trigger NClickAction.
     * @param action Name of class that encapsulates command NClickAction should trigger.
     * @param args Any arguments this NClickAction needs to execute.
     */
    public NClickAction (int numClicks, String action, Object ... args) {
        myCoordinates = new ArrayList<Coordinate>(numClicks);
        myAction = action;
        myNumClicks = numClicks;
        myArgs = args;
    }

    /**
     * Set arguments NClickAction takes
     * @param args
     */
    public void setArgs (Object[] args) {
        myArgs = args;
    }
    
    /**
     * Method to return the argument array for this NClickAction.
     * @return
     */
    public Object[] getCurrentArgs(){
        return myArgs;
    }

 
    /**
     * Set up precursor action that will be triggered after a certain number of clicks before the 
     * main command is executed.
     * @param numClicks Number of clicks to trigger precursor action after.
     * @param action Name of action to execute
     * @param args Any arguments this action needs.
     */
    public void addPrecursorCommand (int numClicks, String action,
                                     Object ... args) {
        if (precursorCommands == null) {
            precursorCommands = new HashMap<>();
        }
        precursorCommands.put(numClicks, new CommandShell(action, args));
    }

    /**
     * Method to trigger a click on this NClickAction.
     * @param coor Coordinate where click occured
     */
    public void click (Coordinate coor) {
        myCoordinates.add(coor);
        checkActions();
    }

    /**
     * Protected method used internally to see if NClicks have occurred and trigger the corresponding actions. This
     */
    protected void checkActions () {
        if (precursorCommands.containsKey(myCoordinates.size())) {
            CommandShell pCmd = precursorCommands.get(myCoordinates.size());
            doAction(pCmd.getCommandName(),
                     //Coordinate list must be first to correctly initialize commands.
                     ArrayUtils.add(pCmd.getArguments(), 0, myArgs[0]));
        }

        if (myCoordinates.size() == myNumClicks) {
            doAction(myAction, myArgs);
        }
    }

    /**
     * Method used internally to trigger commands. Instantiates an instance of the command using reflection
     * and calls the execute method.
     * @param actionName Name of action to instantiate and execute.
     * @param args Any arguments the action needs
     */
    protected void doAction (String actionName, Object[] args) {
        Command action = (Command) Reflection.createInstance(actionName, args,
                                                             myCoordinates);
        action.execute();
    }
}
