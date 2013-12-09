package view.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import view.GameView;
import view.editor.EditorFrame;
import controllers.GameManager;
import controllers.Manager;


/**
 * The main view for playing the game
 * 
 * @author Patrick Schutz
 * 
 */
@SuppressWarnings("serial")
public class PlayerView extends GameView {
    protected GameManager myGameManager;
    protected static final String DEFAULT_GAME_IN_PROGRESS_SAVE_LOCATION = "gamesInProgress";

    public PlayerView () {
    }

    public PlayerView (Manager manager) {
        loadGame(manager);
    }

    @Override
    protected JMenuBar createMenuBar (JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        // first menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        // add menu items
        JMenuItem loadNewGame = new JMenuItem("Load New Game");
        fileMenu.add(loadNewGame);
        // add action listeners
        loadNewGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                loadGame(loadGame("saves"));
            }
        });

        JMenuItem loadGame = new JMenuItem("Load Game in Progress");
        fileMenu.add(loadGame);
        // add action listeners
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                loadGame(loadGame(DEFAULT_GAME_IN_PROGRESS_SAVE_LOCATION));
            }
        });

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                saveGame(DEFAULT_GAME_IN_PROGRESS_SAVE_LOCATION);
            }
        });

        fileMenu.add(saveGame);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        JMenuItem loadInEdit = new JMenuItem("Load Game in Editor");
        loadInEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                new EditorFrame(myGameManager) {
                    private JComponent myGamePanelToRefresh;

                    @Override
                    public void windowClosing (WindowEvent e) {
                        dispose();
                        myGamePanelToRefresh.repaint();
                    }

                    public EditorFrame init (JComponent toRefresh) {
                        myGamePanelToRefresh = toRefresh;
                        return this;
                    }
                }.init(myGame);
            }
        });
        editMenu.add(loadInEdit);

        return menuBar;
    }

    @Override
    protected void loadGame (Manager m) {
        if (m == null)
            return;
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

    public void loadStagePanel () {
        if (myGame != null)
            remove(myGame);
        myGame = new StagePlayerPanel(myGameManager, this);
        showGame();
    }

    public void showDialog (String story) {
        JOptionPane.showMessageDialog(this, story);
    }

    protected void saveGame (String location) {
        myGameManager.saveGame(location);
    }
}
