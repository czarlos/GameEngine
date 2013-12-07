package view.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class StoryPanel extends JPanel {

    private JTextArea myText;

    public StoryPanel (String story, PlayerView p)
    {
        myText = new JTextArea(story);
        myText.setLineWrap(true);
        myText.setWrapStyleWord(true);
        myText.setBackground(Color.magenta);
        myText.setFont(new Font("serif", Font.BOLD, 16));
        add(myText);
        myText.setVisible(true);
        generateMoveButton(p);
    }

    private JButton generateMoveButton (PlayerView p) {
        JButton moveButton = new JButton("DONE READING STORY BITCH");
        moveButton.addActionListener(new ActionListener() {
            private PlayerView myPlayerView;

            @Override
            public void actionPerformed (ActionEvent e) {
                // myPlayerView.myLayeredPane.remove(myPlayerView.myStoryPanel);
            }

            public ActionListener init (PlayerView mvp) {
                myPlayerView = mvp;
                return this;
            }
        }.init(p));
        moveButton.setEnabled(true);
        return moveButton;
    }

    public void setText (String story) {
        myText.setText(story);

    }
}
