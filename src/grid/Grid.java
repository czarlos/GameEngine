package grid;

import gameObject.GameObject;
import gameObject.GameObjectConstants;
import gameObject.GameUnit;
import gameObject.action.Action;
import gameObject.action.CombatAction;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import grid.Coordinate;
import view.Drawable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * Grid class. Holds all tiles and objects, calculates movement
 * 
 * @author Kevin
 * @author Ken
 * 
 */
@JsonAutoDetect
public class Grid extends Drawable {
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
    private FromJSONFactory myFactory;

    /**
     * Creates a grid with the width and height set
     * Only for use by deserializer
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
        myTiles = new Tile[width][height];
        myObjects = new GameObject[width][height];
        myUnits = new ArrayList<>();
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
        placeObject(link, 4,5);
        beginMove(new Coordinate(4,5), link);
    }

    /**
     * Initiates the moving process for a gameUnit
     * 
     * @param coordinate Coordinate where the gameUnit is located
     * @param gameUnit GameUnit that is moving
     * 
     */
    public void beginMove (Coordinate coordinate, GameObject gameUnit) {
        findMovementRange(coordinate,
                          ((GameUnit) gameUnit).getTotalStat(GameObjectConstants.MOVEMENT),
                          gameUnit);
    }

    /**
     * Return boolean of if a gameUnit can move to a given coordinate
     * @param coordinate Coordiante being moved to
     * @param gameUnit GameObject that is moving
     * @return boolean of if move is possible
     */
    public boolean canMove (Coordinate coordinate, GameObject gameUnit) {
        return isActive(coordinate.getX(), coordinate.getY()); // TODO: also check no object on that tile
    }
    
    /**
     * Moves the unit to a new coordinate if the move is valid
     * 
     * @param oldCoordinate - Coordinate of the gameUnit's original position
     * @param newCoordinate - Coordinate that unit is moving to
     * 
     */
    public void doMove (Coordinate oldCoordinate, Coordinate newCoordinate) {
        if (canMove(newCoordinate, myUnits.get(oldCoordinate.getX()).get(oldCoordinate.getY()))) {
            GameObject gameUnit=removeObject(oldCoordinate.getX(), oldCoordinate.getY());
            placeObject(gameUnit, newCoordinate.getX(), newCoordinate.getY());
            setTilesInactive();
        }
    }

    /**
     * Sets the tiles active that the GameObject can move to
     * 
     * @param coordinate Coordinate of the current position of the GameObject
     * @param range int of range that the GameObject can move
     * @param gameObject GameObject that we are finding the range of
     * 
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
                if (currentTile.isPassable(gameObject) && newRange >= 0) {
                    GameObject currentObject = getObject(newX, newY);
                    if (currentObject != null) {
                        if (currentObject.isPassable(gameObject)) {
                            findMovementRange(new Coordinate(newX, newY), newRange, gameObject);
                        }
                        continue;
                    }
                    else {
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
     * @param x int of x coordinate
     * @param y int of y coordinate
     * @return boolean of if the coordinate is valid
     */
    private boolean onGrid (int x, int y) {
        return (0 <= x && x < myWidth && 0 <= y && y < myHeight);
    }

    /**
     * Checks if a coordinate is a valid move or action (the tile is active)
     * 
     * @param x int of x coordinate
     * @param y int of y coordinate
     * @return boolean of if the coordinate is active
     */
    public boolean isActive (int x, int y) {
        return getTile(x, y).isActive();
    }

    /**
     * Initiates the action process
     * 
     * @param objectCoordinate Coordinate where the action originates
     * @param gameUnit GameUnit that is doing the action
     * @param combatAction CombatAction that is being used
     */
    public void beginAction (Coordinate objectCoordinate,
                             GameUnit gameUnit,
                             CombatAction combatAction) {
        findActionRange(objectCoordinate, combatAction.getAOE(), combatAction.isAround());
    }

    /**
     * Returns the game objects affected by the action
     * 
     * @param objectCoordinate Coordinate where the action originates
     * @param gameUnit GameUnit that is doing the action
     * @param combatAction CombatAction that is being used
     * @param actionCoordinate Coordinate that the user selects for the action
     * @return List of GameObjects that are affected
     */
    public List<GameObject> doAction (Coordinate objectCoordinate, GameUnit gameUnit,
                                      CombatAction combatAction,
                                      Coordinate actionCoordinate) {
        String direction = findDirection(objectCoordinate, combatAction, actionCoordinate);
        return findAffectedObjects(objectCoordinate, combatAction, direction);
    }

