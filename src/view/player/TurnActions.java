package view.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;
import javax.swing.JPanel;
import controllers.GUIBlocker;


public class TurnActions extends JPanel {
    public TurnActions () {
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(new ActionListener() {

            private Semaphore mySem;

            @Override
            public void actionPerformed (ActionEvent e) {
                GUIBlocker.getSem().release();
            }

        });

        add(endTurn);
    }
}
