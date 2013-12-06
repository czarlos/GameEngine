package grid;

import gameObject.action.Action;
import gameObject.action.MoveAction;
import gameObject.action.WaitAction;


public class GridConstants {
    public final static int TRANSPARENCY = 50;

    public static final String TILE = "Tile";
    public static final String GAMEUNIT = "GameUnit";
    public static final String GAMEOBJECT = "GameObject";
    public static final String ITEM = "Item";
    public static final String[] DEFAULTTYPES = { TILE, GAMEUNIT, GAMEOBJECT,
                                                 ITEM };

    public static final String STATS = "Stats";
    public static final String MASTERSTATS = "Master Stats";
    public static final String TEAM = "Team";
    public static final String CONDITION = "Condition";
    public static final String ACTION = "Action";

    public final static String DEFAULT_PASS_EVERYTHING = "everything";

    public final static String WAIT = "Wait";
    public final static String MOVE = "Move";
    public final static String GETITEM = "Get item";

    public static final Action[] COREACTIONS = { new MoveAction(), new WaitAction() };
}
