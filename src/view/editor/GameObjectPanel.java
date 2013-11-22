package view.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameObjectPanel extends JPanel implements MouseListener {

    /**
     * 
     */
    private static final long serialVersionUID = -5509317241029064960L;

    private String myName;
    private ImageIcon myImage;
    private boolean isSelected;
    private StageEditorPanel myEditorPanel;

    public GameObjectPanel (ImageIcon image, String name, StageEditorPanel editor) {
        myEditorPanel = editor;
        setLayout(new GridLayout(1, 2));
        // setMaximumSize(new Dimension(100,50));
        setBorder(BorderFactory.createLineBorder(Color.black));
        myName = name;
        myImage = image;
        isSelected = false;
        initPanel();
    }

    private void initPanel () {
        JLabel label = new JLabel(myImage);
        JLabel name = new JLabel(myName);
        add(label);
        add(name);
        addMouseListener(this);
        repaint();
    }

    public void deSelect () {
        isSelected = false;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void mouseClicked (MouseEvent e) {
        isSelected = true;
        setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
        myEditorPanel.changeSelected(this);
        repaint();
    }

    @Override
    public void mousePressed (MouseEvent e) {
    }

    @Override
    public void mouseReleased (MouseEvent e) {
    }

    @Override
    public void mouseEntered (MouseEvent e) {
        if (!isSelected)
            setBorder(BorderFactory.createLineBorder(Color.red, 2));
    }

    @Override
    public void mouseExited (MouseEvent e) {
        if (!isSelected)
            setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
