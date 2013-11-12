package controllers;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.JSONParser;
import stage.Stage;
import view.Drawable;
import gameObject.GameObject;
import grid.FromJSONFactory;
import grid.Grid;
import grid.Tile;


public class WorldManager {

    List<Stage> myStages;
    Stage myActiveStage;
    Grid myGrid;
    FromJSONFactory myFactory;
    JSONParser myParser;
    EditorData myEditorData;

    public WorldManager () {
        myStages = new ArrayList<Stage>();
        myFactory = new FromJSONFactory();
        myParser = new JSONParser();
        myEditorData = new EditorData("defaults");
    }

    // returns Stage ID
    public int addStage (int x, int y, int tileID) {
        myStages.add(new Stage(x, y, tileID));
        setActiveStage(myStages.size() - 1);
        return myStages.size() - 1;
    }

    public void setActiveStage (int stageID) {
        if (stageID < myStages.size())
            myActiveStage = myStages.get(stageID);
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

    // EDITING DEFAULTS/CUSTOMIZING (with JSON)

    public Map<String, Image> get (String className) {
        Map<String, Image> ret = new HashMap<String, Image>();
        ArrayList<Drawable> myList = (ArrayList<Drawable>) myEditorData.get(className);

        for (Drawable item : myList) {
            ret.put(item.getName(), item.getImage());
        }

        return ret;
    }

    // return array

    // add new objects
    // read objects
    // edit objects

}
