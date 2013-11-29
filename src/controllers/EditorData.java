package controllers;

import grid.GridConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import dialog.dialogs.tableModels.MultipleTableModel;
import dialog.dialogs.tableModels.SingleTableModel;
import parser.JSONParser;
import view.Customizable;


@JsonAutoDetect
public class EditorData {
    @JsonProperty
    private Map<String, List<?>> myDataMap;
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
        myDataMap = new HashMap<String, List<?>>();
        loadObjects(folderName);
    }

    /**
     * Load in the objects (eventually JTable data) from the JSONs
     * 
     * @param folderName
     */
    @SuppressWarnings("unchecked")
    private void loadObjects (String folderName) {
        for (String s : GridConstants.DEFAULTTYPES) {
            List<Customizable> list = new ArrayList<Customizable>();
            list =
                    myParser.createObject(folderName + "/" + s,
                                          new ArrayList<Customizable>().getClass());
            myDataMap.put(s, list);
        }

        /*
         * List<Customizable> conditions;
         * conditions =
         * myParser.createObject(folderName + "/Condition",
         * new ArrayList<Condition>().getClass());
         * myDataMap.put("Condition", conditions);
         */
    }

    /**
     * Returns the data associated with the type requested.
     * 
     * @param String type of Object
     * @return Collection of data
     */

    public List<?> get (String type) {
        return myDataMap.get(type);
    }

    public Customizable getObject (String type, int ID) {
        return (Customizable) myDataMap.get(type).get(ID);
    }

    public MultipleTableModel getMultipleTable (String type) {
        MultipleTableModel mtm = (MultipleTableModel) myTableFactory.makeTableModel(type);
        mtm.addObjects(myDataMap.get(type));
        return mtm;
    }

    public SingleTableModel getSingleTableModel (String type, Object toEdit) {
        SingleTableModel stm = (SingleTableModel) myTableFactory.makeTableModel(type);
        stm.loadObject(toEdit);
        return stm;
    }

    public void setData (MultipleTableModel gtm) {
        myDataMap.put(gtm.getName(), gtm.getObjects());
    }
}
