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
        GameTableModel gtm = myWM.getItemTableModel(c);
        if (id >= 0)
            myWM.place(myWM.getActiveType(), id, c);
        displayInfo(c, currentIndex);
        if (id < 0 && gtm  != null){
            TableDialog dialog = new TableDialog(gtm, new ItemDialogListener(gtm, myWM, c), myWM);
            dialog.setVisible(true);
        }
        
    }

    private void displayInfo (Coordinate c, int index) {
        myPanelList.get(index).displayInformation(c);
            
    }
    
    public void addStageSidePanel (int i, StageSidePanel panel) {
        myPanelList.add(i, panel);
    }
    
    class ItemDialogListener implements ActionListener{
        
        private WorldManager myWM;
        private GameTableModel myGTM;
        private Coordinate myCurrentCoordinate;
        
        public ItemDialogListener(GameTableModel gtm, WorldManager wm, Coordinate c){
            myGTM = gtm;
            myWM = wm;
            myCurrentCoordinate = c;
        }
        
        @Override
        public void actionPerformed (ActionEvent e) {
            myWM.setItemTableModel(myGTM, myCurrentCoordinate);
        }
        
    }
}
