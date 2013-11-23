package view.player;

import grid.Coordinate;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import controller.editor.GridController;
import controllers.WorldManager;
import view.canvas.GridCanvas;


public class StagePlayerPanel extends JPanel {
    private JPanel mySidePanel;
    private GridController myController;
    private WorldManager myWM;

    public StagePlayerPanel (String stageName, WorldManager wm) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridCanvas gc = new GridCanvas(wm);
        myController = new GridController(wm);
        gc.addGridMouseListener(myController);
        add(gc);
        mySidePanel = new SelectedInfoPanel(myController);
        mySidePanel.setMaximumSize(new Dimension(200, 500));
        add(mySidePanel);
        repaint();
    }
    
    public void updatedSelectedInfoPanel(Coordinate c){
        SelectedInfoPanel infoPanel=new SelectedInfoPanel(myController);
        infoPanel.makeTabs(myWM.get, objectInfo, actions);
        remove(mySidePanel);
        mySidePanel=infoPanel;
        add(mySidePanel;)
    }

}
