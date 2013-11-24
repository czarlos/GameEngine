package src.view.player;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import controller.editor.GridController;
import controllers.WorldManager;
import view.canvas.GridCanvas;


public class StagePlayerPanel extends JPanel {
    private JPanel mySidePanel;

    public StagePlayerPanel (String stageName, WorldManager wm) {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        GridCanvas gc = new GridCanvas(wm);
        GridController gcontrol = new GridController(wm);
        gc.addGridMouseListener(gcontrol);
        add(gc);
        mySidePanel = new PlayerControlPanel(gcontrol);
        mySidePanel.setMaximumSize(new Dimension(200, 500));
        add(mySidePanel);
        repaint();
    }

}
