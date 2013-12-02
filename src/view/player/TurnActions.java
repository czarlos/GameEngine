package view.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;


public class TurnActions extends JPanel {
    public TurnActions () {
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                // TODO Auto-generated method stub

            }

        });

        add(endTurn);
    }
}
