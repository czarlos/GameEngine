package view.player;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;


public abstract class ScrollableListPane extends JPanel {

    protected final int DATA_HEIGHT = 60;
    protected final int WIDTH = 300;

    public ScrollableListPane () {
        super();
    }
    
    public ScrollableListPane(List<String> data){
        populate(data);
    }

    public ScrollableListPane (LayoutManager layout) {
        super(layout);
    }

    public abstract void populate (List<String> data);

    public ScrollableListPane (boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public ScrollableListPane (LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }



}
