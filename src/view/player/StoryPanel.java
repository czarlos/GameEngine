package view.player;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class StoryPanel extends JScrollPane{

    public StoryPanel(String story)
    {
        JTextArea text = new JTextArea(story);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setBackground(Color.black);
        text.setFont(new Font("serif", Font.BOLD, 12));
        text.setVisible(true);
    }
}
