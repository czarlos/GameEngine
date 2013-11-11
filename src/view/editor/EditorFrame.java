package view.editor;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class EditorFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8550671173122103688L;
    
    private ArrayList<StagePanel> myStagesList = new ArrayList<StagePanel>();
    private JMenuBar myMenuBar;
    
    public EditorFrame(){
        super("Omega_Nu Game Editor");
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar(this));
        pack();
        setSize(600,600);
        setVisible(true);
    }
    
 
    /**
     * adding generic menubar to frame
     * @param frame
     * @return
     */
    private JMenuBar createMenuBar(JFrame frame){
        myMenuBar = new JMenuBar();
        JMenu menuOne = new JMenu("Editor");
        menuOne.setMnemonic(KeyEvent.VK_1);
        myMenuBar.add(menuOne);
        
        return myMenuBar;
    }
    
    /**
     * adds new stage panel to main editor frame
     */
    private void addStagePanel(){
        
    }
    
    public static void main(String[] args) {
        EditorFrame e = new EditorFrame();
    }
}
