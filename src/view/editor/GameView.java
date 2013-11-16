package view.editor;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controllers.WorldManager;


@SuppressWarnings("serial")
public abstract class GameView extends JFrame {
    protected WorldManager myWorldManager;
    
    public GameView () throws HeadlessException {
        super();
    }

    public GameView (GraphicsConfiguration gc) {
        super(gc);
    }

    public GameView (String title) throws HeadlessException {
        super(title);
    }

    public GameView (String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    protected abstract JMenuBar createMenuBar (JFrame frame);

    protected JPanel addEditorBackground () {
        ImageIcon image = new ImageIcon("resources/omega_nu_3.png");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add( label, BorderLayout.CENTER );
        return panel;
    }

    protected void loadGame () {
        myWorldManager = new WorldManager("");
        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new GridLayout(0,2));
        JLabel gameNames = new JLabel("Choose Game Name:");
        JComboBox<String> gameNamesMenu = new JComboBox<String>();
        File savesDir = new File("JSONs/saves");
        for(File child: savesDir.listFiles()){
            gameNamesMenu.addItem(child.getName().split("\\.")[0]);
        }
        loadPanel.add(gameNames);
        loadPanel.add(gameNamesMenu);
        
        int value = JOptionPane.showConfirmDialog(this, loadPanel, "Choose Game", JOptionPane.OK_CANCEL_OPTION);
        if(value == JOptionPane.OK_OPTION){
            String game = (String) gameNamesMenu.getSelectedItem();
            WorldManager newWM = myWorldManager.loadGame(game);
            myWorldManager = newWM;
        }
    }

}
