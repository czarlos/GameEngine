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
        else{
            myWM.place(myWM.getActiveType(currentIndex), id, c);
        }
    }

    // TODO: display relevant information about tile/object (edit as well?)
    private void displayInfo (Coordinate c) {
        System.out.println("WILL BE DISPLAYING STUFF");

    }

}