    /**
     * Sets the tiles active that an action can affect
     * 
     * @param coordinate Coordinate where the action originates
     * @param area List of Coordinates that map the area of the action
     * @param isAround boolean of whether the action only affects one direction, or is all around
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
     * @param unitCoordinate Coordinate of the unit
     * @param area List of Coordinates that action affects
     * @param selectedCoordinate Coordinate that the user selected for the action
     * @return String of the direction
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
     * @param coordinate Coordinate where the action originates
     * @param combatAction CombatAction being used
     * @param direction String of the direction of the action
     * @return List of GameObjects that are affected by the action
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
    
    public List<Action> getInteractions(GameUnit gameUnit) {
        List<Action> interactions = new ArrayList<>();
        int[] rdelta = { -1, 0, 0, 1 };
        int[] cdelta = { 0, -1, 1, 0 };
        
        Coordinate unitCoordinate = getUnitCoordinate(gameUnit); //TODO: what is our getUnitCoordinate now
        for (int i=0; i<rdelta.length; i++) {
            Action interaction = getInteraction(new Coordinate(unitCoordinate.getX()+cdelta[i], unitCoordinate.getY()+rdelta[i]));
            if (interaction != null) {
                interactions.add(interaction);
            }
        }
        return interactions;
    }
    
    private Action getInteraction(Coordinate coordinate) {
        if (onGrid(coordinate.getX(), coordinate.getY())) {
            if (myObjects[coordinate.getX()][coordinate.getY()] != null) {
                return myObjects[coordinate.getX()][coordinate.getY()].getInteraction();
            }
        }
        return null;
    }

    /**
     * Returns an object at the given coordinates
     * 
     * @param x int of x coordinate
     * @param y int of y coordinate
     * @return GameObject at coordinate
     */
    public GameObject getObject (int x, int y) {
        // TODO: Generic method?
        return myObjects[x][y];
    }
    
    // TODO: why is it a double arraylist................
    /*
     * Returns the coordinates of a unit's location
     * 
     * @param gameUnit GameUnit object that is being located
     * @return Coordinate unit's location
     */
    private Coordinate getUnitCoordinate (GameUnit gameUnit) {
        for (int i=0; i<myUnits.size(); i++) {
            for (int j=0; j< myUnits.get(0).size(); j++) {
                if (myUnits.get(i).get(j).equals(gameUnit)) return new Coordinate(i, j);
            }
        }
        return null;
    }

    /**
     * Places a GameObject at given coordinates
     * 
     * @param gameObject GameObject to be placed
     * @param x int of x coordinate
     * @param y int of y coordinate
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

            List<GameUnit> newUnitList = new ArrayList<>();
            newUnitList.add((GameUnit) gameObject);
            myUnits.add((ArrayList<GameUnit>) newUnitList);
        }
    }

    /**
     * Sets position in myObjects map to null
     * 
     * @param x int of x coordinate
     * @param y int of y coordinate
     * @return Object removed from position (x,y)
     */
    private GameObject removeObject (int x, int y) {
        GameObject objToRemove = myObjects[x][y];
        myObjects[x][y] = null;
        return objToRemove;
    }

    public List<ArrayList<GameUnit>> getGameUnits () {
        return myUnits;
    }

    /**
     * Returns an tile at the given coordinates
     * 
     * @param x int of x coordinate
     * @param y int of y coordinate
     * @return Tile at coordinate
     */
    public Tile getTile (int x, int y) {
        // TODO: Generic method?
        return myTiles[x][y];
    }

    /**
     * Places a Tile at given coordinates
     * 
     * @param tile Tile to be placed
     * @param x int of x coordinate
     * @param y int of y coordinate
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

    /**
     * Draws the tiles and objects on the grid
     * @param g Graphics for the image
     * @param x int of x coordinate on the grid
     * @param y int of y coordinate on the grid
     * @param width int of width of object
     * @param height int of height of object
     */
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

    public Tile[][] getTiles () {
        return myTiles;
    }

    public void setTiles (Tile[][] tiles) {
        myTiles = tiles;
    }

    public Coordinate getCoordinate(double fracX, double fracY){
        
        int gridX = (int) (fracX * myWidth);
        int gridY = (int) (fracY * myHeight);

        return new Coordinate(gridX, gridY);
    }
   
}
