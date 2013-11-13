package dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
    
    Image myImage;
    
    public ImageCreator() {
        
        myImage = new BufferedImage(100, 50, BufferedImage.TYPE_INT_ARGB);
        JPanel content = new JPanel(new BorderLayout());
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        
        JMenuItem openItem = new JMenuItem("Open");
        fileMenu.add(openItem);

        
        DrawingPad drawingPad = new DrawingPad();
        
        content.add(drawingPad, BorderLayout.CENTER);
        content.add(fileMenu, BorderLayout.NORTH);

        
        add(content);
    }

    public static JDialog createDialog (JButton button,
                                        String string,
                                        boolean b,
                                        ImageCreator imageCreator,
                                        ImageEditor imageEditor,
                                        Object object) {
        
        JDialog dialog = new JDialog();       
        
        
        
        return dialog;
    }
    
    public ImageIcon setImage(Image image){
        myImage = image;
        return new ImageIcon(myImage);
    }

    public Image getImage() {
        return myImage;
    }
    
    

}
