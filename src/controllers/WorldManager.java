package controllers;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.MasterStats;
import grid.Coordinate;
import grid.GridConstants;
import grid.Tile;
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
import dialog.dialogs.tableModels.MultipleTableModel;
import dialog.dialogs.tableModels.SingleTableModel;
import dialog.dialogs.tableModels.TeamTableModel;
import gameObject.item.Item;


/**
 * 
 * @author Leevi
 * @author Ken McAndrews
 * 
 */
@JsonAutoDetect
public class WorldManager extends Manager {

    private String[] activeEditTypeList;
    private int[] activeEditIDList;

    @JsonProperty
    private MasterStats myMasterStats;
    private List<Action> myMasterActionList;

    /**
     * Intermediary between views and EditorData and Grid, stores List of Stages
     * 
     * @param gameName
     */
    public WorldManager () {
        super();
        activeEditTypeList = new String[4];
        activeEditIDList = new int[4];
        myMasterActionList = new ArrayList<>();
        myMasterStats = MasterStats.getInstance();
    }

    public MultipleTableModel getMultipleTableModel (String type) {
        return myEditorData.getMultipleTable(type);
    }

    public GameTableModel getTeamTableModel () {
        TeamTableModel gtm = new TeamTableModel();
        gtm.addObjects(myActiveStage.getTeams());
        return gtm;
    }

    public void setTeams (MultipleTableModel mtm) {
        List<Team> list = (List<Team>) mtm.getObjects();
        List<String> names = myActiveStage.getTeamNames();

        // adjusting unit affiliation strings for renamed teams
        for (Team t : list) {
            String prevName = names.get(t.getLastEditingID());
            if (!t.getName().equals(prevName)) {
                myActiveStage.setTeamName(t.getLastEditingID(), t.getName());
            }
            names.remove(prevName);
        }

        // units on deleted teams get their affiliation set to the first team.
        List<String> fullList = myActiveStage.getTeamNames();

        for (String s : names) {
            myActiveStage.setTeamName(fullList.indexOf(s), list.get(0).getName());
        }

        // replace all the teams with list
        myActiveStage.setTeams(list);

    }

    public void setData (MultipleTableModel gtm) {
        myEditorData.setData(gtm);
    }

    public void setActiveObject (int index, String type, int id) {
        activeEditTypeList[index] = type;
        activeEditIDList[index] = id;
    }

    public String getActiveType (int index) {
        return activeEditTypeList[index];
    }

    public int getActiveID (int index) {
        return activeEditIDList[index];
    }

    /**
     * Add a new stage
     * 
     * @param x width of the grid in tiles
     * @param y height of the grid in tiles
     * @param tileID, the type of tile to initially fill the background with
     * @return StageID
     */

    public int addStage (int x, int y, int tileID, String name) {
        myStages.add(new Stage(x, y, tileID, name));
        setActiveStage(myStages.size() - 1);
        return myStages.size() - 1;
    }

    public void setPreStory (String prestory) {
        myActiveStage.setPreStory(prestory);
    }

