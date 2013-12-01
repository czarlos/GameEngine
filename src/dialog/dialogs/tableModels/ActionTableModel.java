package dialog.dialogs.tableModels;

import gameObject.action.Action;
import gameObject.action.CombatAction;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ActionTableModel extends GameTableModel {

	public ActionTableModel() {
		String[] names = { "Name" };
		myName = "List";
		setColumnNames(names);
	}

	@Override
	public Object[] getNew() {
		Object[] ret = new Object[myColumnNames.length];
		ret[0] = "New Action";
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadObject(Object object) {
		List<Action> list = (List<Action>) object;
		for (Action a : list) {
			Object[] array = new Object[myColumnNames.length];
			array[0] = a.getName();
			addNewRow(array);
		}
	}

	@Override
	public Object getObject() {
		List<Action> list = new ArrayList<Action>();
		for (Object[] row : myList) {
			Action a = new CombatAction();
			a.setName((String) row[0]);
			list.add(a);
		}
		return list;
	}

}
