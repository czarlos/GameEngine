package view.player;

import grid.Coordinate;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import controller.editor.GridController;
import controllers.GameManager;
import view.canvas.GridCanvas;


public class StagePlayerPanel extends JPanel {
    private JPanel mySidePanel;
    private GridController myController;
    private GameManager myManager;
    private GridCanvas myGridCanvas;

    public StagePlayerPanel (String stageName, GameManager manager) {
        myManager = manager;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        myGridCanvas = new GridCanvas(myManager);
        myController = new GridController(myManager, this);
        myGridCanvas.addGridMouseListener(myController);
        add(myGridCanvas);
        mySidePanel = new SelectedInfoPanel(myController);
        mySidePanel.setMaximumSize(new Dimension(100, 500));
        add(mySidePanel);
        repaint();
    }

    @Override
    public void revalidate () {
        super.revalidate();
        if (myGridCanvas != null) {
            myGridCanvas.repaint();
        }

        if (mySidePanel != null) {
            mySidePanel.repaint();
        }

    }

    @SuppressWarnings("unchecked")
    public void updatedSelectedInfoPanel (Coordinate c) {
        SelectedInfoPanel infoPanel = new SelectedInfoPanel(myController);
        infoPanel.makeTabs(myManager.getActions(c), myManager.generateTileInfoList(c),
                           myManager.generateObjectInfo(c));
        remove(mySidePanel);
        mySidePanel = infoPanel;
        add(mySidePanel);
        revalidate();
    }

}
