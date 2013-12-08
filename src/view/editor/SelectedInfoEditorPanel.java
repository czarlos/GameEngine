package view.editor;

import java.util.List;
import javax.swing.JPanel;
import view.player.InfoPanel;
import view.player.SelectedInfoPanel;


@SuppressWarnings("serial")
public class SelectedInfoEditorPanel extends SelectedInfoPanel {

    public SelectedInfoEditorPanel () {
        super();
    }

    @Override
    public JPanel makeActionPane (List<String> actionInfo) {
        return new InfoPanel(actionInfo);
    }
}
