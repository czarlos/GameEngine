package controllers;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.MasterStats;
import grid.Coordinate;
import grid.FromJSONFactory;
import grid.GridConstants;
import grid.Tile;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import stage.Condition;
import stage.Stage;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import dialog.GameTableModel;
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
    private MasterStats myMasterStatMap;

    /**
     * Intermediary between views and EditorData and Grid, stores List of Stages
     * 
     * @param gameName
     */
    public WorldManager () {
        super();
        activeEditTypeList = new String[4];
        activeEditIDList = new int[4];
        myMasterStatMap = MasterStats.getInstance();
    }

    public GameTableModel getViewModel (String type) {
        return myEditorData.getTable(type);
    }

    public void setData (GameTableModel gtm) {
        myEditorData.setData(gtm);
    }

    public void addTeam (String teamName) {
        myActiveStage.addTeam(teamName);
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
        myActiveStage.addTeam("default");
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

    // WILL BE REMOVED, USE GAMEMANAGER
    @Deprecated
    public void doMove (Coordinate a, Coordinate b) {
        myActiveStage.getGrid().doMove(a, b);
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
        myActiveStage.getGrid().placeTile(new Coordinate(x, y),
                                          (Tile) myEditorData.getObject(GridConstants.DEFAULTTYPES[0], tileID));
    }

    public void placeUnit (int unitID, int x, int y) {
        myActiveStage.getGrid().placeObject(new Coordinate(x, y),
                                            (GameObject) myEditorData.getObject(GridConstants.DEFAULTTYPES[1], unitID));
        myActiveStage.addUnitToTeam(0, myActiveStage.getGrid().getUnit(new Coordinate(x, y)));
        // TODO: actually implement teams

    }

    public void placeObject (int objectID, int x, int y) {
        myActiveStage.getGrid().placeObject(new Coordinate(x, y),
                                            (GameObject) myEditorData.getObject(GridConstants.DEFAULTTYPES[2], objectID));
    }

    public void placeItem (int objectID, int x, int y) {
        GameUnit gu = myActiveStage.getGrid().getUnit(new Coordinate(x, y));
        if (gu != null) {
            gu.addItem((Item) myEditorData.getObject(GridConstants.DEFAULTTYPES[3], objectID));
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

    /**
     * Takes a data type and ID and returns a list of required data that needs to be passed in to
     * create/edit one of those objects
     * 
     * @param ID of object
     * @param data type (i.e. "Condition"
     * @return list of data that needs to be passed into "set" to create object.
     */
    public List<String> getNeededConditionData (int ID) {
        Condition c = (Condition) myEditorData.get("Condition").get(ID);
        return c.getNeededData();
    }

    /**
     * When you make a Condition (and maybe all generic things in the future?) pass in the ID and
     * the data needed in map format.
     * 
     * @param ConditionID
     * @param Map of NeededData mapped to what the user types in
     */
    public void setCondition (int teamID, int ConditionID, Map<String, String> data) {
        Condition c = (Condition) myEditorData.get("Condition").get(ConditionID);
        c.setData(data);
        myActiveStage.getTeam(teamID).addCondition(c);
    }

    /**
     * Gets the stat value in the master stat list for the given stat
     * 
     * @param statName - Name of the stat to get the value for
     * @return The value of the stat for the stat name passed in
     */
    public int getStatValue (String statName) {
        return myMasterStatMap.getStatValue(statName);
    }

    /**
     * Adds a new stat to the game by adding to the master stat list. Calls the update method, which
     * adds the stat to the stats list of all units placed and unit definitions
     * 
     * @param statName - Name of the stat to be added
     * @param statValue - Default value of the stat to be added
     */
    public void addStat (String statName, int statValue) {
        if (!myMasterStatMap.getStatNames().contains(statName)) {
            myMasterStatMap.setStatValue(statName, statValue);
            updateStats();
        }
    }

    /**
     * Removes a stat from the master stat list. Calls the update method, which removes the stat
     * from the stats list of all units placed and unit definitions
     * 
     * @param statName - Name of the stat to be removed
     */
    public void removeStat (String statName) {
        myMasterStatMap.remove(statName);
        updateStats();
    }

    /**
     * Modifies a stat in the master stat list. Does not update that value in the stats list of
     * placed units and unit definitions
     * 
     * @param statName - Name of the stat to be modified
     * @param statValue - Value to update the stat to
     */
    public void modifyStat (String statName, int statValue) {
        if (myMasterStatMap.getStatNames().contains(statName)) {
            myMasterStatMap.modExisting(statName, statValue);
        }
    }

    /**
     * Calls update method for all stats of all placed units and unit definitions. If there are new
     * stats in the master stats list, adds that stat to the stats of all placed units and unit
     * definitions. If there is a stat in the stats list of placed units and unit definitions, but
     * not in the master stats list, then it removes that stat from all stats lists of placed units
     * and unit definitions
     */
    public void updateStats () {
        List<Customizable> editorUnitList = myEditorData.get("GameUnit");
        GameUnit[][] placedUnits = myActiveStage.getGrid().getGameUnits();

        for (Customizable unit : editorUnitList) {
            ((GameUnit) unit).getStats().updateFromMaster();
        }

        for (int i = 0; i < placedUnits.length; i++) {
            for (int j = 0; j < placedUnits[i].length; j++) {
                if (placedUnits[i][j] != null) {
                    placedUnits[i][j].getStats().updateFromMaster();
                }
            }
        }
    }
}
