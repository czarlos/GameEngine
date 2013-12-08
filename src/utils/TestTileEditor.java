package utils;

import gameObject.action.Action;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import parser.JSONParser;
import dialog.dialogs.tableModels.CombatActionTableModel;
import dialog.dialogs.tableModels.GameTableModel;


/**
 * 
 * @author brooksmershon Testing setup for panel and table viewing
 */

public class TestTileEditor {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run () {
                createGUITester();
            }
        });
    }

    /**
     * sets up a GUI for testing
     */
    private static void createGUITester () {
        JFrame frame = new JFrame("Unit Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // GameTableModel model = new CombatActionTableModel();

        List<?> tilesReadIn = makeTestLists();

        // model.loadObject(tilesReadIn);

        Container content = frame.getContentPane();
        // Creates a new container
        content.setLayout(new BorderLayout());

        // final JDialog tileEditor = new TableDialog(model, null);

        JButton launchButton = new JButton("TileEditor");
        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                // tileEditor.setVisible(true);
            }
        });

        JPanel panel = new JPanel();
        panel.add(launchButton);

        panel.setPreferredSize(new Dimension(60, 68));
        panel.setMinimumSize(new Dimension(60, 68));
        panel.setMaximumSize(new Dimension(60, 68));

        content.add(panel, BorderLayout.WEST);

        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static List<?> makeTestLists () {
        JSONParser p = new JSONParser();
        return p.createObject("defaults/Action", new ArrayList<Action>().getClass());
    }
}
