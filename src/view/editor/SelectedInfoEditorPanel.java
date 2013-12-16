package view.editor;

import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;
import view.player.InfoPanel;
import view.player.SelectedInfoPanel;


/**
 * Override of SelectedInfoPanel to show actions as an InfoPanel (as strings)
 * instead of an ActionInfoPanel (as buttons)
 *
 */
@SuppressWarnings("serial")
public class SelectedInfoEditorPanel extends SelectedInfoPanel {

    public SelectedInfoEditorPanel () {
        setMaximumSize(new Dimension(200,200));
    }

    @Override
    public JPanel makeActionPane (List<String> actionInfo) {
        return new InfoPanel(actionInfo);
    }
}
