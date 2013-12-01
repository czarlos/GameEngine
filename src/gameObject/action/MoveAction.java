package gameObject.action;

import gameObject.GameObject;
import gameObject.GameUnit;

public class MoveAction extends Action {
	public final static String MOVE_NAME = "MoveAction";

	public MoveAction() {
		super.setName(MOVE_NAME);
	}

	@Override
	public void doAction(GameUnit initiator, GameObject receiver) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidAction(GameUnit gameUnit, GameObject gameObject) {
		// TODO Auto-generated method stub
		return false;
	}

}
