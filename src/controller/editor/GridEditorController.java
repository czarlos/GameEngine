package controller.editor;

import controllers.WorldManager;
import grid.Coordinate;
import view.canvas.GridMouseListener;


public class GridEditorController implements GridMouseListener {

    private WorldManager myWM;

    public GridEditorController (WorldManager wm) {
        myWM = wm;
    }

    @Override
    public void gridClicked (Coordinate c) {
        int id = myWM.getActiveID();
        if (myWM.getActiveType() != null) {
            switch (myWM.getActiveType().toLowerCase()) {
                case "tile":
                    myWM.setTile(id, c.getX(), c.getY());
                    break;
                case "gameunit":
                    myWM.placeUnit(id, c.getX(), c.getY());
                    break;
                case "gameobject":
                    myWM.placeObject(id, c.getX(), c.getY());
                    break;
            }
        }
    }

}
