package controller.editor;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import controller.actions.grid.AbstractGridCommand;
import grid.Coordinate;
import grid.Grid;
import view.canvas.GridMouseListener;


public class GridController implements GridMouseListener {

    private Coordinate mySelectedCoordinate;
    private Grid myGrid;
    private static Map<String, Class<? extends AbstractGridCommand>> myCommands;

    static{
        myCommands=new HashMap<>();
    }
    public static void registerGridCommand (String commandName,
                                            Class<? extends AbstractGridCommand> command) {
        myCommands.put(commandName, command);
    }

    public void doCommand (String commandName) {
        try {
            AbstractGridCommand command =
                    myCommands.get(commandName).getConstructor(Grid.class, Coordinate.class)
                            .newInstance(myGrid, mySelectedCoordinate);
        }
        catch (IllegalArgumentException | SecurityException | InstantiationException
                | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gridClicked (Coordinate c) {
        mySelectedCoordinate = c;
    }
}
