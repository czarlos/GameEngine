package grid;

import gameObject.GameObject;
import gameObject.GameUnit;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import action.CombatAction;
import view.Drawable;


/**
 * Grid class. Holds all tiles and objects, calculates movement
 * 
 * @author Kevin, Ken
 * 
 */
@JsonAutoDetect
public class Grid implements Drawable {
    @JsonProperty
    private int myWidth;
    @JsonProperty
    private int myHeight;
    @JsonProperty
    private Tile[][] myTiles;
    @JsonProperty
    private GameObject[][] myObjects;
    @JsonProperty
    private List<GameUnit> myUnits;
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
        myUnits = new ArrayList<GameUnit>();
        myPassStatuses = new HashMap<Integer, List<GameObject>>();
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
        myObjects[3][5] = (GameObject) myFactory.make("GameObject", 0);
        GameObject link = (GameUnit) myFactory.make("GameUnit", 0);
        myObjects[4][5] = link;
        findMovementRange(new Coordinate(4, 5),
                          ((GameUnit) link).getStats().getStatValue("movement"), link);
    }

    /**
     * Initiates the moving process for a gameUnit
     * 
     * @param coordinate - Coordinate that the gameUnit is initially on
     */
    public void move (Coordinate coordinate) {
        GameObject movingObject = getObject(coordinate.getX(), coordinate.getY());
        findMovementRange(coordinate,
                          ((GameUnit) movingObject).getStats().getStatValue("movement"),
                          movingObject);
        // TODO: use cursor to select active tile to move to. use canDo. then update map of unit's
        // coordinate
    }

    /**
     * Sets the tiles active that the GameObject can move to
     * 
     * @param coordinate - Coordinate of the current position of the GameObject
     * @param range - int of range that the GameObject can move
     * @param gameObject -
     */
    private void findMovementRange (Coordinate coordinate, int range, GameObject gameObject) {
        int[] rdelta = { -1, 0, 0, 1 };
        int[] cdelta = { 0, -1, 1, 0 };

        for (int i = 0; i < rdelta.length; i++) {
            int newX = coordinate.getX() + cdelta[i];
            int newY = coordinate.getY() + rdelta[i];
            if (onGrid(newX, newY)) {
                Tile currentTile = getTile(newX, newY);
                int newRange = range - currentTile.getMoveCost();
                GameObject currentObject = getObject(newX, newY);
                if (currentObject != null && currentObject.isPassable(gameObject)) {
                    findMovementRange(new Coordinate(newX, newY), newRange, gameObject);
                }
                else if (newRange >= 0) {
                    currentTile.setActive(true);
                    findMovementRange(new Coordinate(newX, newY), newRange, gameObject);
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
     * @return - boolean of if the coordinate is valid
     */
    public boolean canDo (int x, int y) {
        return getTile(x, y).isActive();
    }

    /**
     * Initiates the action process
     * 
     * @param gameObject - GameObject that is doing the action
     * @param combatAction - CombatAction that is being used
     */
    public void action (GameObject gameObject, CombatAction combatAction) {
        // TODO: get coordinates from gameObject
        Coordinate coordinate = new Coordinate(10, 10); // I added in this placeholder because
                                                        // otherwise the build is broken...

        GameObject movingObject = getObject(coordinate.getX(), coordinate.getY());
        findActionRange(coordinate, combatAction.getAOE(), combatAction.isAround());
        // TODO: use cursor to select active tile to do action on. use canDo. find which way unit is
        // facing, use that orientation to call findAffectedObjects
    }

    /**
     * Sets the tiles active that an action can affect
     * 
     * @param coordinate - Coordinate where the action originates
     * @param area - List of Coordinates that map the area of the action
     * @param isAround - boolean of whether the action only affects one direction, or is all around the unit
     */
    private void findActionRange (Coordinate coordinate, List<Coordinate> area, boolean isAround) {
        if (isAround) {
            for (Coordinate cell: area) {
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
     * Returns objects in an action's area of effectiveness
     * 
     * @param coordinate - Coordinate where the action originates
     * @param area - List of Coordinates that map the area of the action
     * @return - List of GameObjects that are affected by the action
     */
    private List<GameObject> findAffectedObjects (Coordinate coordinate, List<Coordinate> area) {
        List<GameObject> affectedObjects = new ArrayList<GameObject>();
        for (Coordinate cell : area) {
            GameObject currentObject =
                    getObject(coordinate.getX() + cell.getX(), coordinate.getY() + cell.getY());
            if (currentObject != null) {
                affectedObjects.add(currentObject);
            }
        }
        return affectedObjects;
    }

    public GameObject getObject (int x, int y) {
        // TODO: Generic method?
        return myObjects[x][y];
    }

    public void placeObject (GameObject newObject, int x, int y) {
        // TODO: Generic method?
        myObjects[x][y] = newObject;

        if (newObject instanceof GameObject) {
            myUnits.add((GameUnit) newObject);
        }
    }
    
    public List<GameUnit> getGameUnits () {
        return myUnits;
    }
    
    public Coordinate getUnitCoordinates (GameUnit gameUnit) {
        for (int i=0; i < myObjects.length; i++ ){
            for (int j=0; j < myObjects[i].length; j++) {
                if (myObjects[i][j].equals(gameUnit)) { return new Coordinate(i, j); }
            }
        }
    }

    public Tile getTile (int x, int y) {
        // TODO: Generic method?
        return myTiles[x][y];
    }

    public void placeTile (Tile newTile, int x, int y) {
        // TODO: Generic method?

        myTiles[x][y] = newTile;
    }

    @Override
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

    @Override
    public String getName () {
        return "Grid";
    }

    @Override
    public Image getImage () {
        return null;
    }
}
