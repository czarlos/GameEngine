package dialog.dialogs.tableModels;

import grid.GridConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import stage.WinCondition;
import team.Team;

@SuppressWarnings("serial")
public class TeamTableModel extends GameTableModel {

	public TeamTableModel() {
		String[] names = { "Name", "Graphic", "Gold", "Win Conditions",
				"Conditions needed to win", "Human?" };
		myName = GridConstants.TEAM;
		setColumnNames(names);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadObject(Object object) {
		myList.clear();
		List<Team> list = (List<Team>) object;
		for (int i = 0; i < list.size(); i++) {

			Object[] array = new Object[myColumnNames.length + 1];
			Team t = (Team) list.get(i);
			array[0] = t.getName();
			array[1] = new File(t.getImagePath());
			array[2] = t.getGold();
			array[3] = t.getWinCondition();
			array[4] = t.getWinCondition().getConditionsNeeded();
			array[5] = t.isHuman();
			array[6] = i;
			myList.add(array);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return (column < 6);
	}

	@Override
	public List<?> getObject() {
		List<Team> ret = new ArrayList<Team>();
		for (Object[] row : myList) {
			Team t = new Team();
			t.setName((String) row[0]);
			t.setImagePath((String) ((File) row[1]).getPath());
			t.setGold((int) row[2]);
			WinCondition wc = (WinCondition) row[3];
			wc.setConditionsNeeded((int) row[4]);
			t.setWinCondition(wc);
			t.setIsHuman((boolean) row[5]);
			t.setEditingID((int) row[6]);
			ret.add(t);
		}

		// checking to make sure there is always one list.
		if (ret.size() == 0) {
			Team t = new Team();
			t.setName("default");
			t.setImagePath("resources/Grass.png");
			t.setGold(0);
			t.setWinCondition(new WinCondition());
			t.setIsHuman(true);
			t.setEditingID(0);
			ret.add(t);
		}
		return ret;
	}

	@Override
	public Object[] getNew() {
		Object[] array = new Object[myColumnNames.length];
		array[0] = "New Team";
		array[1] = new File("resources/Grass.png");
		array[2] = 0;
		array[3] = new WinCondition();
		array[4] = 0;
		array[5] = false;

		return array;
	}
}
