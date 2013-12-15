package tests;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import utils.ImageUtilities;


public class EnumTest extends JFrame {

    public EnumTest () throws IOException {
        super("The title bar");
        setLayout(new FlowLayout());
        File a = new File("resources/tree.png");
        BufferedImage bimg = ImageIO.read(a);
        BufferedImage highlighted = ImageUtilities.highlight(bimg, 3);
        JLabel pic1 = new JLabel(new ImageIcon(bimg));
        JLabel pic2 = new JLabel(new ImageIcon(highlighted));
        add(pic2);
        add(pic1);
    }

    public static void main (String[] args) throws IOException {
        JFrame frame = new EnumTest();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);

    }

}
