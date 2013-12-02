package dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author brooksmershon
 * 
 *         Panel capable of making a dialog (of itself) for drawing images and
 *         saving them
 */
public class ImageCreator extends JPanel {

	/**
     * 
     */
	private static final long serialVersionUID = -38084645183859719L;

	private static final int DEFAULT_RESOLUTION_X = 100;
	private static final int DEFAULT_RESOLUTION_Y = 100;

	Image myImage;
	DrawingPad canvas;

	/**
	 * Creates a new JComponent to permit loading, saving, and editing of images
	 * from file
	 */
	public ImageCreator() {

		myImage = new BufferedImage(DEFAULT_RESOLUTION_X, DEFAULT_RESOLUTION_Y,
				BufferedImage.TYPE_INT_ARGB);

		// a JComponent

		setLayout(new BorderLayout());

		canvas = new DrawingPad();

		add(canvas, BorderLayout.CENTER);

		setPreferredSize(new Dimension(400, 400));

	}

	/**
	 * 
	 * @param component
	 * @param title
	 * @param modal
	 * @param imageCreator
	 * @param okListener
	 * @param cancelListener
	 * @return JDialog in its own parent dialog
	 */
	public static JDialog createDialog(Component component, String title,
			boolean modal, ImageCreator imageCreator,
			ActionListener okListener, ActionListener cancelListener) {

		JDialog dialog = new JDialog();

		dialog.getContentPane().setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		// panel for option buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton ok = new JButton("OK");
		ok.addActionListener(okListener);

		JButton cancel = new JButton("Cancel");

		if (cancelListener != null)
			cancel.addActionListener(cancelListener);
		else
			cancel.addActionListener(new DefaultCancelListener(dialog));

		JButton reset = new JButton("Reset");
		reset.addActionListener(new ResetListener(imageCreator));

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.add(openItem);

		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		buttonPanel.add(reset);

		dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		dialog.getContentPane().add(menuBar, BorderLayout.NORTH);

		dialog.getContentPane().add(imageCreator, BorderLayout.CENTER);
		dialog.pack();

		return dialog;
	}

	/**
	 * 
	 * @param image
	 * @return ImageIcon with underlying image, usually a BufferedImage
	 */
	public ImageIcon setImage(Image image) {

		myImage = image;
		canvas.setBackgroundImage(image);
		return new ImageIcon(myImage);
	}

	/**
	 * Returns underlying image that is usually a BufferedImage
	 * 
	 * @return
	 */
	public Image getImage() {
		return myImage;
	}

	private static class DefaultListener implements ActionListener {

		private JDialog dialog;

		public DefaultListener(JDialog dialog) {
			super();
			this.dialog = dialog;

		}

		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
		}
	}

	private static class DefaultCancelListener implements ActionListener {

		private JDialog dialog;

		public DefaultCancelListener(JDialog dialog) {
			super();
			this.dialog = dialog;

		}

		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
		}
	}

	private static class ResetListener implements ActionListener {

		private JDialog dialog;
		private ImageCreator imageCreator;

		public ResetListener(ImageCreator imageCreator) {
			super();
			this.imageCreator = imageCreator;
		}

		public void actionPerformed(ActionEvent e) {
			imageCreator.getCanvas().clear();
		}
	}

	public DrawingPad getCanvas() {
		return canvas;
	}

}
