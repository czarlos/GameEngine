package controllers;

import gameObject.GameUnit;
import gameObject.MasterStats;
import grid.Coordinate;
import grid.GridConstants;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import stage.Stage;
import team.Team;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dialog.dialogs.tableModels.GameTableModel;
import gameObject.action.Action;
import gameObject.action.MasterActions;
import gameObject.item.Item;


/**
 * 
 * @author Leevi
 * @author Ken McAndrews
 * 
 */
@JsonAutoDetect
public class WorldManager extends Manager {
    // private String[] activeEditTypeList;
    // private int[] activeEditIDList;
    @JsonProperty
    private List<String> activeEditTypeList;
    @JsonProperty
    private List<Integer> activeEditIDList;
    @JsonProperty
    private MasterStats myMasterStats;

    /**
     * Intermediary between views and EditorData and Grid, stores List of Stages
     * 
     * @param gameName
     */
    public WorldManager () {
        super();
        activeEditTypeList = new ArrayList<String>();
        activeEditIDList = new ArrayList<Integer>();
        myMasterStats = MasterStats.getInstance();
    }

    @JsonIgnore
    public GameTableModel getTableModel (String type) {
        return myEditorData.getTableModel(type);
    }

    @SuppressWarnings("unchecked")
    public void setActions (GameTableModel gtm) {
        MasterActions ma = MasterActions.getInstance();
        ma.setActionList((List<Action>) gtm.getObject());
        myEditorData.setData(gtm, myActiveStage);
    }

