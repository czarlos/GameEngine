package dialog.editors;

import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;


@SuppressWarnings("serial")
public class ImagePathEditor extends GameCellEditor {

    JFileChooser fileChooser;

    public ImagePathEditor () {
        fileChooser = new JFileChooser();
    }

    /**
     * Handles events from the editor button and from
     * the dialog's OK button.
     */
    public void actionPerformed (ActionEvent e) {
        int returnVal = fileChooser.showDialog(button, "Choose");

        // check if actual file was chosen
        if (returnVal == 0) {
            current = fileChooser.getSelectedFile();
        }

        fireEditingStopped();
    }
}
