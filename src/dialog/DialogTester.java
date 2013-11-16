package dialog;

import java.awt.Desktop.Action;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class DialogTester {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUITester();
            }
        });
        
        
    }
    
    private static void createGUITester() {
        JFrame frame = new JFrame("Unit Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        UnitTableModel model = new UnitTableModel();
        
        BufferedImage doge_1 = null;
        BufferedImage doge_2 = null;

        ImageIcon icon = new ImageIcon();
        ImageIcon icon_2 = new ImageIcon();
        try {
            doge_1 = ImageIO.read(new File("src/dialog/doge.png"));
            icon = new ImageIcon(doge_1);
            
            doge_2 = ImageIO.read(new File("src/dialog/doge_soldier.jpeg"));
            icon_2 = new ImageIcon(doge_2);

        } catch (IOException e) {
            System.out.println(e);
            
        }
        
        
        ArrayList<ActionTestStub> actionList = new ArrayList<ActionTestStub>();
        actionList.add(new ActionTestStub());
                
        
        model.addNewUnit("Bobby D.oge", "Doge", icon, new StatsTestStub(), new ArrayList<ActionTestStub>(), "offense");
        model.addNewUnit("Bobby D.oge", "Another Doge", icon, new StatsTestStub(), new ArrayList<ActionTestStub>(), "defense");
        model.addNewUnit("Engineer", "Jean", icon_2, new StatsTestStub(), new ArrayList<ActionTestStub>(), "offense");



        //Create and set up the content pane.
        JComponent newContentPane = new UnitEditorPanel(model);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
