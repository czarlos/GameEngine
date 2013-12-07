package controllers;

import gameObject.GameUnit;
import grid.Coordinate;
import grid.GridConstants;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import stage.Stage;
import team.Team;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dialog.dialogs.tableModels.GameTableModel;


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

    public void setData (GameTableModel gtm) {
        myEditorData.setData(gtm, myActiveStage);
    }

    @JsonIgnore
    public void setActiveObject (int index, String type, int id) {
        activeEditTypeList.remove(index);
        activeEditTypeList.add(index, type);
        activeEditIDList.remove(index);
        activeEditIDList.add(index, id);
    }

    @JsonIgnore
    public String getActiveType (int index) {
        return activeEditTypeList.get(index);
    }

    @JsonIgnore
    public int getActiveID (int index) {
        return activeEditIDList.get(index);
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
    public int addStage (int x, int y, int tileID, String name) {
        myStages.add(new Stage(x, y, tileID, name));
        setActiveStage(myStages.size() - 1);
        activeEditTypeList.add("");
        activeEditIDList.add(-1);
        myActiveStage.setTeams((List<Team>) myEditorData
                .get(GridConstants.TEAM));
        return myStages.size() - 1;
    }

    public void deleteStage (int i) {
        myStages.remove(i);
        setActiveStage(i - 1);
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

    // TODO: get rid of this
    public List<String> getDialogList (String myType) {
        List<String> ret = new ArrayList<String>();
        switch (myType) {
            case GridConstants.GAMEUNIT:
                ret.addAll(myEditorData.getNames(GridConstants.TEAM));
                break;
            case GridConstants.GAMEOBJECT:
                ret.add(GridConstants.DEFAULT_PASS_EVERYTHING);
                ret.addAll(myEditorData.getNames(GridConstants.GAMEUNIT));
                break;
            case GridConstants.ITEM:
                ret.addAll(myEditorData.getNames(GridConstants.ACTION));
                break;
            case GridConstants.ACTION:
                // ret.addAll(myEditorData.getNames(GridConstants.MASTERSTATS));
                ret.addAll(myEditorData.getNames(GridConstants.ITEM));
            default:
                break;
        }
        return ret;
    }
}
