package game;

import controllers.GameManager;
import view.player.PlayerView;

public class Game {
    
    GameManager myGM;
    
    public Game(GameManager GM){
        myGM = GM;
        doTurn();
    }
     
    private void doTurn () {
        while(!myGM.conditionsMet()){
            if(myGM.teamIsHuman()){
                doHumanTurn();
            }
            else{
                myGM.doAITurn();
            }
            myGM.nextTurn();
        }
    }


    public void doHumanTurn(){
        
        
        
        // ends when no active units or button click, set all units to inactive
        myGM.nextTurn();
    }
    
    
    public static void main (String[] args) {
        new PlayerView();
        // When LoadGame is called in PlayerView, loads new WorldManager and gets GameManager to create new GamePlay(GameManager GM);
        new Game(new GameManager("game"));
        
    }
}
