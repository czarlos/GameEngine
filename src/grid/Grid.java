package grid;

import gameObject.GameObject;
import gameObject.GameObjectConstants;
import gameObject.GameUnit;
import gameObject.item.Item;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import grid.Coordinate;
import view.Drawable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Grid class. Holds all tiles and objects, calculates movement, actions
 * 
 * @author Kevin
 * @author Ken
 * 
 */
@JsonAutoDetect
public class Grid implements Drawable {
    @JsonProperty
    private int myWidth;
    @JsonProperty
    private int myHeight;

    @JsonProperty
    private Map<String, Object[][]> myArrays;
    private FromJSONFactory myFactory;

    protected static final int TILE_WIDTH = 35;
    protected static final int TILE_HEIGHT = 35;

    /**
     * Creates a grid with the width and height set Only for use by deserializer
     */
    public Grid () {
        myFactory = new FromJSONFactory();
    }

    /**
     * Creates a grid with the width and height set, and default tiles of tileID
     * 
     * @param width int of columns of grid
     * @param height int of rows of grid
     * @param tileID int that defines the tile type
     */
    public Grid (int width, int height, int tileID) {
        myWidth = width;
        myHeight = height;
        myArrays = new HashMap<String, Object[][]>();
        myArrays.put(GridConstants.TILE, new Tile[width][height]);
        myArrays.put(GridConstants.GAMEOBJECT, new GameObject[width][height]);
        myArrays.put(GridConstants.GAMEUNIT, new GameUnit[width][height]);
        myFactory = new FromJSONFactory();
        initTiles(tileID);
    }

    /**
     * Creates default tiles for grid
     */
    private void initTiles (int tileID) {
        for (int i = 0; i < myWidth; i++) {
            for (int j = 0; j < myHeight; j++) {
                myArrays.get(GridConstants.TILE)[i][j] = (Tile) myFactory.make("Tile", tileID);
            }
        }
    }

    /**
     * Initiates the moving process for a gameUnit
     * 
     * @param coordinate
     *        Coordinate where the gameUnit is located
     * 
     */
    public void beginMove (Coordinate coordinate) {
        GameUnit gameUnit = (GameUnit) getObject(GridConstants.GAMEUNIT, coordinate);
        findMovementRange(coordinate, gameUnit.getTotalStat(GameObjectConstants.MOVEMENT), gameUnit);
    }

    /**
     * Return boolean of if a gameUnit can move to a given coordinatee
     * 
     * @param coordinate
     *        Coordinate being moved to
     * @return boolean of if move is possible
     */
    public boolean isValidMove (Coordinate coordinate) {
        return isValid(coordinate) && getObject(GridConstants.GAMEOBJECT, coordinate) == null;
    }

    // TODO: move validMove() check to controller
    /**
     * Moves the unit to a new coordinate if the move is valid
     * 
     * @param oldCoordinate
     *        - Coordinate of the gameUnit's original position
     * @param newCoordinate
     *        - Coordinate that unit is moving to
     * 
     */
    public void doMove (Coordinate oldCoordinate, Coordinate newCoordinate) {
        // if (isValidMove(newCoordinate)) {
        GameObject gameUnit = removeObject(oldCoordinate);
        placeObject(GridConstants.GAMEUNIT, newCoordinate, gameUnit);
        // }
    }

