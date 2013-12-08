package view.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import view.GameView;
import controllers.GameManager;
import controllers.Manager;


@SuppressWarnings("serial")
public class PlayerView extends GameView {
    protected GameManager myGameManager;

    public PlayerView () {
        
    }

    public PlayerView (Manager manager) {
        loadGame(manager);
    }



    @Override
    protected JMenuBar createMenuBar (JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        // first menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(gameMenu);
        // add menu items
        JMenuItem loadNewGame = new JMenuItem("Load New Game");
        gameMenu.add(loadNewGame);
        // add action listeners
        loadNewGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                loadGame(loadGame("saves"));
            }
        });

        JMenuItem loadGame = new JMenuItem("Load Game");
        gameMenu.add(loadGame);
        // add action listeners
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                loadGame(loadGame("gamesInProgress"));
            }
        });

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                saveGame("gamesInProgress");
            }

        });

        gameMenu.add(saveGame);

        return menuBar;
    }

   @Override
   protected void loadGame(Manager m){
       myGameManager = new GameManager(m);
       myGameManager.setView(this);
       loadStagePanel();
       showGame();
       myGameManager.beginTurn();
   }
    

    public void endTurn () {
        showBackground();
        myGameManager.doUntilHumanTurn();
        loadStagePanel();
        myGameManager.beginTurn();
    }
    
    public void loadStagePanel(){
        
        if(myGame!=null)
            remove(myGame);
        myGame = new StagePlayerPanel(myGameManager, this);
        showGame();
    }

    public void showDialog (String story) {
        JOptionPane.showMessageDialog(this, story);
    }

    protected void saveGame () {
        saveGame("saves");
    }

    protected void saveGame (String location) {
        myGameManager.saveGame(location);
    }
}
