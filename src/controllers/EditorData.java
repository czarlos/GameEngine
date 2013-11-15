package controllers;

import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import parser.JSONParser;
import view.Drawable;


@JsonAutoDetect
public class EditorData {
    @JsonProperty
    Map<String, List<Drawable>> myDataMap;
    JSONParser myParser;

    // Only for use by deserializer
    public EditorData () {
        myParser = new JSONParser();
    }

    public EditorData (String folderName) {
        myParser = new JSONParser();
        myDataMap = new HashMap<String, List<Drawable>>();
        loadObjects(folderName);
    }

    @SuppressWarnings("unchecked")
    private void loadObjects (String folderName) {
        List<Drawable> gameObjects;
        gameObjects =
                myParser.createObject(folderName + "/GameObject",
                                      new ArrayList<GameObject>().getClass());
        myDataMap.put("GameObject", gameObjects);

        List<Drawable> gameUnits;
        gameUnits =
                myParser.createObject(folderName + "/GameUnit",
                                      new ArrayList<GameUnit>().getClass());
        myDataMap.put("GameUnit", gameUnits);

        List<Drawable> tiles;
        tiles = myParser.createObject(folderName + "/Tile", new ArrayList<Tile>().getClass());
        myDataMap.put("Tile", tiles);
    }

    public List<Drawable> get (String key) {
        return myDataMap.get(key);
    }

    /**
     * Replaces the Drawable of type "key" at index "ID" with Drawable d.
     * If ID is out of range, append Drawable to list.
     * 
     * @return ID of the set drawable
     **/
    public int setDrawable (String key, int ID, Drawable d) {
        List<Drawable> list = myDataMap.get(key);
        if (ID < list.size() & ID > -1) {
            list.set(ID, d);
            return ID;
        }
        else {
            list.add(d);
            return list.size() - 1;
        }
    }

    // affiliation
}
