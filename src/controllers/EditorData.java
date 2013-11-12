package controllers;

import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.JSONParser;
import view.Drawable;

public class EditorData {
    Map<String, List<Drawable>> myDataMap;

    JSONParser myParser;
    
    public EditorData(String folderName){
        myParser = new JSONParser();
        myDataMap = new HashMap<String, List<Drawable>>();
        loadObjects(folderName);
    }
    
    @SuppressWarnings("unchecked")
    private void loadObjects(String folderName){
        List<Drawable> gameObjects;
        gameObjects = myParser.createObject(folderName + "/GameObject", new ArrayList<GameObject>().getClass());
        myDataMap.put("GameObject", gameObjects);
        
        List<Drawable> gameUnits;
        gameUnits = myParser.createObject(folderName + "/GameUnit", new ArrayList<GameUnit>().getClass());
        myDataMap.put("GameUnit", gameUnits);
        
        List<Drawable> tiles;
        tiles = myParser.createObject(folderName + "/Tile", new ArrayList<Tile>().getClass());
        myDataMap.put("Tile", tiles);
    }
    
    public List<Drawable> get(String key){
        return myDataMap.get(key);
    }
}
