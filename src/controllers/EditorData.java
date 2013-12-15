package controllers;

import gameObject.Customizable;
import gameObject.GameUnit;
import gameObject.IStats;
import gameObject.Item;
import gameObject.Stat;
import gameObject.action.Action;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import dialog.dialogs.tableModels.GameTableModel;
import parser.JSONParser;
import stage.Stage;
import stage.Team;


/**
 * Stores the editor data master lists, creates GameTableModels for editing and
 * deals with data shifts when table models are passed back
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

    public EditorData () {
        myParser = new JSONParser();
        myTableFactory = new TableFactory(this);
        myDataMap = new HashMap<String, List<?>>();
        loadDefaultObjects();
    }

    /**
     * Saves current "library" of data in the editor to userLibraries/NAME
     * 
     * @param name
     */
    public void saveData (String name) {
        myParser.createJSON("userLibraries/" + name, myDataMap);
    }

    /**
     * Loads a "library" of data located in userLibraries/NAME
     * 
     * @param name
     */
    @SuppressWarnings("unchecked")
    public void loadData (String name) {
        myDataMap =
                myParser.createObjectFromFile("userLibraries/" + name,
                                              new HashMap<String, List<?>>().getClass());
    }

    /**
     * Load in default objects
     * 
     * @param folderName
     */
    @SuppressWarnings("unchecked")
    private void loadDefaultObjects () {
        for (String s : GridConstants.DEFAULTEDITTYPES) {
            List<Customizable> list = new ArrayList<Customizable>();
            list = myParser.createObjectFromFile("defaults/" + s,
                                                 new ArrayList<Customizable>().getClass());
            myDataMap.put(s, list);
        }
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

    public GameTableModel getTableModel (String type) {
        GameTableModel gtm = myTableFactory.makeTableModel(type);
        gtm.loadObject(myDataMap.get(type));
        return gtm;
    }

    /**
     * Returns an object of type TYPE and string NAME
     * 
     * @param type Type of object
     * @param name Name of object
     * @return
     */
    @SuppressWarnings("unchecked")
    public Customizable getObject (String type, String name) {
        for (Customizable c : (List<Customizable>) myDataMap.get(type)) {
            if (c.getName().equals(name))
                return c;
        }
        return null;
    }

    /**
     * Returns list of names of objects in master data maps
     * 
     * @param className
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getNames (String className) {
        List<String> ret = new ArrayList<String>();
        List<Customizable> myList = (List<Customizable>) myDataMap.get(className);

        for (Customizable d : myList) {
            ret.add(d.getName());
        }

        return ret;
    }

    /**
     * Refreshes instances of objects in table model so that different instances are placed on the
     * grid
     * 
     * @param type Type of objects that need to be refreshed
     */
    public void refreshObjects (String type) {
        GameTableModel gtm = myTableFactory.makeTableModel(type);
        gtm.loadObject(myDataMap.get(type));
        myDataMap.put(type, (List<?>) gtm.getObject());
    }

    @Deprecated
    // TODO: use myED/Selector combination instead
    public List<String> getDialogList (String myType) {
        List<String> ret = new ArrayList<String>();
        switch (myType) {
            case GridConstants.GAMEOBJECT:
            case GridConstants.TILE:
                ret.add(GridConstants.DEFAULT_PASS_EVERYTHING);
                ret.addAll(getNames(GridConstants.GAMEUNIT));
                break;
            case GridConstants.ITEM:
                ret.addAll(getNames(GridConstants.ACTION));
                break;
        }
        return ret;
    }

    /**
     * Syncs data when table models are saved. Needs to be refactored to keep class uniqueness
     * and more generically update all grid items. After syncing is done, updates master editor
     * data.
     * 
     * @param gtm
     * @param activeStage
     */
    @SuppressWarnings("unchecked")
    public void setData (GameTableModel gtm, Stage activeStage) {
        // TODO: refactor
        switch (gtm.getName()) {
            case GridConstants.ACTION:
                syncActions((List<Object>) gtm.getObject(), activeStage);
                break;
            case GridConstants.MASTERSTATS:
                syncStats((List<Object>) gtm.getObject(), activeStage);
                break;
            case GridConstants.TILE:
                // syncObjects((List<Customizable>) gtm.getObject(), activeStage, gtm.getName());
                break;

            case GridConstants.GAMEOBJECT:
            case GridConstants.GAMEUNIT:
            case GridConstants.ITEM:
                break;
            default:
                break;
        }
        myDataMap.put(gtm.getName(), (List<?>) gtm.getObject());
    }

    @SuppressWarnings("unchecked")
    private void syncStats (List<Object> newStats, Stage activeStage) {
        List<String> fullList = getNames(GridConstants.MASTERSTATS);
        List<String> removedNames = getNames(GridConstants.MASTERSTATS);
        List<IStats> editorUnitList = (List<IStats>) get(GridConstants.GAMEUNIT);

        Map<String, String> nameTranslationMap = new HashMap<>();
        List<IStats> objectEditList = new ArrayList<>();

        for (Object stat : newStats) {
            if (((Stat) stat).getLastIndex() > -1) {
                String prevName = fullList.get(((Stat) stat).getLastIndex());
                if (!((Stat) stat).getName().equals(prevName)) {
                    nameTranslationMap.put(prevName, ((Stat) stat).getName());
                }
                removedNames.remove(prevName);
            }
        }

        for (IStats unit : editorUnitList) {
            objectEditList.addAll(((GameUnit) unit).getItems());
        }

        objectEditList.addAll(editorUnitList);
        objectEditList.addAll((List<IStats>) get(GridConstants.TILE));
        objectEditList.addAll((List<IStats>) get(GridConstants.ITEM));
        objectEditList.addAll((Collection<? extends IStats>) activeStage.getGrid()
                .getCustomizables(GridConstants.GAMEUNIT));
        objectEditList.addAll((Collection<? extends IStats>) activeStage.getGrid()
                .getCustomizables(GridConstants.TILE));

        for (IStats object : objectEditList) {
            for (String removedStat : removedNames) {
                object.removeStat(removedStat);
            }
            for (String oldName : nameTranslationMap.keySet()) {
                object.changeStatName(oldName, nameTranslationMap.get(oldName));
            }
            for (Object stat : newStats) {
                if (!object.containsStat(((Stat) stat).getName())) {
                    object.addStat((Stat) stat);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void syncObjects (List<Customizable> newList, Stage activeStage, String type) {
        List<Customizable> fullList = (List<Customizable>) get(type);
        List<Customizable> removed = new ArrayList<Customizable>((List<Customizable>) get(type));
        Customizable[][] objects = activeStage.getGrid().getArray(type);
        // replace all the tiles with the same ID
        for (Customizable c : newList) {
            if (c.getLastIndex() > -1) {
                Customizable prevObject = fullList.get(c.getLastIndex());
                for (int i = 0; i < objects.length; i++) {
                    for (int j = 0; j < objects[0].length; j++) {
                        if (objects[i][j] != null &&
                            objects[i][j].getName().equals(prevObject.getName())) {
                            objects[i][j] = c;
                        }
                    }
                }
                removed.remove(prevObject);
            }
        }

        // if removed, set Tile to default
        for (Customizable c : removed) {
            for (int i = 0; i < objects.length; i++) {
                for (int j = 0; j < objects[0].length; j++) {
                    if (c.getName().equals(objects[i][j].getName())) {
                        objects[i][j] = fullList.get(0);
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void syncActions (List<Object> newActions, Stage activeStage) {
        List<String> fullList = getNames(GridConstants.ACTION);
        List<String> removedActions = getNames(GridConstants.ACTION);
        List<GameUnit> editorUnitList = (List<GameUnit>) get(GridConstants.GAMEUNIT);
        List<Item> editorItemList = (List<Item>) get(GridConstants.ITEM);

        Map<String, String> nameTranslationMap = new HashMap<>();
        List<Item> itemEditList = new ArrayList<>();

        for (Object action : newActions) {
            if (((Action) action).getLastIndex() > -1) {
                String prevName = fullList.get(((Action) action).getLastIndex());
                if (!((Action) action).getName().equals(prevName)) {
                    nameTranslationMap.put(prevName, ((Action) action).getName());
                }
                removedActions.remove(prevName);
            }
        }

        for (GameUnit unit : editorUnitList) {
            itemEditList.addAll(unit.getItems());
        }
        for (Customizable unit : (Collection<? extends Customizable>) activeStage.getGrid()
                .getCustomizables(GridConstants.GAMEUNIT)) {
            itemEditList.addAll(((GameUnit) unit).getItems());
        }

        itemEditList.addAll(editorItemList);

        for (Item item : itemEditList) {
            for (String removedAction : removedActions) {
                item.removeAction(removedAction);
            }
            for (String oldName : nameTranslationMap.keySet()) {
                item.changeActionName(oldName, nameTranslationMap.get(oldName));
            }
        }
    }

    // different because team data is in stage
    public void syncTeams (List<Team> newList, Stage activeStage) {
        List<Team> fullList = activeStage.getTeams(); // reference list
        List<Team> removed = new ArrayList<Team>(activeStage.getTeams());
        // adjusting unit affiliation strings for renamed teams
        for (Team t : newList) {
            if (t.getLastIndex() > -1) {
                Team prevTeam = fullList.get(t.getLastIndex());
                if (!t.getName().equals(prevTeam.getName())) {
                    activeStage.setTeamName(t.getLastIndex(), t.getName());
                }
                removed.remove(prevTeam);
            }
        }

        // units on deleted teams get their affiliation set to the first team.
        for (Team t : removed) {
            activeStage.setTeamName(fullList.indexOf(t), newList.get(0)
                    .getName());
        }
    }
}
