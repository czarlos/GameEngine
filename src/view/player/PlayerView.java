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

<<<<<<< HEAD
    public PlayerView (WorldManager wm) {
        myGameManager = new GameManager(wm);
        myGameManager.setView(this);
        super.clearWindow();
        this.remove(myBackground);
        //myLayeredPane = new JLayeredPane();
        this.setTitle(myGameManager.getGameName());
        
        revalidate();
        repaint();
        doTurn();
=======
    public PlayerView (Manager manager) {
        loadGame(manager);
>>>>>>> cf3786e0eed233a094b0dc03aeb09a3a2ca57015
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

<<<<<<< HEAD
    protected void loadGame (String folder) {
        if (myStagePlayerPanel != null)
            remove(myStagePlayerPanel);
        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new GridLayout(0, 2));
        JLabel gameNames = new JLabel("Choose Game Name:");
        JComboBox<String> gameNamesMenu = new JComboBox<>();
        File savesDir = new File("JSONs/" + folder + "/");
        for (File child : savesDir.listFiles()) {
            gameNamesMenu.addItem(child.getName().split("\\.")[0]);
        }
        loadPanel.add(gameNames);
        loadPanel.add(gameNamesMenu);

        int value = JOptionPane.showConfirmDialog(this, loadPanel,
                                                  "Choose Game", JOptionPane.OK_CANCEL_OPTION);
        if (value == JOptionPane.OK_OPTION) {
            String game = (String) gameNamesMenu.getSelectedItem();
            JSONParser p = new JSONParser();
            Manager newWM = p.createObject(folder + "/" + game,
                                                controllers.Manager.class);
            myGameManager = new GameManager(newWM);
            myGameManager.setView(this);
            super.clearWindow();
            this.remove(myBackground);

            this.setTitle(myGameManager.getGameName());
        }
        revalidate();
        repaint();
        //doTurn();
    }
=======
   @Override
   protected void loadGame(Manager m){
       myGameManager = new GameManager(m);
       myGameManager.setView(this);
       loadStagePanel();
       showGame();
       myGameManager.beginTurn();
   }
    
>>>>>>> cf3786e0eed233a094b0dc03aeb09a3a2ca57015

    public void endTurn () {
        showBackground();
        myGameManager.doUntilHumanTurn();
        loadStagePanel();
        myGameManager.beginTurn();
    }
    
    public void loadStagePanel(){
        
        remove(myBackground);
        myGame = new StagePlayerPanel(myGameManager, this);
        add(myGame);
        revalidate();
        repaint();
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
