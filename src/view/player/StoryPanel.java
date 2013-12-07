package view.player;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


@SuppressWarnings("serial")
public class StoryPanel extends JPanel {

    private JTextArea myText;

    public StoryPanel (String story)
    {
        myText = new JTextArea(story);
        myText.setLineWrap(true);
        myText.setWrapStyleWord(true);
        myText.setBackground(Color.magenta);
        myText.setFont(new Font("serif", Font.BOLD, 16));
        add(myText);
        myText.setVisible(true);
    }

    private JButton generateMoveButton (PlayerView p) {
        JButton moveButton = new JButton("Continue");
        moveButton.addActionListener(new ActionListener() {
            private PlayerView myPlayerView;

            @Override
            public void actionPerformed (ActionEvent e) {
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
