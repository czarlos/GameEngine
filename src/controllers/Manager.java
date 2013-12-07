package controllers;

import gameObject.action.Action;
import gameObject.GameObject;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.GridConstants;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import parser.JSONParser;
import stage.Stage;
import view.Drawable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public abstract class Manager {

    @JsonProperty
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
    protected int myPhaseCount;
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
     * @return
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
     * @param stageID
     */
    public void setActiveStage (int stageID) {
        if (stageID < myStages.size() & stageID > -1)
            myActiveStage = myStages.get(stageID);
    }

    /**
     * Set list of stages, used by JSON deserializer
     * 
     * @param stages
     */
    public void setStages (List<Stage> stages) {
        myStages = stages;
    }

    /**
     * Gets the game name
     * 
     * @return
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
    public List<String> getActions (Coordinate coordinate) {
        GameUnit gameUnit =
                (GameUnit) myActiveStage.getGrid().getObject(GridConstants.GAMEUNIT, coordinate);
        if (gameUnit != null) {
            if (gameUnit.isActive()) {
                List<String> actions = new ArrayList<>();
                actions.addAll(gameUnit.getActions());
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
        JSONParser p = new JSONParser();
        p.createJSON(folder + "/" + myGameName, this);
    }
}