    // can generalize
    @JsonIgnore
    public GameTableModel getMasterStatsTable () {
        return myEditorData.getTableModel(GridConstants.MASTERSTATS,
                                          myMasterStats.getStats());
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public void setMasterStats (GameTableModel gtm) {
        myMasterStats.setStats((HashMap<String, Integer>) gtm.getObject());
        syncStats();
    }

    // harder to generalize because teams are in stage
    @JsonIgnore
    public GameTableModel getTeamTableModel () {
        return myEditorData.getTableModel(GridConstants.TEAM,
                                          myActiveStage.getTeams());
    }

    // different because team data is in stage
    @SuppressWarnings("unchecked")
    public void setTeams (GameTableModel gtm) {
        List<Team> list = (List<Team>) gtm.getObject();
        List<String> names = myActiveStage.getTeamNames();
        List<String> fullList = myActiveStage.getTeamNames();

        // adjusting unit affiliation strings for renamed teams
        for (Team t : list) {
            String prevName = fullList.get(t.getLastEditingID());
            if (!t.getName().equals(prevName)) {
                myActiveStage.setTeamName(t.getLastEditingID(), t.getName());
            }
            names.remove(prevName);
        }

        // units on deleted teams get their affiliation set to the first team.

        for (String s : names) {
            myActiveStage.setTeamName(fullList.indexOf(s), list.get(0)
                    .getName());
        }

        // replace all the teams with list
        myActiveStage.setTeams(list);
    }

    public void setData (GameTableModel gtm) {
        myEditorData.setData(gtm, myActiveStage);
    }

    @JsonIgnore
    public void setActiveObject (int index, String type, int id) {
        activeEditTypeList.remove(index);
        activeEditTypeList.add(index, type);
        activeEditIDList.remove(index);
        activeEditIDList.add(index, id);
    }

    @JsonIgnore
    public String getActiveType (int index) {
        return activeEditTypeList.get(index);
    }

    @JsonIgnore
    public int getActiveID (int index) {
        return activeEditIDList.get(index);
    }

    /**
     * Add a new stage
     * 
     * @param x
     *        width of the grid in tiles
     * @param y
     *        height of the grid in tiles
     * @param tileID
     *        , the type of tile to initially fill the background with
     * @return StageID
     */

    @SuppressWarnings("unchecked")
    public int addStage (int x, int y, int tileID, String name) {
        myStages.add(new Stage(x, y, tileID, name));
        setActiveStage(myStages.size() - 1);
        activeEditTypeList.add("");
        activeEditIDList.add(-1);
        myActiveStage.setTeams((List<Team>) myEditorData
                .get(GridConstants.TEAM));
        return myStages.size() - 1;
    }

    public void deleteStage (int i) {
        myStages.remove(i);
        setActiveStage(i - 1);
        activeEditTypeList.remove(i);
        activeEditIDList.remove(i);
    }

    public void setPreStory (String prestory) {
        if (prestory.equals(""))
            myActiveStage
                    .setPreStory("YOU SHOULD HAVE PUT IN A PRESTORY, WHAT THE FUCK YOU SCUMBAG OF THE EARTH");
        else myActiveStage.setPreStory(prestory);
    }

    public void setPostStory (String poststory) {
        if (poststory.equals(""))
            myActiveStage
                    .setPreStory("YOU SHOULD HAVE PUT IN A POSTSTORY, WHAT THE FOOK YOU SCUM OF THE EARTH");
        else myActiveStage.setPreStory(poststory);
    }

    /**
     * Set the name of the game
     * 
     * @param gameName
     */
    public void setGameName (String gameName) {
        myGameName = gameName;
    }

    public void displayRange (Coordinate coordinate) {
        myActiveStage.getGrid().beginMove(coordinate);
    }

    public void removeRange () {
        myActiveStage.getGrid().setTilesInactive();
    }

    /**
     * Placing (previously created) things on the board. These will be replaced
     * by table editing stuff
     * 
     * @param ID
     *        of thing to place
     * @param x
     *        Coordinate
     * @param y
     *        Coordinate
     */
    public void place (String type, int objectID, int x, int y) {
        Object object = myEditorData.getObject(type, objectID);
        myActiveStage.getGrid().placeObject(type, new Coordinate(x, y), object);
    }

    /**
     * Gives access to certain names of customizables. Valid parameters are
     * "GameUnit", "GameObject", "Tile", "Condition"
     * 
     * @param className
     * @return List of names of customizable objects of that classname
     */
    public List<String> get (String className) {
        return myEditorData.getNames(className);
    }

    /**
     * Get the image for the specific type of object located at ID
     * 
     * @param className
     * @param ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public Image getImage (String className, int ID) {
        List<Customizable> myList = (List<Customizable>) myEditorData
                .get(className);

        return myList.get(ID).getImage();
    }

    /**
     * Calls update method for all stats of all placed units and unit
     * definitions. If there are new stats in the master stats list, adds that
     * stat to the stats of all placed units and unit definitions. If there is a
     * stat in the stats list of placed units and unit definitions, but not in
     * the master stats list, then it removes that stat from all stats lists of
     * placed units and unit definitions
     */
    public void syncStats () {
        List<?> editorUnitList = myEditorData.get(GridConstants.GAMEUNIT);
        GameUnit[][] placedUnits = myActiveStage.getGrid().getGameUnits();

        for (Object unit : editorUnitList) {
            ((GameUnit) unit).getStats().syncWithMaster();
        }

        for (int i = 0; i < placedUnits.length; i++) {
            for (int j = 0; j < placedUnits[i].length; j++) {
                if (placedUnits[i][j] != null) {
                    placedUnits[i][j].getStats().syncWithMaster();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<String> getDialogList (String myType) {
        List<String> ret = new ArrayList<String>();
        switch (myType) {
            case GridConstants.GAMEUNIT:
                ret = myActiveStage.getTeamNames();
                break;
            case GridConstants.GAMEOBJECT:
                List<GameUnit> list = (List<GameUnit>) myEditorData
                        .get(GridConstants.GAMEUNIT);
                ret.add(GridConstants.DEFAULT_PASS_EVERYTHING);
                for (GameUnit gu : list) {
                    ret.add(gu.getName());
                }
                break;
            case GridConstants.ITEM:
                for (Action a : (List<Action>) myEditorData
                        .get(GridConstants.ACTION)) {
                    ret.add(a.getName());
                }
                break;
            case GridConstants.ACTION:
                for (String s : myMasterStats.getStatNames()) {
                    ret.add(s);
                }
                for (Item i : (List<Item>) myEditorData.get(GridConstants.ITEM)) {
                    ret.add(i.getName());
                }
            default:
                break;
        }
        return ret;
    }
}
