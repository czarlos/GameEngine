package view.player;

import grid.Coordinate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.actions.grid.MoveCharacter;
import controller.editor.GridController;


public class PlayerControlPanel extends JPanel {
    private GridController myGridController;

    public PlayerControlPanel () {

    }

    public PlayerControlPanel (GridController controller) {
        myGridController = controller;
        generatePanel();

    }

    private void generatePanel () {
        add(generateMoveButton());
        setSize(100, 200);
        repaint();

    }

    public JButton generateMoveButton () {
        JButton moveButton = new JButton("Move");
        moveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                myGridController.doCommand("controller.actions.grid.MoveCharacter", 2);
            }

        });
        moveButton.setEnabled(true);

        return moveButton;
    }
}
