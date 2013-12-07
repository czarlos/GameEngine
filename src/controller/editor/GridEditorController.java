package controller.editor;

import java.util.ArrayList;
import javax.swing.JTabbedPane;
import controllers.WorldManager;
import grid.Coordinate;
import grid.GridConstants;
import view.canvas.GridMouseListener;
import view.editor.StagePanel;
import view.editor.StageSidePanel;


public class GridEditorController implements GridMouseListener {

    private WorldManager myWM;
    private JTabbedPane myStagePanels;
    private ArrayList<StageSidePanel> myPanelList;

    public GridEditorController (WorldManager wm, JTabbedPane panel) {
        myWM = wm;
        myStagePanels = panel;
        myPanelList = new ArrayList<StageSidePanel>();
    }

    @Override
    public void gridClicked (Coordinate c) {
        int currentIndex = myStagePanels.getSelectedIndex();
        int id = myWM.getActiveID();
        if (id >= 0)
            myWM.place(myWM.getActiveType(), id, c);
        displayInfo(c, currentIndex);
    }

    private void displayInfo (Coordinate c, int index) {
        myPanelList.get(index).displayInformation(c);
            
    }

    public void addStageSidePanel (int i, StageSidePanel panel) {
        myPanelList.add(i, panel);
    }
}
