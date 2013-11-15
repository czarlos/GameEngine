package parser;

import stage.ItemCondition;
import stage.PositionCondition;
import stage.StatCondition;
import stage.TurnCondition;
import stage.UnitCountCondition;


public class MakeDefaults {

    private JSONParser p;

    public MakeDefaults () {
        p = new JSONParser();
    }

    public void makeTiles () throws Exception {
        java.util.ArrayList<grid.Tile> list = new java.util.ArrayList<grid.Tile>();

        grid.Tile Grass = new grid.Tile();
        Grass.setName("grass");
        Grass.setImagePath("resources/grass.png");
        Grass.setPassableList(new java.util.ArrayList<String>());
        Grass.setStatMods(new java.util.HashMap<String, Double>());
        Grass.setActive(false);
        Grass.setMoveCost(1);

        grid.Tile Water = new grid.Tile();
        Water.setName("water");
        Water.setImagePath("resources/water.png");
        Water.setPassableList(new java.util.ArrayList<String>());
        Water.setStatMods(new java.util.HashMap<String, Double>());
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

        p.createJSON("tree", tree);
        p.createObject("tree", gameObject.GameObject.class);

        list.add(tree);

        p.createJSON("defaults/GameObject", list);
    }

    public void makeUnits () {
        java.util.ArrayList<gameObject.GameUnit> list =
                new java.util.ArrayList<gameObject.GameUnit>();

        gameObject.GameUnit hero = new gameObject.GameUnit();

        gameObject.Stat stats = new gameObject.Stat();
        stats.setStatValue("movement", 3);

        hero.setName("hero");
        hero.setImagePath("resources/hero.png");
        hero.setPassableList(new java.util.ArrayList<String>());
        hero.setAffiliation(0);
        hero.setControllable(true);
        hero.setHealth(20);
        hero.setExperience(0);
        hero.setUnitStats(stats);
        hero.setItemList(new java.util.ArrayList<gameObject.item.Item>());

        list.add(hero);

        p.createJSON("defaults/GameUnit", list);
    }

    public void saveAndLoadGame () {
        controllers.WorldManager wm = new controllers.WorldManager("test");
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

    public static void main (String[] args) throws Exception {
        MakeDefaults maker = new MakeDefaults();
        maker.makeTiles();
        maker.makeObjects();
        maker.makeConditions();
        maker.makeUnits();
        maker.saveAndLoadGame();
    }

}
