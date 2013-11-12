package view.editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import grid.Grid;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import stage.Stage;
import view.canvas.GridCanvas;

public class StagePanel extends JPanel{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1534023398376725167L;

    public StagePanel(String stageName, Grid g){
        add(new GridCanvas(g));
        //addEditorPanel();
    }
    
    
    public void addEditorPanel(){
        
    }
   
}
