package grid;

import gameObject.GameObject;
import gameObject.GameObjectConstants;
import gameObject.GameUnit;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import action.CombatAction;


/**
 * 
 * Grid class. Holds all tiles and objects, calculates movement
 * 
 * @author Kevin
 * @author Ken
 * 
 */
@JsonAutoDetect
public class Grid {
    @JsonProperty
    private int myWidth;
    @JsonProperty
    private int myHeight;
    @JsonProperty
    private Tile[][] myTiles;

    @JsonProperty
    private GameObject[][] myObjects;
    @JsonProperty
    private List<ArrayList<GameUnit>> myUnits;
    @JsonProperty
    private Map<Integer, List<GameObject>> myPassStatuses; // TODO: Add pass statuses. 0 = nothing
                                                           // passes, 1 = everything passes. Put
                                                           // this map in stage controller?
    private FromJSONFactory myFactory;

    /**
     * Creates a grid with the width and height set
     * 
     * @param width - int of number of columns of tiles
     * @param height - int of number of rows of tiles
     */

    // only for use by deserializer
    public Grid () {
    }

    public Grid (int width, int height, int tileID) {
        myWidth = width;
        myHeight = height;
        myTiles = new Tile[width][height];
        myObjects = new GameObject[width][height];
        myUnits = new ArrayList<>();
        myPassStatuses = new HashMap<>();
        myFactory = new FromJSONFactory();
        initGrid(tileID);
    }

    /**
     * Sets up default grid with tiles and objects
     */
    private void initGrid (int tileID) {
        initTiles(tileID);
        testInitObjects();
    }

    /**
     * Creates default tiles for grid
     */
    private void initTiles (int tileID) {
        for (int i = 0; i < myWidth; i++) {
            for (int j = 0; j < myHeight; j++) {
                myTiles[i][j] = (Tile) myFactory.make("Tile", tileID);
            }
        }
    }

    /**
     * Creates default objects and units for grid
     */
    private void testInitObjects () {
        GameObject tree = (GameObject) myFactory.make("GameObject", 0);
        placeObject(tree, 3, 5);
        GameObject link = (GameUnit) myFactory.make("GameUnit", 0);
        placeObject(link, 5, 5);
        beginMove(link);
        System.out.println("isActive: " + isActive(4, 4));
    }

    /**
     * Initiates the moving process for a gameUnit
     * 
     * @param gameUnit - GameUnit that is moving
     */
    public void beginMove (GameObject gameUnit) {
        System.out.println("beginMove, getTotalStat movement: " +
                           ((GameUnit) gameUnit).getTotalStat(GameObjectConstants.MOVEMENT));
        findMovementRange(getObjectCoordinate(gameUnit),
                          ((GameUnit) gameUnit).getTotalStat(GameObjectConstants.MOVEMENT),
                          gameUnit);
    }

    /**
     * Moves the unit to a new coordinate
     * 
     * @param gameUnit - GameUnit being moved
     * @param newCoordinate - Coordinate that unit is moving to
     */
    public void doMove (GameUnit gameUnit, Coordinate newCoordinate) {
        Coordinate oldCoordinate = getObjectCoordinate(gameUnit);
        removeObject(oldCoordinate.getX(), oldCoordinate.getY());
        placeObject(gameUnit, newCoordinate.getX(), newCoordinate.getY());
        setTilesInactive();
    }

    /**
     * Sets the tiles active that the GameObject can move to
     * 
     * @param coordinate - Coordinate of the current position of the GameObject
     * @param range - int of range that the GameObject can move
     * @param gameObject - GameObject that we are finding the range of
     */
    private void findMovementRange (Coordinate coordinate, int range, GameObject gameObject) {
        int[] rdelta = { -1, 0, 0, 1 };
        int[] cdelta = { 0, -1, 1, 0 };

        for (int i = 0; i < rdelta.length; i++) {
            int newX = coordinate.getX() + cdelta[i];
            int newY = coordinate.getY() + rdelta[i];
            System.out.println("findMovementRange: newX, newY: " + newX + ", " + newY);
            if (onGrid(newX, newY)) {
                Tile currentTile = getTile(newX, newY);
                if (currentTile.isPassable(gameObject)) {
                    int newRange = range - currentTile.getMoveCost();
                    System.out.println("findMovementRange: newRange: " + newRange);
                    GameObject currentObject = getObject(newX, newY);
                    if (currentObject != null && currentObject.isPassable(gameObject)) {
                        System.out.println("tree, coords: " + newX + ", " + newY);
                        findMovementRange(new Coordinate(newX, newY), newRange, gameObject);
                    }
                    else if (newRange >= 0) {
                        currentTile.setActive(true);
                        findMovementRange(new Coordinate(newX, newY), newRange, gameObject);
                    }
                }
            }
        }

    }

