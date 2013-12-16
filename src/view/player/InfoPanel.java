package view.player;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;


/**
 * Scrollable panel for displaying GameObject info as strings.
 * 
 */
@SuppressWarnings("serial")
public class InfoPanel extends ScrollableListPane {
    public InfoPanel (List<String> data) {
        populate(data);
    }

    /**
     * Override of populate method to display data as strings in a pane.
     */
    @Override
    public void populate (List<String> data) {
        setLayout(new GridLayout(0, 1));
        StringBuilder info = new StringBuilder();
        info.append("<html>");
        for (int i = 0; i < data.size(); i++) {
            info.append("<p width=\"260\">" + data.get(i) + "</p>");
        }
        info.append("</html>");

        JLabel entry = new JLabel(info.toString(), JLabel.LEFT);
        add(entry);
    }
}
