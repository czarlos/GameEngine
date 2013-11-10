package dialog;

import java.awt.Desktop.Action;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
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
        JFrame frame = new JFrame("UnitEditorTests");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        UnitTableModel model = new UnitTableModel();
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/dialog/doge.png"));
        } catch (IOException e) {
            System.out.println(e);
            
        }
        
        ArrayList<ActionTestStub> actionList = new ArrayList<ActionTestStub>();
        actionList.add(new ActionTestStub());
                
        
        model.addNewUnit("Soldier", "Luke", img, new StatsTestStub(), new ArrayList<ActionTestStub>(), "offense");

        //Create and set up the content pane.
        JComponent newContentPane = new UnitEditorPanel(model);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
