package dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    
    /**
     * Creates a new JComponent to permit loading, saving, and editing of images from file
     */
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
    /**
     * 
     * @param component
     * @param title
     * @param modal
     * @param imageCreator
     * @param okListener
     * @param cancelListener
     * @return
     */
    public static JDialog createDialog (Component component,
                                        String title,
                                        boolean modal, ImageCreator imageCreator,
                                        ActionListener okListener,
                                        ActionListener cancelListener) {
        

        JDialog dialog = new JDialog();
        
        dialog.getContentPane().setLayout(new BorderLayout());
        
        //panel for option buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        
        JButton ok = new JButton("OK");
        ok.addActionListener(okListener);
        
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(cancelListener);
        
        panel.add(ok);
        panel.add(cancel);
        
        dialog.getContentPane().add(panel, BorderLayout.SOUTH);        
        
        return dialog;
    }
    
    /**
     * 
     * @param image
     * @return ImageIcon with underlying image, usually a BufferedImage
     */
    public ImageIcon setImage(Image image){
        myImage = image;
        return new ImageIcon(myImage);
    }
    /**
     * Returns underlying image that is usually a BufferedImage
     * @return
     */
    public Image getImage() {
        return myImage;
    }
    
    

}
