package controllers;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import controllers.EditorData;
import parser.JSONParser;
import stage.Stage;
import view.Drawable;
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
     * Adds a stage to the game
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
    
    public List<String> getStages(){
        List<String> ret = new ArrayList<String>();
        for(Stage s: myStages){
            ret.add(s.getName());
        }
        
        return ret;
    }
    
    public void setStages(List<Stage> stages){
        myStages = stages;
    }

    public void setActiveStage (int stageID) {
        if (stageID < myStages.size())
            myActiveStage = myStages.get(stageID);
    }

    public void setGameName (String gameName) {
        myGameName = gameName;
    }

    public String getGameName () {
        return myGameName;
    }

    public Grid getGrid () {
        return myActiveStage.getGrid();
    }

    public Image getTileImage (int x, int y) {
        return myActiveStage.getGrid().getTile(x, y).getImage();
    }

    // includes Units
    public Image getObjectImage (int x, int y) {
        GameObject o = myActiveStage.getGrid().getObject(x, y);
        if (o != null)
            return o.getImage();
        return null;
    }

    // CHANGING THINGS/ACTIONS
    public void setTile (int tileID, int x, int y) {
        myActiveStage.getGrid().placeTile((Tile) myFactory.make("tile", tileID), x, y);
    }

    public void placeUnit (int unitID, int x, int y) {
        myActiveStage.getGrid().placeObject((GameObject) myFactory.make("unit", unitID), x, y);
    }

    public void placeObject (int objectID, int x, int y) {
        myActiveStage.getGrid().placeObject((GameObject) myFactory.make("gameobject", objectID),
                                            x, y);
    }

    /**
     * Gives access to certain drawable attributes. Valid parameters are "GameUnit", "GameObject",
     * "Tile"
     * 
     * @param className
     * @return Map of Drawable names mapped to Images
     */
    public List<String> get (String className) {
        ArrayList<String> ret = new ArrayList<String>();
        ArrayList<Drawable> myList = (ArrayList<Drawable>) myEditorData.get(className);

        for(Drawable d: myList){
            ret.add(d.getName());
        }
        
        return ret;
    }

    public Image getImage (String className, int ID) {
        ArrayList<Drawable> myList = (ArrayList<Drawable>) myEditorData.get(className);

        return myList.get(ID).getImage();
    }
    
    public int setCustomTile (int ID, String name, String imagePath, int moveCost) {
        Tile t = new grid.Tile();
        t.setName(name);
        t.setImagePath(imagePath);
        t.setMoveCost(moveCost);
        return myEditorData.setDrawable("Tile", ID, t);
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
        return myEditorData.setDrawable("GameUnit", ID, gu);
    }

    public int setCustomObject (int ID, String name, String imagePath) {
        GameObject go = new GameObject();
        go.setName(name);
        go.setImagePath(imagePath);

        return myEditorData.setDrawable("GameObject", ID, go);
    }

    public void saveGame () {
        myParser.createJSON("saves/" + myGameName, this);
    }

    public WorldManager loadGame (String gameName) {
        return myParser.createObject("saves/" + gameName, controllers.WorldManager.class);
    }

    
    // get object information
    // edit from brooks
    // actions, win conditions, items
}
