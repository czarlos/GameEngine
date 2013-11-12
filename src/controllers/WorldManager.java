package controllers;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import stage.Stage;
import gameObject.GameObject;
import grid.FromJSONFactory;
import grid.Grid;
import grid.Tile;

public class WorldManager {

    List<Stage> myStages;
    Stage myActiveStage;
    Grid myGrid;
    FromJSONFactory myFactory;
    
    public WorldManager (int tileID, int x, int y) {
        myStages = new ArrayList<Stage>();
        setActiveStage(addStage(x,y, tileID));
        myFactory = new FromJSONFactory();
    }
    
    // returns Stage ID
    public int addStage(int tileID, int x, int y){
        myStages.add(new Stage(x,y, tileID));
        return myStages.size()-1;
    }
    
    public void setActiveStage(int stageID){
        if(stageID < myStages.size())
            myActiveStage = myStages.get(stageID);
    }
    
    public Grid getGrid() {
        return myActiveStage.getGrid();
    }
    
    public Image getTileImage(int x, int y){
        return myActiveStage.getGrid().getTile(x, y).getImage();
    }
    
    // includes Units
    public Image getObjectImage(int x, int y){
        GameObject o = myActiveStage.getGrid().getObject(x, y);
        if(o != null)
            return o.getImage();
        return null;
    }
    
    // CHANGING THINGS/ACTIONS
    public void setTile(int tileID, int x, int y){
        myActiveStage.getGrid().placeTile((Tile) myFactory.make("tile", tileID), x, y);
    }
    
    public void placeUnit(int unitID, int x, int y){
       // myActiveStage.getGrid().placeObject((Unit) myFactory.make("unit", unitID), x, y);
        
       // uncomment when I'm done JSONifying objects.
    }
    
    public void placeObject(int objectID, int x, int y){
        // myActiveStage.getGrid().placeObject((GameObject) myFactory.make("gameobject", objectID), x, y);
         
        // uncomment when I'm done JSONifying objects.
    }
    
    // EDITING DEFAULTS/CUSTOMIZING (with JSON)
    
    // add new objects
    // read objects
    // edit objects
    
}
