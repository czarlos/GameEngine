package view.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TurnActions extends JPanel {
	public TurnActions(Semaphore sem) {
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(new ActionListener() {

			private Semaphore mySem;

			@Override
			public void actionPerformed(ActionEvent e) {
				mySem.release();
			}

			public ActionListener init(Semaphore sem) {
				mySem = sem;
				return this;
			}

		}.init(sem));

		add(endTurn);
	}
}
