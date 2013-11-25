package controllers;

import grid.Coordinate;
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
    
    public Manager (String gameName) {
        myStages = new ArrayList<Stage>();
        myGameName = gameName;
        myEditorData = new EditorData("defaults");
    }
    
    public Manager(){
        this("");
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
     * Set which stage to assign "active", this
     * is the stage that all methods will return information about by default.
     * 
     * @param stageID
     */
    public void setActiveStage (int stageID) {
        if (stageID < myStages.size())
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
    /**
     * Method to getting a Drawable version of the grid
     * 
     * @return
     */

    public Drawable getGrid () {
        return (Drawable) myActiveStage.getGrid();
    }

    public Coordinate getCoordinate (double fracX, double fracY) {
        return myActiveStage.getGrid().getCoordinate(fracX, fracY);
    }
    
    public void saveGame () {
        JSONParser p = new JSONParser();
        p.createJSON("saves/" + myGameName, this);
    }
}
