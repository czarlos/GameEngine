package parser;

import gameObject.Stats;
import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.action.Outcomes;
import gameObject.item.Item;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import controllers.WorldManager;
import stage.Condition;
import stage.UnitCountCondition;
import stage.WinCondition;
import team.Team;


public class MakeDefaults {

    private JSONParser p;
    private Item defaultItem;
    private CombatAction defaultCombatAction;
    private Stats defaultStats;

    public MakeDefaults () {
        p = new JSONParser();

        defaultStats = new Stats();
        defaultStats.syncWithMaster();

        defaultCombatAction = new CombatAction();
        defaultCombatAction.setName("Slash");
        defaultCombatAction.setActionRange(1);
        defaultCombatAction.setInitiatorOutcomes(new Outcomes());
        defaultCombatAction.setReceiverOutcomes(new Outcomes());
        defaultCombatAction.setInitiatorStatWeights(new Stats());
        defaultCombatAction.setReceiverStatWeights(new Stats());

        defaultItem = new Item();
        List<String> actionList = new ArrayList<>();
        actionList.add("Slash");
        defaultItem.setActions(actionList);
        defaultItem.setName("Item");
        defaultItem.setStats(defaultStats);
        defaultItem.setImagePath("resources/potion.png");
    }

    public void makeTiles () throws Exception {
        java.util.ArrayList<grid.Tile> list = new java.util.ArrayList<>();
        List<String> passableList = new ArrayList<>();
        defaultStats = new Stats();
        defaultStats.syncWithMaster();

        passableList.add(GridConstants.DEFAULT_PASS_EVERYTHING);

        grid.Tile Grass = new grid.Tile();
        Grass.setName("Long Grass");
        Grass.setImagePath("resources/grass1.png");
        Grass.setStats(defaultStats);
        Grass.setActive(false);
        Grass.setMoveCost(1);

        grid.Tile Grass1 = new grid.Tile();
        Grass1.setName("Short Grass");
        Grass1.setImagePath("resources/grass.png");
        Grass1.setStats(defaultStats);
        Grass1.setActive(false);
        Grass1.setMoveCost(1);

        grid.Tile Water = new grid.Tile();
        Water.setName("Water");
        Water.setImagePath("resources/water.png");
        Water.setStats(defaultStats);
        Water.setActive(false);
        Water.setMoveCost(2);

        grid.Tile Lava = new grid.Tile();
        Lava.setName("Lava");
        Lava.setImagePath("resources/lava.png");
        Lava.setStats(defaultStats);
        Lava.setActive(false);
        Lava.setMoveCost(3);

        grid.Tile Brick = new grid.Tile();
        Brick.setName("Brick");
        Brick.setImagePath("resources/brick.png");
        Brick.setStats(defaultStats);
        Brick.setActive(false);
        Brick.setMoveCost(1);

        grid.Tile Rock = new grid.Tile();
        Rock.setName("Rock");
        Rock.setImagePath("resources/rocks.png");
        Rock.setStats(defaultStats);
        Rock.setActive(false);
        Rock.setMoveCost(1);

        grid.Tile Stone = new grid.Tile();
        Stone.setName("Stone");
        Stone.setImagePath("resources/stone.png");
        Stone.setStats(defaultStats);
        Stone.setActive(false);
        Stone.setMoveCost(1);

        grid.Tile Sand = new grid.Tile();
        Sand.setName("Sand");
        Sand.setImagePath("resources/sand.png");
        Sand.setStats(defaultStats);
        Sand.setActive(false);
        Sand.setMoveCost(1);

        grid.Tile Brush = new grid.Tile();
        Brush.setName("Brush");
        Brush.setImagePath("resources/brush.png");
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

        p.createJSON("defaults/" + GridConstants.TILE, list);
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

        list.add(tree);
        list.add(chest);
        list.add(stone);

        p.createJSON("defaults/" + GridConstants.GAMEOBJECT, list);
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
        hero.setStats(defaultStats);
        hero.addItem(defaultItem);
        hero.setAffiliation("player");

        goldensun.setName("Golden Sun");
        goldensun.setImagePath("resources/goldensun.png");
        goldensun.setStats(defaultStats);
        goldensun.addItem(defaultItem);
        goldensun.setAffiliation("player");

        enemy.setName("Enemy");
        enemy.setImagePath("resources/enemy.png");
        enemy.setStats(defaultStats);
        enemy.addItem(defaultItem);
        enemy.setAffiliation("enemy");

        charizard.setName("Dragon");
        charizard.setImagePath("resources/charizard.png");
        charizard.setStats(defaultStats);
        charizard.addItem(defaultItem);
        charizard.setAffiliation("enemy");

        roy.setName("Roy");
        roy.setImagePath("resources/roy.png");
        roy.setStats(defaultStats);
        roy.addItem(defaultItem);
        roy.setAffiliation("enemy");

        list.add(hero);
        list.add(goldensun);
        list.add(enemy);
        list.add(charizard);
        list.add(roy);

        p.createJSON("defaults/" + GridConstants.GAMEUNIT, list);
    }

    public void saveAndLoadGame () {
        controllers.WorldManager wm = new controllers.WorldManager();
        wm.setGameName("test");
        wm.addStage(10, 10, 1, "stageOne");
        wm.saveGame("saves");

        p.createObject("saves/test", WorldManager.class);
    }

    public void makeTeams () {
        List<Team> list = new ArrayList<Team>();
        Team defaultTeam = new Team();
        defaultTeam.setName("player");
        defaultTeam.setGold(0);
        defaultTeam.setImagePath("resources/grass.png");
        defaultTeam.setIsHuman(true);
        WinCondition wc = new WinCondition();
        wc.addCondition(new UnitCountCondition());
        defaultTeam.setWinCondition(wc);

        Team enemyTeam = new Team();
        enemyTeam.setName("enemy");
        enemyTeam.setGold(0);
        enemyTeam.setImagePath("resources/grass.png");
        enemyTeam.setIsHuman(false);

        WinCondition wcEnemy = new WinCondition();
        Condition c = new UnitCountCondition();
        c.addData("affiliation", "player");
        wcEnemy.addCondition(c);

        enemyTeam.setWinCondition(wc);

        list.add(defaultTeam);
        list.add(enemyTeam);

        p.createJSON("defaults/" + GridConstants.TEAM, list);
    }

    public void makeActions () {
        List<Action> list = new ArrayList<Action>();
        list.add(defaultCombatAction);

        p.createJSON("defaults/" + GridConstants.ACTION, list);
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
        potion.setStats(defaultStats);

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

        p.createJSON("defaults/" + GridConstants.ITEM, list);
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

        maker.makeTeams();
        maker.makeActions();

        maker.saveAndLoadGame();
    }

}
