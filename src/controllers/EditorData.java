package controllers;

import gameObject.GameUnit;
import gameObject.Stat;
import gameObject.action.Action;
import gameObject.item.Item;
import grid.GridConstants;
import grid.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import dialog.dialogs.tableModels.GameTableModel;
import parser.JSONParser;
import stage.Stage;
import team.Team;
import view.Customizable;


/**
 * 
 * @author Leevi Gray
 * @author Ken McAndrews
 * 
 */
@JsonAutoDetect
public class EditorData {
    @JsonProperty
    private Map<String, List<?>> myDataMap;
    private JSONParser myParser;
    private TableFactory myTableFactory;

    // Only for use by deserializer
    public EditorData () {
        myParser = new JSONParser();
        myTableFactory = new TableFactory(this);
    }

    /**
     * Loads the data located in JSONs/folderName
     * 
     * @param folderName
     */
    public EditorData (String folderName) {
        myParser = new JSONParser();
        myTableFactory = new TableFactory(this);
        myDataMap = new HashMap<String, List<?>>();
        loadObjects(folderName);
    }

    /**
     * Load in the objects (eventually JTable data) from the JSONs
     * 
     * @param folderName
     */
    @SuppressWarnings("unchecked")
    private void loadObjects (String folderName) {
        for (String s : GridConstants.DEFAULTEDITTYPES) {
            List<Customizable> list = new ArrayList<Customizable>();
            list = myParser.createObject(folderName + "/" + s,
                                         new ArrayList<Customizable>().getClass());
            myDataMap.put(s, list);
        }
        // need to generalize this for all data types. tile, gameobject,
        // gameunit, item, team, action
    }

    /**
     * Returns the data associated with the type requested.
     * 
     * @param String
     *        type of Object
     * @return Collection of data
     */

    public List<?> get (String type) {
        return myDataMap.get(type);
    }

    public Customizable getObject (String type, int ID) {
        return (Customizable) myDataMap.get(type).get(ID);
    }

    public GameTableModel getTableModel (String type) {
        GameTableModel gtm = myTableFactory.makeTableModel(type);
        gtm.loadObject(myDataMap.get(type));
        return gtm;
    }

    @SuppressWarnings("unchecked")
    public void setData (GameTableModel gtm, Stage activeStage) {
        switch (gtm.getName()) {
            case GridConstants.ACTION:
                syncActions((List<Object>) gtm.getObject(), activeStage);
                break;
            case GridConstants.MASTERSTATS:
                syncStats((List<Object>) gtm.getObject(), activeStage);
                break;
            case GridConstants.TEAM:
                syncTeams((List<Team>) gtm.getObject(), activeStage);
                break;
            default:
                break;
        }

        myDataMap.put(gtm.getName(), (List<?>) gtm.getObject());
    }

