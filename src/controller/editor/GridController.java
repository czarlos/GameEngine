package controller.editor;

import controllers.WorldManager;
import org.apache.commons.lang3.ArrayUtils;
import grid.Coordinate;
import view.canvas.GridMouseListener;


public class GridController implements GridMouseListener {
    private NClickAction myCurrentAction;
    private WorldManager myWM;
    private Coordinate mySelectedCoordinate;

    public GridController (WorldManager wm) {
        myWM = wm;
        mySelectedCoordinate = new Coordinate(0, 0);
    }

    public void doCommand (String commandName, int numClicks, Object ... args) {
        myCurrentAction = new NClickAction(numClicks, commandName, myWM, args);
        myCurrentAction.click(mySelectedCoordinate);
    }
    
    public void doCommand(NClickAction action,Object... args){
        myCurrentAction=action;
        args=ArrayUtils.add(args,0,myWM);
        action.setArgs(args);
        myCurrentAction.click(mySelectedCoordinate);
    }

    @Override
    public void gridClicked (Coordinate c) {

        mySelectedCoordinate = c;
        System.out.println(c.getX() + " " + c.getY());
        if (myCurrentAction != null)
            myCurrentAction.click(c);
    }

    public void clearCurrentCommand () {
        myCurrentAction = null;
    }
    
}
