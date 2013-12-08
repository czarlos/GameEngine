package view.player;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import parser.JSONParser;
import view.GameStartView;
import view.GameView;
import controllers.GameManager;
import controllers.Manager;
import controllers.WorldManager;


@SuppressWarnings("serial")
public class PlayerView extends GameView implements WindowListener {
    private StagePlayerPanel myStagePlayerPanel;
    // public JLayeredPane myLayeredPane;
    protected GameManager myGameManager;

    public PlayerView () {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    public PlayerView (WorldManager wm) {
        myGameManager = new GameManager(wm);
        myGameManager.setView(this);
        super.clearWindow();
        this.remove(myBackground);
        // myLayeredPane = new JLayeredPane();
        this.setTitle(myGameManager.getGameName());

        revalidate();
        repaint();
        doTurn();
    }

    public PlayerView (GameManager manager) {
        myGameManager = manager;
        mySaveLocation = "gamesInProgress";
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    @Override
    public void windowClosing (WindowEvent e) {
        new GameStartView();
        dispose();
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
                loadGame("saves");
            }
        });

        JMenuItem loadGame = new JMenuItem("Load Game");
        gameMenu.add(loadGame);
        // add action listeners
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                loadGame("gamesInProgress");
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
        myGameManager.beginTurn();
        loadStagePanel();
    }

    public void doTurn () {
        myGameManager.doUntilHumanTurn();
        loadStagePanel();
    }
    
    public void loadStagePanel(){
        
        remove(myBackground);
        myStagePlayerPanel = new StagePlayerPanel(myGameManager, this);
        add(myStagePlayerPanel);
        revalidate();
        repaint();
    }

    public void endTurn () {
        getContentPane().remove(myStagePlayerPanel);
        getContentPane().add(myBackground);
        revalidate();
        repaint();
        doTurn();

    }

    public static void main (String[] args) {
        new PlayerView();
    }

    public void showDialog (String story) {
        // myLayeredPane = new JLayeredPane();
        remove(myBackground);
        // myLayeredPane.add(curStory);
        // myLayeredPane.moveToFront(curStory);
        // this.add(myLayeredPane);
        JOptionPane.showMessageDialog(this, story);
    }

    protected void saveGame () {
        saveGame("saves");
    }

    protected void saveGame (String location) {
        myGameManager.saveGame(location);
    }

    @Override
    public void windowOpened (WindowEvent e) {

    }

    @Override
    public void windowClosed (WindowEvent e) {

    }

    @Override
    public void windowIconified (WindowEvent e) {

    }

    @Override
    public void windowDeiconified (WindowEvent e) {

    }

    @Override
    public void windowActivated (WindowEvent e) {

    }

    @Override
    public void windowDeactivated (WindowEvent e) {

    }
}
