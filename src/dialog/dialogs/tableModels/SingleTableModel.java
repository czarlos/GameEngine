package dialog.dialogs.tableModels;

@SuppressWarnings("serial")
public abstract class SingleTableModel extends GameTableModel {

    public abstract void loadObject (Object object);

    public abstract Object getObject ();
}
