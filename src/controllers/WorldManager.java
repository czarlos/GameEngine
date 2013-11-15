package controllers;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.EditorData;
import parser.JSONParser;
import stage.Condition;
import stage.Stage;
import view.Customizable;
import gameObject.GameObject;
import gameObject.GameUnit;
import grid.FromJSONFactory;
import grid.Grid;
import grid.Tile;


@JsonAutoDetect
public class WorldManager {
    @JsonProperty
    List<Stage> myStages;
    @JsonProperty
    Stage myActiveStage;
    FromJSONFactory myFactory;
    JSONParser myParser;
    @JsonProperty
    EditorData myEditorData;
    @JsonProperty
    String myGameName;

    public WorldManager (@JsonProperty("myGameName") String gameName) {
        myStages = new ArrayList<Stage>();
        myFactory = new FromJSONFactory();
        myParser = new JSONParser();
        myEditorData = new EditorData("defaults");
        myGameName = gameName;
    }

    /**
     * Methods for managing stages
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

    @JsonIgnore
    public List<String> getStages () {
        List<String> ret = new ArrayList<String>();
        for (Stage s : myStages) {
            ret.add(s.getName());
        }

        return ret;
    }

    public void setStages (List<Stage> stages) {
        myStages = stages;
    }

    public void setActiveStage (int stageID) {
        if (stageID < myStages.size())
            myActiveStage = myStages.get(stageID);
    }

    /**
     * Methods for managing game name
     * @param gameName
     */
    public void setGameName (String gameName) {
        myGameName = gameName;
    }

    public String getGameName () {
        return myGameName;
    }

    /**
     * Method to getting the grid (is this necessary, it seems like too much power...)
     * @return
     */
    public Grid getGrid () {
    // TODO: Talk to chris or patrick about the need for this method.
        return myActiveStage.getGrid();
    }

    /** 
     * Getting images
     * @param x
     * @param y
     * @return
     */
    public Image getTileImage (int x, int y) {
        return myActiveStage.getGrid().getTile(x, y).getImage();
    }

    public Image getObjectImage (int x, int y) {
        GameObject o = myActiveStage.getGrid().getObject(x, y);
        if (o != null)
            return o.getImage();
        return null;
    }

    /**
     * Placing (previously created) things on the board
     * @param ID of thing to place
     * @param x Coordinate
     * @param y Coordinate
     */
    public void setTile (int tileID, int x, int y) {
        myActiveStage.getGrid().placeTile((Tile) myFactory.make("tile", tileID), x, y);
    }

    // if you need to make any more of these, then just combine all these placeObjects into one
    public void placeUnit (int unitID, int x, int y) {
        myActiveStage.getGrid().placeObject((GameObject) myFactory.make("unit", unitID), x, y);
    }

    public void placeObject (int objectID, int x, int y) {
        myActiveStage.getGrid().placeObject((GameObject) myFactory.make("gameobject", objectID),
                                            x, y);
    }

    /**
     * Gives access to certain customizable attributes (name and image). Valid parameters are "GameUnit",
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

    public Image getImage (String className, int ID) {
        ArrayList<Customizable> myList = (ArrayList<Customizable>) myEditorData.get(className);

        return myList.get(ID).getImage();
    }

    /**
     * Customizable object creation!
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
        gu.setAffiliation(affiliation);
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
     * Takes a data type and ID and returns a list of required data that needs to be passed in to create/edit one of those objects
     * @param ID of object
     * @param data type (i.e. "Condition"
     * @return list of data that needs to be passed into "set" to create object. 
     */
    public List<String> getNeededData (int ID, String type) {
        Customizable c = (Customizable) myEditorData.get(type).get(ID);
        return c.getNeededData();
    }

    /**
     * When you make a Condition (and maybe all generic things in the future?) pass in the ID and the data needed in map format.
     * @param ConditionID
     * @param Map of NeededData mapped to what the user types in
     */
    public void setCondition (int ConditionID, Map<String, String> data) {
        Condition c = (Condition) myEditorData.get("Condition").get(ConditionID);
        c.setData(data);
        myActiveStage.addCondition(c);
    }
    
    // TODO: when people are done implementing things add methods for setting/getting actions, items
}
