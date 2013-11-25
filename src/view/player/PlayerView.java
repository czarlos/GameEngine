package view.player;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controllers.GameManager;
import controllers.WorldManager;
import view.editor.GameView;


public class PlayerView extends GameView {
    public PlayerView () {

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

    protected void loadGame () {
        JPanel newGamePanel = new JPanel();
        newGamePanel.setLayout(new GridLayout(1, 2));
        JLabel gameNameLabel = new JLabel("Game Name:");
        JTextField gameNameTextField = new JTextField(25);
        newGamePanel.add(gameNameLabel);
        newGamePanel.add(gameNameTextField);

        this.remove(myBackground);
        String gameName = gameNameTextField.getText();
        this.setTitle(gameName);
        // TODO: needs to read in worldmanager from JSON
        myWorldManager = new WorldManager();
        myWorldManager.setGameName(gameName);
        myGameManager = new GameManager(myWorldManager);
        setGame();
        revalidate();
        repaint();
    }

    private void setGame () {

        myWorldManager.addStage(10, 10, 0, "StageAwesome");// ****
                                                           // fix
        StagePlayerPanel sp = new StagePlayerPanel("MyStage", myWorldManager);
        add(sp);
    }

    public static void main (String[] args) {
        new PlayerView();
    }

}
