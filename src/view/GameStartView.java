package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import view.editor.EditorFrame;
import view.player.PlayerView;

/**
 * View shown when game first launched. Lets user pick between editing 
 * and playing a game.
 */
public class GameStartView extends JFrame {
    
    /**
     * Default constructor loads buttons and sets up the frame.
     */
    public GameStartView () {
        setLayout(new GridLayout(0, 1));
        JButton startGame = new JButton("Start Game Player");
        startGame.setPreferredSize(new Dimension(75, 25));

        startGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                new PlayerView();
                dispose();
            }

        });
        add(startGame);

        JButton startEditor = new JButton("Start Game Editor");
        startEditor.setPreferredSize(new Dimension(75, 25));
        startEditor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {

                new EditorFrame();
                dispose();
            }

        });
        add(startEditor);

        initializeWindow();

    }

    protected void initializeWindow () {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(800, 600);
        setVisible(true);
    }
}
