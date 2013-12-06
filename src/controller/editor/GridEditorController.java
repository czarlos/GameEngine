package controller.editor;

import javax.swing.JTabbedPane;
import controllers.WorldManager;
import grid.Coordinate;
import view.canvas.GridMouseListener;


public class GridEditorController implements GridMouseListener {

    private WorldManager myWM;
    private JTabbedPane myStagePanels;

    public GridEditorController (WorldManager wm, JTabbedPane panel) {
        myWM = wm;
        myStagePanels = panel;
    }

    @Override
    public void gridClicked (Coordinate c) {
        int currentIndex = myStagePanels.getSelectedIndex();
        int id = myWM.getActiveID(currentIndex);
        if (id == -1)
            displayInfo(c);
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
                case "item":
                    myWM.placeItem(id, c.getX(), c.getY());
                    break;
            }
        }

    }
    
    //TODO: display relevant information about tile/object (edit as well?)
    private void displayInfo(Coordinate c){
        System.out.println("WILL BE DISPLAYING STUFF");
        
    }

}
