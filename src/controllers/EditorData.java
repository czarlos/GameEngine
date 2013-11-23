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
import stage.Condition;
import view.Customizable;


@JsonAutoDetect
public class EditorData {
    @JsonProperty
    Map<String, List<Customizable>> myDataMap;
    JSONParser myParser;

    // TODO: redo this whole thing to use JTables
    // Only for use by deserializer
    public EditorData () {
        myParser = new JSONParser();
    }

    /**
     * Loads the data located in JSONs/folderName
     * 
     * @param folderName
     */
    public EditorData (String folderName) {
        myParser = new JSONParser();
        myDataMap = new HashMap<String, List<Customizable>>();
        loadObjects(folderName);
    }

    /**
     * Load in the objects (eventually JTable data) from the JSONs
     * 
     * @param folderName
     */
    @SuppressWarnings("unchecked")
    private void loadObjects (String folderName) {
        List<Customizable> gameObjects;
        gameObjects =
                myParser.createObject(folderName + "/GameObject",
                                      new ArrayList<GameObject>().getClass());
        myDataMap.put("GameObject", gameObjects);

        List<Customizable> gameUnits;
        gameUnits =
                myParser.createObject(folderName + "/GameUnit",
                                      new ArrayList<GameUnit>().getClass());
        myDataMap.put("GameUnit", gameUnits);

        List<Customizable> tiles;
        tiles = myParser.createObject(folderName + "/Tile", new ArrayList<Tile>().getClass());
        myDataMap.put("Tile", tiles);

        List<Customizable> conditions;
        conditions =
                myParser.createObject(folderName + "/Condition",
                                      new ArrayList<Condition>().getClass());
        myDataMap.put("Condition", conditions);

    }

    /**
     * Returns the data associated with the type requested.
     * 
     * @param String type of Object
     * @return Collection of data
     */

    public List<Customizable> get (String type) {
        return myDataMap.get(type);
    }

    /**
     * Replaces the Customizable of type "key" at index "ID" with Customizable d.
     * If ID is out of range, append Customizable to list.
     * 
     * @return ID of the set Customizable
     **/
    public int setCustomizable (String key, int ID, Customizable d) {
        List<Customizable> list = myDataMap.get(key);
        if (ID < list.size() & ID > -1) {
            list.set(ID, d);
            return ID;
        }
        else {
            list.add(d);
            return list.size() - 1;
        }
    }
}
