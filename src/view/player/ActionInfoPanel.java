package view.player;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import controller.actions.grid.BeginDoAction;
import controller.actions.grid.DoAction;
import controller.player.GridController;
import controller.player.NClickAction;


/**
 * Scrollable panel for displaying unit's actions as buttons.
 * 
 */
@SuppressWarnings("serial")
public class ActionInfoPanel extends ScrollableListPane {

    protected GridController myController;

    public ActionInfoPanel (List<String> actions, GridController controller) {
        super(actions);
        myController = controller;
    }

    /**
     * Call to populate the ActionInfoPane with action button. 
     * This override to ScrollableListPane displays the data in this pane as buttons binded to actions
     * instead of merely strings.
     */
    @Override
    public void populate (List<String> data) {

        JPanel buttons = new JPanel();
        buttons.setMaximumSize(new Dimension(200, data.size() * 30));
        buttons.setLayout(new GridLayout(0, 1));
        for (int i = 0; i < data.size(); i++) {
            String s = data.get(i);
            JButton button = new JButton(s);
            button.setAlignmentX(CENTER_ALIGNMENT);
            button.addActionListener(new ActionListener() {

                int myActionId;

                @Override
                public void actionPerformed (ActionEvent e) {
                    NClickAction action = new NClickAction(2, DoAction.class
                            .toString().substring(6), myActionId);
                    action.addPrecursorCommand(1, BeginDoAction.class
                            .toString().substring(6), myActionId);
                    myController.doCommand(action);
                }

                public ActionListener init (int id, JPanel panel) {
                    myActionId = id;
                    return this;
                }

            }.init(i, this));

            buttons.add(button);
        }
        add(buttons);
    }
}
