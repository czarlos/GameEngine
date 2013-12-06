package view.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import view.GameView;

/**
 * 
 * @author brooksmershon
 *
 */
public class TurnActionsPanel extends JPanel {
    public TurnActionsPanel (PlayerView pv) {
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(new ActionListener() {
            
            private PlayerView myPanel;

            @Override
            public void actionPerformed (ActionEvent e) {
                myPanel.endTurn();
            }
            
            public ActionListener init(PlayerView panel){
                myPanel = panel;
                
                return this;
            }
        }.init(pv));
        add(endTurn);
    }
}
