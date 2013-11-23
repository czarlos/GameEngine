package game;

import controllers.GameManager;
import view.player.PlayerView;

public class GamePlay {
    
    public GamePlay(GameManager GM){
        
    }
    public static void main (String[] args) {
        new PlayerView();
        // When LoadGame is called in PlayerView, loads new WorldManager and gets GameManager to create new GamePlay(GameManager GM);
        new GamePlay(new GameManager("game"));
        
    }
}
