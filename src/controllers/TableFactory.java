package controllers;

import grid.GridConstants;
import java.util.HashMap;
import java.util.Map;
import dialog.dialogs.tableModels.GameTableModel;
import dialog.dialogs.tableModels.MasterStatsTableModel;
import dialog.dialogs.tableModels.MultipleTableModel;
import dialog.dialogs.tableModels.StatsTableModel;
import dialog.dialogs.tableModels.TileTableModel;


public class TableFactory {

    private Map<String, GameTableModel> masterTableMap;

    public TableFactory () {
        refreshTables();
    }

    public void refreshTables () {
        masterTableMap = new HashMap<String, GameTableModel>();
        masterTableMap.put(GridConstants.TILE, new TileTableModel());
        masterTableMap.put(GridConstants.STATS, new StatsTableModel());
        masterTableMap.put(GridConstants.MASTERSTATS, new MasterStatsTableModel());
    }

    public GameTableModel makeTableModel (String type) {
        GameTableModel gtm = masterTableMap.get(type);

        try {
            return gtm.getClass().newInstance();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
