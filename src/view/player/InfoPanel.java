package view.player;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;


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
            JLabel entry = new JLabel(s, JLabel.CENTER);
            add(entry);
        }
    }
}
