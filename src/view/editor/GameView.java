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
import controllers.GameManager;
import controllers.WorldManager;


@SuppressWarnings("serial")
/**
 * Class to represent main view for both the game editing environment and the 
 * game play environment. This class encapsulates the things both environments
 *
 */
public abstract class GameView extends JFrame {
    protected WorldManager myWorldManager;
    protected JPanel myBackground;
    protected GameManager myGameManager;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar(this));
        myBackground = createBackground();
        add(myBackground);
        pack();
        setSize(800, 600);
        setVisible(true);
    }
    
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

}
