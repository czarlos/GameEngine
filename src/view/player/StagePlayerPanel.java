package view.player;

import grid.Coordinate;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import controller.editor.GridController;
import controllers.GameManager;
import view.canvas.GridCanvas;


public class StagePlayerPanel extends JPanel {
    private JPanel mySidePanel;
    private GridController myController;
    private GameManager myManager;

    public StagePlayerPanel (String stageName, GameManager manager) {
        myManager=manager;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridCanvas gc = new GridCanvas(myManager);
        myController = new GridController(myManager);
        gc.addGridMouseListener(myController);
        add(gc);
        mySidePanel = new SelectedInfoPanel(myController);
        mySidePanel.setMaximumSize(new Dimension(200, 500));
        add(mySidePanel);
        repaint();
    }

    @SuppressWarnings("unchecked")
    public void updatedSelectedInfoPanel (Coordinate c) {
        SelectedInfoPanel infoPanel = new SelectedInfoPanel(myController);
        infoPanel.makeTabs(myManager.getActions(c), myManager.generateTileInfoList(c),
                           myManager.generateObjectInfo(c));
        remove(mySidePanel);
        mySidePanel = infoPanel;
        add(mySidePanel);
    }

}
