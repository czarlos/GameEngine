package view;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import controllers.Manager;
import view.canvas.GridCanvas;


@SuppressWarnings("serial")
public class GridWithSide extends TwoColumnPanel {
    protected GridCanvas myGrid;

    public GridWithSide (Manager manager) {
        myGrid = new GridCanvas(manager);
        JScrollPane scrollGrid =
                new JScrollPane(myGrid,
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollGrid.setLayout(new ScrollPaneLayout());
        addToMainColumn(scrollGrid);
    }

    @Override
    public void revalidate () {
        super.revalidate();

        super.revalidate();
        if (myGrid != null) {
            myGrid.repaint();
        }
    }
}
