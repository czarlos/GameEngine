package dialog.dialogs.tableModels;

import java.util.List;
import view.Customizable;


@SuppressWarnings("serial")
public abstract class MultipleTableModel extends GameTableModel {

    public abstract void addPreviouslyDefined (List<Customizable> list);
    public abstract List<Customizable> getObjects ();
}
