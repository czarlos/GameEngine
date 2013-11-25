package view.player;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.Semaphore;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import parser.JSONParser;
import controllers.GameManager;
import controllers.WorldManager;
import view.editor.GameView;


public class PlayerView extends GameView {
    private GameManager myManager;
    private Semaphore mySem;
    
    public PlayerView () {
        mySem=new Semaphore(1);
    }
    
    public PlayerView(GameManager manager){
        myManager=manager;
        mySem=new Semaphore(1);
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
            WorldManager newWM = p.createObject("saves/" + game, controllers.WorldManager.class);
            myManager = new GameManager(newWM, this);
        }
        revalidate();
        repaint();
        myManager.nextTurn();
        doTurn();
    }
    

    public void doTurn () {
        remove(myBackground);
        StagePlayerPanel sp = new StagePlayerPanel(myManager,mySem);
        add(sp);
        revalidate();
        repaint();

//        try {
//            //mySem.acquire();
//        }
//        catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    public static void main (String[] args) {
        new PlayerView();
    }

}
