package view.editor;

import grid.GridConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import parser.JSONParser;
import view.GameView;
import view.player.PlayerView;
import controller.editor.GridEditorController;
import controllers.Manager;
import controllers.WorldManager;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.GameTableModel;


public class EditorFrame extends GameView {

    private static final long serialVersionUID = -8550671173122103688L;

    private ArrayList<StagePanel> myStagePanelList;
    private JMenuBar myMenuBar;
    private JTabbedPane stageTabbedPane;
    private GridEditorController myGridController;
    protected WorldManager myWorldManager;
    private TableDialog myDialog;
    protected String mySavesLocation="saves";

    public EditorFrame () {
        super("Omega_Nu Game Editor");
    }

    public EditorFrame (Manager m) {
        this();
        loadGame(m);

    }

    @Override
    protected void initializeWindow () {
        super.initializeWindow();
        stageTabbedPane = new JTabbedPane();
    }

    /**
     * adding menu bar to frame
     * 
     * @param frame
     * @return
     */
    @Override
    protected JMenuBar createMenuBar (JFrame frame) {
        myMenuBar = new JMenuBar();

        // first menu
        JMenu gameMenu = new JMenu("File");
        gameMenu.setMnemonic(KeyEvent.VK_F);
        myMenuBar.add(gameMenu);
        // add menu items
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setAccelerator(KeyStroke.getKeyStroke("control N"));
        gameMenu.add(newGame);
        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.setAccelerator(KeyStroke.getKeyStroke("control L"));
        gameMenu.add(loadGame);
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.setAccelerator(KeyStroke.getKeyStroke("control S"));
        gameMenu.add(saveGame);

        newGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                newGame();
            }
        });
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                loadGame(loadGame(mySavesLocation));
            }
        });
        saveGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                saveGame();
            }
        });

        return myMenuBar;
    }

    private void newGame () {
        JPanel newGamePanel = new JPanel();
        newGamePanel.setLayout(new GridLayout(1, 2));
        JLabel gameNameLabel = new JLabel("Game Name:");
        JTextField gameNameTextField = new JTextField(25);
        newGamePanel.add(gameNameLabel);
        newGamePanel.add(gameNameTextField);
        int value = JOptionPane.showConfirmDialog(this, newGamePanel,
                                                  "New Game!!", JOptionPane.OK_CANCEL_OPTION);
        if (value == JOptionPane.OK_OPTION) {

            String gameName = gameNameTextField.getText();
            WorldManager wm = new WorldManager();
            wm.setGameName(gameName);

            setFrame(wm);
            addStagePanel();
            stageTabbedPane
                    .addChangeListener(new TabChangeListener(
                                                             myWorldManager, stageTabbedPane));
        }
    }

    /**
     * adds new stage panel to main editor frame after asking for information
     * through dialog box.
     */
    private void addStagePanel () {
        JPanel stageInfoPanel = new JPanel();
        stageInfoPanel.setLayout(new GridLayout(0, 2));
        JLabel stageNameLabel = new JLabel("Stage Name:");
        JLabel xLabel = new JLabel("Width:");
        JLabel yLabel = new JLabel("Height:");
        JTextField stageNameTextField = new JTextField(25);
        JTextField xTextField = new JTextField(6);
        JTextField yTextField = new JTextField(6);
        JLabel imageLabel = new JLabel("Default Tile");
        JComboBox<String> imageMenu = new JComboBox<>();
        List<String> tileNames = myWorldManager.get("Tile");
        for (String s : tileNames) {
            imageMenu.addItem(s);
        }
        stageInfoPanel.add(stageNameLabel, BorderLayout.NORTH);
        stageInfoPanel.add(stageNameTextField);
        stageInfoPanel.add(xLabel);
        stageInfoPanel.add(xTextField);
        stageInfoPanel.add(yLabel);
        stageInfoPanel.add(yTextField);
        stageInfoPanel.add(imageLabel);
        stageInfoPanel.add(imageMenu);

        int value =
                JOptionPane.showConfirmDialog(this, stageInfoPanel,
                                              "Enter Stage Information",
                                              JOptionPane.OK_CANCEL_OPTION);
        if (value == JOptionPane.OK_OPTION) {
            String stageName = stageNameTextField.getText();

            try {
                int gridWidth = Integer.parseInt(xTextField.getText());
                int gridHeight = Integer.parseInt(yTextField.getText());
                String image = (String) imageMenu.getSelectedItem();
                int stageID =
                        myWorldManager.addStage(gridWidth, gridHeight, tileNames.indexOf(image),
                                                stageName, stageTabbedPane.getSelectedIndex() + 1);
                setStage(stageName, stageID);
            }
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter integer values", "Input Error",
                                              JOptionPane.WARNING_MESSAGE, null);
                addStagePanel();
                return;
            }
        }

    }

    private void removeStage () {
        int response =
                JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this stage?",
                                              "Delete Stage?", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            int i = stageTabbedPane.getSelectedIndex();
            stageTabbedPane.remove(i);
            myStagePanelList.remove(i);
            stageTabbedPane.setSelectedIndex(i - 1);
            myWorldManager.deleteStage(i);
        }
    }

    @Override
    protected void loadGame (Manager m) {
        if(m!=null){
            setFrame(m);
            setStages(m);
        }
    }

    public void setStages (Manager m) {
        for (String s : m.getStages()) {
            setStage(s, m.getStages().indexOf(s));
        }
    }

    protected void setFrame (Manager m) {
        super.clearWindow();
        myWorldManager = new WorldManager(m);
        myStagePanelList = new ArrayList<StagePanel>();
        myStagePanelList.clear();
        stageTabbedPane.removeAll();
        myGridController = new GridEditorController(myWorldManager, stageTabbedPane);
        addGameEditorMenus();
        this.remove(myBackground);
        this.add(stageTabbedPane, BorderLayout.CENTER);
        this.repaint();
        this.setTitle(myWorldManager.getGameName());
    }

    private void addGameEditorMenus () {
        JMenu stageMenu = new JMenu("Stage");
        stageMenu.setMnemonic(KeyEvent.VK_S);

        JMenuItem addStage = new JMenuItem("Add Stage");
        stageMenu.add(addStage);
        addStage.setAccelerator(KeyStroke.getKeyStroke("control A"));

        addStage.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                addStagePanel();
            }
        });
        JMenuItem prestory = new JMenuItem("Set Pre-Story");
        prestory.setAccelerator(KeyStroke.getKeyStroke("control P"));
        stageMenu.add(prestory);
        prestory.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                setStory("Pre");
            }
        });

        JMenuItem poststory = new JMenuItem("Set Post-Story");
        poststory.setAccelerator(KeyStroke.getKeyStroke("control shift P"));
        stageMenu.add(poststory);
        poststory.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                setStory("Post");
            }
        });

        JMenuItem setTeams = new JMenuItem("Configure Teams");
        stageMenu.add(setTeams);
        setTeams.addActionListener(new GamePrefListener(myWorldManager, GridConstants.TEAM));

        JMenuItem deleteStage = new JMenuItem("Delete Stage");
        stageMenu.add(deleteStage);
        deleteStage.setAccelerator(KeyStroke.getKeyStroke("control shift S"));

        deleteStage.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                removeStage();
            }
        });

        JMenu gameMenu = new JMenu("Game");

        stageMenu.setMnemonic(KeyEvent.VK_S);
        JMenuItem setMaster = new JMenuItem("Set Master Stats");
        setMaster
                .addActionListener(new GamePrefListener(myWorldManager, GridConstants.MASTERSTATS));
        gameMenu.add(setMaster);

        JMenuItem setActions = new JMenuItem("Add/Remove Actions");
        gameMenu.add(setActions);
        setActions.addActionListener(new GamePrefListener(myWorldManager, GridConstants.ACTION));

        JMenuItem runGame = new JMenuItem("Run Game");
        gameMenu.add(runGame);
        runGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                new PlayerView(myWorldManager).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ;
            }

        });

        myMenuBar.add(stageMenu, 1);
        myMenuBar.add(gameMenu, 1);
    }

    protected void setStage (String stageName, int stageID) {
        StagePanel sp =

                new StagePanel(myWorldManager,
                               myGridController, stageID);
        myStagePanelList.add(stageID, sp);
        sp.setName(stageName);
        stageTabbedPane.add(sp, stageID);
        stageTabbedPane.setSelectedIndex(stageID);
        stageTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged (ChangeEvent e) {
                switchActiveStage();
            }
        });

        this.repaint();
    }

    private void setStory (String prepost) {
        JPanel storyPanel = new JPanel();
        storyPanel.setLayout(new BoxLayout(storyPanel, BoxLayout.LINE_AXIS));
        JLabel label = new JLabel("Story:");
        JTextArea field = new JTextArea(10, 40);
        field.setLineWrap(true);
        field.setWrapStyleWord(true);
        storyPanel.add(label);
        storyPanel.add(field);
        int value =
                JOptionPane.showConfirmDialog(this, storyPanel,
                                              "Enter " + prepost + "story",
                                              JOptionPane.OK_CANCEL_OPTION,
                                              JOptionPane.PLAIN_MESSAGE);
        if (value == JOptionPane.OK_OPTION) {
            String story = field.getText();
            if (prepost.equals("Pre"))
                myWorldManager.setPreStory(story);
            if (prepost.equals("Post"))
                myWorldManager.setPostStory(story);
        }
    }

    private void switchActiveStage () {
        myWorldManager.setActiveStage(stageTabbedPane.getSelectedIndex());
    }

    public static void main (String[] args) {
        new EditorFrame();
    }

    class TabChangeListener implements ChangeListener {

        private WorldManager myWM;
        private JTabbedPane myPanel;

        public TabChangeListener (WorldManager wm, JTabbedPane panel) {
            myWM = wm;
            myPanel = panel;
        }

        @Override
        public void stateChanged (ChangeEvent e) {
            myWM.setActiveStage(myPanel.getSelectedIndex());
        }
    }

    class GamePrefListener implements ActionListener {
        private WorldManager myWM;
        private String myRequest;

        public GamePrefListener (WorldManager wm, String request) {
            myWM = wm;
            myRequest = request;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            GameTableModel model = null;
            model = myWM.getTableModel(myRequest);

            myDialog =
                    new TableDialog(model, new GamePrefDialogListener(myWM, model), myWM);
            myDialog.setVisible(true);
            myDialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }
    }

    class GamePrefDialogListener implements ActionListener {

        private WorldManager myWM;
        private GameTableModel myModel;

        public GamePrefDialogListener (WorldManager wm, GameTableModel model) {
            myWM = wm;
            myModel = model;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            myDialog.stopEditing();
            myWM.setData(myModel);
            myDialog.setVisible(false);
        }
    }

    protected void saveGame () {
        saveGame(mySaveLocation);
    }

    protected void saveGame (String location) {
        myWorldManager.saveGame(location);
    }

}