    public void setPostStory (String poststory) {
        myActiveStage.setPostStory(poststory);
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
     * Placing (previously created) things on the board. These will be replaced by table editing
     * stuff
     * 
     * @param ID of thing to place
     * @param x Coordinate
     * @param y Coordinate
     */
    public void setTile (int tileID, int x, int y) {
        myActiveStage.getGrid()
                .placeTile(new Coordinate(x, y),
                           (Tile) myEditorData.getObject(GridConstants.TILE, tileID));
    }

    public void placeUnit (int unitID, int x, int y) {
        myActiveStage.getGrid().placeObject(new Coordinate(x, y),
                                            (GameObject) myEditorData
                                                    .getObject(GridConstants.GAMEUNIT,
                                                               unitID));
        myActiveStage.addUnitToTeam(0, myActiveStage.getGrid().getUnit(new Coordinate(x, y)));
        // TODO: actually implement teams

    }

    public void placeObject (int objectID, int x, int y) {
        myActiveStage.getGrid().placeObject(new Coordinate(x, y),
                                            (GameObject) myEditorData
                                                    .getObject(GridConstants.GAMEOBJECT,
                                                               objectID));
    }

    public void placeItem (int objectID, int x, int y) {
        GameUnit gu = myActiveStage.getGrid().getUnit(new Coordinate(x, y));
        if (gu != null) {
            gu.addItem((Item) myEditorData.getObject(GridConstants.ITEM, objectID));
        }
    }

    /**
     * Gives access to certain names of customizables. Valid parameters are
     * "GameUnit",
     * "GameObject",
     * "Tile", "Condition"
     * 
     * @param className
     * @return List of names of customizable objects of that classname
     */
    public List<String> get (String className) {
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<Customizable> myList = (ArrayList<Customizable>) myEditorData.get(className);

        for (Customizable d : myList) {
            ret.add(d.getName());
        }

        return ret;
    }

    /**
     * Get the image for the specific type of object located at ID
     * 
     * @param className
     * @param ID
     * @return
     */
    public Image getImage (String className, int ID) {
        ArrayList<Customizable> myList = (ArrayList<Customizable>) myEditorData.get(className);

        return myList.get(ID).getImage();
    }

    /*    *//**
     * Takes a data type and ID and returns a list of required data that needs to be passed in
     * to
     * create/edit one of those objects
     * 
     * @param ID of object
     * @param data type (i.e. "Condition"
     * @return list of data that needs to be passed into "set" to create object.
     */
    /*
     * public List<String> getNeededConditionData (int ID) {
     * Condition c = (Condition) myEditorData.get("Condition").get(ID);
     * return c.getNeededData();
     * }
     *//**
     * When you make a Condition (and maybe all generic things in the future?) pass in the ID and
     * the data needed in map format.
     * 
     * @param ConditionID
     * @param Map of NeededData mapped to what the user types in
     */
    /*
     * public void setCondition (int teamID, int ConditionID, Map<String, String> data) {
     * Condition c = (Condition) myEditorData.get("Condition").get(ConditionID);
     * c.setData(data);
     * myActiveStage.getTeam(teamID).addCondition(c);
     * }
     */

    @JsonIgnore
    public GameTableModel getMasterStatsTable () {
        return myEditorData
                .getSingleTableModel(GridConstants.MASTERSTATS, myMasterStats.getStats());
    }

    @JsonIgnore
    public void setMasterStats (SingleTableModel stm) {
        myMasterStats.setStats((HashMap<String, Integer>) stm.getObject());
        syncStats();
    }

    /*    *//**
     * Gets the stat value in the master stat list for the given stat
     * 
     * @param statName - Name of the stat to get the value for
     * @return The value of the stat for the stat name passed in
     */
    /*
     * public int getStatValue (String statName) {
     * return myMasterStats.getStatValue(statName);
     * }
     * 
     * public List<String> getMasterStatNames(){
     * return myMasterStats.getStatNames();
     * }
     *//**
     * Adds a new stat to the game by adding to the master stat list. Calls the update method,
     * which
     * adds the stat to the stats list of all units placed and unit definitions
     * 
     * @param statName - Name of the stat to be added
     * @param statValue - Default value of the stat to be added
     */
    /*
     * public void addStat (String statName, int statValue) {
     * if (!myMasterStats.getStatNames().contains(statName)) {
     * myMasterStats.setStatValue(statName, statValue);
     * updateStats();
     * }
     * }
     *//**
     * Removes a stat from the master stat list. Calls the update method, which removes the stat
     * from the stats list of all units placed and unit definitions
     * 
     * @param statName - Name of the stat to be removed
     */
    /*
     * public void removeStat (String statName) {
     * myMasterStats.remove(statName);
     * updateStats();
     * }
     *//**
     * Modifies a stat in the master stat list. Does not update that value in the stats list of
     * placed units and unit definitions
     * 
     * @param statName - Name of the stat to be modified
     * @param statValue - Value to update the stat to
     */
    /*
     * public void modifyStat (String statName, int statValue) {
     * if (myMasterStats.getStatNames().contains(statName)) {
     * myMasterStats.modExisting(statName, statValue);
     * }
     * }
     */
    /**
     * Calls update method for all stats of all placed units and unit definitions. If there are new
     * stats in the master stats list, adds that stat to the stats of all placed units and unit
     * definitions. If there is a stat in the stats list of placed units and unit definitions, but
     * not in the master stats list, then it removes that stat from all stats lists of placed units
     * and unit definitions
     */
    public void syncStats () {
        List<Customizable> editorUnitList = (List<Customizable>) myEditorData.get("GameUnit");
        GameUnit[][] placedUnits = myActiveStage.getGrid().getGameUnits();

        for (Customizable unit : editorUnitList) {
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

    public void addAction (Action newAction) {
        myMasterActionList.add(newAction);
    }

    // TODO: Should pass in String, action, or ID as parameter? Should we remove this action from
    // all units and/or weapons/items? If so, what do we do with weapons/items with no actions?
    public void removeAction (String actionName) {
        for (int i = 0; i < myMasterActionList.size(); i++) {
            if (myMasterActionList.get(i).getName().equals(actionName)) {
                myMasterActionList.remove(i);
            }
        }

        syncActions();
    }

    // TODO: Make this a generic method to get the type of action to modify. How do we want to
    // modify an action (e.g. what variables would we want to edit)?
    public void modifyAction (Action modAction) {
        for (int i = 0; i < myMasterActionList.size(); i++) {
            if (myMasterActionList.get(i).getName().equals(modAction.getName())) {
                myMasterActionList.set(i, modAction);
            }
        }

        syncActions();
    }

    // TODO
    private void syncActions () {
        List<Customizable> editorUnitList = (List<Customizable>) myEditorData.get("GameUnit");
        GameUnit[][] placedUnits = myActiveStage.getGrid().getGameUnits();

        for (Customizable unit : editorUnitList) {
            ((GameUnit) unit).syncActionsWithMaster(myMasterActionList);
        }

        for (int i = 0; i < placedUnits.length; i++) {
            for (int j = 0; j < placedUnits[i].length; j++) {
                if (placedUnits[i][j] != null) {
                    placedUnits[i][j].syncActionsWithMaster(myMasterActionList);
                }
            }
        }
    }
}
