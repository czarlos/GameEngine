package view.editor;

import grid.Coordinate;
import java.util.List;
import javax.swing.JPanel;
import controllers.WorldManager;
import view.player.InfoPanel;
import view.player.SelectedInfoPanel;


@SuppressWarnings("serial")
public class SelectedInfoEditorPanel extends SelectedInfoPanel {

    private WorldManager myWorldManager;
    private Coordinate myCoordinate;
    
    public SelectedInfoEditorPanel (WorldManager wm) {
        super();
    }

    @Override
    public JPanel makeActionPane (List<String> actionInfo) {
        return new InfoPanel(actionInfo);
    }
    
    public void setCoordinate (Coordinate c){
        myCoordinate = c;
    }
}
