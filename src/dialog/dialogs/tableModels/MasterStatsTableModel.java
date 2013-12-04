package dialog.dialogs.tableModels;

import grid.GridConstants;


@SuppressWarnings("serial")
public class MasterStatsTableModel extends MapTableModel {

    public MasterStatsTableModel () {
        super();
        String[] names = { "Stat", "Default Value" };
        setColumnNames(names);
        myName = GridConstants.MASTERSTATS;
    }
}
