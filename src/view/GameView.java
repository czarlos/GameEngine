package view;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import parser.JSONParser;
import view.dialogs.LoadGameDialog;
import controllers.Manager;


@SuppressWarnings("serial")
/**
 * Class to represent main view for both the game editing environment and the 
 * game play environment. This class encapsulates the things both environments
 *
 */
public abstract class GameView extends JFrame implements WindowListener {
    protected JComponent myBackground;
    protected JComponent myGame;
    protected static final String DEFAULT_SAVE_LOCATION="saves";

    public GameView () throws HeadlessException {
        super();
        initializeWindow();
    }

    public GameView (GraphicsConfiguration gc) {
        super(gc);
        initializeWindow();
    }

    public GameView (String title) throws HeadlessException {
        super(title);
        initializeWindow();
    }

    public GameView (String title, GraphicsConfiguration gc) {
        super(title, gc);
        initializeWindow();
    }

    protected void initializeWindow () {
        setJMenuBar(createMenuBar(this));
        myBackground = createBackground();
        add(myBackground);
        pack();
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }
    
    protected void showBackground(){
        if(myGame!=null){
            remove(myGame);
        }
        add(myBackground);
        revalidate();
        repaint();
    }
    
    protected void showGame(){
        if(myGame!=null){
            remove(myBackground);
            add(myGame);
        }
        revalidate();
        repaint();
    }

    protected Manager loadGame (String folder) {
        LoadGameDialog loader = new LoadGameDialog(folder);

        int value = JOptionPane.showConfirmDialog(this, loader,
                                                  "Choose Game", JOptionPane.OK_CANCEL_OPTION);
        if (value == JOptionPane.OK_OPTION) {
            String gameName = loader.getSelected();
            JSONParser p = new JSONParser();
            return p.createObjectFromFile(folder + "/" + gameName,
                                  controllers.WorldManager.class);
        }
        return null;
    }
    
    protected abstract void loadGame(Manager m);

    protected void clearWindow () {
        setJMenuBar(createMenuBar(this));
        revalidate();
        repaint();
    }

    protected abstract JMenuBar createMenuBar (JFrame frame);

    protected JPanel createBackground () {
        ImageIcon image = new ImageIcon("resources/omega2.gif");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void windowClosing (WindowEvent e) {
        new GameStartView();
        dispose();
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
