package controllers;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import dialog.GameTableModel;
import dialog.UnitTableModel;
import parser.JSONParser;
import stage.Condition;
import stage.Stage;
import view.Customizable;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.MasterStats;
import grid.Coordinate;
import grid.FromJSONFactory;
import grid.Tile;


@JsonAutoDetect
public class WorldManager extends Manager {

    FromJSONFactory myFactory;
    JSONParser myParser;

    private UnitTableModel myUnitModel;
    private String activeEditType;
    private int activeEditID;
    private MasterStats myMasterStatMap;

    /**
     * Intermediary between views and EditorData and Grid, stores List of Stages
     * 
     * @param gameName
     */
    public WorldManager () {
        super();
        myFactory = new FromJSONFactory();
        myParser = new JSONParser();
        myUnitModel = new UnitTableModel();
        myMasterStatMap = new MasterStats();
    }

    public GameTableModel getViewModel (String type) {
        switch (type.toLowerCase()) {
            case "tile":
                return null; // add
            case "gameunit":
                return myUnitModel;
            case "gameobject":

                return null; // add
        }
        return null;
    }

    public void setActiveObject (String type, int id) {
        activeEditType = type;
        activeEditID = id;
    }

    public String getActiveType () {
        return activeEditType;
    }

    public int getActiveID () {
        return activeEditID;
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

    // WILL BE REMOVED, USE GAMEMANAGER
    public void doMove (Coordinate a, Coordinate b) {
        myActiveStage.getGrid().doMove(a, b);
    }

    // do action is in gamemanager

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
                                          (Tile) myFactory.make("tile", tileID));
    }

    public void placeUnit (int unitID, int x, int y) {
        myActiveStage.getGrid().placeObject(new Coordinate(x, y),
                                            (GameObject) myFactory.make("gameunit", unitID));

    }

    public void placeObject (int objectID, int x, int y) {
        myActiveStage.getGrid().placeObject(new Coordinate(x, y),
                                            (GameObject) myFactory.make("gameobject", objectID));
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
     * Customizable object creation!
     * 
     * @param ID
     * @param name
     * @param imagePath
     * @param moveCost
     * @return
     */
    public int setCustomTile (int ID, String name, String imagePath, int moveCost) {
        Tile t = new grid.Tile();
        t.setName(name);
        t.setImagePath(imagePath);
        t.setMoveCost(moveCost);
        return myEditorData.setCustomizable("Tile", ID, t);
    }

    public int setCustomUnit (int ID,
                              String name,
                              String imagePath,
                              int affiliation,
                              boolean controllable) {
        GameUnit gu = new GameUnit();

        gu.setName(name);
        gu.setImagePath(imagePath);
        gu.setControllable(controllable);
        return myEditorData.setCustomizable("GameUnit", ID, gu);
    }

    public int setCustomObject (int ID, String name, String imagePath) {
        GameObject go = new GameObject();
        go.setName(name);
        go.setImagePath(imagePath);

        return myEditorData.setCustomizable("GameObject", ID, go);
    }

    /**
     * Save game and load game. TODO: see if we can refactor this into manager?
     */
    public void saveGame () {
        myParser.createJSON("saves/" + myGameName, this);
    }

    public WorldManager loadGame (String gameName) {
        return myParser.createObject("saves/" + gameName, controllers.WorldManager.class);
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
     * Adds a new stat to the game by adding to the master stat list, adding to all unit definitions
     * stored in the editor data, and adding to all placed units
     * 
     * @param name - Name of the stat to be added
     * @param value - Default value of the stat to be added
     */
    // TODO: Should we have check if there is already that stat? Or just overwrite?
    public void addStat (String name, int value) {
        List<Customizable> unitList = myEditorData.get("GameUnit");
        List<String> newStats = new ArrayList<>();
        GameUnit[][] placedUnits = myActiveStage.getGrid().getGameUnits(); // Is myActiveStage the
                                                                           // stage that is
                                                                           // currently being edited

        myMasterStatMap.setStatValue(name, value);
        // Do we want to do this? Or just add in new stat like in placed units? e.g. Do all units in
        // data have unique stat lists?
        for (Customizable unit : unitList) {
            ((GameUnit) unit).setStats(myMasterStatMap.clone());
        }

        // Need this check? Or assumed that gameUnits array will already be created?
        if (placedUnits.length > 0 && placedUnits[0].length > 0) {
            List<String> currentStatList = ((GameUnit) unitList.get(0)).getStats().getStatNames();
            for (String stat : myMasterStatMap.getStatNames()) {
                if (!currentStatList.contains(stat)) {
                    newStats.add(stat);
                }
            }

            for (int i = 0; i < placedUnits.length; i++) {
                for (int j = 0; j < placedUnits[i].length; j++) {
                    if (placedUnits[i][j] != null) {
                        Map<String, Integer> newStatMap = new HashMap<>();
                        for (String stat : placedUnits[i][j].getStats().getStatNames()) {
                            newStatMap.put(stat, placedUnits[i][j].getStat(stat));
                        }
                        for (String stat : newStats) {
                            newStatMap.put(stat, myMasterStatMap.getStatValue(stat));
                        }
                        placedUnits[i][j].getStats().setStatList(newStatMap);
                    }
                }
            }
        }
    }
}
