package view.dialogs;

import java.awt.GridLayout;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class LoadGameDialog extends JPanel {
    protected JComboBox<String> myOptions;

    public LoadGameDialog (String folder) {
        setLayout(new GridLayout(0, 2));
        JLabel gameNames = new JLabel("Choose Game Name:");
        myOptions = new JComboBox<>();
        File savesDir = new File("JSONs/" + folder);
        for (File child : savesDir.listFiles()) {
            myOptions.addItem(child.getName().split("\\.")[0]);
        }
        add(gameNames);
        add(myOptions);
    }

    public String getSelected () {
        return (String) myOptions.getSelectedItem();
    }

}