    @SuppressWarnings("unchecked")
    private void syncActions (List<Object> newActions, Stage activeStage) {
        List<String> fullList = getNames(GridConstants.ACTION);
        List<String> removedNames = getNames(GridConstants.ACTION);
        List<GameUnit> editorUnitList = (List<GameUnit>) getTableModel("GameUnit").getObject();
        GameUnit[][] placedUnits = activeStage.getGrid().getGameUnits();
        Map<String, String> nameTranslationMap = new HashMap<>();

        for (Object action : newActions) {
            if (((Action) action).getLastIndex() > -1) {
                String prevName = fullList.get(((Action) action).getLastIndex());
                if (!((Action) action).getName().equals(prevName)) {
                    nameTranslationMap.put(prevName, ((Action) action).getName());
                }
                removedNames.remove(prevName);
            }
        }

        for (GameUnit unit : editorUnitList) {
            unit.syncActionsWithMaster(nameTranslationMap, removedNames);
        }

        for (int i = 0; i < placedUnits.length; i++) {
            for (int j = 0; j < placedUnits[i].length; j++) {
                if (placedUnits[i][j] != null) {
                    placedUnits[i][j].syncActionsWithMaster(nameTranslationMap, removedNames);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void syncStats (List<Object> newStats, Stage activeStage) {
        List<String> fullList = getNames(GridConstants.MASTERSTATS);
        List<String> removedNames = getNames(GridConstants.MASTERSTATS);
        List<GameUnit> editorUnitList = (List<GameUnit>) getTableModel("GameUnit").getObject();
        List<Tile> editorTileList = (List<Tile>) getTableModel("Tile").getObject();
        List<Item> editorItemList = (List<Item>) getTableModel("Item").getObject();
        GameUnit[][] placedUnits = activeStage.getGrid().getGameUnits();
        Tile[][] placedTiles = activeStage.getGrid().getTiles();
        Map<String, String> nameTranslationMap = new HashMap<>();

        // Find removed
        for (Object stat : newStats) {
            if (((Stat) stat).getLastIndex() > -1) {
                String prevName = fullList.get(((Stat) stat).getLastIndex());
                if (!((Stat) stat).getName().equals(prevName)) {
                    nameTranslationMap.put(prevName, ((Stat) stat).getName());
                }
                removedNames.remove(prevName);
            }
        }

        // GameUnit definitions
        for (GameUnit unit : editorUnitList) {
            for (String removedStat : removedNames) {
                unit.removeStat(removedStat);
                for (Item item : unit.getItems()) {
                    item.removeStat(removedStat);
                }
            }

            for (String oldName : nameTranslationMap.keySet()) {
                unit.changeStatName(oldName, nameTranslationMap.get(oldName));
                for (Item item : unit.getItems()) {
                    item.changeStatName(oldName, nameTranslationMap.get(oldName));
                }
            }

            for (Object stat : newStats) {
                if (!unit.containsStat(((Stat) stat).getName())) {
                    unit.addStat((Stat) stat);
                }
                for (Item item : unit.getItems()) {
                    if (!item.containsStat(((Stat) stat).getName())) {
                        item.addStat((Stat) stat);
                    }
                }
            }
        }

        // Placed GameUnit's
        for (int i = 0; i < placedUnits.length; i++) {
            for (int j = 0; j < placedUnits[i].length; j++) {
                if (placedUnits[i][j] != null) {
                    for (String removedStat : removedNames) {
                        placedUnits[i][j].removeStat(removedStat);
                        for (Item item : placedUnits[i][j].getItems()) {
                            item.removeStat(removedStat);
                        }
                    }

                    for (String oldName : nameTranslationMap.keySet()) {
                        placedUnits[i][j].changeStatName(oldName, nameTranslationMap.get(oldName));
                        for (Item item : placedUnits[i][j].getItems()) {
                            item.changeStatName(oldName, nameTranslationMap.get(oldName));
                        }
                    }

                    for (Object stat : newStats) {
                        if (!placedUnits[i][j].containsStat(((Stat) stat).getName())) {
                            placedUnits[i][j].addStat((Stat) stat);
                        }
                        for (Item item : placedUnits[i][j].getItems()) {
                            if (!item.containsStat(((Stat) stat).getName())) {
                                item.addStat((Stat) stat);
                            }
                        }
                    }
                }
            }
        }

        // Tile definitions
        for (Tile tile : editorTileList) {
            for (String removedStat : removedNames) {
                tile.removeStat(removedStat);
            }

            for (String oldName : nameTranslationMap.keySet()) {
                tile.changeStatName(oldName, nameTranslationMap.get(oldName));
            }

            for (Object stat : newStats) {
                if (!tile.containsStat(((Stat) stat).getName())) {
                    tile.addStat((Stat) stat);
                }
            }
        }

        // Placed tiles
        for (int i = 0; i < placedTiles.length; i++) {
            for (int j = 0; j < placedTiles[i].length; j++) {
                for (String removedStat : removedNames) {
                    placedTiles[i][j].removeStat(removedStat);
                }

                for (String oldName : nameTranslationMap.keySet()) {
                    placedTiles[i][j].changeStatName(oldName, nameTranslationMap.get(oldName));
                }

                for (Object stat : newStats) {
                    if (!placedTiles[i][j].containsStat(((Stat) stat).getName())) {
                        placedTiles[i][j].addStat((Stat) stat);
                    }
                }
            }
        }

        // Item definitions
        for (Item item : editorItemList) {
            for (String removedStat : removedNames) {
                item.removeStat(removedStat);
            }

            for (String oldName : nameTranslationMap.keySet()) {
                item.changeStatName(oldName, nameTranslationMap.get(oldName));
            }

            for (Object stat : newStats) {
                if (!item.containsStat(((Stat) stat).getName())) {
                    item.addStat((Stat) stat);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getNames (String className) {
        List<String> ret = new ArrayList<String>();
        List<Customizable> myList = (List<Customizable>) myDataMap.get(className);

        for (Customizable d : myList) {
            ret.add(d.getName());
        }

        return ret;
    }

    // different because team data is in stage
    public void syncTeams (List<Team> newList, Stage activeStage) {
        List<Team> list = newList;
        List<String> names = getNames(GridConstants.TEAM); // edited list
        List<String> fullList = getNames(GridConstants.TEAM); // reference list

        // adjusting unit affiliation strings for renamed teams
        for (Team t : list) {
            if (t.getLastIndex() > -1) {
                String prevName = fullList.get(t.getLastEditingID());
                if (!t.getName().equals(prevName)) {
                    activeStage.setTeamName(t.getLastEditingID(), t.getName());
                }
                names.remove(prevName);
            }
        }

        // units on deleted teams get their affiliation set to the first team.
        for (String s : names) {
            activeStage.setTeamName(fullList.indexOf(s), list.get(0)
                    .getName());
        }

        // replace all the teams with list in activeStage... TODO: remove
        activeStage.setTeams(list);
    }

    public void refreshObjects (String type) {
        GameTableModel gtm = myTableFactory.makeTableModel(type);
        gtm.loadObject(myDataMap.get(type));
        myDataMap.put(type, (List<?>) gtm.getObject());
    }

    public List<String> getDialogList (String myType) {
        List<String> ret = new ArrayList<String>();
        switch (myType) {
            case GridConstants.GAMEOBJECT:
                ret.add(GridConstants.DEFAULT_PASS_EVERYTHING);
                ret.addAll(getNames(GridConstants.GAMEUNIT));
                break;
            case GridConstants.ITEM:
                ret.addAll(getNames(GridConstants.ACTION));
                break;
        }
        return ret;
    }
}
