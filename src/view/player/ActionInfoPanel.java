package view.player;

import grid.Coordinate;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import controller.actions.grid.BeginMoveCharacter;
import controller.actions.grid.MoveCharacter;
import controller.editor.GridController;
import controller.editor.NClickAction;


public class ActionInfoPanel extends ScrollableListPane {

    protected Coordinate myCoordinate;
    protected GridController myController;

    public ActionInfoPanel (List<String> actions,
                            GridController controller,
                            Coordinate selectedCoordinate) {
        super(actions);
        populate(actions);
        myController = controller;

    }

    @Override
    public void populate (List<String> data) {
        JButton move = new JButton("Move");
        move.addActionListener(new ActionListener() {

            String myAction;

            @Override
            public void actionPerformed (ActionEvent e) {
                NClickAction action = new NClickAction(2, MoveCharacter.class.toString());
                action.addPrecursorCommand(1, BeginMoveCharacter.class.toString());
                myController.doCommand(action);
            }

        });

        add(move);

        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i);
            JButton button = new JButton(s);
            button.addActionListener(new ActionListener() {

                String myActionName;

                @Override
                public void actionPerformed (ActionEvent e) {
                    NClickAction action =
                            new NClickAction(2, MoveCharacter.class.toString(), myActionName);
                    action.addPrecursorCommand(1, BeginMoveCharacter.class.toString());
                    myController.doCommand(action);
                }

                public ActionListener init (String s) {
                    myActionName = s;
                    return this;
                }

            }.init(s));
        }
    }

}
