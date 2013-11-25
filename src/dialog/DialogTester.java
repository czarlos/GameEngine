package dialog;

import gameObject.Stats;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * 
 * @author brooksmershon
 *         Testing setup for panel and table viewing
 */

public class DialogTester {
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

        GameTableModel model = new UnitTableModel();

        BufferedImage doge_1 = null;
        BufferedImage doge_2 = null;

        ImageIcon icon = new ImageIcon();
        ImageIcon icon_2 = new ImageIcon();
        try {
            doge_1 = ImageIO.read(new File("src/dialog/doge.png"));
            icon = new ImageIcon(doge_1);

            doge_2 = ImageIO.read(new File("src/dialog/doge_soldier.jpeg"));
            icon_2 = new ImageIcon(doge_2);

        }
        catch (IOException e) {
            System.out.println(e);

        }

        ArrayList<ActionTestStub> actionList = new ArrayList<ActionTestStub>();
        actionList.add(new ActionTestStub());

        Object[] row_1 =
                { "Bobby D.oge", "Doge", icon, new Stats(), new ArrayList<ActionTestStub>(),
                 "offense" };
        Object[] row_2 =
                { "Bobby D.oge", "Another Doge", icon, new Stats(),
                 new ArrayList<ActionTestStub>(), "defense" };
        Object[] row_3 =
                { "Engineer", "Jean", icon_2, new Stats(), new ArrayList<ActionTestStub>(),
                 "offense" };

        model.addNewRow(row_1);
        model.addNewRow(row_2);
        model.addNewRow(row_3);

        Container content = frame.getContentPane();
        // Creates a new container
        content.setLayout(new BorderLayout());

        final JDialog unitEditor = new UnitEditorDialog(model);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(40, 68));
        panel.setMinimumSize(new Dimension(32, 68));
        panel.setMaximumSize(new Dimension(32, 68));

        JButton launchButton = new JButton("UnitEditor");
        launchButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                unitEditor.setVisible(true);
            }
        });

        panel.add(launchButton);

        content.add(panel, BorderLayout.WEST);

        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }

}
