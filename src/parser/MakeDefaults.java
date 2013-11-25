package parser;

import gameObject.GameObjectConstants;
import gameObject.MasterStats;
import gameObject.Stats;
import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import stage.ItemCondition;
import stage.PositionCondition;
import stage.StatCondition;
import stage.TurnCondition;
import stage.UnitCountCondition;


public class MakeDefaults {

    private JSONParser p;
    private Item defaultItem;
    private Action defaultAction;
    private Stats defaultStats;

    public MakeDefaults () {
        p = new JSONParser();

        defaultStats = new Stats();
        defaultStats.updateFromMaster();

        defaultAction = new CombatAction();
        defaultAction.setName("Attack");

        defaultItem = new Item();
        defaultItem.addAction(defaultAction);
        defaultItem.setName("Item");
        defaultItem.setStats(defaultStats);
    }

    public void makeTiles () throws Exception {
        java.util.ArrayList<grid.Tile> list = new java.util.ArrayList<grid.Tile>();
        List<String> passableList = new ArrayList<>();
        defaultStats = new Stats();
        defaultStats.updateFromMaster();

        passableList.add(GameObjectConstants.DEFAULT_PASS_EVERYTHING);
        grid.Tile Grass = new grid.Tile();
        Grass.setName("grass");
        Grass.setImagePath("resources/grass.png");
        Grass.setPassableList(passableList);
        Grass.setStats(defaultStats);
        Grass.setActive(false);
        Grass.setMoveCost(1);

        grid.Tile Water = new grid.Tile();
        Water.setName("water");
        Water.setImagePath("resources/water.png");
        Water.setPassableList(passableList);
        Water.setStats(defaultStats);
        Water.setActive(false);
        Water.setMoveCost(2);

        list.add(Grass);
        list.add(Water);

        p.createJSON("defaults/Tile", list);
    }

    public void makeObjects () {
        java.util.ArrayList<gameObject.GameObject> list =
                new java.util.ArrayList<gameObject.GameObject>();

        gameObject.GameObject tree = new gameObject.GameObject();
        tree.setName("tree");
        tree.setImagePath("resources/tree.png");
        tree.setPassableList(new java.util.ArrayList<String>());
        tree.setDisplayData(new ArrayList<String>());

        p.createJSON("tree", tree);
        p.createObject("tree", gameObject.GameObject.class);

        list.add(tree);

        p.createJSON("defaults/GameObject", list);
    }

    public void makeUnits () {
        java.util.ArrayList<gameObject.GameUnit> list =
                new java.util.ArrayList<gameObject.GameUnit>();

        gameObject.GameUnit hero = new gameObject.GameUnit();

        hero.setName("hero");
        hero.setImagePath("resources/hero.png");
        hero.setPassableList(new java.util.ArrayList<String>());
        hero.setControllable(true);
        hero.setStats(defaultStats);
        hero.setAffiliation("");
        hero.addItem(defaultItem);

        list.add(hero);

        p.createJSON("defaults/GameUnit", list);
    }

    public void saveAndLoadGame () {
        controllers.WorldManager wm = new controllers.WorldManager();
        wm.setGameName("test");
        wm.addStage(10, 10, 1, "stageOne");
        wm.saveGame();

        wm.loadGame("test");
    }

    public void makeConditions () {
        java.util.ArrayList<stage.Condition> list = new java.util.ArrayList<stage.Condition>();
        list.add(new PositionCondition());
        list.add(new StatCondition());
        list.add(new ItemCondition());
        list.add(new UnitCountCondition());
        list.add(new TurnCondition());

        p.createJSON("defaults/Condition", list);
    }

    public void makeActions () {
        List<Action> list = new ArrayList<Action>();
        list.add(new CombatAction());

        p.createJSON("defaults/Action", list);
    }

    public void makeStats () {
        List<Item> list = new ArrayList<Item>();
        Item i = new Item();

        i.setName("Generic item");
        i.setStats(defaultStats);
        i.addAction(defaultAction);
        p.createJSON("defaults/Item", list);
    }

    public void makeItems () {
        List<Item> list = new ArrayList<Item>();
        Item i = new Item();

        i.setName("Generic item");
        i.setStats(defaultStats);
        i.addAction(defaultAction);

        p.createJSON("defaults/Item", list);
    }

    /**
     * Just run this to refresh the default JSONs
     * 
     * @param args
     * @throws Exception
     */
    public static void main (String[] args) throws Exception {
        MakeDefaults maker = new MakeDefaults();
        maker.makeTiles();
        maker.makeObjects();
        maker.makeUnits();
        maker.makeItems();
        maker.makeStats();

        // handled differently in editor
        maker.makeConditions();
        maker.makeActions();

        maker.saveAndLoadGame();
    }

}
