package controller.editor;

import grid.Coordinate;
import java.util.ArrayList;
import java.util.List;
import reflection.Reflection;
import controller.actions.Command;

public class NClickAction {
    protected List<Coordinate> myCoordinates;
    protected String myAction;
    protected int myNumClicks;
    protected Object[] myArgs;
    
    public NClickAction(int numClicks,String action,Object... args){
        myCoordinates=new ArrayList<Coordinate>(numClicks);
        myAction=action;
        myNumClicks=numClicks;
        myArgs=args;
    }
    
    public void click(Coordinate coor){
        myCoordinates.add(coor);
        if(myCoordinates.size()==myNumClicks){
            doAction();
        }
    }
    
    private void doAction(){
        Command action=(Command)Reflection.createInstance(myAction, myArgs, myCoordinates);
        action.execute();
    }
}
