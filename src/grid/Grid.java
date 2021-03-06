package grid;

import gameObject.Customizable;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.InventoryObject;
import gameObject.Item;
import gameObject.action.Action;
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

    /**
     * Creates a grid with the width and height set Only for use by deserializer
     */
    public Grid () {
    }

    /**
     * Creates a grid with the width and height set, and default tiles of tileID
     * 
     * @param width int of columns of grid
     * @param height int of rows of grid
     * @param tileID int that defines the tile type
     */
    public Grid (int width, int height) {
        myWidth = width;
        myHeight = height;
        myArrays = new HashMap<String, Object[][]>();
        myArrays.put(GridConstants.TILE, new Tile[width][height]);
        myArrays.put(GridConstants.GAMEOBJECT, new GameObject[width][height]);
        myArrays.put(GridConstants.GAMEUNIT, new GameUnit[width][height]);
    }

    /**
     * Initiates the moving process for a gameUnit
     * 
     * @param coordinate Coordinate where the gameUnit is located
     * 
     */
    public void beginMove (Coordinate coordinate) {
        GameUnit gameUnit = (GameUnit) getObject(GridConstants.GAMEUNIT, coordinate);
        findMovementRange(coordinate, gameUnit.calcTotalStat(GridConstants.MOVEMENT),
                          gameUnit);
    }

    /**
     * Moves the unit to a new coordinate if the move is valid
     * 
     * @param oldCoordinate Coordinate of the gameUnit's original position
     * @param newCoordinate Coordinate that unit is moving to
     * 
     */
    public void doMove (Coordinate oldCoordinate, Coordinate newCoordinate) {
        GameUnit gameUnit = (GameUnit) removeObject(oldCoordinate);
        placeObject(GridConstants.GAMEUNIT, newCoordinate, gameUnit);
    }

    /**
     * Sets the tiles active that the GameObject can move to
     * 
     * @param coordinate Coordinate of the current position of the GameObject
     * @param range int of range that the GameObject can move
     * @param gameUnit GameObject that we are finding the range of
     * 
     */
    private void findMovementRange (Coordinate coordinate, int range, GameUnit gameUnit) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(coordinate);

        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
            if (onGrid(adjacentCoordinate)) {
                Tile currentTile = (Tile) getObject(GridConstants.TILE, adjacentCoordinate);
                int newRange = range - currentTile.getMoveCost();
                if (newRange >= 0 && currentTile.isPassable(gameUnit)) {
                    GameObject currentObject =
                            getObject(GridConstants.GAMEOBJECT, adjacentCoordinate);
                    if (currentObject != null) {
                        if (currentObject.isPassable(gameUnit)) {
                            findMovementRange(adjacentCoordinate, newRange, gameUnit);
                        }
                    }
                    else {
                        currentTile.setActive(true);
                        findMovementRange(adjacentCoordinate, newRange, gameUnit);
                    }
                }
            }
        }
    }

    /**
     * Initiates the action process
     * 
     * @param objectCoordinate Coordinate where the action originates
     * @param gameUnit GameUnit that is doing the action
     * @param combatAction CombatAction that is being used
     */
    public void findActionRange (Coordinate coordinate, int range, Action action) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(coordinate);

        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
            if (onGrid(adjacentCoordinate)) {
                Tile currentTile = (Tile) getObject(GridConstants.TILE, adjacentCoordinate);
                int newRange = range - 1;
                if (newRange >= 0) {
                    currentTile.setActive(action
                            .isValid((GameUnit) getObject(GridConstants.GAMEUNIT, coordinate),
                                     getObject(GridConstants.GAMEOBJECT, adjacentCoordinate)));
                    findActionRange(adjacentCoordinate, newRange, action);
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
     * Checks if the object at the input coordinate is active
     * 
     * @param type String of type of object
     * @param coordinate Coordinate being checked
     * @return boolean of whether the coordinate is active
     */
    public boolean isActive (String type, Coordinate coordinate) {
        return getObject(type, coordinate).isActive();
    }

    /**
     * Gets a list of valid actions that the unit can perform on the objects around him
     * 
     * @param unitCoordinate Coordinate where the unit is
     * @return List of Strings of actions
     */
    public List<String> getAllInteractions (Coordinate unitCoordinate) {
        List<Coordinate> adjacentCoordinates = getAdjacentCoordinates(unitCoordinate);
        List<String> allInteractions = new ArrayList<>();
        for (Coordinate adjacentCoordinate : adjacentCoordinates) {
            List<String> interactions = getInteractions(unitCoordinate, adjacentCoordinate);
            if (interactions != null) {
                allInteractions.addAll(interactions);
            }
        }
        return allInteractions;
    }

    /**
     * Gets an interaction if one exists at the given coordinate
     * 
     * @param actionCoordinate Coordinate of the location being searched
     * @return Action that can be performed
     */
    private List<String> getInteractions (Coordinate unitCoordinate, Coordinate actionCoordinate) {
        if (onGrid(actionCoordinate)) {
            GameUnit initiator = (GameUnit) getObject(GridConstants.GAMEOBJECT, unitCoordinate);
            GameObject receiver = getObject(GridConstants.GAMEOBJECT, actionCoordinate);
            if (receiver != null) {
                if (receiver instanceof GameUnit) {
                    if (initiator.getAffiliation().equals(((GameUnit) receiver).getAffiliation())) { return receiver
                            .getInteractions(); }
                }
                else {
                    return receiver.getInteractions();
                }
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
        for (int i = 0; i < myArrays.get(type).length; i++) {
            for (int j = 0; j < myArrays.get(type)[0].length; j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                GameObject currentObject = (GameObject) getObject(type, currentCoordinate);
                if (currentObject != null) {
                    if (currentObject.equals(gameObject)) { return currentCoordinate; }
                }
            }
        }
        return null;
    }

    /**
     * Places a Customizable at given coordinates
     * 
     * @param type String of the type of object being placed
     * @param coordinate Coordinate of where the object is being placed
     * @param placeObject Customizable of object being placed
     */
    public void placeObject (String type, Coordinate coordinate, Customizable placeObject) {
        if (type.equals(GridConstants.ITEM)) {
            GameObject gameObject = getObject(GridConstants.GAMEOBJECT, coordinate);
            if (gameObject != null) {
                if (gameObject instanceof InventoryObject) {
                    if (gameObject instanceof GameUnit) {
                        gameObject = getObject(GridConstants.GAMEUNIT, coordinate);
                    }
                    ((InventoryObject) gameObject).addItem((Item) placeObject);
                }
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
     * @param coordinate Coordinate being checked
     * 
     * @return Object removed from position
     */
    public GameObject removeObject (Coordinate coordinate) {
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
        List<Coordinate> adjacentCoordinates = new ArrayList<Coordinate>();
        adjacentCoordinates.add(new Coordinate(coord.getX() + 1, coord.getY()));
        adjacentCoordinates.add(new Coordinate(coord.getX(), coord.getY() + 1));
        adjacentCoordinates.add(new Coordinate(coord.getX() - 1, coord.getY()));
        adjacentCoordinates.add(new Coordinate(coord.getX(), coord.getY() - 1));
        return adjacentCoordinates;
    }

    /**
     * Sets all tiles on grid to be inactive
     */
    public void setAllTilesInactive () {
        for (int i = 0; i < myArrays.get(GridConstants.TILE).length; i++) {
            for (int j = 0; j < myArrays.get(GridConstants.TILE)[0].length; j++) {
                ((Tile) getObject(GridConstants.TILE, new Coordinate(i, j))).setActive(false);
            }
        }
    }

    /**
     * Gets all of the currently active tile coordinates on the grid
     * 
     * @return List of Coordinates of active tiles
     */
    @JsonIgnore
    public List<Coordinate> getActiveTileCoordinates () {
        List<Coordinate> activeTiles = new ArrayList<>();
        for (int i = 0; i < myArrays.get(GridConstants.TILE).length; i++) {
            for (int j = 0; j < myArrays.get(GridConstants.TILE)[0].length; j++) {
                if (isActive(GridConstants.TILE, new Coordinate(i, j))) {
                    activeTiles.add(new Coordinate(i, j));
                }
            }
        }
        return activeTiles;
    }

    /**
     * Draws objects on the grid
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

    /**
     * Called by draw, draws specific object on grid
     * 
     * @param type String of the type of object being drawn
     * @param tileWidth int of tile width
     * @param tileHeight int of tile height
     * @param g Graphics for the image
     */
    private void drawType (String type, int tileWidth, int tileHeight, Graphics g) {
        for (int i = 0; i < myArrays.get(type).length; i++) {
            for (int j = 0; j < myArrays.get(type)[i].length; j++) {
                GameObject gameObject = getObject(type, new Coordinate(i, j));
                if (gameObject != null) {
                    if (gameObject instanceof GameUnit) {
                        gameObject = getObject(GridConstants.GAMEUNIT, new Coordinate(i, j));
                    }
                    gameObject.draw(g, i * tileWidth, j * tileHeight, tileWidth, tileHeight);
                }
            }
        }
    }

    public List<?> getCustomizables (String type) {
        Object[][] customizableArray = myArrays.get(type);
        List<Customizable> customizableList = new ArrayList<>();

        for (int i = 0; i < customizableArray.length; i++) {
            for (int j = 0; j < customizableArray[i].length; j++) {
                if (customizableArray[i][j] != null) {
                    customizableList.add((Customizable) customizableArray[i][j]);
                }
            }
        }

        return customizableList;
    }

    @JsonIgnore
    public Customizable[][] getArray (String type) {
        return (Customizable[][]) myArrays.get(type);
    }

    @JsonIgnore
    public Coordinate getCoordinate (double fracX, double fracY) {
        int gridX = (int) (fracX * myWidth);
        int gridY = (int) (fracY * myHeight);

        return new Coordinate(gridX, gridY);
    }

    @JsonIgnore
    public double getWidth () {
        return myWidth;
    }

    @JsonIgnore
    public double getHeight () {
        return myHeight;
    }
}
