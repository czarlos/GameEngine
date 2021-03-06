package view.player;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Panel responsible for the "End Turn" button
 * Calls PlayerView.endTurn() when clicked
 * 
 * @author brooksmershon
 * 
 */
@SuppressWarnings("serial")
public class TurnActionsPanel extends JPanel {
    public TurnActionsPanel (PlayerView pv) {
        setLayout(new BorderLayout());
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(new ActionListener() {

            private PlayerView myPanel;

            @Override
            public void actionPerformed (ActionEvent e) {
                myPanel.endTurn();
            }

            public ActionListener init (PlayerView panel) {
                myPanel = panel;
                return this;
            }
        }.init(pv));
        add(endTurn, BorderLayout.CENTER);
    }
}
