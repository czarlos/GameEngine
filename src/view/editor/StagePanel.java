package view.editor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class StagePanel {
    
    public StagePanel(String stageName, Stage s){
        //Stage s = new Stage(); //instanitate new controller
        //       send stage to controller
        JTabbedPane stagePanel = new JTabbedPane();
        stagePanel.addTab(stageName, stagePanel);
        addGrid(Stage s);
    }
    
    public void addGrid(Stage s){
        
    }
    
    public void addEditorPanel(){
        
    }
}
