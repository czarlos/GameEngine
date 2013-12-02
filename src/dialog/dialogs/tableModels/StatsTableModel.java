package dialog.dialogs.tableModels;

import gameObject.Stats;
import grid.GridConstants;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class StatsTableModel extends GameTableModel {

	public StatsTableModel() {
		super();
		String[] names = { "Stat", "Value" };
		setColumnNames(names);
		myName = GridConstants.STATS;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return col > 0;
	}

	public void loadObject(Object stats) {
		Stats s = (Stats) stats;
		Map<String, Integer> map = s.getStats();
		myList.clear();
		for (String stat : map.keySet()) {
			Object[] row = new Object[myColumnNames.length];
			row[0] = stat;
			row[1] = map.get(stat);
			addNewRow(row);
		}
	}

	public Object getObject() {
		Stats ret = new Stats();
		Map<String, Integer> myMap = new HashMap<String, Integer>();
		for (Object[] row : myList) {
			myMap.put((String) row[0], (Integer) row[1]);
		}

		ret.setStats(myMap);
		return ret;
	}

	@Override
	public Object[] getNew() {
		// TODO: pop up a message telling users to use the master stats editor
		// to add/remove global stats
		return null;
	}

	@Override
	public void removeRow(int index) {
		// TODO: same as above;
	}
}
