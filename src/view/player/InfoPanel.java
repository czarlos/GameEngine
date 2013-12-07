package view.player;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class InfoPanel extends ScrollableListPane {
    public InfoPanel (List<String> data) {
        setPreferredSize(new Dimension(WIDTH, data.size() * DATA_HEIGHT));
        populate(data);
    }

    @Override
    public void populate (List<String> data) {
        setLayout(new GridLayout(0, 1));
        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i);
            JLabel entry = new JLabel(s, JLabel.LEFT);
            add(entry);
        }
    }

    public static void main (String cheese[]) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("This is code");
        list.add("How about that");
        list.add("Here is some more code");
        list.add("ksjfoiajefoijwoijf");

        JFrame frame = new JFrame("My Frame");
        JScrollPane pane = new JScrollPane(new InfoPanel(list));
        frame.add(pane);

        frame.setVisible(true);

    }
}
