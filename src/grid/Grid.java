package grid;

import gameObject.GameObject;
import gameObject.GameObjectConstants;
import gameObject.GameUnit;
import gameObject.action.Action;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import parser.JSONParser;
import grid.Coordinate;
import view.Drawable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Grid class. Holds all tiles and objects, calculates movement, actions
 * 
 * @author Kevin, Ken, Leevi
 * 
 */
@JsonAutoDetect
public class Grid implements Drawable {
    @JsonProperty
    private int myWidth;
    @JsonProperty
    private int myHeight;

    // for editing synchronization
    public static final String[] MYTYPES = {GridConstants.TILE, GridConstants.GAMEOBJECT, GridConstants.GAMEUNIT};
    protected static final int TILE_WIDTH = 35;
    protected static final int TILE_HEIGHT = 35;
    
    @JsonProperty
    private Map<String, Integer[][]> myArrays;
    @JsonProperty
    private Map<String, List<GameObject>> myLists;

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
    public Grid (int width, int height, int tileID) {
        myWidth = width;
        myHeight = height;
        myArrays = new HashMap<String, Integer[][]>();
        myLists = new HashMap<String, List<GameObject>>();
        init(tileID);
    }

    /**
     * Creates default tiles for grid
     */
    @SuppressWarnings("unchecked")
    private void init (int tileID) {
        JSONParser p = new JSONParser();

        for(String s: MYTYPES){
            myArrays.put(s, new Integer[myWidth][myHeight]);
            List<GameObject> list = (List<GameObject>) p.createObjectFromFile("defaults/" + s,
                                                                             new ArrayList<GameObject>().getClass());
            myLists.put(s, list);
        }
        
        for (int i = 0; i < myWidth; i++) {
            Arrays.fill(myArrays.get(GridConstants.TILE)[i], tileID);
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
        findMovementRange(coordinate, gameUnit.calcTotalStat(GameObjectConstants.MOVEMENT),
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
        int gameUnit = removeObject(GridConstants.GAMEUNIT, oldCoordinate);
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
                if (newRange >= 0 &&
                    action.isValid((GameUnit) getObject(GridConstants.GAMEUNIT, coordinate),
                                   getObject(GridConstants.GAMEOBJECT, adjacentCoordinate))) {
                    currentTile.setActive(true);
                    findActionRange(adjacentCoordinate, newRange, action);
                }
            }
        }
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
        if(myArrays.get(type)[coordinate.getX()][coordinate.getY()] != null){
            return myLists.get(type).get((int) myArrays.get(type)[coordinate.getX()][coordinate.getY()]);   
        }
        return null;
    }

    public void setList(String type, List<GameObject> list){
        myLists.put(type, list);
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
    public void placeObject (String type, Coordinate coordinate, int placeObject) {
        removeObject(GridConstants.GAMEUNIT, coordinate);
        removeObject(GridConstants.GAMEOBJECT, coordinate);
        myArrays.get(type)[coordinate.getX()][coordinate.getY()] = placeObject;
    }

    /**
     * Sets position in myObjects map to null
     * 
     * @param coordinate Coordinate being checked
     * 
     * @return Object removed from position
     */
    public int removeObject (String type, Coordinate coordinate) {
        int ret = 0;
        if(myArrays.get(type)[coordinate.getX()][coordinate.getY()] != null){
            ret = myArrays.get(type)[coordinate.getX()][coordinate.getY()];
            myArrays.get(type)[coordinate.getX()][coordinate.getY()] = null; 
        }
        return ret;
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
        drawType(GridConstants.GAMEUNIT, tileWidth, tileHeight, g);
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
                    gameObject.draw(g, i * tileWidth, j * tileHeight, tileWidth, tileHeight);
                }
            }
        }
    }

    @JsonIgnore
    public GameObject[][] getObjects (String type) {
        GameObject[][] ret = new GameObject[myWidth][myHeight];
        for(int i = 0; i < myWidth; i++){
            for(int j = 0; j < myHeight; j++){
                if(myArrays.get(type)[i][j] != null){
                    int index = myArrays.get(type)[i][j];
                    ret[i][j] = (GameObject) myLists.get(type).get(index);
                }
            }
        }
        return ret;
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
