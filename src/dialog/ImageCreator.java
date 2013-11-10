package dialog;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * @author brooksmershon
 *
 * Panel capable of making a dialog (of itself) for drawing images and saving them
 */
public class ImageCreator extends JComponent{

    /**
     * 
     */
    private static final long serialVersionUID = -38084645183859719L;
    
    public ImageCreator() {
        add(new DrawingPad());
    }

    public static JDialog createDialog (JButton button,
                                        String string,
                                        boolean b,
                                        ImageCreator imageCreator,
                                        ImageEditor imageEditor,
                                        Object object) {
        
        JDialog dialog = new JDialog();
        dialog.add(new DrawingPad());
        return dialog;
    }
    
    

}
