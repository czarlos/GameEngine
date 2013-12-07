package controllers;

import grid.GridConstants;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import dialog.dialogs.tableModels.CombatActionTableModel;
import dialog.dialogs.tableModels.TeamTableModel;
import dialog.dialogs.tableModels.UnitTableModel;
import dialog.dialogs.tableModels.GameTableModel;
import dialog.dialogs.tableModels.ItemTableModel;
import dialog.dialogs.tableModels.MasterStatsTableModel;
import dialog.dialogs.tableModels.ObjectTableModel;
import dialog.dialogs.tableModels.TileTableModel;


public class TableFactory {

    private Map<String, GameTableModel> masterTableMap;
    private EditorData myED;

    public TableFactory (EditorData ed) {
        myED = ed;
        refreshTables();
    }

    public void refreshTables () {
        masterTableMap = new HashMap<String, GameTableModel>();
        masterTableMap.put(GridConstants.TILE, new TileTableModel(myED));
        masterTableMap.put(GridConstants.MASTERSTATS,
                           new MasterStatsTableModel(myED));
        masterTableMap.put(GridConstants.GAMEOBJECT, new ObjectTableModel(myED));
        masterTableMap.put(GridConstants.GAMEUNIT, new UnitTableModel(myED));
        masterTableMap.put(GridConstants.ITEM, new ItemTableModel(myED));
        masterTableMap.put(GridConstants.ACTION, new CombatActionTableModel(myED));
        masterTableMap.put(GridConstants.TEAM, new TeamTableModel(myED));
    }

    public GameTableModel makeTableModel (String type) {
        GameTableModel gtm = masterTableMap.get(type);

        try {
            return (GameTableModel) gtm.getClass().getConstructors()[0].newInstance(myED);
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }
}
