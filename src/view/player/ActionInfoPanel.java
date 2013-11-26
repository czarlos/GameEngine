package view.player;

import grid.Coordinate;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import controller.actions.grid.BeginDoAction;
import controller.actions.grid.DoAction;
import controller.editor.GridController;
import controller.editor.NClickAction;


public class ActionInfoPanel extends ScrollableListPane {

    protected GridController myController;

    public ActionInfoPanel (List<String> actions,
                            GridController controller) {
        super(actions);
        myController = controller;

    }

    @Override
    public void populate (List<String> data) {
    	setLayout(new GridLayout(data.size(),0));
        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i);
            JButton button = new JButton(s);
            button.setAlignmentX(CENTER_ALIGNMENT);
            button.addActionListener(new ActionListener() {

                int myActionId;

                @Override
                public void actionPerformed (ActionEvent e) {
                    NClickAction action =
                            new NClickAction(2, DoAction.class.toString().substring(6), myActionId);
                    action.addPrecursorCommand(1, BeginDoAction.class.toString().substring(6),
                                               myActionId);
                    myController.doCommand(action);
                    revalidate();
                }

                public ActionListener init (int id) {
                    myActionId = id;
                    return this;
                }
                
            }.init(i));

            add(button);
        }
    }

}
