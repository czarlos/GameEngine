package controllers;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.item.Item;
import grid.Tile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import dialog.dialogs.tableModels.MultipleTableModel;
import dialog.dialogs.tableModels.SingleTableModel;
import parser.JSONParser;
import stage.Condition;
import view.Customizable;


@JsonAutoDetect
public class EditorData {
    @JsonProperty
    private Map<String, List<Customizable>> myDataMap;
    private JSONParser myParser;
    private TableFactory myTableFactory;

    // Only for use by deserializer
    public EditorData () {
        myParser = new JSONParser();
        myTableFactory = new TableFactory();
    }

    /**
     * Loads the data located in JSONs/folderName
     * 
     * @param folderName
     */
    public EditorData (String folderName) {
        myParser = new JSONParser();
        myTableFactory = new TableFactory();
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

        List<Customizable> items;
        items =
                myParser.createObject(folderName + "/Item",
                                      new ArrayList<Item>().getClass());
        myDataMap.put("Item", items);

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
    
    public Customizable getObject (String type, int ID) {
        return myDataMap.get(type).get(ID);
    }
    
    public MultipleTableModel getMultipleTable(String type){
        MultipleTableModel mtm = (MultipleTableModel) myTableFactory.makeTableModel(type);
        mtm.addPreviouslyDefined(myDataMap.get(type));
        return mtm;
    }
    
    public SingleTableModel getSingleTableModel(String type, Object toEdit){
        SingleTableModel stm = (SingleTableModel) myTableFactory.makeTableModel(type);
        stm.loadObject(toEdit);
        return stm;
    }
    
    public void setData (MultipleTableModel gtm) {
        myDataMap.put(gtm.getName(), gtm.getObjects());
    }
}
