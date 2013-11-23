package controllers;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.EditorData;
import dialog.GameTableModel;
import dialog.UnitTableModel;
import parser.JSONParser;
import stage.Condition;
import stage.Stage;
import view.Customizable;
import view.Drawable;
import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.FromJSONFactory;
import grid.Tile;


@JsonAutoDetect
public class WorldManager extends Manager {

    @JsonProperty
    Stage myActiveStage;
    FromJSONFactory myFactory;
    JSONParser myParser;
    @JsonProperty
    EditorData myEditorData;

    private UnitTableModel myUnitModel;
    private String activeEditType;
    private int activeEditID;

    /**
     * Intermediary between views and EditorData and Grid, stores List of Stages
     * 
     * @param gameName
     */
    public WorldManager (String gameName) {
        super(gameName);
        myFactory = new FromJSONFactory();
        myParser = new JSONParser();
        myEditorData = new EditorData("defaults");
        myUnitModel = new UnitTableModel();
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

    /**
     * Returns list of stage names
     * 
     * @return
     */
    @JsonIgnore
    public List<String> getStages () {
        List<String> ret = new ArrayList<String>();
        for (Stage s : myStages) {
            ret.add(s.getName());
        }

        return ret;
    }

    /**
     * Set list of stages, used by JSON deserializer
     * 
     * @param stages
     */
    public void setStages (List<Stage> stages) {
        myStages = stages;
    }

    /**
     * Set which stage to assign "active", this
     * is the stage that all methods will return information about by default.
     * 
     * @param stageID
     */
    public void setActiveStage (int stageID) {
        if (stageID < myStages.size())
            myActiveStage = myStages.get(stageID);
    }

    /**
     * Set the name of the game
     * 
     * @param gameName
     */
    public void setGameName (String gameName) {
        myGameName = gameName;
    }

    /**
     * Gets the game name
     * 
     * @return
     */
    public String getGameName () {
        return myGameName;
    }

    /**
     * Method to getting a Drawable version of the grid
     * 
     * @return
     */

    public Drawable getGrid () {
        return (Drawable) myActiveStage.getGrid();
    }

    public Coordinate getCoordinate (double fracX, double fracY) {
        return myActiveStage.getGrid().getCoordinate(fracX, fracY);
    }

    public void doMove (Coordinate a, Coordinate b) {
        myActiveStage.getGrid().doMove(a, b);
    }

    public void doAction (Coordinate object, Coordinate action, String actionName) {
        myActiveStage.getGrid().doAction(object, action, actionName);
    }

    /**
     * Getting images
     * 
     * @param x
     * @param y
     * @return
     */
    public Image getTileImage (int x, int y) {
        return myActiveStage.getGrid().getTile(new Coordinate(x, y)).getImage();
    }

    /**
     * Gets the Image of the object and location x and y
     * 
     * @param x
     * @param y
     * @return
     */
    public Image getObjectImage (int x, int y) {
        GameObject o = myActiveStage.getGrid().getObject(new Coordinate(x, y));
        if (o != null)
            return o.getImage();
        return null;
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
     * Gives access to certain customizable attributes (name and image). Valid parameters are
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
     * Save game and load game
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
    public void setCondition (int ConditionID, Map<String, String> data) {
        Condition c = (Condition) myEditorData.get("Condition").get(ConditionID);
        c.setData(data);
        myActiveStage.addCondition(c);
    }
}
