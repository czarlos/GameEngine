package controller.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import controllers.WorldManager;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.GameTableModel;
import grid.Coordinate;
import grid.GridConstants;
import view.canvas.GridMouseListener;
import view.editor.StageEditorPanel;
import view.editor.StageSidePanel;


public class GridEditorController implements GridMouseListener {

    private WorldManager myWM;
    private JTabbedPane myStagePanels;
    private ArrayList<StageSidePanel> myPanelList;
    private ArrayList<StageEditorPanel> myEditorPanelList;
    private TableDialog myDialog;

    public GridEditorController (WorldManager wm, JTabbedPane panel) {
        myWM = wm;
        myStagePanels = panel;
        myPanelList = new ArrayList<StageSidePanel>();
        myEditorPanelList = new ArrayList<StageEditorPanel>();
    }

    @Override
    public void gridClicked (Coordinate c) {
        int currentIndex = myStagePanels.getSelectedIndex();
        int id = myWM.getActiveID();
        GameTableModel gtm = myWM.getItemTableModel(c);
        if (id >= 0)
            myWM.place(myWM.getActiveType(), id, c);
        displayInfo(c, currentIndex);
        if (id < 0 && gtm != null) {
            myDialog = new TableDialog(gtm, new ItemDialogListener(gtm, myWM, c));
            myDialog.setVisible(true);
        }

    }

    private void displayInfo (Coordinate c, int index) {
        myPanelList.get(index).displayInformation(c);

    }

    public void refreshEditorPanels () {
        for (StageEditorPanel p : myEditorPanelList) {
            p.drawTabs(GridConstants.DEFAULTTABTYPES);
        }
    }

    public void addStageSidePanel (int i, StageSidePanel panel) {
        myPanelList.add(i, panel);
    }

    public void addEditorPanel (StageEditorPanel panel) {
        myEditorPanelList.add(panel);
    }

    class ItemDialogListener implements ActionListener {

        private WorldManager myWM;
        private GameTableModel myGTM;
        private Coordinate myCurrentCoordinate;

        public ItemDialogListener (GameTableModel gtm, WorldManager wm, Coordinate c) {
            myGTM = gtm;
            myWM = wm;
            myCurrentCoordinate = c;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            myDialog.stopEditing();
            myDialog.setVisible(false);
            myWM.setItemTableModel(myGTM, myCurrentCoordinate);
        }
    }
}
