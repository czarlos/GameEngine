package controller.editor;

import grid.Coordinate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import reflection.Reflection;
import controller.actions.Command;
import controller.actions.CommandShell;


public class NClickAction {
    protected List<Coordinate> myCoordinates;
    protected String myAction;
    protected int myNumClicks;
    protected Object[] myArgs;

    protected Map<Integer, CommandShell> precursorCommands;

    public NClickAction (int numClicks, String action, Object ... args) {
        myCoordinates = new ArrayList<Coordinate>(numClicks);
        myAction = action;
        myNumClicks = numClicks;
        myArgs = args;
    }

    public void setArgs (Object[] args) {
        myArgs = args;
    }

    public void addPrecursorCommand (int numClicks, String action, Object ... args) {
        if (precursorCommands == null) {
            precursorCommands = new HashMap<>();
        }
        precursorCommands.put(numClicks, new CommandShell(action, args));
    }

    public void click (Coordinate coor) {
        myCoordinates.add(coor);
        if (myCoordinates.size() == myNumClicks) {
            doAction();
        }
    }

    private void doAction () {
        Command action = (Command) Reflection.createInstance(myAction, myArgs, myCoordinates);
        if (precursorCommands.containsKey(myCoordinates.size())) {
            CommandShell pCmd = precursorCommands.get(myCoordinates.size());
            doAction(pCmd.getCommandName(), ArrayUtils.addAll(myArgs, pCmd.getArguments()));
        }

        if (myCoordinates.size() == myNumClicks) {
            doAction(myAction, myArgs);
        }
    }

    protected void doAction (String actionName, Object[] args) {
        Command action = (Command) Reflection.createInstance(actionName, args, myCoordinates);
        action.execute();
    }

}
