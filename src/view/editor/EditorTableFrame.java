package view.editor;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

public class EditorTableFrame extends JFrame implements WindowListener {

	/**
     * 
     */
	private static final long serialVersionUID = 7225370093723510786L;

	private StageEditorPanel myParent;
	private String myType;

	public EditorTableFrame(String type, StageEditorPanel stage) {
		super(type + " Editor");
		myParent = stage;
		myType = type;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		myParent.refreshTab(myType);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
