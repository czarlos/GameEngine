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
 * Grid class. Holds all tiles and objects, calculates movement, actions
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
     * @param coordinate Coordinate where the gameUnit is located
     * 
     */
    public void beginMove (Coordinate coordinate) {
        GameUnit gameUnit = (GameUnit) getObject(GridConstants.GAMEUNIT, coordinate);
        findMovementRange(coordinate, gameUnit.getTotalStat(GameObjectConstants.MOVEMENT), gameUnit);
    }

    /**
     * Moves the unit to a new coordinate if the move is valid
     * 
     * @param oldCoordinate Coordinate of the gameUnit's original position
     * @param newCoordinate Coordinate that unit is moving to
     * 
     */
    public void doMove (Coordinate oldCoordinate, Coordinate newCoordinate) {
        GameObject gameUnit = removeObject(GridConstants.GAMEUNIT, oldCoordinate);
        placeObject(GridConstants.GAMEUNIT, newCoordinate, gameUnit);
    }

    /**
     * Sets the tiles active that the GameObject can move to
     * 
     * @param coordinate Coordinate of the current position of the GameObject
     * @param range int of range that the GameObject can move
     * @param gameObject GameObject that we are finding the range of
     * 
     */
    private void findMovementRange (Coordinate coordinate, int range, GameUnit gameObject) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(coordinate);

        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
            if (onGrid(adjacentCoordinate)) {
                Tile currentTile = (Tile) getObject(GridConstants.TILE, adjacentCoordinate);
                int newRange = range - currentTile.getMoveCost();

                if (newRange >= 0) {
                    GameObject currentObject = getObject(GridConstants.GAMEOBJECT, adjacentCoordinate);
                    if (currentObject != null) {
                        if (currentObject.isPassable(gameObject)) {
                            findMovementRange(adjacentCoordinate, newRange, gameObject);
                        }
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
     * @param coordinate Coordinate being checked
     * @return boolean of if the coordinate is valid
     */
    private boolean onGrid (Coordinate coordinate) {
        return (0 <= coordinate.getX() && coordinate.getX() < myWidth
                && 0 <= coordinate.getY() && coordinate.getY() < myHeight);
    }

    /**
     * Checks if a coordinate is a valid move or action (the tile is active)
     * 
     * @param coordinate Coordinate being checked
     * @return boolean of if the coordinate is active
     */
    public boolean isActive (String type, Coordinate coordinate) {
        return ((GameObject) getObject(type, coordinate)).isActive();
    }
    
    /**
     * Return boolean of if a gameUnit can move to a given coordinatee
     * 
     * @param coordinate Coordinate being moved to
     * @return boolean of if move is possible
     */
    public boolean isValidMove (Coordinate coordinate) {
        return isValid(coordinate) && getObject(GridConstants.GAMEOBJECT, coordinate) == null;
    }
    
    /**
     * Returns a boolean if a coordinate is on the grid and the tile for the
     * coordinate is active
     * 
     * @param coordinate Coordinate being checked
     * @return boolean of if the coordinate is valid
     */
    public boolean isValid (Coordinate coordinate) {
        // make tiles active in AI
        return onGrid(coordinate);
        // return onGrid(coordinate) && isActive(coordinate);
    }

    /**
     * Initiates the action process
     * 
     * @param objectCoordinate Coordinate where the action originates
     * @param gameUnit GameUnit that is doing the action
     * @param combatAction CombatAction that is being used
     */
    public void beginAction (Coordinate coordinate, int range) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(coordinate);

        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
            if (onGrid(adjacentCoordinate)) {
                Tile currentTile = (Tile) getObject(GridConstants.TILE, adjacentCoordinate);
                int newRange = range - 1;
                if (newRange >= 0) {
                    currentTile.setActive(true);
                    beginAction(adjacentCoordinate, newRange);
                }
            }
        }
    }


    /**
     * Gets a list of valid actions that the unit can perform on the objects around him
     * 
     * @param coordinate Coordinate where the unit is
     * @return List of Strings of actions
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
     * @param coordinate Coordinate of the location being searched
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

    /**
     * Gets the coordinate for a given gameObject
     * 
     * @param type String of type of gameObject being looked for
     * @param gameObject GameObject being looked for
     * @return Coordinate of gameObject's location
     */
    public Coordinate getObjectCoordinate (String type, GameObject gameObject) {
        for (int i=0; i < myArrays.get(type).length; i++) {
            for (int j=0; j < myArrays.get(type)[0].length; j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                GameObject currentObject = (GameObject) getObject(type, currentCoordinate);
                if (currentObject != null) {
                    if (currentObject.equals(gameObject)) {
                        return currentCoordinate;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Places a GameObject at given coordinates
     * 
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
                removeObject(type, coordinate);
            }
        }
    }

    /**
     * Sets position in myObjects map to null
     * 
     * @param coordinate Coordinate being checked
     * @return Object removed from position (x,y)
     */
    private GameObject removeObject (String type, Coordinate coordinate) {
        GameObject removeObject = getObject(GridConstants.GAMEOBJECT, coordinate);
        myArrays.get(GridConstants.GAMEOBJECT)[coordinate.getX()][coordinate.getY()] = null;
        
        if (removeObject instanceof GameUnit) {
            removeObject = getObject(GridConstants.GAMEUNIT, coordinate);
            myArrays.get(GridConstants.GAMEUNIT)[coordinate.getX()][coordinate.getY()] = null;
        }
        return removeObject;
    }

    /**
     * Finds all coordinates adjacent to the coordinate given.
     * 
     * @param Coordinate from which to find adjacent coordinates
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
        drawType(GridConstants.TILE, tileWidth, tileHeight, g);
        drawType(GridConstants.GAMEOBJECT, tileWidth, tileHeight, g);
    }
    
    private void drawType (String type, int tileWidth, int tileHeight, Graphics g) {
        for (int i=0; i < myArrays.get(type).length; i++) {
            for (int j=0; j < myArrays.get(type)[i].length; j++) {
                GameObject gameObject = (GameObject) myArrays.get(type)[i][j];
                if (gameObject != null) {
                    gameObject.draw(g, i * tileWidth, j * tileHeight, tileWidth, tileHeight);
                }                
            }
        }
    }

    @JsonIgnore
    public GameUnit[][] getGameUnits () {
        return (GameUnit[][]) myArrays.get(GridConstants.GAMEUNIT);
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