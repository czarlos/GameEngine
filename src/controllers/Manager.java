package controllers;

import grid.Coordinate;
import java.util.ArrayList;
import java.util.List;
import stage.Stage;
import view.Drawable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Manager {

    @JsonProperty
    Stage myActiveStage;
    @JsonProperty
    List<Stage> myStages;
    @JsonProperty
    String myGameName;
    @JsonProperty
    EditorData myEditorData;
    
    // TODO: When chris is around, refactor to add gameName as a method not constructor call
    public Manager (@JsonProperty("myGameName") String gameName) {
        myStages = new ArrayList<Stage>();
        myGameName = gameName;
        myEditorData = new EditorData("defaults");
        // TODO Auto-generated constructor stub
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
}
