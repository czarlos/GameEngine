package controllers;

import java.util.ArrayList;
import java.util.List;
import stage.Stage;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Manager {
    @JsonProperty
    List<Stage> myStages;

    @JsonProperty
    String myGameName;
    
    // TODO: When chris is around, refactor to add gameName as a method not constructor call
    public Manager (@JsonProperty("myGameName") String gameName) {
        myStages = new ArrayList<Stage>();
        myGameName = gameName;
        // TODO Auto-generated constructor stub
    }

}
