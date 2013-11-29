package view.player;

import grid.Coordinate;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.concurrent.Semaphore;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import controller.editor.GridController;
import controllers.GameManager;
import view.canvas.GridCanvas;


@SuppressWarnings("serial")
public class StagePlayerPanel extends JPanel {
    private JScrollPane mySidePanel;
    private GridController myController;
    private GameManager myManager;
    private GridCanvas myGridCanvas;
    private TurnActions myTurnActions;

    public StagePlayerPanel (GameManager manager, Semaphore sem) {
        myManager = manager;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 4;
        c.gridwidth = 4;
        c.weightx = 1;
        c.weighty = 1;
        myGridCanvas = new GridCanvas(myManager);
        add(myGridCanvas, c);
        myController = new GridController(myManager, this);
        myGridCanvas.addGridMouseListener(myController);

        myTurnActions = new TurnActions(sem);
        c.gridx = 0;
        c.gridy = 4;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 5;
        c.weightx = 0;
        c.weighty = 0;
        add(myTurnActions, c);
        repaint();
        revalidate();
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

    public void updatedSelectedInfoPanel (Coordinate c) {
        SelectedInfoPanel infoPanel = new SelectedInfoPanel(myController);
        infoPanel.makeTabs(myManager.getActions(c), myManager.generateTileInfoList(c),
                           myManager.generateObjectInfo(c));
        infoPanel.setPreferredSize(new Dimension(300, 500));
        if (mySidePanel != null) {
            remove(mySidePanel);
        }

        mySidePanel = new JScrollPane(infoPanel);
        mySidePanel.setMinimumSize(new Dimension(300, 0));
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 5;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.gridheight = 4;
        cons.gridwidth = 1;
        cons.weightx = 0;
        cons.weighty = 0;
        add(mySidePanel, cons);
        revalidate();
    }
}