    /**
     * Sets the tiles active that the GameObject can move to
     * 
     * @param coordinate
     *        Coordinate of the current position of the GameObject
     * @param range
     *        int of range that the GameObject can move
     * @param gameObject
     *        GameObject that we are finding the range of
     * 
     */
    private void findMovementRange (Coordinate coordinate, int range, GameUnit gameObject) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(coordinate);

        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
            if (onGrid(adjacentCoordinate)) {
                Tile currentTile = getTile(adjacentCoordinate);
                int newRange = range - currentTile.getMoveCost();

                if (newRange >= 0) {
                    GameObject currentObject = getObject(GridConstants.GAMEOBJECT, adjacentCoordinate);
                    if (currentObject != null) {
                        if (currentObject.isPassable(gameObject)) {
                            findMovementRange(adjacentCoordinate, newRange, gameObject);
                        }
                        continue;
                    }
                    else {
                        currentTile.setActive(true);
                        findMovementRange(adjacentCoordinate, newRange, gameObject);
                    }
                }
            }
        }
    }

    /**
     * Checks if the input coordinate is on the grid
     * 
     * @param coordinate
     *        Coordinate being checked
     * @return boolean of if the coordinate is valid
     */
    private boolean onGrid (Coordinate coordinate) {
        return (0 <= coordinate.getX() && coordinate.getX() < myWidth
                && 0 <= coordinate.getY() && coordinate.getY() < myHeight);
    }

    /**
     * Checks if a coordinate is a valid move or action (the tile is active)
     * 
     * @param coordinate
     *        Coordinate being checked
     * @return boolean of if the coordinate is active
     */
    public boolean isActive (Coordinate coordinate) {
        return getTile(coordinate).isActive();
    }

    /**
     * Initiates the action process
     * 
     * @param objectCoordinate
     *        Coordinate where the action originates
     * @param gameUnit
     *        GameUnit that is doing the action
     * @param combatAction
     *        CombatAction that is being used
     */
    public void beginAction (Coordinate coordinate, int range) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(coordinate);

        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
            if (onGrid(adjacentCoordinate)) {
                Tile currentTile = getTile(adjacentCoordinate);
                int newRange = range - 1;

                if (newRange >= 0) {
                    currentTile.setActive(true);
                    beginAction(adjacentCoordinate, newRange);
                }
            }
        }
    }

    /**
     * Returns a boolean if a coordinate is on the grid and the tile for the
     * coordinate is active
     * 
     * @param coordinate
     *        Coordinate being checked
     * @return boolean of if the coordinate is valid
     */
    public boolean isValid (Coordinate coordinate) {
        // make tiles active in AI
        return onGrid(coordinate);
        // return onGrid(coordinate) && isActive(coordinate);
    }

    /**
     * Gets a list of valid actions that the unit can perform on the objects
     * around him
     * 
     * @param gameUnit
     * @return
     */
    public List<String> getAllInteractions (Coordinate coordinate) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(coordinate);
        List<String> allInteractions = new ArrayList<>();
        for (Coordinate adjacentCoordinate: adjacentCoordinates) {
            List<String> interactions = getInteractions(adjacentCoordinate);
            if (interactions != null) {
                allInteractions.addAll(interactions);
            }
        }
        return allInteractions;
    }

    /**
     * Gets an interaction if one exists at the given coordinate
     * 
     * @param coordinate
     *        Coordinate of the location being searched
     * @return Action that can be performed
     */
    private List<String> getInteractions (Coordinate coordinate) {
        if (onGrid(coordinate)) {
            if (getObject(GridConstants.GAMEOBJECT, coordinate) != null) { 
                return ((GameObject) myArrays.get(GridConstants.GAMEOBJECT)[coordinate.getX()][coordinate.getY()]).getInteractions();
            }
        }
        return null;
    }

    /**
     * Returns an object at the given coordinates
     * 
     * @param coordinate Coordinate being checked
     * @return GameObject at coordinate
     */
    public GameObject getObject (String type, Coordinate coordinate) {
        return (GameObject) myArrays.get(type)[coordinate.getX()][coordinate.getY()];
    }

    // TODO: may not be necessary
    /**
     * Returns the coordinates of a unit's location
     * 
     * @param gameUnit
     *        GameUnit that is being located
     * @return Coordinate of unit's location
     */
    public Coordinate getUnitCoordinate (GameUnit gameUnit) {
        for (int i = 0; i < myArrays.get(GridConstants.TILE).length; i++) {
            for (int j = 0; j < myArrays.get(GridConstants.TILE)[0].length; j++) {
                Coordinate tileCoordinate = new Coordinate(i, j);
                GameUnit currentTileUnit = (GameUnit) getObject(GridConstants.GAMEUNIT, tileCoordinate);

                if (currentTileUnit == null) {
                    continue;
                }
                else if (currentTileUnit.equals(gameUnit)) {
                    return tileCoordinate;
                }
                else {
                    continue;
                }
            }
        }
        return null;
    }

    public Coordinate getTileCoordinate (Tile tile) {
        for (int i = 0; i < myArrays.get(GridConstants.TILE).length; i++) {
            for (int j = 0; j < myArrays.get(GridConstants.TILE)[0].length; j++) {
                Coordinate tileCoordinate = new Coordinate(i, j);

                if (tile == null) {
                    continue;
                }
                else if (tile.equals(myArrays.get(GridConstants.TILE)[i][j])) {
                    return tileCoordinate;
                }
                else {
                    continue;
                }
            }
        }
        return null;

    }

    /**
     * Places a GameObject at given coordinates
     * @param type
     * @param coordinate
     * @param placeObject
     */
    public void placeObject (String type, Coordinate coordinate, Object placeObject) {

        if (type.equals(GridConstants.ITEM)) {
            GameUnit gameUnit = (GameUnit) getObject(GridConstants.GAMEUNIT, coordinate);
            if (gameUnit != null) {
                gameUnit.addItem((Item) placeObject);
            }
        }
        else {
            myArrays.get(type)[coordinate.getX()][coordinate.getY()] = placeObject;

            if (type.equals(GridConstants.GAMEUNIT)) {
                myArrays.get(GridConstants.GAMEOBJECT)[coordinate.getX()][coordinate.getY()] =
                        placeObject;
            }
            if (type.equals(GridConstants.TILE)) {
                removeObject(coordinate);
            }
        }
    }

    /**
     * Sets position in myObjects map to null
     * 
     * @param coordinate
     *        Coordinate being checked
     * @return Object removed from position (x,y)
     */
    private GameObject removeObject (Coordinate coordinate) {
        GameObject objToRemove = getObject(GridConstants.GAMEOBJECT, coordinate);
        myArrays.get(GridConstants.GAMEOBJECT)[coordinate.getX()][coordinate.getY()] = null;
        
        if (objToRemove instanceof GameUnit) {
            objToRemove = getObject(GridConstants.GAMEUNIT, coordinate);
            myArrays.get(GridConstants.GAMEUNIT)[coordinate.getX()][coordinate.getY()] = null;
        }
        return objToRemove;
    }

    @JsonIgnore
    public GameUnit[][] getGameUnits () {
        return (GameUnit[][]) myArrays.get(GridConstants.GAMEUNIT);
    }

    /**
     * Finds all coordinates adjacent to the coordinate given.
     * 
     * @param - Coordinate from which to find adjacent coordinates
     * @return List of adjacent Coordinates
     */
    public List<Coordinate> getAdjacentCoordinates (Coordinate coord) {
        List<Coordinate> returnArray = new ArrayList<Coordinate>();
        returnArray.add(new Coordinate(coord.getX() + 1, coord.getY()));
        returnArray.add(new Coordinate(coord.getX(), coord.getY() + 1));
        returnArray.add(new Coordinate(coord.getX() - 1, coord.getY()));
        returnArray.add(new Coordinate(coord.getX(), coord.getY() - 1));
        return returnArray;
    }

    /**
     * Returns a tile at the given coordinates
     * 
     * @param coordinate Coordinate being checked
     * @return Tile at coordinate
     */
    public Tile getTile (Coordinate coordinate) {
        // TODO: Generic method?
        return (Tile) myArrays.get(GridConstants.TILE)[coordinate.getX()][coordinate.getY()];
    }

    /**
     * Sets all tiles on grid to be inactive
     */
    public void setTilesInactive () {
        for (int i = 0; i < myArrays.get(GridConstants.TILE).length; i++) {
            for (int j = 0; j < myArrays.get(GridConstants.TILE)[i].length; j++) {
                ((Tile) myArrays.get(GridConstants.TILE)[i][j]).setActive(false);
            }
        }
    }

    /**
     * Draws the tiles and objects on the grid
     * 
     * @param g Graphics for the image
     * @param x int of x coordinate on the grid
     * @param y int of y coordinate on the grid
     * @param width int of width of object
     * @param height int of height of object
     */
    public void draw (Graphics g, int x, int y, int width, int height) {
        int tileWidth = width / myWidth;
        int tileHeight = height / myHeight;

        for (int i = 0; i < myArrays.get(GridConstants.TILE).length; i++) {
            for (int j = 0; j < myArrays.get(GridConstants.TILE)[i].length; j++) {
                Tile tile = (Tile) myArrays.get(GridConstants.TILE)[i][j];
                tile.draw(g, i * tileWidth, j * tileHeight, tileWidth,
                          tileHeight);
            }
        }

        // TODO: duplicate for tile and object. generic
        for (int i = 0; i < myArrays.get(GridConstants.GAMEOBJECT).length; i++) {
            for (int j = 0; j < myArrays.get(GridConstants.GAMEOBJECT)[i].length; j++) {
                GameObject gameObject = (GameObject) myArrays.get(GridConstants.GAMEOBJECT)[i][j];
                if (gameObject != null) {
                    gameObject.draw(g, i * tileWidth, j * tileHeight,
                                    tileWidth, tileHeight);
                }
            }
        }
    }

    @JsonIgnore
    public Tile[][] getTiles () {
        return (Tile[][]) myArrays.get(GridConstants.TILE);
    }

    public void setTiles (Tile[][] tiles) {
        myArrays.put(GridConstants.TILE, tiles);
    }

    public Coordinate getCoordinate (double fracX, double fracY) {

        int gridX = (int) (fracX * myWidth);
        int gridY = (int) (fracY * myHeight);

        return new Coordinate(gridX, gridY);
    }

    public double getWidth () {
        return myWidth;
    }

    public void setWidth (int width) {
        myWidth = width;
    }

    public double getHeight () {
        return myHeight;
    }

    public void setHeight (int height) {
        myHeight = height;
    }
}