    /**
     * Checks if the input coordinate is on the grid
     * 
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     * @return - boolean of if the coordinate is valid
     */
    private boolean onGrid (int x, int y) {
        return (0 <= x && x < myWidth && 0 <= y && y < myHeight);
    }

    /**
     * Checks if a coordinate is a valid move or action (the tile is active)
     * 
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     * @return - boolean of if the coordinate is active
     */
    public boolean isActive (int x, int y) {
        return getTile(x, y).isActive();
    }

    /**
     * Initiates the action process
     * 
     * @param gameUnit - GameUnit that is doing the action
     * @param combatAction - CombatAction that is being used
     */
    public void beginAction (GameUnit gameUnit, CombatAction combatAction) {
        Coordinate objectCoordinate = getObjectCoordinate(gameUnit);
        findActionRange(objectCoordinate, combatAction.getAOE(), combatAction.isAround());
    }

    /**
     * Returns the game objects affected by the action
     * 
     * @param gameUnit - GameUnit that is doing the action
     * @param combatAction - CombatAction that is being used
     * @param actionCoordinate - Coordinate that the user selects for the action
     * @return - List of GameObjects that are affected
     */
    public List<GameObject> doAction (GameUnit gameUnit,
                                      CombatAction combatAction,
                                      Coordinate actionCoordinate) {
        Coordinate objectCoordinate = getObjectCoordinate(gameUnit);
        String direction = findDirection(objectCoordinate, combatAction, actionCoordinate);
        return findAffectedObjects(objectCoordinate, combatAction, direction);
    }

    /**
     * Sets the tiles active that an action can affect
     * 
     * @param coordinate - Coordinate where the action originates
     * @param area - List of Coordinates that map the area of the action
     * @param isAround - boolean of whether the action only affects one direction, or is all around
     *        the unit
     */
    private void findActionRange (Coordinate coordinate, List<Coordinate> area, boolean isAround) {
        if (isAround) {
            for (Coordinate cell : area) {
                getTile(coordinate.getX() + cell.getX(), coordinate.getY() + cell.getY())
                        .setActive(true); // up)
            }
        }
        else {
            for (Coordinate cell : area) {
                getTile(coordinate.getX() + cell.getX(), coordinate.getY() + cell.getY())
                        .setActive(true); // up
                getTile(coordinate.getX() + cell.getY(), coordinate.getY() - cell.getX())
                        .setActive(true); // right
                getTile(coordinate.getX() - cell.getX(), coordinate.getY() - cell.getY())
                        .setActive(true); // down
                getTile(coordinate.getX() - cell.getY(), coordinate.getY() + cell.getX())
                        .setActive(true); // left
            }
        }
    }

    /**
     * Finds direction of action user selects
     * 
     * @param unitCoordinate - Coordinate of the unit
     * @param area - List of Coordinates that action affects
     * @param selectedCoordinate - Coordinate that the user selected for the action
     * @return - String of the direction
     */
    private String findDirection (Coordinate unitCoordinate,
                                  CombatAction combatAction,
                                  Coordinate selectedCoordinate) {
        List<Coordinate> area = combatAction.getAOE();
        if (combatAction.isAround()) { return "around"; }
        for (Coordinate cell : area) {
            if (selectedCoordinate.equals(new Coordinate(unitCoordinate.getX() + cell.getX(),
                                                         unitCoordinate.getY() + cell.getY()))) {
                return "up";
            }
            else if (selectedCoordinate.equals(new Coordinate(unitCoordinate.getX() + cell.getY(),
                                                              unitCoordinate.getY() - cell.getX()))) {
                return "right";
            }
            else if (selectedCoordinate.equals(new Coordinate(unitCoordinate.getX() - cell.getX(),
                                                              unitCoordinate.getY() - cell.getY()))) {
                return "down";
            }
            else if (selectedCoordinate.equals(new Coordinate(unitCoordinate.getX() - cell.getY(),
                                                              unitCoordinate.getY() + cell.getX()))) { return "left"; }
        }
        return null;
    }

