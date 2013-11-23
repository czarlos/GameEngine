package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameManager extends Manager {

    int myActiveTeam;
    
    public GameManager (@JsonProperty("myGameName") String gameName) {
        super(gameName);
        myActiveTeam = 0;
    }
    
    public int getActiveTeam(){
        return myActiveTeam;
    }
    
    public void nextTurn(){
        // increment activeteam
    }

}
