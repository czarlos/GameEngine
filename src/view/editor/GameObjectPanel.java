package view.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
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
    private String myType;
    private String myName;
    private Image myImage;
    private boolean isSelected;
    private StageEditorPanel myEditorPanel;

    public GameObjectPanel (String type, Image image, String name, StageEditorPanel editor) {
        myType = type;
        myEditorPanel = editor;
        setLayout(new GridLayout(1, 2));
        setBorder(BorderFactory.createLineBorder(Color.black));
        myName = name;
        myImage = image;
        isSelected = false;
        initPanel();
    }

    private void initPanel () {
        // JLabel label = new JLabel(myImage);
        ImagePanel imagePanel = new ImagePanel(myImage);
        imagePanel.setPreferredSize(new Dimension(35, 35));
        JLabel name = new JLabel(myName);
        add(imagePanel);
        add(name);
        addMouseListener(this);
        repaint();
    }

    public String getType () {

        return myType;
    }

    public String getName () {
        return myName;
    }

    public void deSelect () {
        isSelected = false;
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public void mouseClicked (MouseEvent e) {
        isSelected = true;
        setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
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
