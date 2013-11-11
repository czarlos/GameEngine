package grid;
 
import gameObject.GameObject;
import gameObject.GameUnit;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import view.Drawable;

/**
 * Grid class. Holds all tiles and objects, calculates movement
 * @author Kevin, Ken
 *
 */
public class Grid implements Drawable {
    private int myWidth;
    private int myHeight;
    private Map<Coordinate, Tile> myTileMap;
    private Map<Coordinate, GameObject> myObjects;
    private Map<Integer, List<GameObject>> myPassStatuses; // TODO: Add pass statuses. 0 = nothing
                                                           // passes, 1 = everything passes. Put
                                                           // this map in stage controller?

    /**
     * Creates a grid with the width and height set
     * @param width - int of number of columns of tiles
     * @param height - int of number of rows of tiles
     */
    public Grid (int width, int height) {
        myWidth = width;
        myHeight = height;
        myTileMap = new HashMap<Coordinate, Tile>();
        myObjects = new HashMap<Coordinate, GameObject>();
        myPassStatuses = new HashMap<Integer, List<GameObject>>();

        initGrid();
    }

    /**
     * Sets up default grid with tiles and objects
     */
    private void initGrid () {
        initTiles();
        testInitObjects();
    }

    /**
     * Creates default tiles for grid
     */
    private void initTiles () {
        for (int i = 0; i < myWidth; i++) {
            for (int j = 0; j < myHeight; j++) {
                myTileMap.put(new Coordinate(i, j), new Tile());
            }
        }
    }
    
    /**
     * Creates default objects and units for grid
     */
    private void testInitObjects () {
        myObjects.put(new Coordinate(3, 5),  new GameObject());
        GameObject link = new GameUnit();
        myObjects.put(new Coordinate(4, 5), link);
        findMovementRange(new Coordinate(4, 5), ((GameUnit) link).getStats().getStatValue("movement"), link);
    }
    
    /**
     * Initiates the moving process for a gameUnit
     * @param gameUnit - GameUnit that is being moved
     */
    public void move (GameUnit gameUnit) {
        
    }
    
    /**
     * Sets the tiles active that the GameObject can move to
     * @param coordinate - Coodinate of the current position of the GameObject
     * @param range - int of range that the GameObject can move
     * @param gameObject - 
     */
    private void findMovementRange (Coordinate coordinate, int range, GameObject gameObject) {
        int[] rdelta = {-1, 0, 0, 1};
        int[] cdelta = {0, -1, 1, 0};
        
        for (int i=0; i < rdelta.length; i++) {
            int newX = coordinate.getX() + cdelta[i];
            int newY = coordinate.getY() + rdelta[i];
            if (onGrid(newX, newY)) {
                Tile currTile = getTile(newX, newY);
                int newRange = range - currTile.getMoveCost();
                GameObject currObject = getObject(newX, newY);
                if (currObject != null && currObject.isPassable(gameObject)) {
                    findMovementRange(new Coordinate(newX, newY), newRange, gameObject);
                }
                else if (newRange >= 0 ) {
                    currTile.setActive(true);
                    findMovementRange(new Coordinate(newX, newY), newRange, gameObject);
                }
            }
        }
        
    }
    
    /**
     * Checks if the input coordinate is on the grid
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     * @return - boolean of if the coordinate is valid
     */
    private boolean onGrid (int x, int y) {
        return (0 <= x && x < myWidth && 0 <= y && y < myHeight);
    }
    
    /**
     * Checks if a coordinate is a valid move (the tile is active)
     * @param x - int of x coordinate
     * @param y - int of y coordinate
     * @return - boolean of if the coordinate is valid
     */
    public boolean canMove (int x, int y) {
        return getTile(x, y).isActive();
    }

    public GameObject getObject (int x, int y) {
        // TODO: Generic method?
        for (Coordinate coord : myObjects.keySet()) {
            if (coord.getX() == x && coord.getY() == y) { return myObjects.get(coord); }
        }

        return null;
    }

    public void placeObject (GameObject newObject, int x, int y) {
        // TODO: Generic method?
        for (Coordinate coord : myObjects.keySet()) {
            if (coord.getX() == x && coord.getY() == y) {
                myObjects.put(coord, newObject);
                return;
            }
        }

        myObjects.put(new Coordinate(x, y), newObject);
    }

    public Tile getTile (int x, int y) {
        // TODO: Generic method?
        for (Coordinate coord : myTileMap.keySet()) {
            if (coord.getX() == x && coord.getY() == y) { return myTileMap.get(coord); }
        }

        return null;
    }

    public void placeTile (Tile newTile, int x, int y) {
        // TODO: Generic method?
        for (Coordinate coord : myTileMap.keySet()) {
            if (coord.getX() == x && coord.getY() == y) {
                myTileMap.put(coord, newTile);
                return;
            }
        }

        myTileMap.put(new Coordinate(x, y), newTile);
    }

    @Override
    public void draw (Graphics g, int x, int y, int width, int height) {
        int tileWidth = width / myWidth;
        int tileHeight = height / myHeight;

        for (Entry<Coordinate, Tile> entry : myTileMap.entrySet()) {
            Tile tile = entry.getValue();
            x = entry.getKey().getX();
            y = entry.getKey().getY();
            tile.draw(g, x * tileWidth, y * tileHeight, tileWidth, tileHeight);
        }
        
        // TODO: dupe for tile and object. generic
        for (Entry<Coordinate, GameObject> entry : myObjects.entrySet()) {
            GameObject gameObject = entry.getValue();
            x = entry.getKey().getX();
            y = entry.getKey().getY();

            gameObject.draw(g, x * tileWidth, y * tileHeight, tileWidth, tileHeight);
        }

    }

    public Map<GameUnit, Coordinate> getGameUnits () {
        Map<GameUnit, Coordinate> gameUnitMap = new HashMap<GameUnit, Coordinate>();

        for (Coordinate coord : myObjects.keySet()) {
            if (myObjects.get(coord) instanceof GameUnit) {
                gameUnitMap.put((GameUnit) myObjects.get(coord), coord);
            }
        }

        return gameUnitMap;
    }
}
