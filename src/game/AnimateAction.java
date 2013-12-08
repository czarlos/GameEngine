package game;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;


/**
 * Animate action creates a jframe and animates an attack between
 * two images via their image paths.
 * 
 * @author carlosreyes
 * 
 */
public class AnimateAction {

    public static void main (String[] args) {
        new AnimateAction(myEnemyPath, myEnemyPath);
    }

    static String myUnitPath;
    static String myEnemyPath;

    public AnimateAction (final String unitPath, final String enemyPath) {
        myUnitPath = unitPath;
        myEnemyPath = enemyPath;
        final JFrame frame = new JFrame("Test");

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run () {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new AnimationPane(frame, unitPath, enemyPath));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }

    public class AnimationPane extends JPanel {

        private BufferedImage unit;
        private BufferedImage enemy;
        private int xPos = 0;
        private int direction = 1;

        public AnimationPane (final JFrame frame, String unitPath, String enemyPath) {
            try {
                unit = ImageIO.read(new File(unitPath));
                enemy = ImageIO.read(new File(enemyPath));
                Timer timer = new Timer(5, new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent e) {
                        xPos += direction;
                        if (xPos + unit.getWidth() > getWidth()) {
                            xPos = getWidth() - unit.getWidth();
                            direction *= -1;
                        }
                        else if (xPos + unit.getWidth() >= getWidth() - enemy.getWidth()) {
                            frame.setVisible(false);
                            frame.dispose();
                        }
                        repaint();
                    }

                });
                timer.setRepeats(true);
                timer.setCoalesce(true);
                timer.start();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public Dimension getPreferredSize () {
            return unit == null ? super.getPreferredSize() : new Dimension(unit.getWidth() * 4,
                                                                           unit.getHeight());
        }

        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);

            int y = getHeight() - unit.getHeight();
            g.drawImage(unit, xPos, y, this);

            int x = getWidth() - unit.getWidth();
            g.drawImage(enemy, x, y, this);

        }

    }

}
