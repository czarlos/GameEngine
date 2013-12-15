package controllers;

import gameObject.Customizable;
import gameObject.GameObject;
import gameObject.InventoryObject;
import gameObject.Item;
import grid.Coordinate;
import grid.GridConstants;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import stage.Stage;
import stage.Team;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dialog.dialogs.tableModels.GameTableModel;
import dialog.dialogs.tableModels.ItemsTableModel;


/**
 * Interface between the Editor GUI and the backend.
 * 
 * @author Leevi
 * @author Ken McAndrews
 * 
 */
@JsonAutoDetect
public class WorldManager extends Manager {

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
        if (type.equals(GridConstants.TEAM)) { return getTeamTableModel(); }
        return myEditorData.getTableModel(type);
    }

    /**
     * Teams are stage specific and so have to be different
     * 
     * @param gtm
     */
    public void setData (GameTableModel gtm) {
        if (gtm.getName().equals(GridConstants.TEAM)) {
            setTeamData(gtm);
        }
        else {
            for (Stage s : myStages) {
                myEditorData.setData(gtm, s);
            }
        }
    }

    /**
     * Populates a team editing table model with data from the current active stage
     */
    @JsonIgnore
    public GameTableModel getTeamTableModel () {
        GameTableModel gtm = myEditorData.getTableModel(GridConstants.TEAM);
        gtm.loadObject(myActiveStage.getTeams());

        return gtm;
    }

    /**
     * Sets the current stage's team data to the modified team data
     * 
     * @param gtm
     */
    @JsonIgnore
    @SuppressWarnings("unchecked")
    public void setTeamData (GameTableModel gtm) {
        myEditorData.syncTeams((List<Team>) gtm.getObject(), myActiveStage);
        myActiveStage.setTeams((List<Team>) gtm.getObject());
    }

    /**
     * Sets the currently "selected" object in the editor.
     * 
     * @param type
     * @param id
     */
    @JsonIgnore
    public void setActiveObject (String type, int id) {
        activeEditTypeList.set(myStages.indexOf(myActiveStage), type);
        activeEditIDList.set(myStages.indexOf(myActiveStage), id);
    }

    /**
     * For specific InventoryObject's items, not the editor item table model
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

    /**
     * For setting an individual InventoryObject's inventory
     * 
     * @param gtm
     * @param coordinate
     */
    @SuppressWarnings("unchecked")
    @JsonIgnore
    public void setItemTableModel (GameTableModel gtm, Coordinate coordinate) {
        InventoryObject io =
                (InventoryObject) myActiveStage.getGrid().getObject(GridConstants.GAMEOBJECT,
                                                                    coordinate);
        io.setItemAmounts((Map<String, Integer>) gtm.getObject());

        Set<Item> set = new HashSet<Item>();
        for (String s : ((Map<String, Integer>) gtm.getObject()).keySet()) {
            set.add((Item) myEditorData.getObject(GridConstants.ITEM, s));
        }
        io.setItems(set);
    }

    /**
     * Save a "library" of data
     * 
     * @param name
     */
    public void saveEditorData (String name) {
        myEditorData.saveData(name);
    }

    /**
     * Load a "library" of data
     * 
     * @param name
     */
    public void loadEditorData (String name) {
        myEditorData.loadData(name);
    }

    /**
     * Returns the name of the selected object's type
     * 
     * @return type
     */
    @JsonIgnore
    public String getActiveType () {
        return activeEditTypeList.get(myStages.indexOf(myActiveStage));
    }

    /**
     * Returns the ID of the selected object
     * 
     * @return ID
     */
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
        myStages.add(index, new Stage(x, y, name));
        setActiveStage(index);
        activeEditTypeList.add(index, "");
        activeEditIDList.add(index, -1);
        myActiveStage.setTeams((List<Team>) myEditorData
                .get(GridConstants.TEAM));
        placeDefaultTiles(x, y, tileID);
        return index;
    }

    /**
     * Helper method to fill grid with default tiles
     * 
     * @param x
     * @param y
     * @param tileID
     */
    private void placeDefaultTiles (int x, int y, int tileID) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                place(GridConstants.TILE, tileID, new Coordinate(i, j));
            }
        }
    }

    /**
     * Remove a stage from the game
     * 
     * @param i index of the stage to remove
     */
    public void deleteStage (int i) {
        myStages.remove(i);

        setActiveStage(Math.min(i, myStages.size() - 1));

        activeEditTypeList.remove(i);
        activeEditIDList.remove(i);
    }

    /**
     * Sets the active stage's prestory
     * 
     * @param prestory
     */
    public void setPreStory (String prestory) {
        myActiveStage.setPreStory(prestory);
    }

    /**
     * Sets the active stage's poststory
     * 
     * @param poststory
     */
    public void setPostStory (String poststory) {
        myActiveStage.setPostStory(poststory);
    }

    /**
     * Sets the name of the game
     * 
     * @param gameName
     */
    public void setGameName (String gameName) {
        myGameName = gameName;
    }

    /**
     * Place a (previously created) customizable on the board.
     * 
     * @param ID int of ID thing to place
     * @param x Coordinate
     * @param y Coordinate
     */
    public void place (String type, int objectID, Coordinate coordinate) {
        Object object = myEditorData.get(type).get(objectID);
        myActiveStage.getGrid().placeObject(type, coordinate, (Customizable) object);
        myEditorData.refreshObjects(type);
    }

    /**
     * Returns a list of all editor object names for a type of customizable.
     * 
     * @param className
     * @return List of names of customizable objects of that classname
     */
    public List<String> getNames (String className) {
        return myEditorData.getNames(className);
    }

    /**
     * Returns the image for the specific type of object located at ID
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