    /**
     * Returns objects in an action's area of effectiveness
     * 
     * @param coordinate - Coordinate where the action originates
     * @param combatAction - CombatAction being used
     * @param direction - String of the direction of the action
     * @return - List of GameObjects that are affected by the action
     */
    private List<GameObject> findAffectedObjects (Coordinate coordinate,
                                                  CombatAction combatAction,
                                                  String direction) {
        List<Coordinate> area = combatAction.getAOE();
        List<GameObject> affectedObjects = new ArrayList<GameObject>();
        GameObject currentObject;
        for (Coordinate cell : area) {
            if (direction.equals("all")) {
                currentObject =
                        getObject(coordinate.getX() + cell.getX(), coordinate.getY() + cell.getY());
            }
            else if (direction.equals("up")) {
                currentObject =
                        getObject(coordinate.getX() + cell.getX(), coordinate.getY() + cell.getY());
            }
            else if (direction.equals("right")) {
                currentObject =
                        getObject(coordinate.getX() + cell.getY(), coordinate.getY() - cell.getX());
            }
            else if (direction.equals("down")) {
                currentObject =
                        getObject(coordinate.getX() - cell.getX(), coordinate.getY() - cell.getY());
            }
            else {
                currentObject =
                        getObject(coordinate.getX() - cell.getY(), coordinate.getY() + cell.getX());
            }
            if (currentObject != null) {
                affectedObjects.add(currentObject);
            }
        }
        return affectedObjects;
    }

    /**
     * Returns an object at the given coordinates
     * 
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     * @return - GameObject at coordinate
     */
    public GameObject getObject (int x, int y) {
        // TODO: Generic method?
        return myObjects[x][y];
    }

    /**
     * Places a GameObject at given coordinates
     * 
     * @param gameObject - GameObject to be placed
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     */
    public void placeObject (GameObject gameObject, int x, int y) {
        // TODO: Generic method?
        myObjects[x][y] = gameObject;

        if (gameObject instanceof GameUnit) {
            if (!myUnits.isEmpty()) {
                for (ArrayList<GameUnit> unitList : myUnits) {
                    if (((GameUnit) gameObject).getAffiliation() == unitList.get(0)
                            .getAffiliation()) {
                        unitList.add((GameUnit) gameObject);
                    }
                }
                return;
            }

            ArrayList<GameUnit> newUnitList = new ArrayList<GameUnit>();
            newUnitList.add((GameUnit) gameObject);
            myUnits.add(newUnitList);
        }
    }

    /**
     * Sets position in myObjects map to null
     * 
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     */
    private void removeObject (int x, int y) {
        myObjects[x][y] = null;
    }

    public List<ArrayList<GameUnit>> getGameUnits () {
        return myUnits;
    }

    /**
     * Returns the coordinate of a gameObject
     * 
     * @param gameObject - GameObject being located
     * @return - Coordinate of object location
     */
    public Coordinate getObjectCoordinate (GameObject gameObject) {
        return myUnits.get(gameObject);
    }

    /**
     * Returns an tile at the given coordinates
     * 
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     * @return - Tile at coordinate
     */
    public Tile getTile (int x, int y) {
        // TODO: Generic method?
        return myTiles[x][y];
    }

    /**
     * Places a Tile at given coordinates
     * 
     * @param tile - Tile to be placed
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     */
    public void placeTile (Tile tile, int x, int y) {
        // TODO: Generic method?
        myTiles[x][y] = tile;
    }

    /**
     * Sets all tiles on grid to be inactive
     */
    private void setTilesInactive () {
        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[i].length; j++) {
                myTiles[i][j].setActive(false);
            }
        }
    }

    public void draw (Graphics g, int x, int y, int width, int height) {
        int tileWidth = width / myWidth;
        int tileHeight = height / myHeight;

        for (int i = 0; i < myTiles.length; i++) {
            for (int j = 0; j < myTiles[i].length; j++) {
                Tile tile = myTiles[i][j];
                tile.draw(g, i * tileWidth, j * tileHeight, tileWidth, tileHeight);
            }
        }

        // TODO: dupe for tile and object. generic
        for (int i = 0; i < myObjects.length; i++) {
            for (int j = 0; j < myObjects[i].length; j++) {
                GameObject gameObject = myObjects[i][j];
                if (gameObject != null) {
                    gameObject.draw(g, i * tileWidth, j * tileHeight, tileWidth, tileHeight);
                }
            }
        }
    }

    public Tile[][] getMyTiles () {
        return myTiles;
    }

    public void setMyTiles (Tile[][] myTiles) {
        this.myTiles = myTiles;
    }

    /**
     * @return The width of the grid
     */
    public int getWidth () {
        return myWidth;
    }

    /**
     * @return The height of the grid
     */
    public int getHeight () {
        return myHeight;
    }
}
