package game;

import view.player.PlayerView;

public class GamePlay {
    
    public GamePlay(){
        
    }
    public static void main (String[] args) {
        new PlayerView();
        // When LoadGame is called in PlayerView, start new GamePlay(GameManager GM);
        new GamePlay();
        
    }
}
