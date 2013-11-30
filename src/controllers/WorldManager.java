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
    @Deprecated
    private List<Action> myMasterActionList;
    // Use (List<Action>) myEditorData.get(GridConstants.ACTION) for programmatic consistency
    
    /**
     * Intermediary between views and EditorData and Grid, stores List of Stages
     * 
     * @param gameName
     */
    public WorldManager () {
        super();
        activeEditTypeList = new String[4];
        activeEditIDList = new int[4];
        myMasterStats = MasterStats.getInstance();
    }

    @JsonIgnore
    public GameTableModel getTableModel (String type) {
        return myEditorData.getTableModel(type);
    }

    @JsonIgnore
    public GameTableModel getTableModel (String type, Object toEdit){
        return myEditorData.getTableModel(GridConstants.TEAM, myActiveStage.getTeams());
    }
    
    // makes more sense as an arraylist... especially since default values should be 0 anyways.
    @JsonIgnore
    public GameTableModel getMasterStatsTable () {
        return myEditorData
                .getTableModel(GridConstants.MASTERSTATS, myMasterStats.getStats());
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public void setMasterStats (GameTableModel gtm) {
        myMasterStats.setStats((HashMap<String, Integer>) gtm.getObject());
        syncStats();
    }
    
    @SuppressWarnings("unchecked")
    public void setTeams (GameTableModel gtm) {
        List<Team> list = (List<Team>) gtm.getObject();
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

    public void setData (GameTableModel gtm) {
        myEditorData.setData(gtm);
    }

    public void setActions (GameTableModel gtm){
        // TODO: put all action checking here.
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

    @SuppressWarnings("unchecked")
    public int addStage (int x, int y, int tileID, String name) {
        myStages.add(new Stage(x, y, tileID, name));
        setActiveStage(myStages.size() - 1);
        myActiveStage.setTeams((List<Team>) myEditorData.get(GridConstants.TEAM));
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
        GameUnit go = (GameUnit) myEditorData.getObject(GridConstants.GAMEUNIT, unitID);
        go.setAffiliation(myActiveStage.getTeamNames().get(0));
        myActiveStage.getGrid().placeObject(new Coordinate(x, y), go);
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
    @SuppressWarnings("unchecked")
    public List<String> get (String className) {
        List<String> ret = new ArrayList<String>();
        List<Customizable> myList = (List<Customizable>) myEditorData.get(className);

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
    @SuppressWarnings("unchecked")
    public Image getImage (String className, int ID) {
        List<Customizable> myList = (List<Customizable>) myEditorData.get(className);

        return myList.get(ID).getImage();
    }

    /**
     * Calls update method for all stats of all placed units and unit definitions. If there are new
     * stats in the master stats list, adds that stat to the stats of all placed units and unit
     * definitions. If there is a stat in the stats list of placed units and unit definitions, but
     * not in the master stats list, then it removes that stat from all stats lists of placed units
     * and unit definitions
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

    @Deprecated
    public void addAction (Action newAction) {
        myMasterActionList.add(newAction);
    }

    // TODO: Should pass in String, action, or ID as parameter? Should we remove this action from
    // all units and/or weapons/items? If so, what do we do with weapons/items with no actions?
    @Deprecated
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
    @Deprecated
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

    @SuppressWarnings("unchecked")
    public List<String> getDialogList (String myType) {
        List<String> ret = new ArrayList<String>();
        switch (myType) {
            case GridConstants.GAMEUNIT:
                ret = myActiveStage.getTeamNames();
                break;
            case GridConstants.GAMEOBJECT:
                List<GameUnit> list = (List<GameUnit>) myEditorData.get(GridConstants.GAMEUNIT);
                ret.add(GridConstants.DEFAULT_PASS_EVERYTHING);
                for (GameUnit gu : list) {
                    ret.add(gu.getName());
                }
                break;
            case GridConstants.ITEM:
                for (Action a : (List<Action>) myEditorData.get(GridConstants.ACTION)) {
                    ret.add(a.getName());
                }
                break;
            default:
                break;
        }

        return ret;
    }
}
