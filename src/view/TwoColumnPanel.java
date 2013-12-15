package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;


/**
 * Class to make a two column layout JPanel.
 * The class takes two JPanels and displays them properly using a
 * GridBagLayout. To tweak layout options, make changes to the GridBagConstraints
 * generated in this class.
 * 
 */
@SuppressWarnings("serial")
public class TwoColumnPanel extends JPanel {

    public TwoColumnPanel () {
        super();
        setLayout(new GridBagLayout());
    }

    // I made the choice to have distinct methods to generate
    // the two layouts needed for columns because GridBagLayouts are
    // very verbose and easier to understand when written all the way out.
    // The alternative would be to hand values for each GridBagConstraint into
    // a method as parameters. I feel explicitly defining a new GridBagLayout
    // for each column makes changing the layout simpler, as it is easier to see
    // which value is associated with which property.
    private GridBagConstraints generateMainColumnConstraints () {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 4;
        c.gridwidth = 4;
        c.weightx = 1;
        c.weighty = 1;

        return c;
    }

    private GridBagConstraints generateSideColumnConstraints () {
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 4;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.gridheight = 4;
        cons.gridwidth = 1;
        cons.weightx = 0;
        cons.weighty = 0;

        return cons;
    }

    public void addToMainColumn (JComponent panel) {
        add(panel, generateMainColumnConstraints());
    }

    public void addToSideColumn (JComponent panel) {
        add(panel, generateSideColumnConstraints());
    }

    public void addCustomPanel (JComponent panel, GridBagConstraints constraint) {
        add(panel, constraint);
    }

}
