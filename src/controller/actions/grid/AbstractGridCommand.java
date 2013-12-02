package controller.actions.grid;

import java.util.List;
import grid.Coordinate;
import controller.actions.AbstractUndoableCommand;
import controllers.GameManager;

public abstract class AbstractGridCommand extends AbstractUndoableCommand {
	protected GameManager myManager;
	protected List<Coordinate> mySelectedCoordinates;

	public AbstractGridCommand(List<Coordinate> selectedCoordinates,
			GameManager manager) {
		myManager = manager;
		mySelectedCoordinates = selectedCoordinates;
	}
}
