package grid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Grid {
    private int myCol;
    private int myRow;
    private Map<Coordinate, Tile> myTileMap;
    private Map<Coordinate, GameObject> myObjects;
    private Map<Integer, List<GameObject>> myPassStatuses;

    public Grid (int col, int row) {
        myCol = col;
        myRow = row;
        myTileMap = new HashMap<Coordinate, Tile>();
        myObjects = new HashMap<Coordinate, GameObject>();
        myPassStatuses = new HashMap<Integer, List<GameObject>>();

        initGrid();
    }

    private void initGrid () {
        initTiles();
    }

    private void initTiles () {
        for (int i = 0; i < myCol; i++) {
            for (int j = 0; j < myRow; j++) {
                myTileMap.put(new Coordinate(i, j), new Tile());
            }
        }
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
}
