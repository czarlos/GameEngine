package controllers;

import gameObject.GameObject;
import gameObject.InventoryObject;
import gameObject.item.Item;
import grid.Coordinate;
import grid.GridConstants;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import stage.Stage;
import team.Team;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dialog.dialogs.tableModels.GameTableModel;
import dialog.dialogs.tableModels.ItemsTableModel;


/**
 * 
 * @author Leevi
 * @author Ken McAndrews
 * 
 */
@JsonAutoDetect
public class WorldManager extends Manager {

    /**
     * Intermediary between views and EditorData and Grid, stores List of Stages
     * 
     * @param gameName
     */
    public WorldManager () {
        super();
        activeEditTypeList = new ArrayList<String>();
        activeEditIDList = new ArrayList<Integer>();
    }

    public WorldManager (Manager m) {
        super(m);
    }

    @JsonIgnore
    public GameTableModel getTableModel (String type) {
        return myEditorData.getTableModel(type);
    }

    /**
     * Teams are stage specific and so have to be different
     * 
     * @param gtm
     */
    public void setData (GameTableModel gtm) {
        for (Stage s : myStages) {
            myEditorData.setData(gtm, s);
        }
    }

    /**
     * Teams are stage specific
     */
    @JsonIgnore
    public GameTableModel getTeamTableModel () {
        GameTableModel gtm = myEditorData.getTableModel(GridConstants.TEAM);
        gtm.loadObject(myActiveStage.getTeams());

        return gtm;
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public void setTeamData (GameTableModel gtm) {
        myEditorData.syncTeams((List<Team>) gtm.getObject(), myActiveStage);
    }

    @JsonIgnore
    public void setActiveObject (String type, int id) {
        activeEditTypeList.set(myStages.indexOf(myActiveStage), type);
        activeEditIDList.set(myStages.indexOf(myActiveStage), id);
    }

    /**
     * For specific unit/chest items. To get the generic item table model use generic
     * getTableModel/setDatas
     */
    @JsonIgnore
    public GameTableModel getItemTableModel (Coordinate coordinate) {
        GameTableModel gtm = new ItemsTableModel(myEditorData);

        GameObject go = myActiveStage.getGrid().getObject(GridConstants.GAMEOBJECT, coordinate);

        if (go != null && go instanceof InventoryObject) {
            Map<String, Integer> items = ((InventoryObject) go).getItemAmounts();
            gtm.loadObject(items);
            return gtm;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public void setItemTableModel (GameTableModel gtm, Coordinate coordinate) {
        InventoryObject io =
                (InventoryObject) myActiveStage.getGrid().getObject(GridConstants.GAMEOBJECT,
                                                                    coordinate);
        io.setItemAmounts((Map<String, Integer>) gtm.getObject());
        
        Set<Item> set = new HashSet<Item>();
        for(String s: ((Map<String, Integer>) gtm.getObject()).keySet()){
            set.add((Item) myEditorData.getObject(GridConstants.ITEM, s));
        }
        io.setItems(set);
    }
    
    public void saveEditorData (String name) {
        myEditorData.saveData(name);
    }

    public void loadEditorData (String name) {
        myEditorData.loadData(name);
    }

    @JsonIgnore
    public String getActiveType () {
        return activeEditTypeList.get(myStages.indexOf(myActiveStage));
    }

    @JsonIgnore
    public int getActiveID () {
        return activeEditIDList.get(myStages.indexOf(myActiveStage));
    }

    /**
     * Add a new stage
     * 
     * @param x int of width of the grid in tiles
     * @param y int of height of the grid in tiles
     * @param tileID int of the type of tile to initially fill the background with
     * @param name String of name of stage
     * @return StageID int of ID of stage
     */

    @SuppressWarnings("unchecked")
    public int addStage (int x, int y, int tileID, String name, int index) {
        myStages.add(index, new Stage(x, y, tileID, name));
        setActiveStage(index);
        activeEditTypeList.add(index, "");
        activeEditIDList.add(index, -1);
        myActiveStage.setTeams((List<Team>) myEditorData
                .get(GridConstants.TEAM));
        return index;
    }

    public void deleteStage (int i) {
        myStages.remove(i);

        setActiveStage(Math.min(i, myStages.size() - 1));

        activeEditTypeList.remove(i);
        activeEditIDList.remove(i);
    }

    public void setPreStory (String prestory) {
        myActiveStage.setPreStory(prestory);
    }

    public void setPostStory (String poststory) {
        myActiveStage.setPostStory(poststory);
    }

    /**
     * Set the name of the game
     * 
     * @param gameName
     */
    public void setGameName (String gameName) {
        myGameName = gameName;
    }

    public void displayRange (Coordinate coordinate) {
        myActiveStage.getGrid().beginMove(coordinate);

    }

    public void removeRange () {
        myActiveStage.getGrid().setAllTilesInactive();
    }

    /**
     * Placing (previously created) things on the board. These will be replaced
     * by table editing stuff
     * 
     * @param ID int of ID thing to place
     * @param x Coordinate
     * @param y Coordinate
     */
    public void place (String type, int objectID, Coordinate coordinate) {
        Object object = myEditorData.getObject(type, objectID);
        myActiveStage.getGrid().placeObject(type, coordinate, (Customizable) object);
        myEditorData.refreshObjects(type);
    }

    /**
     * Gives access to certain names of customizables. Valid parameters are
     * "GameUnit", "GameObject", "Tile", "Condition"
     * 
     * @param className
     * @return List of names of customizable objects of that classname
     */
    public List<String> get (String className) {
        return myEditorData.getNames(className);
    }

    /**
     * Get the image for the specific type of object located at ID
     * 
     * @param className
     * @param ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public Image getImage (String className, int ID) {
        List<Customizable> myList = (List<Customizable>) myEditorData
                .get(className);

        return myList.get(ID).getImage();
    }

}
