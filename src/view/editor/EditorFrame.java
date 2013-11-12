package view.editor;

import grid.Grid;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import controllers.WorldManager;
import stage.Stage;

public class EditorFrame extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = -8550671173122103688L;
    
    private ArrayList<StagePanel> myStagePanelList = new ArrayList<StagePanel>();
    private JMenuBar myMenuBar;
    private JTabbedPane stageTabbedPane;
    private WorldManager myWorldManager;
    
    public EditorFrame(){
        super("Omega_Nu Game Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar(this));
        stageTabbedPane = new JTabbedPane();
        stageTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(stageTabbedPane, BorderLayout.CENTER);
        pack();
        setSize(800,600);
        setVisible(true);
        //addStagePanel();
    }
    
 
    /**
     * adding menu bar to frame
     * @param frame
     * @return
     */
    private JMenuBar createMenuBar(JFrame frame){
        myMenuBar = new JMenuBar();
        
        //first menu 
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_F);
        myMenuBar.add(gameMenu);
        //add menu items
        JMenuItem newGame = new JMenuItem("New Game");
        gameMenu.add(newGame);
        JMenuItem loadGame = new JMenuItem("Load Game");
        gameMenu.add(loadGame);
        JMenuItem saveGame = new JMenuItem("Save Game");
        gameMenu.add(saveGame);
        JMenuItem addStage = new JMenuItem("Add Stage");
        gameMenu.add(addStage);
        //add action listeners
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newGame();
            }});
        addStage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                addStagePanel();
            }});
        
        //second menu 
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        myMenuBar.add(editMenu);
        //add menu items
        JMenuItem undo = new JMenuItem("Undo");
        editMenu.add(undo);
        JMenuItem redo = new JMenuItem("Redo");
        editMenu.add(redo);
        
        return myMenuBar;
    }
    
    private void newGame(){
        System.out.print("new game!!");//checks actionlistener for now
        JPanel newGamePanel = new JPanel();
        newGamePanel.setLayout(new GridLayout(0,2));
        JLabel gameNameLabel = new JLabel("Game Name:");
        JTextField gameNameTextField = new JTextField(25);
        newGamePanel.add(gameNameLabel);
        newGamePanel.add(gameNameTextField);
        int value = JOptionPane.showConfirmDialog(this, newGamePanel, "New Game!!", JOptionPane.OK_CANCEL_OPTION);
        if(value == JOptionPane.OK_OPTION){
            String gameName = gameNameTextField.getText();
            addStagePanel();
        } 
    }
    
    
    /**
     * adds new stage panel to main editor frame after asking
     * for information through dialog box.
     */
    private void addStagePanel(){
        JPanel stageInfoPanel = new JPanel();
        stageInfoPanel.setLayout(new GridLayout(0,2));
        JLabel stageNameLabel = new JLabel("Stage Name:");
        JLabel xLabel = new JLabel("Width:");
        JLabel yLabel = new JLabel("Height:");
        JTextField stageNameTextField = new JTextField(25);
        JTextField xTextField = new JTextField(6);
        JTextField yTextField = new JTextField(6);
        JLabel imageLabel = new JLabel("Default Tile");
        JComboBox<String> imageMenu = new JComboBox<String>();
        imageMenu.addItem("grass");
        imageMenu.addItem("water");
        
        stageInfoPanel.add(stageNameLabel,BorderLayout.NORTH);
        stageInfoPanel.add(stageNameTextField);
        stageInfoPanel.add(xLabel);
        stageInfoPanel.add(xTextField);
        stageInfoPanel.add(yLabel);
        stageInfoPanel.add(yTextField);
        stageInfoPanel.add(imageLabel);
        stageInfoPanel.add(imageMenu);
        

        int value = JOptionPane.showConfirmDialog(this, stageInfoPanel, "Enter Stage Information", JOptionPane.OK_CANCEL_OPTION);
        if(value == JOptionPane.OK_OPTION){
            String stageName = stageNameTextField.getText();
            int gridWidth = Integer.parseInt(xTextField.getText());
            int gridHeight = Integer.parseInt(yTextField.getText());
            String image = (String) imageMenu.getSelectedItem();
            //instantiate Stage and send it to controller
            myWorldManager.addStage(1, gridWidth, gridHeight);
            StagePanel sp = new StagePanel(stageName, myWorldManager.getGrid());//stage.getGrid);
            myStagePanelList.add(sp);
            stageTabbedPane.addTab(stageName, sp);
        }

    }
    
    public static void main(String[] args) {
        new EditorFrame();
    }
}
