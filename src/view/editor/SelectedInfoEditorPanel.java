package view.editor;

import java.util.List;
import view.player.InfoPanel;
import view.player.SelectedInfoPanel;

@SuppressWarnings("serial")
public class SelectedInfoEditorPanel extends SelectedInfoPanel {

    public SelectedInfoEditorPanel () {
        super();
        
    }

    public void makeTabs (List<String> actionInfo, List<String> tileInfo,
                          List<String> objectInfo) {
       removeAll();

        if (objectInfo != null) {
            add("Object", new InfoPanel(objectInfo));
        }
        
        if (tileInfo != null) {
            add("Tile", new InfoPanel(tileInfo));
        }
        
        if (actionInfo != null) {

            add("Actions", new InfoPanel(actionInfo));
        }

        
        repaint();
    }
}
