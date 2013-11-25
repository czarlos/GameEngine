package parser;

import gameObject.GameObjectConstants;
import gameObject.Stats;
import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.item.Item;
import java.util.ArrayList;
import java.util.List;
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
        Grass.setName("Long Grass");
        Grass.setImagePath("resources/grass1.png");
        Grass.setPassableList(passableList);
        Grass.setStats(defaultStats);
        Grass.setActive(false);
        Grass.setMoveCost(1);

        grid.Tile Grass1 = new grid.Tile();
        Grass1.setName("Short Grass");
        Grass1.setImagePath("resources/grass.png");
        Grass1.setPassableList(passableList);
        Grass1.setStats(defaultStats);
        Grass1.setActive(false);
        Grass1.setMoveCost(1);

        grid.Tile Water = new grid.Tile();
        Water.setName("Water");
        Water.setImagePath("resources/water.png");
        Water.setPassableList(passableList);
        Water.setStats(defaultStats);
        Water.setActive(false);
        Water.setMoveCost(2);

        grid.Tile Lava = new grid.Tile();
        Lava.setName("Lava");
        Lava.setImagePath("resources/lava.png");
        Lava.setPassableList(passableList);
        Lava.setStats(defaultStats);
        Lava.setActive(false);
        Lava.setMoveCost(1);

        grid.Tile Brick = new grid.Tile();
        Brick.setName("Brick");
        Brick.setImagePath("resources/brick.png");
        Brick.setPassableList(passableList);
        Brick.setStats(defaultStats);
        Brick.setActive(false);
        Brick.setMoveCost(1);

        grid.Tile Rock = new grid.Tile();
        Rock.setName("Rock");
        Rock.setImagePath("resources/rocks.png");
        Rock.setPassableList(passableList);
        Rock.setStats(defaultStats);
        Rock.setActive(false);
        Rock.setMoveCost(1);

        grid.Tile Stone = new grid.Tile();
        Stone.setName("Stone");
        Stone.setImagePath("resources/stone.png");
        Stone.setPassableList(passableList);
        Stone.setStats(defaultStats);
        Stone.setActive(false);
        Stone.setMoveCost(1);

        grid.Tile Sand = new grid.Tile();
        Sand.setName("Sand");
        Sand.setImagePath("resources/sand.png");
        Sand.setPassableList(passableList);
        Sand.setStats(defaultStats);
        Sand.setActive(false);
        Sand.setMoveCost(1);

        grid.Tile Brush = new grid.Tile();
        Brush.setName("Brush");
        Brush.setImagePath("resources/brush.png");
        Brush.setPassableList(passableList);
        Brush.setStats(defaultStats);
        Brush.setActive(false);
        Brush.setMoveCost(1);

        list.add(Grass);
        list.add(Grass1);
        list.add(Brush);
        list.add(Water);
        list.add(Lava);
        list.add(Brick);
        list.add(Rock);
        list.add(Stone);
        list.add(Sand);

        p.createJSON("defaults/Tile", list);
    }

    public void makeObjects () {
        java.util.ArrayList<gameObject.GameObject> list =
                new java.util.ArrayList<gameObject.GameObject>();

        gameObject.GameObject tree = new gameObject.GameObject();
        tree.setName("Tree");
        tree.setImagePath("resources/tree.png");

        gameObject.GameObject chest = new gameObject.GameObject();
        chest.setName("Chest");
        chest.setImagePath("resources/chest.png");

        gameObject.GameObject stone = new gameObject.GameObject();
        stone.setName("Stone");
        stone.setImagePath("resources/stone1.png");

        tree.setPassableList(new java.util.ArrayList<String>());
        tree.setDisplayData(new ArrayList<String>());

        chest.setPassableList(new java.util.ArrayList<String>());
        chest.setDisplayData(new ArrayList<String>());
        
        stone.setPassableList(new java.util.ArrayList<String>());
        stone.setDisplayData(new ArrayList<String>());

        list.add(tree);
        list.add(chest);
        list.add(stone);

        p.createJSON("defaults/GameObject", list);
    }

    public void makeUnits () {
        java.util.ArrayList<gameObject.GameUnit> list =
                new java.util.ArrayList<gameObject.GameUnit>();

        gameObject.GameUnit hero = new gameObject.GameUnit();
        gameObject.GameUnit goldensun = new gameObject.GameUnit();
        gameObject.GameUnit enemy = new gameObject.GameUnit();
        gameObject.GameUnit charizard = new gameObject.GameUnit();
        gameObject.GameUnit roy = new gameObject.GameUnit();

        hero.setName("hero");
        hero.setImagePath("resources/hero.png");
        hero.setPassableList(new java.util.ArrayList<String>());
        hero.setControllable(true);
        hero.setStats(defaultStats);
        hero.setAffiliation("");
        hero.addItem(defaultItem);

        goldensun.setName("Golden Sun");
        goldensun.setImagePath("resources/goldensun.png");
        goldensun.setPassableList(new java.util.ArrayList<String>());
        goldensun.setControllable(true);
        goldensun.setAffiliation("");
        goldensun.setStats(defaultStats);
        goldensun.addItem(defaultItem);

        enemy.setName("Enemy");
        enemy.setImagePath("resources/enemy.png");
        enemy.setPassableList(new java.util.ArrayList<String>());
        enemy.setControllable(true);
        enemy.setAffiliation("");
        enemy.setStats(defaultStats);
        enemy.addItem(defaultItem);

        charizard.setName("Dragon");
        charizard.setImagePath("resources/charizard.png");
        charizard.setPassableList(new java.util.ArrayList<String>());
        charizard.setControllable(true);
        charizard.setAffiliation("");
        charizard.setStats(defaultStats);
        charizard.addItem(defaultItem);

        roy.setName("Roy");
        roy.setImagePath("resources/roy.png");
        roy.setPassableList(new java.util.ArrayList<String>());
        roy.setControllable(true);
        roy.setAffiliation("");
        roy.setStats(defaultStats);
        roy.addItem(defaultItem);

        list.add(hero);
        list.add(goldensun);
        list.add(enemy);
        list.add(charizard);
        list.add(roy);

        p.createJSON("defaults/GameUnit", list);
    }

    public void saveAndLoadGame () {
        controllers.WorldManager wm = new controllers.WorldManager();
        wm.setGameName("test");
        wm.addStage(10, 10, 1, "stageOne");
        wm.saveGame();
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

    public void makeItems () {
        List<Item> list = new ArrayList<Item>();

        Item milk = new Item();
        milk.setName("Milk");
        milk.setImagePath("resources/milk.png");
        milk.setStats(defaultStats);

        Item potion = new Item();
        potion.setName("Potion");
        potion.setImagePath("resources/potion.png");
        milk.setStats(defaultStats);

        Item armor = new Item();
        armor.setName("Armor");
        armor.setImagePath("resources/armor.png");

        Item weapon = new Item();
        weapon.setName("Weapon");
        weapon.setImagePath("resources/weapon.png");

        Item helmet = new Item();
        helmet.setName("Helmet");
        helmet.setImagePath("resources/helmet.png");
        
        list.add(milk);
        list.add(potion);
        list.add(armor);
        list.add(weapon);
        list.add(helmet);
        
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
        // handled differently in editor
        maker.makeConditions();
        maker.makeActions();

        maker.saveAndLoadGame();
    }

}