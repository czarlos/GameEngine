package view.editor;

import grid.Coordinate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import controllers.WorldManager;
import view.player.InfoPanel;

public class ActionInfoPanel extends InfoPanel {

    private WorldManager myWorldManager;
    private Coordinate myCoordinate;
    
    public ActionInfoPanel (List<String> data, WorldManager wm, Coordinate c) {
        super(data);
        myWorldManager = wm;
        myCoordinate = c;
        addButtonPanel();
        
    }
    
    private void addButtonPanel(){
        JButton highlight = new JButton("Show Range");
        highlight.addActionListener(new InfoActionListener(myWorldManager, myCoordinate));
        super.add(highlight);
    }
    
    
    class InfoActionListener implements ActionListener {

        private WorldManager myWM;
        private Coordinate myCoordinate;
        private boolean pressed;
        
        public InfoActionListener(WorldManager wm, Coordinate c){
            myWM = wm;
            myCoordinate = c;
            pressed = false;
        }
        
        @Override
        public void actionPerformed (ActionEvent e) {
            pressed = !pressed;
            if(pressed){
                myWorldManager.displayRange(myCoordinate);
            }else{
                myWorldManager.removeRange();
            }
            
        }
        
    }
}
