package controllers;

import gameObject.GameUnit;
import gameObject.action.Action;
import gameObject.action.MasterActions;
import grid.GridConstants;
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
        myTableFactory = new TableFactory();
    }

    /**
     * Loads the data located in JSONs/folderName
     * 
     * @param folderName
     */
    public EditorData (String folderName) {
        myParser = new JSONParser();
        myTableFactory = new TableFactory();
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
        for (String s : GridConstants.DEFAULTTYPES) {
            List<Customizable> list = new ArrayList<Customizable>();
            list = myParser.createObject(folderName + "/" + s,
                                         new ArrayList<Customizable>().getClass());
            myDataMap.put(s, list);
        }

        List<Team> list = new ArrayList<Team>();
        list = myParser.createObject(folderName + "/" + GridConstants.TEAM,
                                     new ArrayList<Team>().getClass());
        myDataMap.put(GridConstants.TEAM, list);

        List<Action> list2 = new ArrayList<Action>();
        list2 = myParser.createObject(folderName + "/" + GridConstants.ACTION,
                                      new ArrayList<Action>().getClass());
        myDataMap.put(GridConstants.ACTION, list2);

        // need to generalize this for all data types. tile, gameobject,
        // gameunit, item, team, action
        MasterActions ma = MasterActions.getInstance();
        ma.setActionList(list2);
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

    public GameTableModel getTableModel (String type, Object toEdit) {
        GameTableModel gtm = myTableFactory.makeTableModel(type);
        gtm.loadObject(toEdit);
        return gtm;
    }

    @SuppressWarnings("unchecked")
    public void setData (GameTableModel gtm, Stage activeStage) {
        switch (gtm.getName()) {
            case GridConstants.ACTION:
                syncActions((List<Object>) gtm.getObject(), activeStage);
                break;
            case GridConstants.STATS:
                syncStats();
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
            String prevName = fullList.get(((Action) action).getLastIndex());
            if (!((Action) action).getName().equals(prevName)) {
                nameTranslationMap.put(prevName, ((Action) action).getName());
            }
            System.out.println(prevName);
            removedNames.remove(prevName);
        }

        for (Object unit : editorUnitList) {
            ((GameUnit) unit).syncActionsWithMaster(nameTranslationMap, removedNames);
        }

        for (int i = 0; i < placedUnits.length; i++) {
            for (int j = 0; j < placedUnits[i].length; j++) {
                if (placedUnits[i][j] != null) {
                    placedUnits[i][j].syncActionsWithMaster(nameTranslationMap, removedNames);
                }
            }
        }
    }

    private void syncStats () {
        // TODO Auto-generated method stub

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
}
