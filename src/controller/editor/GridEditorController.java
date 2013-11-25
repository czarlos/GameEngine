package controller.editor;

import controllers.WorldManager;
import grid.Coordinate;
import view.canvas.GridMouseListener;
import view.editor.StageEditorPanel;


public class GridEditorController implements GridMouseListener {

    private WorldManager myWM;
    private StageEditorPanel myEditorPanel;
    
    public GridEditorController (WorldManager wm, StageEditorPanel panel) {
        myWM = wm;
        myEditorPanel = panel;
    }

    @Override
    public void gridClicked (Coordinate c) {
        int currentIndex = myEditorPanel.getSelectedIndex();
        int id = myWM.getActiveID(currentIndex);
        if (myWM.getActiveType(currentIndex) != null) {
            switch (myWM.getActiveType(currentIndex).toLowerCase()) {
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
