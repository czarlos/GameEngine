package dialog.dialogs.tableModels;

import java.util.List;


@SuppressWarnings("serial")
public abstract class MultipleTableModel extends GameTableModel {

    public abstract void addObjects (List<?> list);

    public abstract List<?> getObjects ();
}
