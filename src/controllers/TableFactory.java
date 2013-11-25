package controllers;

import java.util.HashMap;
import java.util.Map;
import dialog.GameTableModel;
import dialog.TileTableModel;


public class TableFactory {

    private Map<String, GameTableModel> masterTableMap;

    public TableFactory () {
        refreshTables();
    }

    public void refreshTables () {
        masterTableMap = new HashMap<String, GameTableModel>();
        masterTableMap.put("Tile", new TileTableModel());
    }

    public GameTableModel makeTableModel (String s) {
        GameTableModel gtm = masterTableMap.get(s);

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
