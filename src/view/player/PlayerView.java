package view.player;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import parser.JSONParser;
import controllers.GameManager;
import controllers.WorldManager;
import view.canvas.GridCanvas;
import view.editor.GameView;


public class PlayerView extends GameView {
    private GameManager myManager;
    
    public PlayerView () {
    }
    
    public PlayerView(GameManager manager){
        myManager=manager;
    }

    @Override
    protected JMenuBar createMenuBar (JFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        // first menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(gameMenu);
        // add menu items
        JMenuItem loadGame = new JMenuItem("Load Game");
        gameMenu.add(loadGame);
        // add action listeners
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                loadGame();
            }
        });

        return menuBar;
    }

    @Override
    protected void loadGame () {
        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new GridLayout(0, 2));
        JLabel gameNames = new JLabel("Choose Game Name:");
        JComboBox<String> gameNamesMenu = new JComboBox<>();
        File savesDir = new File("JSONs/saves");
        for (File child : savesDir.listFiles()) {
            gameNamesMenu.addItem(child.getName().split("\\.")[0]);
        }
        loadPanel.add(gameNames);
        loadPanel.add(gameNamesMenu);

        int value =
                JOptionPane.showConfirmDialog(this, loadPanel, "Choose Game",
                                              JOptionPane.OK_CANCEL_OPTION);
        if (value == JOptionPane.OK_OPTION) {
            String game = (String) gameNamesMenu.getSelectedItem();
            JSONParser p = new JSONParser();
            WorldManager wm = new WorldManager();
            wm.setGameName("My game");
            List<String> tileNames = wm.get("Tile");
            wm.addStage(10,10, tileNames.indexOf("grass"),
                                    "My Stage");
            myManager = new GameManager(wm);
        }
        
        setGame();
        revalidate();
        repaint();
    }
    
    //@Override
//    protected void loadGame () {
//        JPanel newGamePanel = new JPanel();
//        newGamePanel.setLayout(new GridLayout(1, 2));
//        JLabel gameNameLabel = new JLabel("Game Name:");
//        JTextField gameNameTextField = new JTextField(25);
//        newGamePanel.add(gameNameLabel);
//        newGamePanel.add(gameNameTextField);
//
//        this.remove(myBackground);
//        String gameName = gameNameTextField.getText();
//        this.setTitle(gameName);
//
//        myWorldManager.setGameName(gameName);
//        setGame();
//        revalidate();
//        repaint();
//    }

    private void setGame () {
        remove(myBackground);
        StagePlayerPanel sp = new StagePlayerPanel("MyStage", myManager);
        add(sp);
    }

    public static void main (String[] args) {
        new PlayerView();
    }

}
