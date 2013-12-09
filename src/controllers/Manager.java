package controllers;

import gameObject.action.Action;
import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import grid.Tile;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import parser.JSONParser;
import stage.Stage;
import view.Drawable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Communicates between backend and frontend
 * @author Leevi, Chris, Kevin
 *
 */
@JsonAutoDetect
public abstract class Manager {

    protected Stage myActiveStage;
    @JsonProperty
    protected List<Stage> myStages;
    @JsonProperty
    protected String myGameName;
    @JsonProperty
    protected EditorData myEditorData;

    // Editor instance variables
    @JsonProperty
    protected List<String> activeEditTypeList;
    @JsonProperty
    protected List<Integer> activeEditIDList;

    // GameManager instance variables
    @JsonProperty
    protected int myPhaseCount;
    @JsonProperty
    protected int myActiveTeam;
    protected List<Action> myActiveActions;
    protected boolean isTurnCompleted;

    public Manager () {
        myStages = new ArrayList<Stage>();
        myEditorData = new EditorData("defaults");
    }

    public Manager (Manager m) {
        myActiveStage = m.myActiveStage;
        myStages = m.myStages;
        myGameName = m.myGameName;
        myEditorData = m.myEditorData;
        activeEditTypeList = m.activeEditTypeList;
        activeEditIDList = m.activeEditIDList;
        myPhaseCount = m.myPhaseCount;
        myActiveTeam = m.myActiveTeam;
    }
    
    public void setGameName (String gameName) {
        myGameName = gameName;
    }

    /**
     * Returns list of stage names
     * 
     * @return List of Strings of stage names
     */
    @JsonIgnore
    public List<String> getStages () {
        List<String> ret = new ArrayList<String>();
        for (Stage s : myStages) {
            ret.add(s.getName());
        }

        return ret;
    }

    /**
     * Set which stage to assign "active", this is the stage that all methods
     * will return information about by default.
     * 
     * @param stageID int of the ID of the stage to be set active
     */
    @JsonProperty("activeStage")
    public void setActiveStage (int stageID) {
        if (stageID < myStages.size() & stageID > -1){
            myActiveStage = myStages.get(stageID);
        }
    }

    @JsonProperty("activeStage")
    public int getActiveStage () {
        return myStages.indexOf(myActiveStage);
    }

    /**
     * Set list of stages, used by JSON deserializer
     * 
     * @param stages List of Stages to set
     */
    public void setStages (List<Stage> stages) {
        myStages = stages;
    }

    /**
     * Gets the game name
     * 
     * @return String of the gameName
     */
    public String getGameName () {
        return myGameName;
    }

    @JsonIgnore
    protected String getActiveStageName () {
        return myActiveStage.getName();
    }

    /**
     * Creates a List of Strings that contain data about a coordinate
     * 
     * @param type String of type of gameObject being queried
     * @param coordinate Coordinate containing object being queried
     * @return List of Strings containing data about coordinate
     */
    public List<String> generateInfoList (String type, Coordinate coordinate) {
        GameObject gameObject = myActiveStage.getGrid().getObject(type, coordinate);
        if (gameObject != null) {
            if (gameObject instanceof GameUnit) {
                gameObject = myActiveStage.getGrid().getObject(GridConstants.GAMEUNIT, coordinate);
                ((GameUnit) gameObject).setTotalStats(((Tile) myActiveStage.getGrid().getObject(GridConstants.TILE, coordinate)).getStats());                
            }            
            gameObject.generateDisplayData();
            addCoordinateData(gameObject, coordinate);
            return gameObject.getDisplayData();
        }
        return null;
    }

    private void addCoordinateData (GameObject gameObject, Coordinate coordinate) {
        List<String> displayData = gameObject.getDisplayData();
        displayData.add("Coordinate: " + coordinate.getX() + ", " + coordinate.getY());
        gameObject.setDisplayData(displayData);
    }

    /**
     * Gets a list of actions that a unit at a coordinate can perform. Null if
     * there is no unit.
     * 
     * @param coordinate Coordinate that is being asked for
     * @return List of Strings that contain the action names
     */
    @JsonIgnore
    public List<String> getActionNames (Coordinate coordinate) {
        GameUnit gameUnit =
                (GameUnit) myActiveStage.getGrid().getObject(GridConstants.GAMEUNIT, coordinate);
        if (gameUnit != null) {
            if (gameUnit.isActive()) {
                List<String> actions = new ArrayList<>();
                actions.addAll(gameUnit.getActionNames());
                actions.addAll(myActiveStage.getGrid().getAllInteractions(coordinate));
                return actions;
            }
        }
        return null;
    }

    /**
     * Method to getting a Drawable version of the grid
     * 
     * @return
     */
    public Drawable getGrid () {
        return (Drawable) myActiveStage.getGrid();
    }

    @JsonIgnore
    public Coordinate getCoordinate (double fracX, double fracY) {
        return myActiveStage.getGrid().getCoordinate(fracX, fracY);
    }

    public Dimension calculateGridDimensions (int preferredTileDimension) {
        int width = (int) myActiveStage.getGrid().getWidth();
        int height = (int) myActiveStage.getGrid().getHeight();
        return new Dimension(width * preferredTileDimension, height * preferredTileDimension);
    }

    public void saveGame (String folder) {
        for (int i = 0; i < activeEditTypeList.size(); i++) {
            activeEditTypeList.set(i, "");
            activeEditIDList.set(i, -1);
        }
        JSONParser p = new JSONParser();
        p.createJSON(folder + "/" + myGameName, this);
    }
}
