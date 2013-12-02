package controller.editor;

import javax.swing.JTabbedPane;
import controllers.WorldManager;
import grid.Coordinate;
import view.canvas.GridMouseListener;

public class GridEditorController implements GridMouseListener {

	private WorldManager myWM;
	private JTabbedPane myStagePanels;

	public GridEditorController(WorldManager wm, JTabbedPane panel) {
		myWM = wm;
		myStagePanels = panel;
	}

	@Override
	public void gridClicked(Coordinate c) {
		int currentIndex = myStagePanels.getSelectedIndex();
		if (currentIndex == -1)
			return;
		int id = myWM.getActiveID(currentIndex);
		if (myWM.getActiveType(currentIndex) != null) {
			switch (myWM.getActiveType(currentIndex).toLowerCase()) {
			case "tile":
				myWM.setTile(id, c.getX(), c.getY());
				break;
			case "gameunit":
				myWM.placeUnit(id, c.getX(), c.getY());
				break;
			case "gameobject":
				myWM.placeObject(id, c.getX(), c.getY());
				break;
			}
		}

	}

}
