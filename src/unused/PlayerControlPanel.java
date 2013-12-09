package unused;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.editor.GridController;
import controller.editor.NClickAction;


@SuppressWarnings("serial")
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
                NClickAction move = new NClickAction(2,
                                                     "controller.actions.grid.MoveCharacter");
                move.addPrecursorCommand(1,
                                         "controller.actions.grid.BeginMoveCharacter");
                myGridController.doCommand(move);
            }

        });
        moveButton.setEnabled(true);

        return moveButton;
    }
}
