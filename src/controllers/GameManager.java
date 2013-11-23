package controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameManager extends Manager {

    public GameManager (@JsonProperty("myGameName") String gameName) {
        super(gameName);
    }

}
